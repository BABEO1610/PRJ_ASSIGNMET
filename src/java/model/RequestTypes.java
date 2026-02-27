/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ADMIN
 */
public class RequestTypes {
    private int requestTypeId;
    private String typeName;

    public RequestTypes() {}

    public RequestTypes(int requestTypeId, String typeName) {
        this.requestTypeId = requestTypeId;
        this.typeName = typeName;
    }

    public int getRequestTypeId() { return requestTypeId; }
    public void setRequestTypeId(int requestTypeId) { this.requestTypeId = requestTypeId; }

    public String getTypeName() { return typeName; }
    public void setTypeName(String typeName) { this.typeName = typeName; }
}
