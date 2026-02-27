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
public class RequestLogs {
    private int logId;
    private Requests request;
    private Users updatedBy;
    private String oldStatus;
    private String newStatus;
    private String note;
    private Date updatedAt;

    public RequestLogs() {}

    public RequestLogs(int logId, Requests request, Users updatedBy, String oldStatus, 
                       String newStatus, String note, Date updatedAt) {
        this.logId = logId;
        this.request = request;
        this.updatedBy = updatedBy;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.note = note;
        this.updatedAt = updatedAt;
    }

    public int getLogId() { return logId; }
    public void setLogId(int logId) { this.logId = logId; }

    public Requests getRequest() { return request; }
    public void setRequest(Requests request) { this.request = request; }

    public Users getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(Users updatedBy) { this.updatedBy = updatedBy; }

    public String getOldStatus() { return oldStatus; }
    public void setOldStatus(String oldStatus) { this.oldStatus = oldStatus; }

    public String getNewStatus() { return newStatus; }
    public void setNewStatus(String newStatus) { this.newStatus = newStatus; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
