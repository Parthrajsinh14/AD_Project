package com.example.College_Details;

public class CollegeModel {
    String name,branch;
    int fees;

    CollegeModel(){

    }

    public CollegeModel(String name, String branch, int fees) {
        this.name = name;
        this.branch = branch;
        this.fees = fees;
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
