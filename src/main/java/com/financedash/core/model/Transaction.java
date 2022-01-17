package com.financedash.core.model;

public class Transaction {
    private String id;

    private String description;
    private String userId;
    private double sum;

    public Transaction(){}

    public Transaction(String userId, double sum){
        this.userId = userId;
        this.sum = sum;
    }

    public Transaction(String id, String userId, double sum) {
        this.id = id;
        this.userId = userId;
        this.sum = sum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Transaction(String id, String description, String userId, double sum) {
        this.id = id;
        this.description = description;
        this.userId = userId;
        this.sum = sum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", userId='" + userId + '\'' +
                ", sum=" + sum +
                '}';
    }
}
