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

    private Handler getAccountById = (ctx) -> {
        List<Account> accounts = accountService.getAllAccountsById();

        ctx.json(accounts);

        ctx.json(accounts);
    };


    @Override
    public void mapEndpoints(Javalin app) {
        app.get("/clients/{clientId}/accounts", getAccountById);

    }
}
