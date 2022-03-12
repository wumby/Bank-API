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

    public Account addAccount(String client_id, Account a) throws SQLException{
        int clientId = Integer.parseInt(client_id);
        a.setClient_id(clientId);
        validateAccountInformation(a);


        Account accountToAdd = accountDao.addAccount(a);

        return accountToAdd;
    }


    public Account editAccount(String id, String client_id, Account a) {
        try {
            int accountId = Integer.parseInt(id);
            int clientId = Integer.parseInt(client_id);
            if (accountDao.getAccountsById(clientId) == null) {
                throw new ClientNotFoundException("User is trying to edit an account that is not linked to a client ");
            }

            validateAccountInformation(a);

            a.setId(accountId);
            Account editedAccount = accountDao.updateAccount(a);

            return editedAccount;
        } catch(NumberFormatException | ClientNotFoundException | SQLException e) {
            throw new IllegalArgumentException("Id provided for client must be a valid int");
        }
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


    public Account getAccountByIds(String account_id, String client_id) throws SQLException, ClientNotFoundException {
        try{
            int clientId = Integer.parseInt(client_id);
            int accountId = Integer.parseInt(account_id);

            Account a = accountDao.getAccountByIds(accountId, clientId);
        if (a == null) {
            throw new ClientNotFoundException("Client with id " + clientId + " was not found associated with that account number");
        }

        return a;
        } catch (NumberFormatException e) {
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


    private void validateAccountInformation(Account a) {
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
