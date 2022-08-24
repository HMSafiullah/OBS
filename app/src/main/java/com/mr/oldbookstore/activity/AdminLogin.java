package com.mr.oldbookstore.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mr.oldbookstore.R;

public class AdminLogin extends AppCompatActivity {
    EditText editText_username;
    EditText editText_password;
    Button button_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        editText_password=findViewById(R.id.password);
        editText_username=findViewById(R.id.username);
        button_login=findViewById(R.id.login);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText_username.getText().toString().equals("admin")&&editText_password.getText().toString().equals("admin")){
                    Intent intent=new Intent(AdminLogin.this,AdminPanel.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Kindly Input Correct Credentials",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}