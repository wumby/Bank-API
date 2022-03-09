package com.revature.dao;

import com.revature.model.Account;
import com.revature.model.Client;
import com.revature.utility.ConnectionUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {




    public List<Account> getAllAccountsById() throws SQLException {
        List<Account> accounts = new ArrayList<>();

        try (Connection con = ConnectionUtility.getConnection()) { // try-with-resources
            String sql = "SELECT * FROM accounts Where client_id = 2";
            PreparedStatement pstmt = con.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery(); // executeQuery() is used with SELECT

            while (rs.next()) {
                String account_name = rs.getString("account_name");
                int client_id = rs.getInt("client_id");
                double balance = rs.getDouble("balance");

                accounts.add(new Account(account_name, balance, client_id));
            }

        }

        return accounts;
    }
}
