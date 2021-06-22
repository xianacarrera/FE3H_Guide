package com.xiana.fe3hguide.supports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.xiana.fe3hguide.R;

public class SupportsTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supports_text);
        // Set up the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Support conversations");
        setSupportActionBar(toolbar);

        // Add back arrow to the toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Get info
        Intent intent = getIntent();
        String title = intent.getStringExtra("supportRank");
        String text = intent.getStringExtra("supportText");

        /*
         * The text contains "\n" characters, but they are interpreted as string literals.
         * I want to replace each one of those with 2 newlines. To find all instances of it,
         * I used replaceAll. However, "\n" must be escaped *twice* ("\\\\n"). Using "\n", the
         * regex engine would localize the new lines. Using "\\n", the compiler would escape
         * the backlash and send "\n" to the regex (same result). Using "\\\n", the regex would
         * search for a backlash followed by a newline. With "\\\\n", the compiler deletes two
         * backlashes and the regex receives "\\n", so it translates it to the literal "\n", which
         * gives the desired result.
         */
        text = text.replaceAll("\\\\n", "\n\n");

        TextView titleTextView = (TextView) findViewById(R.id.textView_support_rank);
        TextView textTextView = (TextView) findViewById(R.id.textView_support_text);

        // Set the texts
        titleTextView.setText(title);
        textTextView.setText(text);
    }

    @Override
    // Back arrow on the toolbar returns to previous activity (if there is any)
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}