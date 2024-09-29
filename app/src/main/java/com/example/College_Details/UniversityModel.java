package com.example.College_Details;

public class UniversityModel {
    String name,link;
    int college;

    UniversityModel(){

    }

    public UniversityModel(String name, String link, int college) {
        this.name = name;
        this.link = link;
        this.college = college;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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
