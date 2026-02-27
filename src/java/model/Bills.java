/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author ADMIN
 */
public class Bills {
    private int billId;
    private Apartments apartment;
    private int billingMonth;
    private int billingYear;
    private double totalAmount;
    private String status;
    private Date createdAt;

    public Bills() {}

    public Bills(int billId, Apartments apartment, int billingMonth, int billingYear, 
                 double totalAmount, String status, Date createdAt) {
        this.billId = billId;
        this.apartment = apartment;
        this.billingMonth = billingMonth;
        this.billingYear = billingYear;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getBillId() { return billId; }
    public void setBillId(int billId) { this.billId = billId; }

    public Apartments getApartment() { return apartment; }
    public void setApartment(Apartments apartment) { this.apartment = apartment; }

    public int getBillingMonth() { return billingMonth; }
    public void setBillingMonth(int billingMonth) { this.billingMonth = billingMonth; }

    public int getBillingYear() { return billingYear; }
    public void setBillingYear(int billingYear) { this.billingYear = billingYear; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
