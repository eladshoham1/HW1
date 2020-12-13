package com.example.homework.objects;

import com.example.homework.utils.Constants;

import java.util.ArrayList;

public class TopTen {
    private ArrayList<Record> allRecords;

    public TopTen() {
        allRecords = new ArrayList<>();
    }

    public TopTen(ArrayList<Record> allRecords) {
        this.allRecords = allRecords;
    }

    public ArrayList<Record> getAllRecords() {
        return allRecords;
    }

    public void setAllRecords(ArrayList<Record> allRecords) {
        this.allRecords = allRecords;
    }

    public int addNewRecord(Record record) {
        int rank = Constants.NOT_IN_TOP_TEN;

        for (int i = 0; rank == Constants.NOT_IN_TOP_TEN && i < Constants.CAPACITY && i < allRecords.size(); i++) {
            if (record.getScore() > allRecords.get(i).getScore()) {
                allRecords.add(i, record);
                rank = i + 1;
            }
        }

        if (rank == Constants.NOT_IN_TOP_TEN && allRecords.size() < Constants.CAPACITY) {
            allRecords.add(record);
            rank = allRecords.size();
        } else if (allRecords.size() > Constants.CAPACITY)
            allRecords.remove(Constants.CAPACITY);

        return rank;
    }
}
