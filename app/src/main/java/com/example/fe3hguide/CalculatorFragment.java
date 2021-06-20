package com.example.fe3hguide;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
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
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class CalculatorFragment extends Fragment {

    private NumberPicker numberPicker;
    private TextView textViewTrueHit;
    private Button button;
    private FloatingActionButton fab;
    private TableLayout hitTable;
    private GraphView graph;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ScrollView layout = (ScrollView) inflater.inflate(R.layout.fragment_calculator,
                container, false);

        initComponents(layout);
        setupComponents();
        addListeners();

        createTable();
        createGraph();

        return layout;
    }

    private void initComponents(ScrollView layout){
        numberPicker = (NumberPicker) layout.findViewById(R.id.number_picker_hit);
        textViewTrueHit = (TextView) layout.findViewById(R.id.true_hit);
        button = (Button) layout.findViewById(R.id.button_calculate_true_hit);
        fab = layout.findViewById(R.id.fab);
        hitTable = layout.findViewById(R.id.hit_table);
        graph = (GraphView) layout.findViewById(R.id.graph);
    }

    private void setupComponents(){
        // Set the number range for the numberPicker -> [0, 100], since it represents Displayed Hit
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(100);

        fab.setBackgroundColor(Color.WHITE);

        // Set "Calculator" as the text in the toolbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("True hit calculator");
    }

    private void addListeners(){
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                textViewTrueHit.setVisibility(View.INVISIBLE);
            }
        });
        // NumberPicker text color can only be changed after version 29
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            numberPicker.setTextColor(getResources().getColor(R.color.mainText));
        }

        // When the button is clicked, the True Hit is calculated and shown
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewTrueHit.setText(Float.toString(trueHit(numberPicker.getValue())));
                textViewTrueHit.setVisibility(View.VISIBLE);
            }
        });


        //TODO: this
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void createTable(){
        // Set the values of the table
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
    }

    private void createGraph(){
        DataPoint[] data = buildData();
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(data);
        customizeGraph(series, graph);
        graph.addSeries(series);
    }

    private DataPoint[] buildData(){
        DataPoint[] data = new DataPoint[101];
        for (int i = 0; i <= 100; i++){
            data[i] = new DataPoint(i, trueHit(i));
        }
        return data;
    }

    private void customizeGraph(LineGraphSeries<DataPoint> series, GraphView graph){
        series.setTitle("Displayed Hit vs True Hit");
        series.setDrawBackground(true);
        series.setColor(Color.RED);
        series.setBackgroundColor(R.color.light_blue_background);
        series.setDrawDataPoints(false);

        // x axis limits
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(100);

        // y axis limits
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(100);

        // enable scrolling
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScrollableY(true);

        // enable scalling
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);

        // colors
        graph.getGridLabelRenderer().setHorizontalLabelsColor(getResources().getColor(R.color.mainText));
        graph.getGridLabelRenderer().setVerticalLabelsColor(getResources().getColor(R.color.mainText));
    }


    // Returns the True Hit, i.e., the real probability, given a Displayed Hit (n)
    public float trueHit(int n){
        float trueHit = 2*n*n + n;
        if (n > 50) trueHit -= (2*n - 100) * (2*n - 99);
        return trueHit/100;
    }
}