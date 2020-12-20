package com.example.homework.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.homework.R;
import com.example.homework.callbacks.CallBack_List;
import com.example.homework.objects.Adapter_Record;
import com.example.homework.objects.Record;
import com.example.homework.objects.TopTen;
import com.example.homework.utils.MySP;
import com.google.gson.Gson;

public class Fragment_List extends Fragment {
    private RecyclerView list_LST_topTenList;
    private CallBack_List callBack_list;

    public void setCallBack_list(CallBack_List callBack_list) {
        this.callBack_list = callBack_list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);

        displayTopTenList();
        return view;
    }

    private void findViews(View view) {
        list_LST_topTenList = view.findViewById(R.id.list_LST_topTenList);
        list_LST_topTenList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void displayTopTenList() {
        String topTenString = MySP.getInstance().getString(MySP.KEYS.TOP_TEN, "");
        TopTen topTen = topTenString.isEmpty() ? new TopTen() : new Gson().fromJson(topTenString, TopTen.class);

        Adapter_Record adapter_record = new Adapter_Record(getContext(), topTen);
        adapter_record.setClickListener(new Adapter_Record.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Record record = adapter_record.getItem(position);

                if (callBack_list != null) {
                    callBack_list.addMarkerToMap(record.getLatitude() ,record.getLongitude());
                }
            }
        });

        list_LST_topTenList.setAdapter(adapter_record);
    }
}