package com.example.homework.objects;

import com.example.homework.utils.Constants;

import java.text.SimpleDateFormat;

public class Record {
    private String name = "";
    private int score = 0;
    private long date = 0;
    private double latitude = 0;
    private double longitude = 0;

    public Record() { }

    public Record(String name, long date, int score, double latitude , double longitude) {
        this.name = name;
        this.date = date;
        this.score = score;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public Record setName(String name) {
        this.name = name;
        return this;
    }

    public long getDate() {
        return date;
    }

    public Record setDate(long date) {
        this.date = date;
        return this;
    }

    public int getScore() {
        return score;
    }

    public Record setScore(int score) {
        this.score = score;
        return this;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDateByFormat() {
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_PATTERN);
        return formatter.format(this.date);
    }
}
