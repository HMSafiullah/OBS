package com.mr.oldbookstore.firebase;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mr.oldbookstore.R;
import com.mr.oldbookstore.activity.DashboardActivity;
import com.mr.oldbookstore.activity.MainActivity;
import com.mr.oldbookstore.activity.ProfileActivity;
import com.mr.oldbookstore.model.ModelUsersData;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class PhoneNumberAuthentication {
    private static FirebaseAuth mAuth1;
    private static FirebaseAuth mAuth;
    private static DatabaseReference database;
    private static DatabaseReference databaseReference;

    // string for storing our verification ID
    private static String verificationId;
    private static PhoneAuthCredential credential;
    private static String number;
    private static String code;
    private static Activity activity;
    private static ModelUsersData modelUsersData;
    private ModelUsersData blockedUsers;
    private ArrayList<ModelUsersData> blockeduserList;
    private DatabaseReference blockedDatabaseReference;
    private String blockedID="";

    private static EditText otp_text_1;
    private static EditText otp_text_2;
    private static EditText otp_text_3;
    private static EditText otp_text_4;
    private static EditText otp_text_5;
    private static EditText otp_text_6;

    public PhoneNumberAuthentication (){

    }

    public PhoneNumberAuthentication(Activity activity){
        database= FirebaseDatabase.getInstance("https://old-book-store-144a2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        databaseReference= FirebaseDatabase.getInstance("https://old-book-store-144a2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Users");
        mAuth1=FirebaseAuth.getInstance();
        mAuth=FirebaseAuth.getInstance();
        this.credential=credential;
        this.number=number;
        this.code=code;
        this.activity=activity;
        blockeduserList=new ArrayList<>();

        otp_text_1=activity.findViewById(R.id.otp1);
        otp_text_2=activity.findViewById(R.id.otp2);
        otp_text_3=activity.findViewById(R.id.otp3);
        otp_text_4=activity.findViewById(R.id.otp4);
        otp_text_5=activity.findViewById(R.id.otp5);
        otp_text_6=activity.findViewById(R.id.otp6);

        blockedDatabaseReference= FirebaseDatabase.getInstance("https://old-book-store-144a2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Blocked");
        if(blockedDatabaseReference!=null) {
            blockedDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                        blockedUsers=dataSnapshot.getValue(ModelUsersData.class);
                        blockeduserList.add(blockedUsers);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }



    }

    private static void signInWithCredential(PhoneAuthCredential credential,Activity activity) {
        final Boolean[] check = {true};
        // inside this method we are checking if
        // the code entered is correct or not.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    modelUsersData=dataSnapshot.getValue(ModelUsersData.class);
                    if(FirebaseAuth.getInstance().getCurrentUser().getUid().toString().equals(dataSnapshot.getKey())){
                        check[0] =false;
                        Log.d("myApp","check1"+check[0]);
                        break;
                    }
                }
                Log.d("myApp","check2"+check[0]);
                mAuth1.signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // if the code is correct and the task is successful
                                    // we are sending our user to new activity.
                                    /*if(modelUsersData.getName().isEmpty()) {
                                        if (check[0]) {
                                            Log.d("myApp", "getuid: " + FirebaseAuth.getInstance().getCurrentUser().getUid() + "boolean: " + check[0]);
                                            AddToFirebase(number, "", "", "", "5", FirebaseAuth.getInstance().getCurrentUser().getUid());
                                        } else {
                                            Intent i = new Intent(activity, ProfileActivity.class);
                                            activity.startActivity(i);
                                            activity.finish();
                                        }
                                    }else {
                                        Intent intent=new Intent(activity,DashboardActivity.class);
                                        activity.startActivity(intent);
                                    }*/


                                } else {
                                    // if the code is not correct then we are
                                    // displaying an error message to the user.
                                    Toast.makeText(activity.getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                                if(task.isComplete()){
                                     Toast.makeText(activity.getApplicationContext(),"Task is Completed",Toast.LENGTH_SHORT).show();
                                    databaseReference.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                                                modelUsersData=dataSnapshot.getValue(ModelUsersData.class);
                                                if(FirebaseAuth.getInstance().getCurrentUser().getUid().toString().equals(dataSnapshot.getKey())){
                                                    check[0] =false;
                                                    Log.d("myApp", String.valueOf(check[0]));
                                                    break;
                                                }else {
                                                    modelUsersData = null;
                                                }
                                            }
                                            if(modelUsersData!=null) {
                                                if (modelUsersData.getName().isEmpty()||modelUsersData.getCity().isEmpty()||modelUsersData.getPhoneNumber().isEmpty()||modelUsersData.getImageUri().isEmpty()) {
                                                    if (check[0]) {
                                                        Log.d("myApp", "getuid: " + "ran");
                                                        AddToFirebase(number, "", "", "", "5", FirebaseAuth.getInstance().getCurrentUser().getUid(),"");
                                                    } else {
                                                        Intent i = new Intent(activity, ProfileActivity.class);
                                                        activity.startActivity(i);
                                                        Toast.makeText(activity.getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                                                        //activity.finish();
                                                    }
                                                } else {
                                                    Intent intent = new Intent(activity, DashboardActivity.class);
                                                    activity.startActivity(intent);
                                                    Toast.makeText(activity, "Login successful", Toast.LENGTH_SHORT).show();
                                                    //activity.finish();
                                                }
                                            }
                                            else{
                                                AddToFirebase(number, "", "", "", "5", FirebaseAuth.getInstance().getCurrentUser().getUid(),"");
                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }else{
                                    Log.w("PhoneNumber", "signInWithCredential" + task.getException().getMessage());
                                    task.getException().printStackTrace();
                                    Toast.makeText(activity, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        /*mAuth1.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // if the code is correct and the task is successful
                            // we are sending our user to new activity.
                                if (check[0]) {
                                    Log.d("myApp", "getuid: " + FirebaseAuth.getInstance().getCurrentUser().getUid() + "boolean: " + check[0]);
                                    AddToFirebase(number, "", "", "", "5", FirebaseAuth.getInstance().getCurrentUser().getUid());
                                } else {
                                    Intent i = new Intent(activity, ProfileActivity.class);
                                    activity.startActivity(i);
                                    activity.finish();
                                }


                        } else {
                            // if the code is not correct then we are
                            // displaying an error message to the user.
                            Toast.makeText(activity.getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });*/
    }


    public void sendVerificationCode(String number) {
        final Boolean[] check = {true,true};
        for(int i=0;i<blockeduserList.size();i++){
            if(blockeduserList.get(i).getPhoneNumber().equals(number)){
                Intent intent=new Intent(activity, MainActivity.class);
                activity.startActivity(intent);
                check[1]=false;
                //activity.finish();
                Toast.makeText(activity.getApplicationContext(),"You are blocked",Toast.LENGTH_SHORT).show();
            }
        }

        // this method is used for getting
        // OTP on user phone number.
        //number="+92 333 5160474";
        String num=number;
        this.number=number;
        number=formatE164Number("PK",number);
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth1)
                        .setPhoneNumber(number)            // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(activity)                 // Activity (for callback binding)
                        .setCallbacks(mCallBack)           // OnVerificationStateChangedCallbacks
                        .build();
        if(check[1]) {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        modelUsersData = dataSnapshot.getValue(ModelUsersData.class);
                        assert modelUsersData != null;
                        if (modelUsersData.getPhoneNumber().equals(num))
                            check[0] = false;
                    }
                    if (check[0]) {
                        PhoneAuthProvider.verifyPhoneNumber(options);
                    } else {
                        Intent intent = new Intent(activity, DashboardActivity.class);
                        activity.startActivity(intent);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }

    // callback method is called on Phone auth provider.
    private static PhoneAuthProvider.OnVerificationStateChangedCallbacks

            // initializing our callbacks for on
            // verification callback method.
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        // below method is used when
        // OTP is sent from Firebase
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            // when we receive the OTP it
            // contains a unique id which
            // we are storing in our string
            // which we have already created.
            verificationId = s;
        }

        // this method is called when user
        // receive OTP from Firebase.
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            // below line is used for getting OTP code
            // which is sent in phone auth credentials.
            final String code = phoneAuthCredential.getSmsCode();

            // checking if the code
            // is null or not.
            if (code != null) {
                // if the code is not null then
                // we are setting that code to
                // our OTP edittext field.
                Log.d("myApp", String.valueOf(code.charAt(0)));
                otp_text_1.setText(String.valueOf(code.charAt(0)));
                otp_text_2.setText(String.valueOf(code.charAt(1)));
                otp_text_3.setText(String.valueOf(code.charAt(2)));
                otp_text_4.setText(String.valueOf(code.charAt(3)));
                otp_text_5.setText(String.valueOf(code.charAt(4)));
                otp_text_6.setText(String.valueOf(code.charAt(5)));
                //edtOTP.setText(code);

                // after setting this code
                // to OTP edittext field we
                // are calling our verifycode method.
                verifyCode(code);
            }
        }

        // this method is called when firebase doesn't
        // sends our OTP code due to any error or issue.
        @Override
        public void onVerificationFailed(FirebaseException e) {
            // displaying error message with firebase exception.
            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("myApp",e.getMessage());
        }
    };

    // below method is use to verify code from Firebase.
    public static void verifyCode(String code) {
        // below line is used for getting
        // credentials from our verification id and code.
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        // after getting credential we are
        // calling sign in method.
        signInWithCredential(credential,activity);
    }
    public static String formatE164Number(String countryCode, String phNum) {

        String e164Number="";
        if (TextUtils.isEmpty(countryCode)) {
            e164Number = phNum;
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                /*e164Number = PhoneNumberUtils.formatNumberToE164(phNum, countryCode);*/
                e164Number =PhoneNumberUtils.formatNumber(phNum, countryCode);
                Log.d("myApp", e164Number);
            }else{

                Log.d("myApp","error Phone Number Authentication");
            }
        }

        return e164Number;
    }
    public static void AddToFirebase(String phoneNumber,String name,String email,String city,String point,String userID,String imageUri){
        ModelUsersData modelUsersData=new ModelUsersData(phoneNumber,name,email,city,point,userID,imageUri);
        Log.d("myApp",modelUsersData.getCity());

        database.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(modelUsersData);
        Log.d("myApp",database.getRoot().toString());
        Intent i = new Intent(activity, ProfileActivity.class);
        activity.startActivity(i);


    }
}
