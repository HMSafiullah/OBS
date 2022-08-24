package com.mr.oldbookstore.activity.ui.profile;

import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.mr.oldbookstore.activity.DashboardActivity;
import com.mr.oldbookstore.activity.FreeActivity;
import com.mr.oldbookstore.activity.ProfileActivity;
import com.mr.oldbookstore.databinding.FragmentProfileBinding;
import com.mr.oldbookstore.model.ModelUsersData;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.UUID;


public class ProfileFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    private Spinner spinner;
    private static String location;
    private String[] array;
    private EditText edt_name;
    private EditText edt_phoneNumber;
    private EditText edt_email;
    private Button btn_submit;
    private DatabaseReference database;
    private static String point;
    private FrameLayout frameLayout;
    private final int PICK_IMAGE_REQUEST = 22;
    private static Uri filePath;
    private static String imageUri;
    private static ImageView profile;
    StorageReference storageReference;
    public ProfileFragment(){

    }

    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        spinner=binding.spinnerLocationProfileFragment;
        edt_name=binding.profileNameFragment;
        edt_email=binding.profileEmailFragment;
        edt_phoneNumber=binding.profilePhoneNumberFragment;
        btn_submit=binding.btSubmitProfileFragment;
        frameLayout=binding.frameLayoutImageProfile;
        profile=binding.profilePicProfile;
        storageReference= FirebaseStorage.getInstance("gs://old-book-store-144a2.appspot.com").getReference();
        array=getContext().getResources().getStringArray(R.array.locations);
        database= FirebaseDatabase.getInstance("https://old-book-store-144a2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Users");







        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Log.d("myApp",dataSnapshot.getKey());
                    if(dataSnapshot.getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        ModelUsersData modelUsersData=dataSnapshot.getValue(ModelUsersData.class);
                        Picasso.get().load(modelUsersData.getImageUri()).fit().into(profile);
                        edt_name.setText(modelUsersData.getName());
                        edt_email.setText(modelUsersData.getEmail());
                        edt_phoneNumber.setText(modelUsersData.getPhoneNumber());
                        point=modelUsersData.getPoint();
                        location=modelUsersData.getCity();
                        imageUri=modelUsersData.getImageUri();
                    }
                }
                spinner.setOnItemSelectedListener(ProfileFragment.this);
                ArrayAdapter aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,array);
                int pos=aa.getPosition(location);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //Setting the ArrayAdapter data on the Spinner

                spinner.setAdapter(aa);
                Log.d("myApp", String.valueOf(pos));
                spinner.setSelection(pos);
                aa.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(edt_name.getText())){

                    uploadImage();

/*
                    addToFirebase(edt_phoneNumber.getText().toString(),edt_name.getText().toString(),edt_email.getText().toString(),location,point,FirebaseAuth.getInstance().getCurrentUser().getUid(),imageUri);
*/
                }
            }
        });
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });



        return root;
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

        //Toast.makeText(getContext(),"Profile Updated",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void uploadImage()
    {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(getContext());
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


                                    Toast.makeText(getContext(),
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
                                    .makeText(getContext(),
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
                            Intent intent=new Intent(getContext(), DashboardActivity.class);
                            startActivity(intent);
                            getActivity().finish();

                        }
                    });
        }else{
            addToFirebase(edt_phoneNumber.getText().toString(),edt_name.getText().toString(),edt_email.getText().toString(),location,point,FirebaseAuth.getInstance().getCurrentUser().getUid(),imageUri);
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
                && resultCode == getActivity().RESULT_OK
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
                                getActivity().getContentResolver(),
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