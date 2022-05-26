package com.example.chengecontacts;

public class User {
    public String phone;
    public String email;
    //public String password;

    public User(){
    }

    public User(String phone, String email) {
        this.phone = phone;
        this.email = email;
        //this.password = password;
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

/*    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }*/
}

