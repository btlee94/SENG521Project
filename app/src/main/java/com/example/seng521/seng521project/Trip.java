package com.example.seng521.seng521project;


/**
 * Created by brand on 2016-11-23.
 */

public class Trip {
    //probaby a timestamp type we could use
    private String date;
    private String time;

    private String name;

/****FROM REQUIREMENTS DOC****
    Data to be stored:
    Cylinder Misfire (P0300 - P0312)
    Fuel Pump Status (P1230 - P1239)
    Engine RPM/Speed limiter reached (P1270)
    Seat Belt Status (B1426 - B1430)
    Brake Pedal Status (B1483 - B1486)
    ABS Status (C1095 - C1103)
    Power Steering Failure (C1778)
    Heater System Failure (C1776)
    */
    private boolean cylMis;
    private boolean fuelStat;
    private boolean rpmLimit;
    private boolean seatBelt;
    private boolean brakePedal;
    private boolean abs;
    private boolean powerSteerFail;
    private boolean heaterFail;

    public Trip(String name, String date, String time){
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getName(){
        return this.name;
    }

    public String getDate(){
        return this.date;
    }

    public String getTime(){
        return this.time;
    }
}
