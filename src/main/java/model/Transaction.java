package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Transaction {
    private String dealUniqueId;
    private String fromCurrencyISO;
    private String toCurrencyISO;
    private Timestamp dealTimestamp;
    private BigDecimal dealAmount;

    // Constructor for the Transaction object
    public Transaction(String dealUniqueId, String fromCurrencyISO, String toCurrencyISO, Timestamp dealTimestamp, BigDecimal dealAmount) {
        this.dealUniqueId = dealUniqueId;
        this.fromCurrencyISO = fromCurrencyISO;
        this.toCurrencyISO = toCurrencyISO;
        this.dealTimestamp = dealTimestamp;
        this.dealAmount = dealAmount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "dealUniqueId='" + dealUniqueId + '\'' +
                ", fromCurrencyISO='" + fromCurrencyISO + '\'' +
                ", toCurrencyISO='" + toCurrencyISO + '\'' +
                ", dealTimestamp=" + dealTimestamp +
                ", dealAmount=" + dealAmount +
                '}';
    }

    // Getters and setters for each field
    public String getDealUniqueId() {
        return dealUniqueId;
    }

    public void setDealUniqueId(String dealUniqueId) {
        this.dealUniqueId = dealUniqueId;
    }

    public String getFromCurrencyISO() {
        return fromCurrencyISO;
    }

    public void setFromCurrencyISO(String fromCurrencyISO) {
        this.fromCurrencyISO = fromCurrencyISO;
    }

    public String getToCurrencyISO() {
        return toCurrencyISO;
    }

    public void setToCurrencyISO(String toCurrencyISO) {
        this.toCurrencyISO = toCurrencyISO;
    }

    public Timestamp getDealTimestamp() {
        return dealTimestamp;
    }

    public void setDealTimestamp(Timestamp dealTimestamp) {
        this.dealTimestamp = dealTimestamp;
    }

    public BigDecimal getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(BigDecimal dealAmount) {
        this.dealAmount = dealAmount;
    }
}
