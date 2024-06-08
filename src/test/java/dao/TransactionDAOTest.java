package dao;

import model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TransactionDAOTest {
    private TransactionDAO transactionDAO;
    private Connection connection;
    private PreparedStatement preparedStatement;

    @BeforeEach
    void setUp() throws Exception {
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        transactionDAO = new TransactionDAO(); // Assuming this is your DAO implementation

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        doNothing().when(connection).setAutoCommit(false);
    }

    @Test
    void testInsertTransaction() throws Exception {
        Transaction transaction = new Transaction("TX1001", "USD", "EUR", Timestamp.valueOf("2023-06-01 12:30:00"), new BigDecimal("1000.00"));

        transactionDAO.insertTransaction(transaction); // Call the method to test

        verify(preparedStatement, times(1)).executeUpdate(); // Verify if insert was attempted
        verify(connection, times(1)).commit(); // Verify if commit was called
    }

    @Test
    void testInsertTransactionError() throws Exception {
        doThrow(new SQLException("Error executing insert")).when(preparedStatement).executeUpdate();

        Exception exception = assertThrows(Exception.class, () -> transactionDAO.insertTransaction(new Transaction("TX1001", "USD", "EUR", Timestamp.valueOf("2023-06-01 12:30:00"), new BigDecimal("1000.00"))));

        assertTrue(exception.getMessage().contains("Failed to insert transaction"));
        verify(connection, times(1)).rollback();}}