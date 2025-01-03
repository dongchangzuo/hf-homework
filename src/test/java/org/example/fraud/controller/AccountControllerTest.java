package org.example.fraud.controller;

import org.example.fraud.model.Account;
import org.example.fraud.model.SuspiciousStatus;
import org.example.fraud.service.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    void testGetAccountById() throws Exception {
        Account mockAccount = new Account();
        mockAccount.setId("123");
        mockAccount.setSuspiciousStatus(String.valueOf(SuspiciousStatus.SUSPICIOUS));
        Mockito.when(accountService.getAccountById(anyString())).thenReturn(mockAccount);
        mockMvc.perform(get("/account/123")).andExpect(status().isOk()).andExpect(jsonPath("$.id").value("123")).andExpect(jsonPath("$.suspiciousStatus").value(String.valueOf(SuspiciousStatus.SUSPICIOUS)));
    }

    @Test
    void testGetAccountById_NotFound() throws Exception {
        // Arrange
        Mockito.when(accountService.getAccountById(anyString())).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/account/123")).andExpect(status().isOk()).andExpect(content().string(""));
    }
}
