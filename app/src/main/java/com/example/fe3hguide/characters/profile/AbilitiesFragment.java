package com.example.fe3hguide.characters.profile;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fe3hguide.R;

public class AbilitiesFragment extends Fragment {

    private Dialog myDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myDialog = new Dialog(getActivity());

        return inflater.inflate(R.layout.fragment_abilities, container, false);
    }

    public void shopPopup(){
        TextView textclose = null;
        myDialog.setContentView(R.layout.popup_ability);
        textclose = (TextView) myDialog.findViewById(R.id.text_close);
        textclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }
}