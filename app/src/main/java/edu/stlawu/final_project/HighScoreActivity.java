package edu.stlawu.final_project;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static edu.stlawu.final_project.MainFragment.PREF_NAME;
import static edu.stlawu.final_project.MainFragment.SIGNUP_ENABLE;

public class HighScoreActivity extends Activity {
    private String username;
    private int time;
    private EditText usernameInput;
    private DatabaseReference databaseHighscore;
    private ScrollView scorboard;
    private Button sumbmitButton;
    private LinearLayout highscoreList;
    private GridLayout aGrid;
    private boolean allowSubmit;
    private TextView timeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hs);
        // need to get database to connect to scoreboard

        //attach to scoreboard database
        databaseHighscore = FirebaseDatabase.getInstance().getReference("highscores");



        this.usernameInput = (EditText) findViewById(R.id.highscoreEditText);
        this.scorboard = findViewById(R.id.scoreBoard);
        this.sumbmitButton = findViewById(R.id.highscoreButton);
        this.highscoreList = findViewById(R.id.high_scores_list);
        this.timeText = findViewById(R.id.textView7);

        this.aGrid = findViewById(R.id.grid);

        this.allowSubmit = getSharedPreferences(
                SIGNUP_ENABLE, Context.MODE_PRIVATE).getBoolean(
                "SIGNUPENABLED",true);

        this.time = getSharedPreferences(SIGNUP_ENABLE,MODE_PRIVATE).getInt("Time",0);

        System.out.print("the current state of submission is: "+allowSubmit);
        if(allowSubmit != true){
            hideForum();
        }
        timeText.setText(Integer.toString(time));

        sumbmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameText = usernameInput.getText().toString().trim();

                if (usernameText.isEmpty()) {
                    usernameInput.setError("username is required");
                    usernameInput.requestFocus(); // way to get program to emphasize the empty input
                    return;
                }
                // if here their is a username and a highscore to add to database
                username = usernameText;
                addHighscore(usernameText, Integer.toString(time));
                //disable and hide button if successful upload of high-score
                hideForum();


            }
        });

        final Context acon = this;

        databaseHighscore.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                String aUsername = dataSnapshot.child("username").getValue().toString();
                String aScore = dataSnapshot.child("score").getValue().toString();
                System.out.println(aUsername + " : "+aScore);
                String text ="Username:      "+ aUsername + "     Time: " + aScore;

               TextView aTextView = new TextView(acon);

                aTextView.setText(text);
                aTextView.setWidth(MATCH_PARENT);
                aTextView.setWidth(WRAP_CONTENT);
                aTextView.setGravity(Gravity.CENTER);
                aTextView.setBackground(getDrawable(R.drawable.rounded_corners));
                aTextView.setTextColor(Color.BLACK);
                aTextView.setTextSize(14);
                highscoreList.addView(aTextView);


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        //update all the highscores
    }

    private void addHighscore(String aName,String aTime){
        //make a unique key and push node to database
        String id = databaseHighscore.push().getKey();
        highscore aHighscore = new highscore(id,username,Integer.toString(time));
        databaseHighscore.child(id).setValue(aHighscore);
        Toast.makeText(this,"Highscore added",Toast.LENGTH_SHORT).show();
    }

    private void hideForum(){
        //function to remove submission of high scores
        sumbmitButton.setVisibility(View.INVISIBLE);
        sumbmitButton.setEnabled(false);
        aGrid.setVisibility(View.INVISIBLE);
        aGrid.setEnabled(false);
    }



    }
