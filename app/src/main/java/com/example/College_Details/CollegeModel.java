package com.example.College_Details;

public class CollegeModel {
    String name,branch,link;
    int fees;

    CollegeModel(){

    }

    public CollegeModel(String name, String branch, String link, int fees) {
        this.name = name;
        this.branch = branch;
        this.link = link;
        this.fees = fees;
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

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public int getFees() {
        return fees;
    }

    public void setFees(int fees) {
        this.fees = fees;
    }
}
