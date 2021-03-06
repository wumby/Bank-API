package com.revature.dao;

import com.revature.model.Account;
import com.revature.model.Client;
import com.revature.utility.ConnectionUtility;

import javax.swing.plaf.nimbus.State;
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
            rs.next();

            return new Account(generatedId, account.getAccount_name(), account.getBalance(),account.getClient_id());


        }


    }


    public List<Account> getAccountsLessThanById(int max_value, int clientId) throws SQLException {
        List<Account> accounts = new ArrayList<>();
        //Call connection method
        try (Connection con = ConnectionUtility.getConnection()) { // try-with-resources
            //Prepare our prepared SQL statement using the connection method
            String sql = "SELECT *" +" FROM accounts" + " Where client_id = ? AND balance < ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,clientId);
            pstmt.setInt(2,max_value);

            ResultSet rs = pstmt.executeQuery(); // executeQuery() is used with SELECT

            while (rs.next()) {
                int id = rs.getInt("id");
                String account_name = rs.getString("account_name");
                double balance = rs.getDouble("balance");
                int client_id = rs.getInt("client_id");

                accounts.add(new Account(id, account_name, balance, client_id));
            }

        }

        return accounts;
    }


    public List<Account> getAccountsGreaterThanById(int min_value, int clientId) throws SQLException {
        List<Account> accounts = new ArrayList<>();
        //Call connection method
        try (Connection con = ConnectionUtility.getConnection()) {
            //Prepare our prepared SQL statement using the connection method
            String sql = "SELECT *" +" FROM accounts" + " Where client_id = ? AND balance > ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,clientId);
            pstmt.setDouble(2,min_value);

            ResultSet rs = pstmt.executeQuery(); // executeQuery() is used with SELECT

            while (rs.next()) {
                int id = rs.getInt("id");
                String account_name = rs.getString("account_name");
                double balance = rs.getDouble("balance");
                int client_id = rs.getInt("client_id");

                accounts.add(new Account(id, account_name, balance, client_id));
            }

        }

        return accounts;
    }


    public List<Account> getAccountsBetweenById(int min_value, int max_value, int clientId) throws SQLException {
        List<Account> accounts = new ArrayList<>();
        //Call connection method
        try (Connection con = ConnectionUtility.getConnection()) { // try-with-resources
            //Prepare our prepared SQL statement using the connection method
            String sql = "SELECT *" +" FROM accounts" + " Where client_id = ? AND balance BETWEEN ? AND ? ";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,clientId);
            pstmt.setInt(2,min_value);
            pstmt.setInt(3,max_value);

            ResultSet rs = pstmt.executeQuery(); // executeQuery() is used with SELECT

            while (rs.next()) {
                int id = rs.getInt("id");
                String account_name = rs.getString("account_name");
                double balance = rs.getDouble("balance");
                int client_id = rs.getInt("client_id");

                accounts.add(new Account(id, account_name, balance, client_id));
            }

        }

        return accounts;
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

            ResultSet rs = pstmt.executeQuery(); // executeQuery() is used with SELECT

            while (rs.next()) {
                int id = rs.getInt("id");
                String account_name = rs.getString("account_name");
                double balance = rs.getDouble("balance");
                int client_id = rs.getInt("client_id");

                accounts.add(new Account(id, account_name, balance, client_id));
            }

        }

        return accounts;
    }


    public Account getAccountByIds(int client_id, int account_id){
        try (Connection con = ConnectionUtility.getConnection()) {
            String sql = "SELECT * FROM accounts WHERE (id = ? AND client_id = ?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, account_id);
            pstmt.setInt(2, client_id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String account_name = rs.getString("account_name");
                double balance = rs.getDouble("balance");
                int clientId = rs.getInt("client_id");

                return new Account(account_id, account_name, balance, clientId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    
    public Account updateAccount(Account account) throws SQLException {
        try (Connection con = ConnectionUtility.getConnection()) {
            String sql = "UPDATE accounts " +
                    "SET account_name = ?, " +
                    "balance = ? " +
                    "WHERE (client_id = ? AND id = ?)";

            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setString(1, account.getAccount_name());
            pstmt.setDouble(2, account.getBalance());
            pstmt.setInt(3, account.getClient_id());
            pstmt.setInt(4, account.getId());

            pstmt.executeUpdate();
        }

        return account;
    }


    public boolean deleteAccountById(int client_id, int account_id) throws SQLException{
        try (Connection con = ConnectionUtility.getConnection()) {
        String sql = "DELETE FROM accounts WHERE (id = ? AND client_id = ?)";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, account_id);
        pstmt.setInt(2, client_id);

        int numberOfRecordsDeleted = pstmt.executeUpdate(); // executeUpdate() is used with INSERT, UPDATE, DELETE

        if (numberOfRecordsDeleted == 1) {
            return true;
        }
    } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
}
}

