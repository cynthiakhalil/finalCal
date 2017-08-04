package com.example.cynthiakhalil.finalcal;

/**
 * Created by Cynthia.Khalil on 7/22/2017.
 */

public class CustEvent {
    
    private static int ID = 0;
    private String name;
    private String year;
    private String month;
    private String day;
    private String time;


    public CustEvent(String name, String year, String month, String day, String time) {
        this.name = name;
        this.year = year;
        this.month = month;
        this.day = day;
        this.time = time;
        ID++;
    }

    public static int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
