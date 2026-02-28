/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;
import java.sql.Date;

/**
 *
 * @author ADMIN
 */
public class Requests {
    private int requestId;
    private int residentID;
    private String residentName;
    private int apartmentID;
    private int requestTypeID;
    private String title;
    private String description;
    private Timestamp transferDate;
    private String status;
    private Timestamp createdAt;
    private int approvedBy;
    private int assignedTo;
    
    public Requests() {}

    public Requests(int requestId, int residentID, String residentName, int apartmentID, int requestTypeID, String title, String description, Timestamp transferDate, String status, Timestamp createdAt, int approvedBy, int assignedTo) {
        this.requestId = requestId;
        this.residentID = residentID;
        this.residentName = residentName;
        this.apartmentID = apartmentID;
        this.requestTypeID = requestTypeID;
        this.title = title;
        this.description = description;
        this.transferDate = transferDate;
        this.status = status;
        this.createdAt = createdAt;
        this.approvedBy = approvedBy;
        this.assignedTo = assignedTo;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getResidentID() {
        return residentID;
    }

    public void setResidentID(int residentID) {
        this.residentID = residentID;
    }

    public String getResidentName() {
        return residentName;
    }

    public void setResidentName(String residentName) {
        this.residentName = residentName;
    }

    public int getApartmentID() {
        return apartmentID;
    }

    public void setApartmentID(int apartmentID) {
        this.apartmentID = apartmentID;
    }

    public int getRequestTypeID() {
        return requestTypeID;
    }

    public void setRequestTypeID(int requestTypeID) {
        this.requestTypeID = requestTypeID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Timestamp transferDate) {
        this.transferDate = transferDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public int getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(int approvedBy) {
        this.approvedBy = approvedBy;
    }

    public int getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(int assignedTo) {
        this.assignedTo = assignedTo;
    }

    
}
