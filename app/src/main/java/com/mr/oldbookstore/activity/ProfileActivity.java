package com.mr.oldbookstore.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mr.oldbookstore.R;
import com.mr.oldbookstore.model.ModelUsersData;

import java.io.IOException;
import java.util.UUID;

public class ProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Spinner spinner;
    private static String location;
    private String[] array;
    private EditText edt_name;
    private EditText edt_phoneNumber;
    private EditText edt_email;
    private Button btn_submit;
    private FrameLayout frameLayout;
    private final int PICK_IMAGE_REQUEST = 22;
    private static Uri filePath;
    private static String imageUri;
    private static ImageView profile;
    StorageReference storageReference;
    private DatabaseReference database;
    private static String point;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        spinner=findViewById(R.id.spinner_location_profile);
        edt_name=findViewById(R.id.profile_name);
        edt_email=findViewById(R.id.profile_email);
        edt_phoneNumber=findViewById(R.id.profile_phoneNumber);
        frameLayout=findViewById(R.id.frameLayout_image_activity_profile);
        profile=findViewById(R.id.profilePicActivityProfile);
        storageReference= FirebaseStorage.getInstance("gs://old-book-store-144a2.appspot.com").getReference();
        btn_submit=findViewById(R.id.bt_submit_profile);
        array=getApplicationContext().getResources().getStringArray(R.array.locations);
        database=FirebaseDatabase.getInstance("https://old-book-store-144a2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Users");
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,array);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);
        ProgressDialog progressDialog
                = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Log.d("myApp",dataSnapshot.getKey());
                    if(dataSnapshot.getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        ModelUsersData modelUsersData=dataSnapshot.getValue(ModelUsersData.class);
                        edt_name.setText(modelUsersData.getName());
                        edt_email.setText(modelUsersData.getEmail());
                        edt_phoneNumber.setText(modelUsersData.getPhoneNumber());
                        point=modelUsersData.getPoint();
                    }
                }
                progressDialog.dismiss();
                /*btn_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!TextUtils.isEmpty(edt_name.getText())){
                            addToFirebase(edt_phoneNumber.getText().toString(),edt_name.getText().toString(),edt_email.getText().toString(),location,point,FirebaseAuth.getInstance().getCurrentUser().getUid());
                        }
                    }
                });*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myApp","times");
                if(!TextUtils.isEmpty(edt_name.getText())){
                    uploadImage();
                    //addToFirebase(edt_phoneNumber.getText().toString(),edt_name.getText().toString(),edt_email.getText().toString(),location,point,FirebaseAuth.getInstance().getCurrentUser().getUid());
                }
                else
                    Toast.makeText(getApplicationContext(),"Name Cannot be Empty",Toast.LENGTH_SHORT).show();
            }
        });
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        location=array[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        location="None";
    }

    public void addToFirebase(String phoneNumber,String name,String email,String city,String point,String userID,String imageUri){
        ModelUsersData modelUsersData=new ModelUsersData(phoneNumber,name,email,city,point,userID,imageUri);
        Log.d("myApp",modelUsersData.getCity());
        database.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(modelUsersData);
        Log.d("myApp",database.getRoot().toString());
        Intent intent=new Intent(ProfileActivity.this,DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        ProfileActivity.this.finish();
    }

    private void uploadImage()
    {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(ProfileActivity.this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref = storageReference.child("profiles/" + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            imageUri=uri.toString();
                                            Log.d("myApp",imageUri);
                                            addToFirebase(edt_phoneNumber.getText().toString(),edt_name.getText().toString(),edt_email.getText().toString(),location,point,FirebaseAuth.getInstance().getCurrentUser().getUid(),imageUri);

                                        }
                                    });

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();


                                    Toast.makeText(ProfileActivity.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(ProfileActivity.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploading.... ");
                                }
                            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            Intent intent=new Intent(ProfileActivity.this, DashboardActivity.class);
                            startActivity(intent);
                            finish();

                        }
                    });
        }
    }
    private void SelectImage()
    {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            //uploadImage();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                profile.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

}