package com.mr.oldbookstore.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.mr.oldbookstore.R;
import com.squareup.picasso.Picasso;

public class ViewProfileActivity extends AppCompatActivity {

    private TextView txt_name;
    private TextView txt_phoneNumber;
    private TextView txt_email;
    private TextView txt_location;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        String name=getIntent().getStringExtra("name");
        String phoneNumber=getIntent().getStringExtra("phoneNumber");
        String location=getIntent().getStringExtra("location");
        String image=getIntent().getStringExtra("image");
        String email=getIntent().getStringExtra("email");

        txt_name=findViewById(R.id.profile_view_name);
        txt_email=findViewById(R.id.profile_view_email);
        txt_location=findViewById(R.id.profile_view_location);
        txt_phoneNumber=findViewById(R.id.profile_view_phoneNumber);
        imageView=findViewById(R.id.profilePicViewActivityProfile);

        txt_name.setText(name);
        txt_location.setText(location);
        txt_email.setText(email);
        txt_phoneNumber.setText(phoneNumber);
        Picasso.get().load(image).fit().into(imageView);
    }
}