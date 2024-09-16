package com.example.College_Details;

public class UserModel {
    String name,email,mobile;
    double spi;

    public UserModel() {
    }

    public UserModel(String name, String email, String mobile, double spi) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.spi = spi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public double getSpi() {
        return spi;
    }

    public void setSpi(double spi) {
        this.spi = spi;
    }
}
