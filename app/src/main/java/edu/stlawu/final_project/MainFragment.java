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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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
    private View baseView;
    private String email;
    private String password;
    private TextView signup;

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
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    public View onCreateView(

            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        // Inflate the layout for this
        rootView = inflater.inflate(R.layout.fragment_main, container, false);

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

        View newButton = rootView.findViewById(R.id.new_button);
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
                                     }
        );

        // launch the signup activity when text is clicked below login
        View signup = rootView.findViewById(R.id.signUpText);
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