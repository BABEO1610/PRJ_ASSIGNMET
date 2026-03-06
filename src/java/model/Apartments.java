/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class Apartments {
    private int apartmentId;
    private String apartmentNumber;
    private int floor;
    private double area;
    private String types;
    private String status;
    private int ownerId;    // Sửa từ User thành int theo yêu cầu
    private String ownerName; // Thêm trường này để nhận dữ liệu từ JOIN

    // Constructor, Getter và Setter cho các trường trên...
    public int getOwnerId() { return ownerId; }
    public void setOwnerId(int ownerId) { this.ownerId = ownerId; }
    
    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public Apartments() {}

    public Apartments(int apartmentId, String apartmentNumber, int floor, double area, 
                      String types, String status, int ownerId, String ownerName) {
        this.apartmentId = apartmentId;
        this.apartmentNumber = apartmentNumber;
        this.floor = floor;
        this.area = area;
        this.types = types;
        this.status = status;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
    }

    public int getApartmentId() { return apartmentId; }
    public void setApartmentId(int apartmentId) { this.apartmentId = apartmentId; }

    public String getApartmentNumber() { return apartmentNumber; }
    public void setApartmentNumber(String apartmentNumber) { this.apartmentNumber = apartmentNumber; }

    public int getFloor() { return floor; }
    public void setFloor(int floor) { this.floor = floor; }

    public double getArea() { return area; }
    public void setArea(double area) { this.area = area; }

    public String getTypes() { return types; }
    public void setTypes(String types) { this.types = types; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}