package com.example.homework.objects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.homework.R;

public class Adapter_Record extends RecyclerView.Adapter<Adapter_Record.MyViewHolder> {

    private TopTen topTen;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public Adapter_Record(Context context, TopTen topTen) {
        this.mInflater = LayoutInflater.from(context);
        this.topTen = topTen;
    }

    // inflates the row layout from xml when needed
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_record, parent, false);
        return new MyViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Record record = topTen.getAllRecords().get(position);
        holder.record_LBL_rank.setText("" + (position + 1));
        holder.record_LBL_name.setText(record.getName());
        holder.record_LBL_date.setText(record.getDateByFormat());
        holder.record_LBL_score.setText("" + record.getScore());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return topTen.getAllRecords().size();
    }

    // convenience method for getting data at click position
    public Record getItem(int id) {
        return topTen.getAllRecords().get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    // stores and recycles views as they are scrolled off screen
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView record_LBL_rank;
        TextView record_LBL_name;
        TextView record_LBL_date;
        TextView record_LBL_score;

        MyViewHolder(View itemView) {
            super(itemView);
            record_LBL_rank = itemView.findViewById(R.id.record_LBL_rank);
            record_LBL_name = itemView.findViewById(R.id.record_LBL_name);
            record_LBL_date = itemView.findViewById(R.id.record_LBL_date);
            record_LBL_score = itemView.findViewById(R.id.record_LBL_score);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) {
                        mClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
        }
    }
}
