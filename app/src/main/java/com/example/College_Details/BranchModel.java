package com.example.College_Details;

public class BranchModel {
    String name,link;
    int college,seat;

    BranchModel(){

    }


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }



    public int getCollege() {
        return college;
    }

    public void setCollege(int college) {
        this.college = college;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public BranchModel(String name, int college, int seat, String link) {
        this.name = name;
        this.college = college;
        this.seat = seat;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
