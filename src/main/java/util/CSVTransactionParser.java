package util;

import model.Transaction;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class CSVTransactionParser {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Set<Transaction> parseTransactions(String filePath) throws IOException {
        Set<Transaction> transactions = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Transaction transaction = parseLine(line);
                if (transaction != null && transactions.add(transaction)) {
                    // Successfully added means no duplicate based on the composite key.
                } else {
                    System.out.println("Duplicate or invalid transaction skipped: " + line);
                }
            }
        }
        return transactions;
    }

    private Transaction parseLine(String line) {
        String[] parts = line.split(",");
        if (parts.length != 5) {
            System.out.println("Incomplete data: " + line);
            return null; // skip this line due to incomplete data
        }
        try {
            String dealUniqueId = parts[0].trim();
            String fromCurrencyISO = parts[1].trim();
            String toCurrencyISO = parts[2].trim();
            Timestamp dealTimestamp = new Timestamp(dateFormat.parse(parts[3].trim()).getTime());
            BigDecimal dealAmount = new BigDecimal(parts[4].trim());
            return new Transaction(dealUniqueId, fromCurrencyISO, toCurrencyISO, dealTimestamp, dealAmount);
        } catch (Exception e) {
            System.out.println("Error parsing line: " + line + "; Error: " + e.getMessage());
            return null;
        }
    }
}
