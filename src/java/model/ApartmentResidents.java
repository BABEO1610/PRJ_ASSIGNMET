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
public class ApartmentResidents {
    private int id;
    private Apartments apartment;
    private Users user;
    private String residentType;
    private Date moveInDate;
    private Date moveOutDate;
    private boolean isActive;

    public ApartmentResidents() {}

    public ApartmentResidents(int id, Apartments apartment, Users user, String residentType, 
                              Date moveInDate, Date moveOutDate, boolean isActive) {
        this.id = id;
        this.apartment = apartment;
        this.user = user;
        this.residentType = residentType;
        this.moveInDate = moveInDate;
        this.moveOutDate = moveOutDate;
        this.isActive = isActive;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Apartments getApartment() { return apartment; }
    public void setApartment(Apartments apartment) { this.apartment = apartment; }

    public Users getUser() { return user; }
    public void setUser(Users user) { this.user = user; }

    public String getResidentType() { return residentType; }
    public void setResidentType(String residentType) { this.residentType = residentType; }

    public Date getMoveInDate() { return moveInDate; }
    public void setMoveInDate(Date moveInDate) { this.moveInDate = moveInDate; }

    public Date getMoveOutDate() { return moveOutDate; }
    public void setMoveOutDate(Date moveOutDate) { this.moveOutDate = moveOutDate; }

    public boolean isIsActive() { return isActive; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }
}
