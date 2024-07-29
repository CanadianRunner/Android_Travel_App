package com.example.d308_vacation_planner.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "excursion_table")
public class Excursion {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int vacationId;
    private String excursionName;
    private String date;

    public Excursion(int vacationId, String excursionName, String date) {
        this.vacationId = vacationId;
        this.excursionName = excursionName;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVacationId() {
        return vacationId;
    }

    public void setVacationId(int vacationId) {
        this.vacationId = vacationId;
    }

    public String getExcursionName() {
        return excursionName;
    }

    public void setExcursionName(String excursionName) {
        this.excursionName = excursionName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
