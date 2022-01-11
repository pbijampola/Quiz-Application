package com.example.appquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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


public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    Button loginbtn;
    String emailPattern= "[a-zA-Z0-9._-]+@[a-z]+\\._[a-z]+";
    ProgressDialog progressDialog;

    //instance of firebase
    FirebaseAuth mAuth;
    FirebaseUser mUser;

   // AwesomeValidation awesomeValidation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // HOOKING
        email = findViewById(R.id.login_mail);
        password = findViewById(R.id.login_password);
        loginbtn = findViewById(R.id.loginbtnid);
        progressDialog = new ProgressDialog(this);

        mAuth= FirebaseAuth.getInstance();
        mUser= mAuth.getCurrentUser();

//        //initializing the validation style
//        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
//
//        //validating email
//        awesomeValidation.addValidation(this,R.id.login_mail, Patterns.EMAIL_ADDRESS,R.string.invalid_email);
//        //validating password
//        awesomeValidation.addValidation(this,R.id.login_password,".{8,}",R.string.invalid_password);
//
//        //Event logging
//        loginbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // cheking validation
//                if(awesomeValidation.validate()){
//                    Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    Toast.makeText(getApplicationContext(), "Failed to Login", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appLogin();
            }
        });
    }

    private void appLogin() {

        String mail = email.getText().toString();
        String passwd = password.getText().toString();

        if (!mail.matches(emailPattern)) {
            email.setError("Enter correct E-mai");
        }
        if (passwd.isEmpty() || passwd.length() < 8) {
            password.setError("Enter Correct Password");
        } else {
            progressDialog.setMessage("Please Wait While Login");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(mail,passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUserToHomeActivity();
                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private void sendUserToHomeActivity() {
        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

}