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


    public boolean deleteAccountById(String account_id, String client_id) throws SQLException, ClientNotFoundException {

        try {

            int accountId = Integer.parseInt(account_id);
            int clientId = Integer.parseInt(client_id);// This could throw an unchecked exception
            // known as NumberFormatException
            // Important to take note of this, because any unhandled exceptions will result
            // in a 500 Internal Server Error (which we should try to avoid)

            boolean a = accountDao.deleteAccountById(accountId, clientId); // this could return null

            return a;

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Id provided for client and account must both be a valid int");
        }
    }


    public void validateAccountInformation(Account a) {
        a.setAccount_name(a.getAccount_name().trim());
        a.setBalance(a.getBalance());

        if (a.getAccount_name().equals("checking")) {
            a.setAccount_name("Checking");
        }
        else if (a.getAccount_name().equals("savings")){
            a.setAccount_name("Savings");
        }
        else{
            throw new IllegalArgumentException("Account name must be Checking or Savings. Account name input was " + a.getAccount_name());
        }

        if (a.getBalance()<0) {
            throw new IllegalArgumentException("Balance must be above 0. Balance input was " + a.getBalance());
        }


    }




}
