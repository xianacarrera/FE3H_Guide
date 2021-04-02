package com.example.fe3hguide;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.facebook.stetho.inspector.database.ContentProviderSchema;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class CalculatorFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ScrollView layout = (ScrollView) inflater.inflate(R.layout.fragment_calculator,
                container, false);

        // Set the number range for the numberPicker -> [0, 100], since it represents Displayed Hit
        final NumberPicker numberPicker = (NumberPicker) layout.findViewById(R.id.number_picker_hit);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(100);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                TextView textViewTrueHit = (TextView) layout.findViewById(R.id.true_hit);
                textViewTrueHit.setVisibility(View.INVISIBLE);
            }
        });

        // When the button is clicked, the True Hit is calculated and shown
        Button button = (Button) layout.findViewById(R.id.button_calculate_true_hit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textViewTrueHit = (TextView) layout.findViewById(R.id.true_hit);
                textViewTrueHit.setText(Float.toString(trueHit(numberPicker.getValue())));
                textViewTrueHit.setVisibility(View.VISIBLE);
            }
        });

        // Set the values of the table
        TableLayout hitTable = layout.findViewById(R.id.hit_table);
        for (int i = 1; i < hitTable.getChildCount(); i++){
            TableRow row = (TableRow) hitTable.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++){
                TextView text = (TextView) row.getChildAt(j);
                switch(j){
                    case 0:         // Displayed Hit (range -> [0, 50])
                        text.setText(Integer.toString(i - 1));
                        break;
                    case 1:         // True Hit corresponding to column 0
                        text.setText(Float.toString(trueHit(i - 1)));
                        break;
                    case 2:         // Another Displayed Hit column (range -> [50, 100])
                        text.setText(Integer.toString(50 + i - 1));
                        break;
                    case 3:         // True Hit corresponding to column 2
                        text.setText(Float.toString(trueHit(50 + i - 1)));
                }
            }
        }

        FloatingActionButton fab = layout.findViewById(R.id.fab);
        fab.setBackgroundColor(Color.WHITE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return layout;
    }

    // Returns the True Hit, i.e., the real probability, given a Displayed Hit (n)
    public float trueHit(int n){
        float trueHit = 2*n*n + n;
        if (n > 50) trueHit -= (2*n - 100) * (2*n - 99);
        return trueHit/100;
    }
}