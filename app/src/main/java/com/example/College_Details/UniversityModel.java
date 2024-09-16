package com.example.College_Details;

public class UniversityModel {
    String name;
    int college;

    UniversityModel(){

    }

    public UniversityModel(String name, int college) {
        this.name = name;
        this.college = college;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCollege() {
        return college;
    }

    public void setCollege(int college) {
        this.college = college;
    }
}
