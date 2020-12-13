package com.example.homework.objects;

import java.text.SimpleDateFormat;

public class Record {
    private String name = "";
    private int score = 0;
    private long date;
    private double latitude;
    private double longitude;

    public Record() { }

    public Record(String name, long date, int score, double latitude, double longitude) {
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

    public String dateToString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatter.format(date);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Name: " + name);
        sb.append("\nDate: " + dateToString());
        sb.append("\nScore: " + score);

        return sb.toString();
    }
}
