package com.example.fe3hguide.supports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.fe3hguide.R;

public class SupportsTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supports_text);
        // Set up the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // TODO: up button in toolbar

        // Get info
        Intent intent = getIntent();
        String title = intent.getStringExtra("supportRank");
        String text = intent.getStringExtra("supportText");

        TextView titleTextView = (TextView) findViewById(R.id.textView_support_rank);
        TextView textTextView = (TextView) findViewById(R.id.textView_support_text);

        // Set the texts
        titleTextView.setText(title);
        textTextView.setText(text);
    }
}