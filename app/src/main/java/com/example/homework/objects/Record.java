package com.example.homework.objects;

public class Record {
    private String name = "";
    private int score = 0;
    private long date = 0;
    //private Location location;

    public Record() { }

    public Record(String name, long date, int score) {
        this.name = name;
        this.date = date;
        this.score = score;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name + ", ");
        sb.append(date + ", ");
        sb.append(score + ", ");

        return sb.toString();
    }
}
