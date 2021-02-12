package com.example.fe3hguide.supports;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.fe3hguide.R;

import java.util.ArrayList;

import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner;
import gr.escsoft.michaelprimez.searchablespinner.interfaces.OnItemSelectedListener;

public class SupportsFragment extends Fragment implements View.OnClickListener {

    private final SQLiteDatabase db;
    private ImageView icon1, icon2;
    private CardView supportOptions;
    private SearchableSpinner character2;
    private Button buttonSeeSupports;
    private SimpleListAdapter searchableSpinnerAdapter1, searchableSpinnerAdapter2;
    private String name1, name2;
    private String cSupport, bSupport, aSupport, interSupport, interRank, sSupport;

    private OnItemSelectedListener searchableSpinnerListener =
            new OnItemSelectedListener() {
                /*
                 * Listens for selections in the first SearchableSpinner. Shows the icon of the character
                 * selected, populates the second SearchableSpinner with the characters that have supports
                 * with the selected one, and shows the second SearchableSpinner.
                 */
                @Override
                public void onItemSelected(View view, int position, long id) {
                    if (searchableSpinnerAdapter1.getItem(position) == null ||
                            !(searchableSpinnerAdapter1.getItem(position) instanceof String)) {
                        /*
                         * Among the options displayed, there's one line that says "select a
                         * character". Without this check, clicking on it would cause an exception
                         */
                        return;
                    }
                    // Get the name and save the information
                    name1 = (String) searchableSpinnerAdapter1.getItem(position);
                    Cursor cursor = db.query("CHARACTERS", new String[]{"PORTRAIT"},
                            "NAME = ?", new String[]{name1}, null, null,
                            null);
                    if (cursor.moveToFirst()) {     // A valid name was introduced
                        // Show the portrait of the selected character
                        int portrait = cursor.getInt(0);
                        icon1.setImageResource(portrait);
                        icon1.setVisibility(View.VISIBLE);

                        // Activate the second SearchableSpinner
                        character2.setEnabled(true);

                        // Hide its portrait in case there was a previous selection
                        icon2.setVisibility(View.INVISIBLE);

                        // Hide the buttons until the second selection is made
                        buttonSeeSupports.setVisibility(View.INVISIBLE);
                        supportOptions.setVisibility(View.INVISIBLE);

                        // Reset saved texts
                        cSupport = null;
                        bSupport = null;
                        aSupport = null;
                        interSupport = null;
                        interRank = null;
                        sSupport = null;

                        // Get the id of the character
                        cursor = db.query("CHARACTERS", new String[]{"_id"},
                                "NAME = ?", new String[]{name1},
                                null, null, null);
                        int _id;
                        if (cursor.moveToFirst()) {
                            _id = cursor.getInt(0);
                        } else {
                            return;
                        }

                        // Populate character2 with characters with whom the selected one has supports
                        cursor = db.rawQuery("SELECT C.NAME FROM SUPPORTS AS S JOIN CHARACTERS " +
                                        "AS C ON S.CHARACTER_2 = C._id WHERE S.CHARACTER_1 = ?",
                                new String[]{Integer.toString(_id)});
                        ArrayList<String> namesCharacters2 = new ArrayList<>();
                        if (cursor.moveToFirst()) {
                            do {
                                namesCharacters2.add(cursor.getString(0));
                            } while (cursor.moveToNext());
                        }
                        cursor.close();

                        // Set the adapter
                        searchableSpinnerAdapter2 = new SimpleListAdapter(getActivity(),
                                namesCharacters2);
                        character2.setAdapter(searchableSpinnerAdapter2);
                        character2.setSelectedItem(0);

                        // Show the view
                        character2.setVisibility(View.VISIBLE);
                    } else {         // The name is not valid
                        name1 = null;
                    }
                }

                @Override
                public void onNothingSelected() {

                }
            };

    private OnItemSelectedListener searchableSpinnerListener2 =
            new OnItemSelectedListener() {
                /*
                 * Spinner for the second searchableSpinner. Shows the picture of the character that was
                 * introduced, and sets the visibility of the 'see support' button to visible.
                 */
                @Override
                public void onItemSelected(View view, int position, long id) {
                    if (searchableSpinnerAdapter1.getItem(position) == null ||
                            !(searchableSpinnerAdapter1.getItem(position) instanceof String)) {
                        /*
                         * Among the options displayed, there's one line that says "select a
                         * character". Without this check, clicking on it would cause an exception
                         */
                        return;
                    }
                    name2 = (String) searchableSpinnerAdapter2.getItem(position);
                    if (name2 != null) {
                        Cursor cursor = db.query("CHARACTERS", new String[]{"PORTRAIT"},
                                "NAME = ?", new String[]{name2}, null,
                                null, null);
                        if (cursor.moveToFirst()) {     // A valid name was introduced
                            // Show the portrait of the selected character
                            int portrait = cursor.getInt(0);
                            icon2.setImageResource(portrait);
                            icon2.setVisibility(View.VISIBLE);

                            // Shows the button
                            buttonSeeSupports.setVisibility(View.VISIBLE);

                            cursor.close();
                        }
                    } else {        // The name is not valid
                        name2 = null;
                    }
                }

                @Override
                public void onNothingSelected() {

                }
            };

    public SupportsFragment(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ConstraintLayout layout = (ConstraintLayout)
                inflater.inflate(R.layout.fragment_supports, container, false);

        // Populate the first AutoCompleteTextView with options for all the characters
        // Get all the names
        Cursor cursor = db.query("CHARACTERS", new String[]{"NAME"},
                null, null, null, null, null);
        ArrayList<String> names = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                names.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        // Insert the information for the AutoCompleteTextView
        SearchableSpinner character1 = (SearchableSpinner) layout.findViewById(
                R.id.searchable_spinner);
        searchableSpinnerAdapter1 =
                new SimpleListAdapter(getActivity(), names);
        character1.setAdapter(searchableSpinnerAdapter1);
        character1.setSelectedItem(0);

        // At first, the spinner is not enabled (the AutoCompleteTextView goes first)
        character2 = (SearchableSpinner) layout.findViewById(R.id.searchable_spinner_2);
        character2.setVisibility(View.INVISIBLE);

        // Set a listener for both AutoCompleteTextViews
        character1.setOnItemSelectedListener(searchableSpinnerListener);
        character2.setOnItemSelectedListener(searchableSpinnerListener2);

        // Set the visibility of both icon images to invisible
        icon1 = layout.findViewById(R.id.imageView_icon1);
        icon1.setVisibility(View.INVISIBLE);
        icon2 = layout.findViewById(R.id.imageView2_icon2);
        icon2.setVisibility(View.INVISIBLE);

        // Set the visibility of the button that shows supports to invisible
        buttonSeeSupports = (Button) layout.findViewById(R.id.button_see_supports);
        buttonSeeSupports.setVisibility(View.INVISIBLE);

        // Add this as a listener to the button
        buttonSeeSupports.setOnClickListener(this);

        // Set the visibility of the support options to gone
        supportOptions = layout.findViewById(R.id.card_support_options);
        supportOptions.setVisibility(View.GONE);

        // Set listeners for the supports buttons
        Button button0 = (Button) layout.findViewById(R.id.button_support0);
        button0.setOnClickListener(this);
        Button button1button2 = (Button) layout.findViewById(R.id.button_support1);
        button1button2.setOnClickListener(this);
        Button button2 = (Button) layout.findViewById(R.id.button_support2);
        button2.setOnClickListener(this);
        Button button3 = (Button) layout.findViewById(R.id.button_support3);
        button3.setOnClickListener(this);
        Button button4 = (Button) layout.findViewById(R.id.button_Ssupport);
        button4.setOnClickListener(this);

        return layout;
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        Button buttonClicked = (Button) view;
        switch (view.getId()) {
            case R.id.button_see_supports:
                if (name1 == null || name2 == null) {    // At least one of the names is not valid
                    return;
                }
                // name1 must be before name2 following alphabetical order
                if (name1.compareTo(name2) > 0) {            // Swap
                    String temp = name1;
                    name1 = name2;
                    name2 = temp;
                }
                Cursor cursor = db.rawQuery("SELECT sup.C_SUPPORT, sup.B_SUPPORT, " +
                        "sup.A_support, sup.INTER_SUPPORT, sup.INTER_RANK, sup.S_SUPPORT " +
                        "FROM CHARACTERS AS c1, SUPPORTS AS sup, CHARACTERS AS c2 " +
                        "WHERE sup.CHARACTER_1 = c1._id AND sup.CHARACTER_2 = c2._id AND " +
                        "c1.name = ? AND c2.name = ?", new String[]{name1, name2});
                // There should be only 1 result
                if (cursor.moveToFirst()) {
                    cSupport = cursor.getString(0);
                    bSupport = cursor.getString(1);
                    aSupport = cursor.getString(2);
                    interSupport = cursor.getString(3);
                    interRank = cursor.getString(4);
                    sSupport = cursor.getString(5);

                    supportOptions.setVisibility(View.VISIBLE);

                    // There's always at least a C-Support and a B-Support
                    // The C-Support button already has text
                    Button button1 = (Button) getView().findViewById(R.id.button_support1);
                    Button button2 = (Button) getView().findViewById(R.id.button_support2);
                    Button button3 = (Button) getView().findViewById(R.id.button_support3);

                    if (interRank != null) {
                        switch (interRank) {
                            case "C+":
                                // 2º button -> C+
                                button1.setText(R.string.c_plus_support);
                                // Because there's always a B Support and the intermediate
                                // has already been placed, the 3º button must be for B
                                button2.setText(R.string.b_support);
                                if (aSupport != null) {
                                    button3.setText(R.string.a_support);
                                } else {
                                    button3.setVisibility(View.GONE);
                                }
                                break;

                            case "B+":
                                // 2º button -> B support, 3º button -> B+
                                button1.setText(R.string.b_support);
                                button2.setText(R.string.b_plus_support);
                                if (aSupport != null) {
                                    button3.setText(R.string.a_support);
                                } else {
                                    button3.setVisibility(View.GONE);
                                }
                                break;

                            case "A+":
                                // 2º button -> B, 3º button -> A, 4º button -> A+
                                button1.setText(R.string.b_support);
                                button2.setText(R.string.a_support);
                                button3.setText(R.string.a_plus_support);
                        }
                    } else {            // There's no intermediate support
                        button1.setText(R.string.b_support);
                        if (aSupport != null) {
                            button2.setText(R.string.a_support);
                        } else {
                            button2.setVisibility(View.GONE);
                        }
                        button3.setVisibility(View.GONE);
                    }

                    Button sSupportButton = (Button)
                            getView().findViewById(R.id.button_Ssupport);
                    if (sSupport != null) {
                        sSupportButton.setText(R.string.s_support);
                    } else {
                        sSupportButton.setVisibility(View.GONE);
                    }
                }
                break;

            case R.id.button_support0:
                intent = new Intent(getActivity(), SupportsTextActivity.class);
                intent.putExtra("supportRank", "C");
                intent.putExtra("supportText", cSupport);
                startActivity(intent);
                break;
            case R.id.button_support1:
                intent = new Intent(getActivity(), SupportsTextActivity.class);
                if (buttonClicked.getText().toString().equals("C+ Support")) {
                    intent.putExtra("supportRank", "C+");
                    intent.putExtra("supportText", interSupport);
                } else {
                    intent.putExtra("supportRank", "B");
                    intent.putExtra("supportText", bSupport);
                }
                startActivity(intent);
                break;

            case R.id.button_support2:
                intent = new Intent(getActivity(), SupportsTextActivity.class);
                switch (buttonClicked.getText().toString()) {            // 3 possibilities
                    case "B Support":
                        intent.putExtra("supportRank", "B Support");
                        intent.putExtra("supportText", bSupport);
                        break;
                    case "B+ Support":
                        intent.putExtra("supportRank", "B+ Support");
                        intent.putExtra("supportText", interSupport);
                        break;
                    case "A Support":
                        intent.putExtra("supportRank", "A Support");
                        intent.putExtra("supportText", aSupport);
                }
                startActivity(intent);
                break;

            case R.id.button_support3:
                intent = new Intent(getActivity(), SupportsTextActivity.class);
                if (buttonClicked.getText().toString().equals("A Support")) {
                    intent.putExtra("supportRank", "A");
                    intent.putExtra("supportText", aSupport);
                } else {            // text -> A+
                    intent.putExtra("supportRank", "A+ Support");
                    intent.putExtra("supportText", interSupport);
                }
                startActivity(intent);
                break;

            case R.id.button_Ssupport:
                intent = new Intent(getActivity(), SupportsTextActivity.class);
                intent.putExtra("supportRank", "S");
                intent.putExtra("supportText", sSupport);
                startActivity(intent);
        }
    }
}
