package com.revature.service;

import com.revature.dao.ClientDao;
import com.revature.exception.ClientNotFoundException;
import com.revature.model.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientServiceTest {


    @Test
    public void testGetAllClients() throws SQLException {
        // Arrange
        ClientDao mockedObject = mock(ClientDao.class);

        List<Client> fakeClients = new ArrayList<>();
        fakeClients.add(new Client(1, "Bill", "Smith", 20));
        fakeClients.add(new Client(2, "Test", "Test123", 15));
        fakeClients.add(new Client(3, "John", "Doe", 30));

        when(mockedObject.getAllClients()).thenReturn(fakeClients);

        ClientService clientService = new ClientService(mockedObject);

        List<Client> actual = clientService.getAllClients();

        List<Client> expected = new ArrayList<>(fakeClients);
        Assertions.assertEquals(expected, actual);
    }


    @Test
    public void testGetClientById_positiveTest() throws SQLException, ClientNotFoundException {

        ClientDao mockDao = mock(ClientDao.class);


        when(mockDao.getClientById(eq(10))).thenReturn(
                new Client(10, "Test", "Testy", 20)
        );

        ClientService studentService = new ClientService(mockDao);

        // Act
        Client actual = studentService.getClientById("10");

        // Assert
        Client expected = new Client(10, "Test", "Testy", 20);
        Assertions.assertEquals(expected, actual);
    }


    @Test
    public void test_getStudentById_studentDoesNotExist() throws SQLException, ClientNotFoundException {
        // Arrange
        ClientDao mockDao = mock(ClientDao.class);

        ClientService clientService = new ClientService(mockDao);

        // Act + Assert

        // The test case will pass if we encounter this exception
        // (StudentNotFoundException)
        Assertions.assertThrows(ClientNotFoundException.class, () -> {
            clientService.getClientById("10");
        });
    }


    @Test
    public void test_getClientById_invalidId() throws SQLException, ClientNotFoundException {
        // Arrange
        ClientDao mockDao = mock(ClientDao.class);

        ClientService clientService = new ClientService(mockDao);

        // Act
        try {
            clientService.getClientById("abc");

            fail();
        } catch(IllegalArgumentException e) {
            String expectedMessage = "Id provided for client must be a valid int";
            String actualMessage = e.getMessage();

            Assertions.assertEquals(expectedMessage, actualMessage);
        }
    }


    @Test
    public void test_getClientById_sqlExceptionFromDao() throws SQLException {
        // Arrange
        ClientDao mockDao = mock(ClientDao.class);

        when(mockDao.getClientById(anyInt())).thenThrow(SQLException.class);

        ClientService clientService = new ClientService(mockDao);

        // Act + Assert
        Assertions.assertThrows(SQLException.class, () -> {
            clientService.getClientById("5");
        });
    }


    @Test
    public void testDeleteClientById() throws SQLException{
        ClientDao mockDao = mock(ClientDao.class);


        when(mockDao.deleteClientById(eq(5))).thenReturn(true
        );
    }

    }

