package com.example.homework.objects;

import com.example.homework.utils.Constants;

import java.util.ArrayList;

public class TopTen {
    private ArrayList<Record> records = new ArrayList<>();
    private final int CAPACITY = 10;

    public TopTen() { }

    public TopTen(ArrayList<Record> records) {
        this.records = records;
    }

    public ArrayList<Record> getRecords() {
        return records;
    }

    public TopTen setRecords(ArrayList<Record> records) {
        this.records = records;
        return this;
    }

    public int addNewRecord(Record record) {
        int rank = Constants.NOT_IN_TOP_TEN;

        for (int i = (records.size() >= CAPACITY) ? CAPACITY : records.size(); i > 0; i--) {
            if (record.getScore() > records.get(i - 1).getScore()) {
                records.add(i - 1, record);
                rank = i;
            }
        }

        return rank;
    }
}
