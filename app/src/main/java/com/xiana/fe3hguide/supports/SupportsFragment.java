package com.xiana.fe3hguide.supports;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.xiana.fe3hguide.R;
import com.xiana.fe3hguide.adapters.SimpleListAdapter;
import com.xiana.fe3hguide.database.Facade;

import java.util.ArrayList;

import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner;
import gr.escsoft.michaelprimez.searchablespinner.interfaces.OnItemSelectedListener;

public class SupportsFragment extends Fragment implements View.OnClickListener {

    private Facade fc;
    private ImageView icon1, icon2;
    private CardView supportOptions;
    private SearchableSpinner character1, character2;
    private Button buttonSeeSupports;
    private Button button0, button1, button2, button3, button4;
    private SimpleListAdapter searchableSpinnerAdapter1, searchableSpinnerAdapter2;
    private String name1, name2;
    private String cSupport, bSupport, aSupport, interSupport, interRank, sSupport;

    private OnItemSelectedListener searchableSpinnerListener =
            new OnItemSelectedListener() {
                /*
                 * Listens for selections in the first SearchableSpinner. Shows the icon of the
                 * character selected, populates the second SearchableSpinner with the characters
                 * that have supports with the selected one, and shows the second SearchableSpinner.
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
                    Integer portrait = null;
                    if ((portrait = fc.getPortrait(name1)) == null) { // The name is not valid
                        name1 = null;
                        return;
                    }

                    // Show the portrait of the selected character
                    icon1.setImageResource(portrait);
                    icon1.setVisibility(View.VISIBLE);

                    // Activate the second SearchableSpinner
                    character2.setEnabled(true);

                    // Hide previous views in case input is not correct
                    hideViews();

                    // Reset saved texts
                    resetTexts();

                    // Get the id of the character
                    Integer _id = fc.getId(name1);

                    // Populate character2 with characters with whom the selected one has
                    // supports
                    ArrayList<String> namesCharacters2 = fc.getCharacterNamesWithSupportWith(_id);

                    // Set the adapter
                    searchableSpinnerAdapter2 = new SimpleListAdapter(getActivity(),
                            namesCharacters2);
                    character2.setAdapter(searchableSpinnerAdapter2);
                    character2.setSelectedItem(0);

                    // Show the view
                    character2.setVisibility(View.VISIBLE);
                }

                private void hideViews() {
                    // Hide its portrait in case there was a previous selection
                    icon2.setVisibility(View.INVISIBLE);

                    // Hide the buttonsOn until the second selection is made
                    buttonSeeSupports.setVisibility(View.INVISIBLE);
                    supportOptions.setVisibility(View.INVISIBLE);
                }

                private void resetTexts() {
                    cSupport = null;
                    bSupport = null;
                    aSupport = null;
                    interSupport = null;
                    interRank = null;
                    sSupport = null;
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
                    if (searchableSpinnerAdapter2.getItem(position) == null ||
                            !(searchableSpinnerAdapter2.getItem(position) instanceof String)) {
                        /*
                         * Among the options displayed, there's one line that says "select a
                         * character". Without this check, clicking on it would cause an exception
                         */
                        return;
                    }

                    name2 = (String) searchableSpinnerAdapter2.getItem(position);
                    if (name2 == null) {
                        return;
                    }

                    Integer portrait = null;
                    if ((portrait = fc.getPortrait(name2)) == null) {   // Name not valid
                        name2 = null;
                        return;
                    }

                    // Show icon of the character and the button to search for supports
                    show(portrait);
                }

                private void show(int portrait) {
                    // Show the portrait of the selected character
                    icon2.setImageResource(portrait);
                    icon2.setVisibility(View.VISIBLE);

                    // Shows the button
                    buttonSeeSupports.setVisibility(View.VISIBLE);
                }

                @Override
                public void onNothingSelected() {

                }
            };

    public SupportsFragment(Facade fc) {
        this.fc = fc;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ScrollView layout = (ScrollView)
                inflater.inflate(R.layout.fragment_supports, container, false);

        initComponents(layout);
        setupComponents();
        addListeners();

        return layout;
    }

    private void initComponents(ScrollView layout) {
        icon1 = layout.findViewById(R.id.imageView_icon1);
        icon2 = layout.findViewById(R.id.imageView2_icon2);
        character1 = (SearchableSpinner) layout.findViewById(R.id.searchable_spinner);
        character2 = (SearchableSpinner) layout.findViewById(R.id.searchable_spinner_2);
        buttonSeeSupports = (Button) layout.findViewById(R.id.button_see_supports);
        supportOptions = layout.findViewById(R.id.card_support_options);
        button0 = (Button) layout.findViewById(R.id.button_support0);
        button1 = (Button) layout.findViewById(R.id.button_support1);
        button2 = (Button) layout.findViewById(R.id.button_support2);
        button3 = (Button) layout.findViewById(R.id.button_support3);
        button4 = (Button) layout.findViewById(R.id.button_Ssupport);
    }

    private void setupComponents() {
        // Set "Supports" as the text in the toolbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().
                setTitle(getString(R.string.nav_supports));

        // Add the list of character names to the SearchableSpinner
        searchableSpinnerAdapter1 = new SimpleListAdapter(getActivity(), fc.getAllNames());
        character1.setAdapter(searchableSpinnerAdapter1);
        character1.setSelectedItem(0);

        // At first, the spinner is not enabled (the SearchableSpinner goes first)
        character2.setVisibility(View.INVISIBLE);

        // Set the visibility of both icon images to invisible
        icon1.setVisibility(View.INVISIBLE);
        icon2.setVisibility(View.INVISIBLE);

        // Set the visibility of the button that shows supports to invisible
        buttonSeeSupports.setVisibility(View.INVISIBLE);

        // Set the visibility of the support options to gone
        supportOptions.setVisibility(View.GONE);
    }

    private void addListeners() {
        // Set a listener for both SearchableSpinners
        character1.setOnItemSelectedListener(searchableSpinnerListener);
        character2.setOnItemSelectedListener(searchableSpinnerListener2);

        // Add this object as a listener to the See Supports button
        buttonSeeSupports.setOnClickListener(this);

        // Add this object as a listener for all the support (c, b, a...) buttons
        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
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
                // name1 and name2 are copied into new variables so as to never change the order
                // of the attributes themselves, and only swap the local variables
                String char1 = name1;
                String char2 = name2;
                // Byleth must always be the second character
                if (char1.contains("Byleth")){
                    String temp = char1;
                    char1 = char2;
                    char2 = temp;
                } else if (char1.compareTo(char2) > 0) {            // Swap
                    String temp = char1;
                    char1 = char2;
                    char2 = temp;
                }

                // Get the supports between the two characters
                ArrayList<String> supports = fc.searchSupports(char1, char2);

                if (supports == null) {
                    return;
                }

                cSupport = supports.get(0);
                bSupport = supports.get(1);
                aSupport = supports.get(2);
                interSupport = supports.get(3);
                interRank = supports.get(4);
                sSupport = supports.get(5);

                supportOptions.setVisibility(View.VISIBLE);

                // There's always at least a C-Support and a B-Support
                // The C-Support button already has text

                if (interRank != null) {
                    switch (interRank) {
                        case "C+":
                            // 2º button -> C+
                            button1.setText(R.string.c_plus_support);
                            // Because there's always a B Support and the intermediate
                            // has already been placed, the 3º button must be for B
                            button2.setText(R.string.b_support);
                            button2.setVisibility(View.VISIBLE);
                            if (aSupport != null) {
                                button3.setText(R.string.a_support);
                                button3.setVisibility(View.VISIBLE);
                            } else {
                                button3.setVisibility(View.GONE);
                            }
                            break;

                        case "B+":
                            // 2º button -> B support, 3º button -> B+
                            button1.setText(R.string.b_support);
                            button2.setText(R.string.b_plus_support);
                            button2.setVisibility(View.VISIBLE);
                            if (aSupport != null) {
                                button3.setText(R.string.a_support);
                                button3.setVisibility(View.VISIBLE);
                            } else {
                                button3.setVisibility(View.GONE);
                            }
                            break;

                        case "A+":
                            // 2º button -> B, 3º button -> A, 4º button -> A+
                            button1.setText(R.string.b_support);
                            button2.setText(R.string.a_support);
                            button2.setVisibility(View.VISIBLE);
                            button3.setText(R.string.a_plus_support);
                            button3.setVisibility(View.VISIBLE);
                    }
                } else {            // There's no intermediate support
                    button1.setText(R.string.b_support);
                    if (aSupport != null) {
                        button2.setText(R.string.a_support);
                        button2.setVisibility(View.VISIBLE);
                    } else {
                        button2.setVisibility(View.GONE);
                    }
                    button3.setVisibility(View.GONE);
                }

                if (sSupport != null) {
                    button4.setText(R.string.s_support);
                    button4.setVisibility(View.VISIBLE);
                } else {
                    button4.setVisibility(View.GONE);
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
