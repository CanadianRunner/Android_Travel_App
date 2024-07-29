package com.example.d308_vacation_planner.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "vacation_table")
public class Vacation {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String vacationName;
    private String startDate;
    private String endDate;
    private String hotel;

    public Vacation(String vacationName, String startDate, String endDate, String hotel) {
        this.vacationName = vacationName;
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

    public String getVacationName() {
        return vacationName;
    }

    public void setVacationName(String vacationName) {
        this.vacationName = vacationName;
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
