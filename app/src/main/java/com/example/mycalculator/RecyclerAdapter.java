package com.example.mycalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    Context context;
    ArrayList<history_info> arrHistory;

    RecyclerAdapter(Context context, ArrayList<history_info> arrHistory)
    {
        this.arrHistory = arrHistory;
        this.context = context;
    }


    @NonNull
    @Override

    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycleview_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {

        holder.inputTxt.setText(arrHistory.get(position).inputExp);
        holder.outputTxt.setText(arrHistory.get(position).outputval);
    }

    @Override
    public int getItemCount() {
        return arrHistory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView inputTxt, outputTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            inputTxt = itemView.findViewById(R.id.expTxtView);
            outputTxt = itemView.findViewById(R.id.ansTxtView);
        }
    }
}
