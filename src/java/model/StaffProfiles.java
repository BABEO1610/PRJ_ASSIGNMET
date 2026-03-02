/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class StaffProfiles {
    private Users user; // Trùng với UserId (PK/FK)
    private Positions position; // Trùng với PositionId (FK)

    public StaffProfiles() {}

    public StaffProfiles(Users user, Positions position) {
        this.user = user;
        this.position = position;
    }

    // Getters and Setters
    public Users getUser() { return user; }
    public void setUser(Users user) { this.user = user; }

    public Positions getPosition() { return position; }
    public void setPosition(Positions position) { this.position = position; }
}
