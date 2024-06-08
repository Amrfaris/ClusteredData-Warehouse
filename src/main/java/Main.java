
import dao.TransactionDAO;
import model.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.sql.SQLException;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        TransactionDAO transactionDAO = new TransactionDAO();
        int successfulInserts = 0;

        Transaction transaction1 = new Transaction("TX1001", "USD", "EUR", Timestamp.valueOf("2023-06-01 12:30:00"), new BigDecimal("1000.00"));
        Transaction transaction2 = new Transaction("TX1009", "USD", "GBP", Timestamp.valueOf("2023-06-01 13:00:00"), new BigDecimal("2000.00"));
        Transaction transaction3 = new Transaction("TX1004", "EUR", "JPY", Timestamp.valueOf("2023-06-01 13:30:00"), new BigDecimal("1500.00"));

        if (tryInsertTransaction(transactionDAO, transaction1)) successfulInserts++;
        if (tryInsertTransaction(transactionDAO, transaction2)) successfulInserts++;
        if (tryInsertTransaction(transactionDAO, transaction3)) successfulInserts++;

        if (successfulInserts > 0) {
            logger.info(successfulInserts + " transactions inserted successfully.");
        } else {
            logger.info("No transactions were inserted.");
        }
    }

    private static boolean tryInsertTransaction(TransactionDAO dao, Transaction transaction) {
        try {
            if (!dao.transactionExists(transaction)) {
                dao.insertTransaction(transaction);
                return true;  // Return true only if the insertion was successful and no exception was thrown
            } else {
                logger.info("Transaction already exists and was skipped: " + transaction);
                return false;
            }
        } catch (SQLException e) {
            logger.error("Error inserting transaction: " + transaction, e);
            return false;  // Return false if an exception occurs
        }
    }


}
