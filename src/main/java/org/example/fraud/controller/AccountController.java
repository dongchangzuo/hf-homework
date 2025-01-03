package org.example.fraud.controller;

import org.example.fraud.model.Account;
import org.example.fraud.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/account/{id}")
    public Account getAccountById(@PathVariable String id) {
        return accountService.getAccountById(id);
    }
}
