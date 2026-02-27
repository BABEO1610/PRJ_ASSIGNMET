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
public class Requests {
    private int requestId;
    private Users resident;
    private Apartments apartment;
    private RequestTypes requestType;
    private String title;
    private String description;
    private Date transferDate;
    private String status;
    private Date createdAt;
    private Users approvedBy;
    private Users assignedTo;

    public Requests() {}

    public Requests(int requestId, Users resident, Apartments apartment, RequestTypes requestType,
                    String title, String description, Date transferDate, String status, 
                    Date createdAt, Users approvedBy, Users assignedTo) {
        this.requestId = requestId;
        this.resident = resident;
        this.apartment = apartment;
        this.requestType = requestType;
        this.title = title;
        this.description = description;
        this.transferDate = transferDate;
        this.status = status;
        this.createdAt = createdAt;
        this.approvedBy = approvedBy;
        this.assignedTo = assignedTo;
    }

    public int getRequestId() { return requestId; }
    public void setRequestId(int requestId) { this.requestId = requestId; }

    public Users getResident() { return resident; }
    public void setResident(Users resident) { this.resident = resident; }

    public Apartments getApartment() { return apartment; }
    public void setApartment(Apartments apartment) { this.apartment = apartment; }

    public RequestTypes getRequestType() { return requestType; }
    public void setRequestType(RequestTypes requestType) { this.requestType = requestType; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Date getTransferDate() { return transferDate; }
    public void setTransferDate(Date transferDate) { this.transferDate = transferDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Users getApprovedBy() { return approvedBy; }
    public void setApprovedBy(Users approvedBy) { this.approvedBy = approvedBy; }

    public Users getAssignedTo() { return assignedTo; }
    public void setAssignedTo(Users assignedTo) { this.assignedTo = assignedTo; }
}
