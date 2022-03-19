package com.revature.service;

import com.revature.dao.AccountDao;
import com.revature.dao.ClientDao;
import com.revature.exception.ClientNotFoundException;
import com.revature.model.Account;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountServiceTest {

    @Mock
    ClientDao mockClientDao;

    @Mock
    AccountDao mockAccountDao;

    @InjectMocks
    AccountService accountService;



    private AutoCloseable closeable;
    @BeforeAll
    public void set_up() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterAll
    public void close_mocks() throws Exception {
        closeable.close();
    }





    @Test
    void getAccountByIds() throws ClientNotFoundException, SQLException {
        when(mockAccountDao.getAccountByIds(eq(1), eq(1))).thenReturn(
                new Account(1, "Checking", 1, 1)
        );
        Account actualAccount = accountService.getAccountByIds("1", "1");

        Account expectedAccount = new Account(1, "Checking", 1, 1);

        Assertions.assertEquals(expectedAccount, actualAccount);
    }

    @Test
    void deleteAccountById() throws SQLException{

        when(mockAccountDao.deleteAccountById(eq(1),eq(1))).thenReturn(true);
    }

    @Test
    void editAccount() throws SQLException {
        Account mock = new Account(1, "Checking", 200, 1);
        Account expected = new Account(1, "Checking", 20, 1);
        when(mockAccountDao.updateAccount(mock)).thenReturn(new Account());
    }


    @Test
    void addAccount() throws SQLException{

        when(mockAccountDao.addAccount(any(Account.class))).thenReturn(new Account(1,"Checking", 1, 1));

        Account expected = new Account(1, "Checking",1,1);
        Account actual = accountService.addAccount(new Account(1,"Checking",1, 1));

        Assertions.assertEquals(expected, actual);

    }

}

