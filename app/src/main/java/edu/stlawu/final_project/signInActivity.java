package edu.stlawu.final_project;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseUser;

import edu.stlawu.final_project.MainFragment.OnFragmentInteractionListener;

public class signInActivity
        extends AppCompatActivity
        implements OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_fragment);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}