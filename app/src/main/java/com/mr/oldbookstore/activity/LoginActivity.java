package com.mr.oldbookstore.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mr.oldbookstore.R;
import com.mr.oldbookstore.UsersDetails;
import com.mr.oldbookstore.databinding.ActivityLoginBinding;
import com.mr.oldbookstore.firebase.PhoneNumberAuthentication;
import com.mr.oldbookstore.model.ModelUsersData;
import com.mr.oldbookstore.viewModel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    private static PhoneNumberAuthentication phoneNumberAuthentication;
    private static DatabaseReference database;
    private static ModelUsersData usersDetails;
    public static Activity activity = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);


        ActivityLoginBinding activityLoginBinding= DataBindingUtil.setContentView(this,R.layout.activity_login);
        activityLoginBinding.setViewmodel(new LoginViewModel());
        activityLoginBinding.executePendingBindings();
        phoneNumberAuthentication=new PhoneNumberAuthentication(LoginActivity.this);
        database=FirebaseDatabase.getInstance().getReference();
        usersDetails=new ModelUsersData();
        activity=LoginActivity.this;


    }
    @BindingAdapter({"toastMessage"})
    public static void runMe(View view, String message) {
        if (message != null) {
            Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
            Log.d("myApp",message);
            phoneNumberAuthentication.verifyCode(message);

        }
    }
    @BindingAdapter({"otpMessage"})
    public static void runMeOTP(View view,String message){
        if(message!=null){
            Log.d("myApp",message);
            phoneNumberAuthentication.sendVerificationCode(message);
            Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();


            //myRef.setValue();


        }
    }
    @BindingAdapter({"backMessage"})
    public static void runMeBack(View view,String message){
        if(message!=null) {
            Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
            activity.finish();
        }

    }

}