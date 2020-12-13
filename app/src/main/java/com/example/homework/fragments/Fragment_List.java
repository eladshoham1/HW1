package com.example.homework.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.homework.R;
import com.example.homework.callbacks.CallBack_List;
import com.example.homework.objects.Record;
import com.example.homework.objects.TopTen;
import com.example.homework.utils.MySP;
import com.example.homework.utils.MySignal;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Fragment_List extends Fragment {
    private ListView list_LST_topTenList;
    private CallBack_List callBack_list;

    public void setCallBack_list(CallBack_List callBack_list) {
        this.callBack_list = callBack_list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        initViews();

        displayTopTenList();
        return view;
    }

    private void findViews(View view) {
        list_LST_topTenList = view.findViewById(R.id.list_LST_topTenList);
    }

    private void initViews() {
        list_LST_topTenList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Record record = (Record) parent.getItemAtPosition(position);

                if (callBack_list != null)
                    callBack_list.addMarkerToMap(record.getLatitude(), record.getLongitude());
            }
        });
    }

    public void displayTopTenList() {
        String topTenString = MySP.getInstance().getString(MySP.KEYS.TOP_TEN, "");
        TopTen topTen = topTenString.isEmpty() ? new TopTen() : new Gson().fromJson(topTenString, TopTen.class);

        list_LST_topTenList.setAdapter(MySignal.getInstance().getArrayAdapter(topTen));
    }
}