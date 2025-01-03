package org.example.fraud.mapper;

import org.apache.ibatis.annotations.Select;
import org.example.fraud.model.Transaction;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransactionMapper {

    @Insert("INSERT INTO transactions(transaction_id,account_id, amount, transaction_time) VALUES(#{transactionId}, #{accountId}, #{amount}, #{transactionTime})")
    void insertTransaction(Transaction transaction);

    @Select("SELECT COUNT(*) FROM transactions " + "WHERE account_id = #{accountId} " + "AND transaction_time BETWEEN #{startTime} AND #{endTime}")
    int countTransactionsInTimeRange(String accountId, long startTime, long endTime);
}
