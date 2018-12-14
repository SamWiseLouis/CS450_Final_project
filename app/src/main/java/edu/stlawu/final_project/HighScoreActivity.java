package edu.stlawu.final_project;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HighScoreActivity extends Activity {
    private FirebaseAuth mAuth;
    private String username;
    private String time;
    private DatabaseReference HighscoresList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_hs);

    }



}
