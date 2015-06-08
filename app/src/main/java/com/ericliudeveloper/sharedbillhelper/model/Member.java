package com.ericliudeveloper.sharedbillhelper.model;

/**
 * Created by liu on 8/06/15.
 */
public class Member {

    private long id = -1L;
    private String firstName = null;
    private String lastName;
    private String phone;
    private String email;
    private String moveInDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMoveInDate() {
        return moveInDate;
    }

    public void setMoveInDate(String moveInDate) {
        this.moveInDate = moveInDate;
    }

    public String getMoveOutDate() {
        return moveOutDate;
    }

    public void setMoveOutDate(String moveOutDate) {
        this.moveOutDate = moveOutDate;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    private String moveOutDate;
    private int deleted;

}
