/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.gov.iti.chatcommon.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ghazallah
 */
public class User implements Serializable{
    private String phoneNumber;
    private String name;
    private String password;
    private String gender;
    private String bio;
    private byte [] picture;
    private Date birthDate;
    private String email;
    private int status_id;
    private String Country;

    public User() {
    }

    public User(String phoneNumber, String name, String password, String gender, String bio, byte[] picture, Date birthDate, String email, int status_id,String country) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.bio = bio;
        this.picture = picture;
        this.birthDate = birthDate;
        this.email = email;
        this.status_id = status_id;
        this.Country=country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus_id() {
        return status_id;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }
    
    
}