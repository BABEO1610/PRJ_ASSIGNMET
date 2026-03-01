/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

public class Notifications {
    private int NotificationId;
    private String Title;
    private String Content;
    private String Type;
    private int SenderID;
    private String senderName;
    private int ReceiverID;
    private int ApartmentID;
    private boolean IsRead = false;
    private Date CreatedAt;
    private boolean IsGlobal = false;

    public Notifications() {
    }

    public Notifications(int NotificationId, String Title, String Content, String Type, int SenderID, String senderName, int ReceiverID, int ApartmentID, Date CreatedAt) {
        this.NotificationId = NotificationId;
        this.Title = Title;
        this.Content = Content;
        this.Type = Type;
        this.SenderID = SenderID;
        this.senderName = senderName;
        this.ReceiverID = ReceiverID;
        this.ApartmentID = ApartmentID;
        this.CreatedAt = CreatedAt;
    }

    public int getNotificationId() {
        return NotificationId;
    }

    public void setNotificationId(int NotificationId) {
        this.NotificationId = NotificationId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public int getSenderID() {
        return SenderID;
    }

    public void setSenderID(int SenderID) {
        this.SenderID = SenderID;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public int getReceiverID() {
        return ReceiverID;
    }

    public void setReceiverID(int ReceiverID) {
        this.ReceiverID = ReceiverID;
    }

    public int getApartmentID() {
        return ApartmentID;
    }

    public void setApartmentID(int ApartmentID) {
        this.ApartmentID = ApartmentID;
    }

    public boolean isIsRead() {
        return IsRead;
    }

    public void setIsRead(boolean IsRead) {
        this.IsRead = IsRead;
    }

    public Date getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Date CreatedAt) {
        this.CreatedAt = CreatedAt;
    }

    public boolean isIsGlobal() {
        return IsGlobal;
    }

    public void setIsGlobal(boolean IsGlobal) {
        this.IsGlobal = IsGlobal;
    }

    
}

//CREATE TABLE [Notifications] (
// [NotificationId] int PRIMARY KEY IDENTITY(1, 1),
// [Title] nvarchar(200) NOT NULL,
// [Content] nvarchar(max) NOT NULL,
// [Type] nvarchar(50), 
// [SenderId] int,      
// [ReceiverId] int,    
// [ApartmentId] int,   
// [IsRead] bit DEFAULT 0,
// [CreatedAt] datetime DEFAULT GETDATE(),
// [IsGlobal] bit DEFAULT 0 
//)
