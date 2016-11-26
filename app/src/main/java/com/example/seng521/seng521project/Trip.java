package com.example.seng521.seng521project;


/**
 * Created by brand on 2016-11-23.
 */

public class Trip {
    //using Strings for now but could be something better later
    //date and time basically used for better readability for the user but the full timestamps will have to be used for the database
    private String date;
    private String time;
    private String timestampStart;
    private String timestampEnd;
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

    public Trip(String name, String start, String end){
        this.name = name;
        this.timestampStart = start;
        this.timestampEnd = end;
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

    public String getTimestampEnd() { return this.timestampEnd; }

    public String getTimestampStart() { return this.timestampStart; }
}
