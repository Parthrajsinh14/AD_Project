package com.example.College_Details;

public class UserModel {
    private String name;
    private String email;
    private Double spi;
    private Long mobile;
    private Boolean admin;

    // Default constructor required for calls to DataSnapshot.getValue(UserModel.class)
    public UserModel() {}

    public UserModel(String name, String email, Double spi, Long mobile, Boolean admin) {
        this.name = name;
        this.email = email;
        this.spi = spi;
        this.mobile = mobile;
        this.admin = admin;
    }

    // Getters and Setters
    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
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

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public Double getSpi() {
        return spi;
    }

    public void setSpi(Double spi) {
        this.spi = spi;
    }
}
