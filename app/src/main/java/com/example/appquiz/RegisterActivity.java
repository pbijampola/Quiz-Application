package com.example.appquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    EditText username,email,password;
    Button directlog,signupbtn;

    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //HOOKING
        username= findViewById(R.id.usernameid);
        email = findViewById(R.id.emailid);
        password = findViewById(R.id.passwordid);
        signupbtn = findViewById(R.id.signupid);

        // Initializing validation style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //validating username
        awesomeValidation.addValidation(this,R.id.usernameid, RegexTemplate.NOT_EMPTY,R.string.invalid_username);
        //validating email
        awesomeValidation.addValidation(this,R.id.emailid, Patterns.EMAIL_ADDRESS,R.string.invalid_email);
        //validating password
        awesomeValidation.addValidation(this,R.id.passwordid,".{8,}",R.string.invalid_password);

        // onclickEvent signupbtn
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //checking for validation
                if(awesomeValidation.validate()){
                    //Success message
                    Toast.makeText(getApplicationContext(), "Your Registration is Successful", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Your Registration is not Successful", Toast.LENGTH_SHORT).show();
                }
            }
        });
        directlog=findViewById(R.id.alreadyaccount);
        directlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
                finish();

            }
        });

    }
}