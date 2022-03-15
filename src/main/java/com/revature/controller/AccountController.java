package com.revature.controller;


import com.revature.model.Account;
import com.revature.model.Client;
import com.revature.service.AccountService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.util.List;

public class AccountController implements Controller{

    private AccountService accountService;

    public AccountController(){
        this.accountService = new AccountService();
    }

    private Handler getAccountsById = (ctx) -> {
        String id = ctx.pathParam("clientId");
        String maxValue = ctx.queryParam("amountLessThan");
        String minValue = ctx.queryParam("amountGreaterThan");
        if(maxValue != null & minValue != null){
            List<Account> accounts = accountService.getAccountsBetweenById(minValue, maxValue,id);
            System.out.println("between");
            ctx.json(accounts);
        }
        else if (maxValue != null) {
            List<Account> accounts = accountService.getAccountsLessThanById(maxValue,id);
            System.out.println("less than");
            ctx.json(accounts);
        }
        else if (minValue != null) {
            List<Account> accounts = accountService.getAccountsGreaterThanById(minValue,id);
            System.out.println("ggreater than");
            ctx.json(accounts);
        }
        else{
            List<Account> accounts = accountService.getAccountsById(id);
            System.out.println("default");
            ctx.json(accounts);
        }




    };

    private Handler addAccount = (ctx) -> {
        Account accountToAdd = ctx.bodyAsClass(Account.class);
        String clientId = ctx.pathParam("clientId");
        Account account = accountService.addAccount(accountToAdd);
        ctx.json(account);
    };

    private Handler deleteAccountById = (ctx) -> {
        String accountId = ctx.pathParam("accountId");
        String clientId = ctx.pathParam("clientId");

        boolean account = accountService.deleteAccountById(clientId, accountId);

        ctx.json(account);
    };


    private Handler getAccountByIds = (ctx) -> {
        String clientId = ctx.pathParam("clientId");
        String accountId = ctx.pathParam("accountId");
        Account account = accountService.getAccountByIds(clientId, accountId);

        ctx.json(account);


    };


    private Handler editAccount = (ctx) -> {
        Account accountToEdit = ctx.bodyAsClass(Account.class);
        String clientId = ctx.pathParam("clientId");
        String accountId = ctx.pathParam("accountId");
        Account editedAccount = accountService.editAccount(clientId,accountId,accountToEdit);
        ctx.status(200);
        ctx.json(editedAccount);
    };


    @Override
    public void mapEndpoints(Javalin app) {
        app.get("/clients/{clientId}/accounts", getAccountsById);
        app.post("/clients/{clientId}/accounts", addAccount);
        app.delete("/clients/{clientId}/accounts/{accountId}", deleteAccountById);
        app.put("/clients/{clientId}/accounts/{accountId}", editAccount);
        app.get("/clients/{clientId}/accounts/{accountId}", getAccountByIds);
    }
}
