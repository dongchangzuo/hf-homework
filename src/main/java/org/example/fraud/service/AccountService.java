package org.example.fraud.service;

import org.example.fraud.mapper.AccountMapper;
import org.example.fraud.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountMapper accountMapper;

    @Autowired
    public AccountService(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    @Cacheable(value = "accounts", key = "#id")
    public Account getAccountById(String id) {
        return accountMapper.findById(id);
    }

}