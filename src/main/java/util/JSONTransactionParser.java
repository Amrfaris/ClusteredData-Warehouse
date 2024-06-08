package util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Transaction;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class JSONTransactionParser {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public Set<Transaction> parseTransactions(String filePath) throws IOException {
        Set<Transaction> transactions = new HashSet<>();
        JsonNode rootNode = objectMapper.readTree(new File(filePath));
        for (JsonNode node : rootNode) {
            Transaction transaction = parseNode(node);
            if (transaction != null && transactions.add(transaction)) {
                // no duplicates
            } else {
                System.out.println("Duplicate or invalid transaction skipped: " + node);
            }
        }
        return transactions;
    }

    private Transaction parseNode(JsonNode node) {
        try {
            String dealUniqueId = node.get("dealUniqueId").asText();
            String fromCurrencyISO = node.get("fromCurrencyISO").asText();
            String toCurrencyISO = node.get("toCurrencyISO").asText();
            Timestamp dealTimestamp = new Timestamp(dateFormat.parse(node.get("dealTimestamp").asText()).getTime());
            BigDecimal dealAmount = new BigDecimal(node.get("dealAmount").asText());
            return new Transaction(dealUniqueId, fromCurrencyISO, toCurrencyISO, dealTimestamp, dealAmount);
        } catch (Exception e) {
            System.out.println("Error parsing JSON node: " + node + "; Error: " + e.getMessage());
            return null;
        }
    }
}
