package com.example.appquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    EditText username,email,password;
    Button directlog,signupbtn;
    String emailPattern= "[a-zA-Z0-9._-]+@[a-z]+\\._[a-z]+";
    ProgressDialog progressDialog;

    //instance of firebase
    FirebaseAuth mAuth;
    FirebaseUser mUser;

//    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //HOOKING
        username= findViewById(R.id.usernameid);
        email = findViewById(R.id.emailid);
        password = findViewById(R.id.passwordid);
        signupbtn = findViewById(R.id.signupid);
        progressDialog = new ProgressDialog(this);

        mAuth= FirebaseAuth.getInstance();
        mUser= mAuth.getCurrentUser();

//        // Initializing validation style
//        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
//
//        //validating username
//        awesomeValidation.addValidation(this,R.id.usernameid, RegexTemplate.NOT_EMPTY,R.string.invalid_username);
//        //validating email
//        awesomeValidation.addValidation(this,R.id.emailid, Patterns.EMAIL_ADDRESS,R.string.invalid_email);
//        //validating password
//        awesomeValidation.addValidation(this,R.id.passwordid,".{8,}",R.string.invalid_password);
//        // onclickEvent signupbtn
//        signupbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //checking for validation
//                if(awesomeValidation.validate()){
//                    //Success message
//                    Toast.makeText(getApplicationContext(), "Your Registration is Successful", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    Toast.makeText(getApplicationContext(), "Your Registration is not Successful", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        //USING FIREBASE AUTHENTICATION

        directlog=findViewById(R.id.alreadyaccount);
        directlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
                finish();

            }
        });
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAuth();
            }
        });
    }

    private void checkAuth() {
        // checking internet connection
        if(!isconnected()){
            Toast.makeText(getApplicationContext(), "NO INTERNET ACCESS", Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(getApplicationContext(), "CONNECTED", Toast.LENGTH_SHORT).show();
        }
        // getting the edit fields first
        String name_=username.getText().toString();
        String mail=email.getText().toString();
        String passwd=password.getText().toString();

        if(!mail.matches(emailPattern)){
            email.setError("Enter correct E-mai");

        }
        if(passwd.isEmpty()||passwd.length()<8){
            password.setError("Enter Correct Password");
        }else{
            progressDialog.setMessage("Please Wait While Registration");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            //creating a user
            mAuth.createUserWithEmailAndPassword(mail,passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUserToLoginActivity();
                        Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    //Internet connection
    private boolean isconnected() {
        ConnectivityManager connectivityManager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo()!=null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    private void sendUserToLoginActivity() {
        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}