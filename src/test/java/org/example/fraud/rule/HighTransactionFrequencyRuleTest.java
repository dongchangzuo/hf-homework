package org.example.fraud.rule;

import org.example.fraud.model.Transaction;
import org.example.fraud.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class HighTransactionFrequencyRuleTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private HighTransactionFrequencyRule highTransactionFrequencyRule;

    private Transaction transaction;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);

        // 使用反射设置 @Value 注解的字段值
        Field timeWindowMinutesField = HighTransactionFrequencyRule.class.getDeclaredField("timeWindowMinutes");
        timeWindowMinutesField.setAccessible(true);
        timeWindowMinutesField.set(highTransactionFrequencyRule, 5); // 设置 timeWindowMinutes 为 5

        Field maxTransactionsField = HighTransactionFrequencyRule.class.getDeclaredField("maxTransactions");
        maxTransactionsField.setAccessible(true);
        maxTransactionsField.set(highTransactionFrequencyRule, 10); // 设置 maxTransactions 为 10

        // 设置一个测试交易对象
        transaction = new Transaction();
        transaction.setTransactionTime(System.currentTimeMillis());
        transaction.setAccountId("testAccount");
    }

    @Test
    void testApply_WhenTransactionCountExceedsMaxTransactions() {
        // 设置模拟行为：在指定时间窗口内，交易次数为 12
        when(transactionService.countTransactionsInTimeRange(anyString(), anyLong(), anyLong())).thenReturn(12L);

        // 调用 apply 方法
        boolean result = highTransactionFrequencyRule.apply(transaction);

        // 验证结果
        assertTrue(result, "The rule should trigger when transaction count exceeds maxTransactions");
    }

    @Test
    void testApply_WhenTransactionCountIsBelowMaxTransactions() {
        // 设置模拟行为：在指定时间窗口内，交易次数为 5
        when(transactionService.countTransactionsInTimeRange(anyString(), anyLong(), anyLong())).thenReturn(5L);

        // 调用 apply 方法
        boolean result = highTransactionFrequencyRule.apply(transaction);

        // 验证结果
        assertFalse(result, "The rule should not trigger when transaction count is below maxTransactions");
    }

    @Test
    void testGetRuleName() {
        // 验证规则名称是否正确
        String ruleName = highTransactionFrequencyRule.getRuleName();
        assertEquals("HighTransactionFrequencyRule", ruleName, "Rule name should be 'HighTransactionFrequencyRule'");
    }
}
