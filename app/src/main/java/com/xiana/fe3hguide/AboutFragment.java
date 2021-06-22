package com.xiana.fe3hguide;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AboutFragment extends Fragment {

    private TextView aboutInfo;
    private TextView twitterAccount;
    private TextView disclaimer;

    private TextView fireEmblemFandom;
    private TextView fireEmblemWiki;
    private TextView fe3h;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ScrollView layout = (ScrollView)
                inflater.inflate(R.layout.fragment_about, container, false);

        initComponents(layout);
        setUpComponents();
        addListeners();

        return layout;
    }

    private void initComponents(ScrollView layout){
        twitterAccount = (TextView) layout.findViewById(R.id.twitter_account);
        disclaimer = (TextView) layout.findViewById(R.id.text_disclaimer);
        aboutInfo = (TextView) layout.findViewById(R.id.text_about_info);

        fireEmblemFandom = (TextView) layout.findViewById(R.id.fire_emblem_fandom);
        fireEmblemWiki = (TextView) layout.findViewById(R.id.fire_emblem_wiki);
        fe3h = (TextView) layout.findViewById(R.id.fe3hcom);
    }

    private void setUpComponents(){
        // Set "About" as the text in the toolbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.nav_about));

        setUpAboutInfo();
        setUpDisclaimer();
    }

    private void setUpAboutInfo(){
        String line = null;
        aboutInfo.setText("");
        InputStream is = getResources().openRawResource(R.raw.about_text);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                aboutInfo.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setUpDisclaimer(){
        String line = null;
        disclaimer.setText("");
        InputStream is = getResources().openRawResource(R.raw.disclaimer);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                disclaimer.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addListeners(){
        // Fire Emblem Wiki - Fandom link
        fireEmblemFandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://fireemblem.fandom.com/wiki/Fire_Emblem_Wiki")));
                } catch(Exception e){
                    Snackbar.make(view, "Could not open link", Snackbar.LENGTH_LONG)
                            .setAction("Could not open link", null).show();
                }
            }
        });

        // Fire Emblem Wiki
        fireEmblemWiki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://fireemblemwiki.org/wiki/Main_Page")));
                } catch(Exception e){
                    Snackbar.make(view, "Could not open link", Snackbar.LENGTH_LONG)
                            .setAction("Could not open link", null).show();
                }
            }
        });

        // FE3H.com
        fe3h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://fe3h.com/")));
                } catch(Exception e){
                    Snackbar.make(view, "Could not open link", Snackbar.LENGTH_LONG)
                            .setAction("Could not open link", null).show();
                }
            }
        });


        // Twitter link
        twitterAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("twitter://user?user_id=1026922462607495171"));
                    startActivity(intent);
                } catch (Exception e){
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://twitter.com/Kinai_24")));
                    } catch (Exception ex){
                        Snackbar.make(view, "Could not open link", Snackbar.LENGTH_LONG)
                                .setAction("Could not open link", null).show();
                    }
                }
            }
        });
    }
}