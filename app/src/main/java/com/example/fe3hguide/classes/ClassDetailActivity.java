package com.example.fe3hguide.classes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.fe3hguide.R;
import com.example.fe3hguide.database.Facade;
import com.example.fe3hguide.model.InGameClass;

public class ClassDetailActivity extends AppCompatActivity {

    private InGameClass inGameClass;
    private Facade fc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_detail);

        inGameClass = getSelectedClass();
        fc = new Facade(this);
    }

    public InGameClass getSelectedClass(){
        Intent previousIntent = getIntent();
        String className = previousIntent.getStringExtra("className");

        return fc.getInGameClass(className);
    }
}