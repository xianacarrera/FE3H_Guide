package com.example.fe3hguide.classes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.fe3hguide.R;
import com.example.fe3hguide.database.Facade;
import com.example.fe3hguide.model.InGameClass;

public class InGameClassActivity extends AppCompatActivity {

    private InGameClass inGameClass;
    private Facade fc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_detail);

        inGameClass = getSelectedClass();
        fc = Facade.getInstance(this);
    }

    // Returns the InGameClass whose cell was clicked in the ListView of ClassesFragment
    public InGameClass getSelectedClass(){
        Intent previousIntent = getIntent();
        // The name of the InGameClass was passed as extra info with the intent
        String className = previousIntent.getStringExtra("className");

        // Retrieve all the information about the InGameClass with the previous name
        return fc.getInGameClass(className);
    }
}