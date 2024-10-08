package com.example.mycalculator;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link historyFragment} factory method to
 * create an instance of this fragment.
 */
public class historyFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private ArrayList<history_info> arrHistory;

    Button clearHistory;

    public historyFragment(ArrayList<history_info> arrHistory) {
            this.arrHistory = arrHistory;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_history, container, false);

        View histFrag = v.findViewById(R.id.histFrag);

        int id = getResources().getIdentifier("config_showNavigationBar","bool","android");
        boolean result = id > 0 && getResources().getBoolean(id);

        if(result) {
            ViewGroup.MarginLayoutParams paramsHistFrag = (ViewGroup.MarginLayoutParams) histFrag.getLayoutParams();
            paramsHistFrag.bottomMargin = 50;
        }

        v.setBackgroundColor(getResources().getColor(R.color.grey));

        if(arrHistory.isEmpty()) return v;

        clearHistory = v.findViewById(R.id.clearHist);
        clearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrHistory.clear();
                adapter = new RecyclerAdapter(getActivity(),arrHistory);
                recyclerView.setAdapter(adapter);
            }
        });

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setReverseLayout(true);

        recyclerView.setLayoutManager(llm);
        recyclerView.smoothScrollToPosition(arrHistory.size()-1);

//        ArrayList<history_info> arrHistory = new ArrayList<>();
//        arrHistory.add(new history_info("hello","123"));
//        arrHistory.add(new history_info("hello","123"));
//        arrHistory.add(new history_info("hello","123"));
//        arrHistory.add(new history_info("hello","123"));

        adapter = new RecyclerAdapter(getActivity(),arrHistory);
        recyclerView.setAdapter(adapter);

        return v;
    }
}