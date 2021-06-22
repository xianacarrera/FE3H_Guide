package com.xiana.fe3hguide.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xiana.fe3hguide.R;
import com.xiana.fe3hguide.model.InGameClass;

import java.util.List;

/*
 * This class is an adapter for the cells in the ListView of the ClassesFragment and the in game
 * classes.
 */

public class InGameClassAdapter extends ArrayAdapter<InGameClass> {


    public InGameClassAdapter(Context context, int resource, List<InGameClass> classList){
        super(context, resource, classList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InGameClass inGameClass = getItem(position);            // Requested class

        if (convertView == null){
            // A new cell with the corresponding class is created
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.cell_class, parent, false);
        }

        // Information in the cell
        TextView className = (TextView) convertView.findViewById(R.id.textView_class_name);
        ImageView classIcon = (ImageView) convertView.findViewById(R.id.imageView_class_icon);

        className.setText(inGameClass.getName());
        classIcon.setImageResource(inGameClass.getIcon());

        return convertView;
    }
}
