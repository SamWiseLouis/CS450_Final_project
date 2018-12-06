package edu.stlawu.final_project;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class signupActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private EditText emailText;
    private EditText passwordText;
    private TextView BackToLogin;
    private Button Signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // the edit views the create account button and back to login textview
        emailText = (EditText) findViewById(R.id.CreateAccountEmail);
        passwordText =(EditText) findViewById(R.id.CreateAccountPass);
        mAuth = FirebaseAuth.getInstance();
        BackToLogin = findViewById(R.id.backToLogin);
        BackToLogin.setOnClickListener(this);
        Signup = findViewById(R.id.CreateAccount);
        Signup.setOnClickListener(this);

    }

    private void signupUser() {
        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();

        //make sure that both of the edit-text boxes are filled in with something
        if(email.isEmpty()){
            emailText.setError("Email is required");
            emailText.requestFocus(); // way to get program to emphasize the empty input
            return;
        }
        if(password.isEmpty()){
            passwordText.setError("Password is required");
            passwordText.requestFocus();
            return;
        }
        //if neither of these trigger then create a user
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    // if this was successful then a toast line will appear for short amount of time
                    Toast.makeText(getApplicationContext(),"User was created", Toast.LENGTH_SHORT).show();
                }else {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(),"Email is already registered", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"An Error has Occurred", Toast.LENGTH_SHORT).show();
                    }


                }

            }
        });
    }
    // not while watching a tutorial on firebase I noticed the coder
    // liked to use this to organize the onclick actions and though it worked better
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.CreateAccount:
                System.out.println("clicking signup button");
                signupUser();

                break;
            case R.id.backToLogin:
                // this is too easy
                System.out.println("heading back to main fragment");
                finish();
                break;
        }
    }
}
