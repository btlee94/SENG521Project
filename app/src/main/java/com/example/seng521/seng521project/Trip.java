package com.example.seng521.seng521project;


/**
 * Created by brand on 2016-11-23.
 */

public class Trip {
    private String timestampStart;
    private String timestampEnd;
    private String name;

    public Trip(String name, String start, String end){
        this.name = name;
        this.timestampStart = start;
        this.timestampEnd = end;
    }

    public String getName(){
        return this.name;
    }

    public String getTimestampEnd() { return this.timestampEnd; }

    public String getTimestampStart() { return this.timestampStart; }
}
