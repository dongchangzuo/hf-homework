package org.example.fraud.service;

import org.example.fraud.mapper.TransactionMapper;
import org.example.fraud.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);
    private final TransactionMapper transactionMapper;
    private final RedisTemplate<String, Long> redisTemplate;


    @Autowired
    public TransactionService(TransactionMapper transactionMapper, RedisTemplate<String, Long> redisTemplate) {
        this.transactionMapper = transactionMapper;
        this.redisTemplate = redisTemplate;
    }

    @Transactional
    public void saveTransaction(Transaction transaction) {
        transactionMapper.insertTransaction(transaction);
    }

    public long countTransactionsInTimeRange(String accountId, long startTime, long endTime) {
        String queueKey = "transaction_queue:" + accountId;
        redisTemplate.opsForList().rightPush(queueKey, endTime);
        List<Long> transactionTimestamps = redisTemplate.opsForList().range(queueKey, 0, -1);
        if (transactionTimestamps == null || transactionTimestamps.isEmpty()) {
            return 0;
        }
        int ans = transactionTimestamps.size();
        int left = 0;
        for (Long timestamp : transactionTimestamps) {
            if (timestamp < startTime) {
                left++;
            } else {
                break;
            }
        }
        if (left > 0) {
            redisTemplate.opsForList().trim(queueKey, left, -1);
        }
        return ans - left;

    }
}
