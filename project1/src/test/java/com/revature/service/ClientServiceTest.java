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

    // Positive (happy path)
    @Test
    public void testGetAllClients() throws SQLException {
        // Arrange
        ClientDao mockedObject = mock(ClientDao.class);

        List<Client> fakeClients = new ArrayList<>();
        fakeClients.add(new Client(1, "Bill", "Smith", 20));
        fakeClients.add(new Client(2, "Test", "Test123", 15));
        fakeClients.add(new Client(3, "John", "Doe", 30));

        // Whenever the code in the Service layer calls the getAllStudents() method
        // for the dao layer, then return the list of students
        // we have specified above
        when(mockedObject.getAllClients()).thenReturn(fakeClients);

        ClientService clientService = new ClientService(mockedObject);

        // Act
        List<Client> actual = clientService.getAllClients();

        // Assert
        List<Client> expected = new ArrayList<>(fakeClients);
        Assertions.assertEquals(expected, actual);
    }

    // Positive test is also known as the
    // "Happy path"
    // The "user" is utilizing this method correctly
    @Test
    public void testGetStudentById_positiveTest() throws SQLException, ClientNotFoundException {
        // Arrange
        ClientDao mockDao = mock(ClientDao.class);

        // Mocking the return value for id 10
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

    // Negative test
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

    // Negative test
    @Test
    public void test_getStudentById_invalidId() throws SQLException, ClientNotFoundException {
        // Arrange
        ClientDao mockDao = mock(ClientDao.class);

        ClientService clientService = new ClientService(mockDao);

        // Act
        try {
            clientService.getClientById("abc");

            fail(); // We only reach this line if no exception is caught
        } catch(IllegalArgumentException e) {
            String expectedMessage = "Id provided for student must be a valid int";
            String actualMessage = e.getMessage();

            Assertions.assertEquals(expectedMessage, actualMessage);
        }
    }

    // Negative
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
}
