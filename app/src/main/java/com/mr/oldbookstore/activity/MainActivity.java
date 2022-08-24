package com.mr.oldbookstore.activity;

import static android.provider.Settings.System.getString;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mr.oldbookstore.R;
import com.mr.oldbookstore.firebase.GoogleAuthentication;
import com.mr.oldbookstore.model.ModelUsersData;

import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity{
    Button phoneNumber;
    Button googleSignIn;
    Button admin;
    DatabaseReference database;
    //Google Authentication
    GoogleAuthentication googleAuthentication;
    private static final int RC_SIGN_IN = 1;
    private static final String TAG = "MainActivity";
    private static String name, email,id;
    private static GoogleSignInClient mSignInClient;
    private static String idToken;
    private static FirebaseAuth firebaseAuth;
    CircularProgressIndicator progressCircular;
    private static FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database= FirebaseDatabase.getInstance("https://old-book-store-144a2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        googleAuthentication=new GoogleAuthentication(firebaseAuth,authStateListener,MainActivity.this,mSignInClient);
        Log.d("myApp", String.valueOf(googleAuthentication.getAuthStateListener().toString().isEmpty()));
        phoneNumber=findViewById(R.id.phoneNumber_button);
        progressCircular=findViewById(R.id.progress_circular_main);
        googleSignIn=findViewById(R.id.google_button);
        admin=findViewById(R.id.admin_button);
        progressCircular.setVisibility(View.VISIBLE);
        final int intervalTime = 3000; // 10 sec
        Handler handler = new Handler();
        handler.postDelayed(new Runnable()  {
            @Override
            public void run() {
                progressCircular.setVisibility(View.GONE);
            }
        }, intervalTime);
        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent=new Intent(MainActivity.this,DashboardActivity.class);
                startActivity(intent);*/
                Intent intent=googleAuthentication.getmSignInClient().getSignInIntent();
                startActivityForResult(intent,RC_SIGN_IN);
            }
        });
        phoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AdminLogin.class);
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            googleAuthentication.handleSignInResult(result,MainActivity.this,name,idToken,email,id);


        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //googleAuthentication.onStart(firebaseAuth,authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //googleAuthentication.onStop(firebaseAuth,authStateListener);
    }

}