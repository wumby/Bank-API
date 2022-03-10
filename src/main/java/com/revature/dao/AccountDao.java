package com.revature.dao;

import com.revature.model.Account;
import com.revature.model.Client;
import com.revature.utility.ConnectionUtility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {

    //Add a new account for a client with an id of x
    public Account addAccount(Account account) throws SQLException {
        try (Connection con = ConnectionUtility.getConnection()) {
            String sql = "INSERT INTO accounts (account_name, balance, client_id) VALUES (?, ?, ?)";

            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, account.getAccount_name());
            pstmt.setDouble(2,account.getBalance());
            pstmt.setInt(3, account.getClient_id());

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();

            rs.next();
            int generatedId = rs.getInt(1);
            return new Account(generatedId, account.getAccount_name(), account.getBalance(),account.getClient_id());


        }


    }

    //Get all accounts for client with id of X (if client exists)
    public List<Account> getAccountsById(int clientId) throws SQLException {
        List<Account> accounts = new ArrayList<>();
        //Call connection method
        try (Connection con = ConnectionUtility.getConnection()) { // try-with-resources
            //Prepare our prepared SQL statement using the connection method
            String sql = "SELECT *" +" FROM accounts" + " Where client_id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,clientId);
            // TODO 11: If any parameters need to be set, set the parameters (?)

            // TODO 12: Execute the query and retrieve a ResultSet object
            ResultSet rs = pstmt.executeQuery(); // executeQuery() is used with SELECT

            // TODO 13: Iterate over record(s) using the ResultSet's next() method
            while (rs.next()) {
                // TODO 14: Grab the information from the record
                int id = rs.getInt("id");
                String account_name = rs.getString("account_name");
                double balance = rs.getDouble("balance");
                int client_id = rs.getInt("client_id");

                accounts.add(new Account(id, account_name, balance, client_id));
            }

        }

        return accounts;
    }


}
