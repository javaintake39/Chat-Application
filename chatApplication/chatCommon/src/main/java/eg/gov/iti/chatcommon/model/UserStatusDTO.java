package eg.gov.iti.chatcommon.model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ghazallah
 */
public class UserStatusDTO {
    
    String phone;
    int status;

    public UserStatusDTO() {
    }

    public UserStatusDTO(String phone, int status) {
        this.phone = phone;
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    
}
