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
public class RentalContracts {
    private int contractId;
    private Apartments apartment;
    private String representativeName;
    private String representativePhone;
    private String representativeEmail;
    private String representativeIdNumber;
    private Date startDate;
    private Date endDate;
    private String status;
    private Date createdAt;

    public RentalContracts() {}

    public RentalContracts(int contractId, Apartments apartment, String representativeName, 
                           String representativePhone, String representativeEmail, 
                           String representativeIdNumber, Date startDate, Date endDate, 
                           String status, Date createdAt) {
        this.contractId = contractId;
        this.apartment = apartment;
        this.representativeName = representativeName;
        this.representativePhone = representativePhone;
        this.representativeEmail = representativeEmail;
        this.representativeIdNumber = representativeIdNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getContractId() { return contractId; }
    public void setContractId(int contractId) { this.contractId = contractId; }

    public Apartments getApartment() { return apartment; }
    public void setApartment(Apartments apartment) { this.apartment = apartment; }

    public String getRepresentativeName() { return representativeName; }
    public void setRepresentativeName(String representativeName) { this.representativeName = representativeName; }

    public String getRepresentativePhone() { return representativePhone; }
    public void setRepresentativePhone(String representativePhone) { this.representativePhone = representativePhone; }

    public String getRepresentativeEmail() { return representativeEmail; }
    public void setRepresentativeEmail(String representativeEmail) { this.representativeEmail = representativeEmail; }

    public String getRepresentativeIdNumber() { return representativeIdNumber; }
    public void setRepresentativeIdNumber(String representativeIdNumber) { this.representativeIdNumber = representativeIdNumber; }

    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
