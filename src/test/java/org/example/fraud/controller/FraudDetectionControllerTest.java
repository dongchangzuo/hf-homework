package org.example.fraud.controller;

import org.example.fraud.model.Transaction;
import org.example.fraud.service.FraudDetectionService;
import org.example.fraud.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FraudDetectionController.class)
class FraudDetectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FraudDetectionService fraudDetectionService;

    @MockBean
    private TransactionService transactionService;

    @Test
    void testDetectFraud_FraudulentTransaction() throws Exception {
        Transaction mockTransaction = new Transaction();
        mockTransaction.setTransactionId("tx123");
        mockTransaction.setAmount(5000.0);
        mockTransaction.setAccountId("acc123");
        mockTransaction.setTransactionTime(1672956423671L);

        Mockito.when(fraudDetectionService.detectFraud(any(Transaction.class))).thenReturn(true);

        mockMvc.perform(post("/fraud-detection").contentType(MediaType.APPLICATION_JSON).content("{\"transactionId\":\"tx123\", \"accountId\":\"acc123\", \"amount\":5000.0,  \"transactionTime\":1672956423671}")).andExpect(status().isOk()).andExpect(content().string("true"));

        Mockito.verify(transactionService).saveTransaction(any(Transaction.class));
    }

    @Test
    void testDetectFraud_NonFraudulentTransaction() throws Exception {
        Transaction mockTransaction = new Transaction();
        mockTransaction.setTransactionId("tx124");
        mockTransaction.setAmount(100.0);
        mockTransaction.setAccountId("acc124");
        mockTransaction.setTransactionTime(1672956423671L);

        Mockito.when(fraudDetectionService.detectFraud(any(Transaction.class))).thenReturn(false);

        mockMvc.perform(post("/fraud-detection").contentType(MediaType.APPLICATION_JSON).content("{\"transactionId\":\"tx124\", \"amount\":100.0, \"accountId\":\"acc124\"， \"transactionTime\":1672956423671}")).andExpect(status().isOk()).andExpect(content().string("false"));

        Mockito.verify(transactionService).saveTransaction(any(Transaction.class));
    }
}
