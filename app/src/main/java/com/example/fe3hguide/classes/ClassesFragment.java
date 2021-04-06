package com.example.fe3hguide.classes;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.fe3hguide.R;
import com.example.fe3hguide.adapters.InGameClassAdapter;
import com.example.fe3hguide.database.Facade;
import com.example.fe3hguide.model.InGameClass;

import java.util.ArrayList;
import java.util.List;


public class ClassesFragment extends Fragment {

    private Facade fc;
    private List<InGameClass> classes;
    private ListView listView;

    public ClassesFragment(Facade fc){
        this.fc = fc;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LinearLayout layout = (LinearLayout)
                inflater.inflate(R.layout.fragment_classes, container, false);

        classes = fc.getClasses();

        initComponents(layout);
        setupComponents():
        addListeners();

        return layout;
    }

    public void initComponents(LinearLayout layout){
        listView = (ListView) layout.findViewById(R.id.listView_classes);
    }

    public void setupComponents(){
        InGameClassAdapter adapter = new InGameClassAdapter(getActivity(), 0, classes);
        listView.setAdapter(adapter);
    }

    public void addListeners(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                InGameClass selectedClass = (InGameClass) listView.getItemAtPosition(position);
                Intent classDetailIntent = new Intent(getActivity(), InGameClassActivity.class);
                classDetailIntent.putExtra("className", selectedClass.getName());
                startActivity(classDetailIntent);
            }
        });
    }
}