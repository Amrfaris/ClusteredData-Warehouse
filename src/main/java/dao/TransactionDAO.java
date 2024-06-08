package dao;

import model.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class TransactionDAO {
    private static final Logger logger = LogManager.getLogger(TransactionDAO.class);
    private static final String INSERT_SQL = "INSERT INTO Transactions " +
            "(deal_unique_id, from_currency_iso, to_currency_iso, deal_timestamp, deal_amount) VALUES (?, ?, ?, ?, ?)";

    public void insertTransaction(Transaction transaction) throws SQLException {
        if (transactionExists(transaction)) {
            logger.info("Transaction already exists and was skipped: " + transaction);
            return; // Skip insertion if the transaction already exists
        }
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL);
            conn.setAutoCommit(false);
            pstmt.setString(1, transaction.getDealUniqueId());
            pstmt.setString(2, transaction.getFromCurrencyISO());
            pstmt.setString(3, transaction.getToCurrencyISO());
            pstmt.setTimestamp(4, transaction.getDealTimestamp());
            pstmt.setBigDecimal(5, transaction.getDealAmount());
            pstmt.executeUpdate();
            conn.commit();
            logger.info("Transaction inserted successfully: " + transaction);
        } catch (SQLException e) {
            logger.error("Failed to insert transaction: " + transaction, e);
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    logger.error("Failed to rollback transaction: " + transaction, ex);
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    logger.info("Database connection closed successfully.");
                } catch (SQLException e) {
                    logger.error("Failed to close connection", e);
                }
            }
        }
    }

    public boolean transactionExists(Transaction transaction) throws SQLException {
        String CHECK_SQL = "SELECT count(*) FROM Transactions WHERE deal_unique_id = ? AND deal_timestamp = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(CHECK_SQL)) {

            pstmt.setString(1, transaction.getDealUniqueId());
            pstmt.setTimestamp(2, transaction.getDealTimestamp());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        } catch (SQLException e) {
            logger.error("Failed to check if transaction exists: " + transaction, e);
            throw e;
        }
    }

}
