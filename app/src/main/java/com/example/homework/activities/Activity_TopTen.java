package com.example.homework.activities;

import android.os.Bundle;

import com.example.homework.R;
import com.example.homework.callbacks.CallBack_List;
import com.example.homework.fragments.Fragment_List;
import com.example.homework.fragments.Fragment_Map;

public class Activity_TopTen extends Activity_Base {
    private Fragment_List fragment_list;
    private Fragment_Map fragment_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten);

        initFragments();
    }

    private void initFragments() {
        fragment_list = new Fragment_List();
        fragment_list.setCallBack_list(callBack_list);
        getSupportFragmentManager().beginTransaction().add(R.id.top_ten_LAY_list, fragment_list).commit();

        fragment_map = new Fragment_Map();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.top_ten_LAY_map, fragment_map)
                .commit();
    }

    private CallBack_List callBack_list = new CallBack_List() {
        @Override
        public void addMarkerToMap(double latitude, double longitude) {
            fragment_map.addMarker(latitude, longitude);
        }
    };

}