package com.example.d308_vacation_planner.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "vacation_table")
public class Vacation implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String startDate;
    private String endDate;
    private String hotel;

    public Vacation(String title, String startDate, String endDate, String hotel) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.hotel = hotel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }
}