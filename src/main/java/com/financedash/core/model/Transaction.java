package com.financedash.core.model;

public class Transaction {
    private String id;

    private String description;
    private String userId;
    private double sum;
    private String date;

    private String categoryId = null;

    public Transaction() {
    }

    public Transaction(String userId, double sum) {
        this.userId = userId;
        this.sum = sum;
    }

    public Transaction(String userId, double sum, String categoryId) {
        this.userId = userId;
        this.sum = sum;
        this.categoryId = categoryId;
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

    public Transaction(String id, String description, String userId, double sum, String date) {
        this.id = id;
        this.description = description;
        this.userId = userId;
        this.sum = sum;
        this.date = date;
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

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}