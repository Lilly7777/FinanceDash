package com.financedash.resourceserver.model;

public class ExtendedTransaction {

    private String id;

    private String description;
    private String userId;
    private double sum;
    private String date;

    private String categoryId = null;

    private String categoryName = null;

    public ExtendedTransaction() {
    }

    public ExtendedTransaction(Transaction transaction, Category category) {
        this.id = transaction.getId();
        this.description = transaction.getDescription();
        this.userId = transaction.getUserId();
        this.sum = transaction.getSum();
        this.date = transaction.getDate();
        this.categoryId = category.getId();
        this.categoryName = category.getCategoryName();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ExtendedTransaction(String id, String description, String userId, double sum, String date) {
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
