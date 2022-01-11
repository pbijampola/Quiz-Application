package com.example.appquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;


public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    AwesomeValidation awesomeValidation;
    Button loginbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // HOOKING
        email = findViewById(R.id.login_mail);
        password = findViewById(R.id.login_password);
        loginbtn = findViewById(R.id.loginbtnid);

        //initializing the validation style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //validating email
        awesomeValidation.addValidation(this,R.id.login_mail, Patterns.EMAIL_ADDRESS,R.string.invalid_email);
        //validating password
        awesomeValidation.addValidation(this,R.id.login_password,".{8,}",R.string.invalid_password);

        //Event logging
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // cheking validation
                if(awesomeValidation.validate()){
                    Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Failed to Login", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}