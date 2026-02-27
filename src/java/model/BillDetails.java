/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ADMIN
 */
public class BillDetails {
    private int billDetailId;
    private Bills bill;
    private ServiceTypes serviceType;
    private double quantity;
    private double amount;

    public BillDetails() {}

    public BillDetails(int billDetailId, Bills bill, ServiceTypes serviceType, 
                       double quantity, double amount) {
        this.billDetailId = billDetailId;
        this.bill = bill;
        this.serviceType = serviceType;
        this.quantity = quantity;
        this.amount = amount;
    }

    public int getBillDetailId() { return billDetailId; }
    public void setBillDetailId(int billDetailId) { this.billDetailId = billDetailId; }

    public Bills getBill() { return bill; }
    public void setBill(Bills bill) { this.bill = bill; }

    public ServiceTypes getServiceType() { return serviceType; }
    public void setServiceType(ServiceTypes serviceType) { this.serviceType = serviceType; }

    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) { this.quantity = quantity; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}
