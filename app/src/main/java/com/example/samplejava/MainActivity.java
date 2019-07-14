package com.example.samplejava;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Variables

    Button btn_gmail, btn_fb, btn_signup;
    EditText txt_fullname, txt_email,txt_phone,txt_pass;
    TextView txtvw_login;

    //Database
    UserDBHelper userDBHelper;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //IDs
        btn_gmail = findViewById(R.id.btn_googlesignup);
        btn_fb = findViewById(R.id.btn_facebooksignup);
        btn_signup = findViewById(R.id.btn_signup);
        txt_fullname = findViewById(R.id.txtsignup_fullname);
        txt_email = findViewById(R.id.txtsignup_email);
        txt_phone = findViewById(R.id.txtsignup_phoneno);
        txt_pass = findViewById(R.id.txt_signuppass);
        txtvw_login = findViewById(R.id.login_user);

        //GMAIL SIGNUP


        //FACEBOOK SIGNUP

        //ORDINARY SIGNUP

        userDBHelper = new UserDBHelper(this);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountValidation();

            }
        });

        //LOGIN

    }

    private void accountValidation() {

        String valfullname = txt_fullname.getText().toString();
        String valemail = txt_email.getText().toString();
        String valphone = txt_phone.getText().toString();
        String valpass = txt_pass.getText().toString();
        sqLiteDatabase = userDBHelper.getReadableDatabase();
        Cursor emailres = userDBHelper.emailValidation(valemail,sqLiteDatabase);

        if(valfullname.isEmpty()||valemail.isEmpty()||valphone.isEmpty()||valpass.isEmpty()) {
            txt_fullname.setError("Name required.");
            txt_email.setError("Email required.");
            txt_phone.setError("Phone number required.");
            txt_pass.setError("Password required.");


        } else {
            if(valphone.length()==11 && valpass.length()>=8) {
                //Do Nothing
                if(!emailres.moveToFirst()) {
                    emailres.close();

                    boolean isRegistered = userDBHelper.signup_user(
                            txt_fullname.getText().toString(),
                            txt_email.getText().toString(),
                            txt_phone.getText().toString(),
                            txt_pass.getText().toString());

                    if (isRegistered) {
                        Toast.makeText(MainActivity.this, "Account Registered.", Toast.LENGTH_SHORT).show();
                        Intent toLogin = new Intent(MainActivity.this, UserLogin.class);
                        startActivity(toLogin);
                    }


                } else {
                    txt_email.setError("Email Address already exists or not valid.");
                    txt_email.setText("");
                }
            } else {
                txt_phone.setError("Invalid phone number.");
                txt_pass.setError("Minimum of 8 characters.");
                txt_phone.setText("");
                txt_pass.setText("");
            }
        }


    }

}
