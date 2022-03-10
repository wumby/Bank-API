package com.revature.service;

import com.revature.dao.AccountDao;
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

    public Account addAccount(Account a) throws SQLException{
        validateAccountInformation(a);

        Account accountToAdd = accountDao.addAccount(a);

        return accountToAdd;
    }


    public List<Account> getAccountsById(String id) throws SQLException, ClientNotFoundException {
        try{
            int clientId = Integer.parseInt(id);

            //Check if client exists
            if (this.accountDao.getAccountsById(clientId).isEmpty()) {
                throw new ClientNotFoundException("Client with id " + clientId + " was not found");
            }

            return this.accountDao.getAccountsById(clientId);
        }catch (NumberFormatException e){
            throw new IllegalArgumentException("Id provided for client must be a valid int");
        }


    }

    public void validateAccountInformation(Account a) {
        a.setAccount_name(a.getAccount_name().trim());
        a.setBalance(a.getBalance());

        if (!a.getAccount_name().equals("Checking") || !a.getAccount_name().equals("Savings")) {
            throw new IllegalArgumentException("Account name must be Checking or Savings. Account name input was " + a.getAccount_name());
        }

        if (a.getBalance()<0) {
            throw new IllegalArgumentException("Balance must be above 0. Balance input was " + a.getBalance());
        }


    }




}
