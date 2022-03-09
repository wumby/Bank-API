package com.revature.service;

import com.revature.dao.AccountDao;
import com.revature.dao.ClientDao;
import com.revature.exception.ClientNotFoundException;
import com.revature.model.Account;
import com.revature.model.Client;

import java.sql.SQLException;
import java.util.List;

public class AccountService {

    private AccountDao accountDao;

    public AccountService(){this.accountDao = new AccountDao();}

    public AccountService(AccountDao mockDao) {
        this.accountDao = mockDao;
    }

    public List<Account> getAllAccountsById() throws SQLException {
        return this.accountDao.getAllAccountsById();
    }




}
