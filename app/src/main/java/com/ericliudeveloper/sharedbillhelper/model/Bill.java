package com.ericliudeveloper.sharedbillhelper.model;

/**
 * Created by liu on 7/06/15.
 */
public class Bill {

    private final long id;
    private String type;
    private double amount;
    private String startDate;
    private String endDate;
    private String dueDate;
    private int paid;


    private int deleted;


    public Bill(){
        id = - 100L;
    }

    public Bill(long id) {
        this.id = id;
    }




    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }
    public long getId() {
        return id;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }




}
