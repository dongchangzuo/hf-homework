package org.example.fraud.service;

import org.example.fraud.mapper.TransactionMapper;
import org.example.fraud.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionMapper transactionMapper;

    @Mock
    private RedisTemplate<String, Long> redisTemplate;

    @Mock
    private ListOperations<String, Long> listOperations;

    @InjectMocks
    private TransactionService transactionService;

    private Transaction transaction;

    @BeforeEach
    void setUp() {
        // 创建一个 Transaction 对象用于测试
        transaction = new Transaction();
        transaction.setTransactionId("123");
        transaction.setAccountId("12345");
        transaction.setAmount(1000.0);
        transaction.setTransactionTime(System.currentTimeMillis());

        // 模拟 redisTemplate 和 listOperations 的行为
        lenient().when(redisTemplate.opsForList()).thenReturn(listOperations);


    }

    @Test
    void testSaveTransaction() {
        transactionService.saveTransaction(transaction);

        verify(transactionMapper, times(1)).insertTransaction(transaction);
    }

    @Test
    void testCountTransactionsInTimeRange_withValidData() {
        when(listOperations.rightPush(anyString(), anyLong())).thenReturn(6000L);
        when(listOperations.range(anyString(), anyLong(), anyLong())).thenReturn(Arrays.asList(1000L, 2000L, 3000L, 4000L, 5000L, 6000L));
        long startTime = 2000L;
        long endTime = 6000L;

        long result = transactionService.countTransactionsInTimeRange(transaction.getAccountId(), startTime, endTime);

        assertEquals(5, result);

        verify(listOperations, times(1)).rightPush("transaction_queue:" + transaction.getAccountId(), endTime);

        verify(listOperations, times(1)).range("transaction_queue:" + transaction.getAccountId(), 0, -1);
        verify(listOperations, times(1)).trim("transaction_queue:" + transaction.getAccountId(), 1, -1);
    }


    @Test
    void testCountTransactionsInTimeRange_withEmptyList() {
        when(listOperations.range(anyString(), anyLong(), anyLong())).thenReturn(Collections.emptyList());
        when(listOperations.rightPush(anyString(), anyLong())).thenReturn(1L);

        long startTime = System.currentTimeMillis() - 10000L;
        long endTime = System.currentTimeMillis();

        // 调用业务方法
        long result = transactionService.countTransactionsInTimeRange(transaction.getAccountId(), startTime, endTime);

        // 断言返回的交易次数是 0，因为列表是空的
        assertEquals(0, result);

        // 验证 rightPush 被调用一次，推送新的时间戳
        verify(listOperations, times(1)).rightPush("transaction_queue:" + transaction.getAccountId(), endTime);
    }

    @Test
    void testCountTransactionsInTimeRange_withnullList() {
        when(listOperations.range(anyString(), anyLong(), anyLong())).thenReturn(null);

        long startTime = System.currentTimeMillis() - 10000L;
        long endTime = System.currentTimeMillis();

        // 调用业务方法
        long result = transactionService.countTransactionsInTimeRange(transaction.getAccountId(), startTime, endTime);

        // 断言返回的交易次数是 0，因为列表是空的
        assertEquals(0, result);

        // 验证 rightPush 被调用一次，推送新的时间戳
        verify(listOperations, times(1)).rightPush("transaction_queue:" + transaction.getAccountId(), endTime);
    }
}
