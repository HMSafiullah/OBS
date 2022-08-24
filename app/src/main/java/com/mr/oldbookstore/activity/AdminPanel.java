package com.mr.oldbookstore.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mr.oldbookstore.R;

public class AdminPanel extends AppCompatActivity {
    Button button_all_users;
    Button button_reported_users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        button_all_users=findViewById(R.id.all_users);
        button_reported_users=findViewById(R.id.reported_users);

        button_reported_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminPanel.this,ReportedUsers.class);
                startActivity(intent);
            }
        });
        button_all_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminPanel.this,AllUsersActivity.class);
                startActivity(intent);
            }
        });
    }
}