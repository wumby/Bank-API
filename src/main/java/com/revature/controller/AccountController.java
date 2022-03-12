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
        List<Account> accounts = accountService.getAccountsById(id);


        ctx.json(accounts);
    };

    private Handler addAccount = (ctx) -> {
        Account accountToAdd = ctx.bodyAsClass(Account.class);
        String clientId = ctx.pathParam("clientId");
        Account account = accountService.addAccount(clientId, accountToAdd);
        ctx.json(account);
    };

    private Handler deleteAccountById = (ctx) -> {
        String accountId = ctx.pathParam("accountId");
        String clientId = ctx.pathParam("clientId");

        boolean account = accountService.deleteAccountById(accountId, clientId);

        ctx.json(account);
    };


    private Handler getAccountByIds = (ctx) -> {
        String clientId = ctx.pathParam("clientId");
        String accountId = ctx.pathParam("accountId");
        Account account = accountService.getAccountByIds(accountId, clientId);

        ctx.json(account);


    };
    private Handler editAccount = (ctx) -> {
        Account accountToEdit = ctx.bodyAsClass(Account.class);

        //Account editedAccount = accountService.editAccount((ctx.pathParam("clientId"),accountToEdit);
        //ctx.status(200);
        //ctx.json(editedAccount);
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
