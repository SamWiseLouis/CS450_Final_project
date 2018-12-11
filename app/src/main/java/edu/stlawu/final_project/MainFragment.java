package edu.stlawu.final_project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class MainFragment extends Fragment {

    public static final String PREF_NAME = "MazeRun";
    public static final String NEW_CLICKED = "NEWCLICKED";
    private FirebaseAuth mAuth;
    private OnFragmentInteractionListener mListener;
    private View rootView;
    private EditText email;
    private EditText password;
    private TextView signup;
    private Boolean loggedIn;
    private FirebaseUser currentUser;
    private Button newButton;
    private Button continueButton;


    public MainFragment() {
        // Required empty public constructor
    }

    // onCreate gets called when the fragment is created
    // before the UI views are constructed
    // Initialize data needed for the fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        // initalize the firebaseAuthor instance
        mAuth = FirebaseAuth.getInstance();

    }
    @Override
    public void onStart()
    {
        //check to see if user is logged in
        super.onStart();
        currentUser = mAuth.getCurrentUser();


        // if not logged in disable buttons to run game and continue game
        if (currentUser == null){
            loggedIn = false;
        }else{
            loggedIn = true;

        }
    }




    @Override
    public View onCreateView(

            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        // Inflate the layout for this
        rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // find the locations of the password and email
        email = rootView.findViewById(R.id.signInEmail);
        password = rootView.findViewById(R.id.signInPass);


        if(currentUser == null){
            loggedIn = false;
            System.out.println("you are not logged in");
        }else if(currentUser.isEmailVerified()){
            loggedIn = true;
            System.out.println("You are logged in");

        }
        //new game button
        newButton = rootView.findViewById(R.id.new_button);
        newButton.setEnabled(loggedIn);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor pref_ed =
                        getActivity().getSharedPreferences(
                                PREF_NAME, Context.MODE_PRIVATE).edit();
                pref_ed.putBoolean(NEW_CLICKED, true).apply();

                Intent intent = new Intent(
                        getActivity(), GameActivity.class);
                getActivity().startActivity(intent);
                }
        });
        //continue game button
        continueButton = rootView.findViewById(R.id.continue_button);
        continueButton.setEnabled(loggedIn);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        getActivity(), GameActivity.class);
                getActivity().startActivity(intent);
            }
        });

        //highscore button
        View highScoreButton = rootView.findViewById(R.id.high_scores_button);
        highScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor pref_ed =
                        getActivity().getSharedPreferences(
                                PREF_NAME, Context.MODE_PRIVATE).edit();
                pref_ed.putBoolean(NEW_CLICKED, true).apply();
                Intent intent = new Intent(
                        getActivity(), HighScoreActivity.class);
                // this is killing the program right now
                getActivity().startActivity(intent);
            }
        });


        // launch the signup activity when text is clicked below login
        signup = rootView.findViewById(R.id.signUpText);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),signupActivity.class));
            }
        });


       // login button - > after login changes to username
        View loginbutton = rootView.findViewById(R.id.LoginButton);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

        View aboutButton = rootView.findViewById(R.id.about_button);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.about_title_text);
                builder.setMessage(R.string.about);
                builder.setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                return;
                            }
                        });
                builder.show();
            }
        });



        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);

        }
    }

    //method for logging the user into an existing account
    private void userLogin(){

        String Email = email.getText().toString().trim();
        String Pass = password.getText().toString().trim();

        //make sure that both of the edit-text boxes are filled in with something
        if(Email.isEmpty()){
            email.setError("Email is required");
            email.requestFocus(); // way to get program to emphasize the empty input
            return;
        }
        if(Pass.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
            return;
        }
        //attempt to sign in the user so that they may play the game
        mAuth.signInWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //check to see if user was logged in successfully
                if(task.isSuccessful()){
                    // if this was successful then a toast line will appear for short amount of time
                    Toast.makeText(getActivity(),"User login successful", Toast.LENGTH_SHORT).show();
                    loggedIn = true;
                    newButton.setEnabled(loggedIn);
                    continueButton.setEnabled(loggedIn);
                    View login = rootView.findViewById(R.id.loginView);
                    login.setVisibility(View.GONE);
                }else {
                    // the pass and or username were not correct
                    if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                        Toast.makeText(getActivity(),"Email is not registered", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(),"An Error has Occurred", Toast.LENGTH_SHORT).show();
                    }


                }

            }
        });



    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}