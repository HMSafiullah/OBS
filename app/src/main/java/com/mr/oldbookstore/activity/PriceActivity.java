package com.mr.oldbookstore.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mr.oldbookstore.R;
import com.mr.oldbookstore.model.ModelPrice;

import java.io.IOException;
import java.util.UUID;

public class PriceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private DatabaseReference database;
    //private Button bt_image_url;
    LinearLayout main_price;
    LinearLayout front_price;
    LinearLayout back_price;
    private Button bt_price_publish;
    private EditText edt_price;
    private EditText edt_book_title;
    private EditText edt_description;
    private Spinner spinner;
    private static Uri filePathMain;
    private static Uri filePathFront;
    private static Uri filePathBack;
    //private static Uri filePath;
    private static String location;
    private String[] array;
    //private static String imageUri;
    private static String imageUriMain=null;
    private static String imageUriBack=null;
    private static String imageUriFront=null;
    String category = "";
    private ImageView imageViewMain;
    private ImageView imageViewBack;
    private ImageView imageViewFront;


    // request code
    private final int PICK_IMAGE_REQUEST_MAIN = 22;
    private final int PICK_IMAGE_REQUEST_FRONT = 23;
    private final int PICK_IMAGE_REQUEST_BACK = 24;

    // instance for firebase storage and StorageReference
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);


        imageViewMain=findViewById(R.id.image_main_price);
        imageViewBack=findViewById(R.id.image_back_price);
        imageViewFront=findViewById(R.id.image_front_price);
        storageReference=FirebaseStorage.getInstance("gs://old-book-store-144a2.appspot.com").getReference();
        database=FirebaseDatabase.getInstance("https://old-book-store-144a2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        edt_price=findViewById(R.id.price);
        edt_book_title=findViewById(R.id.book_title);
        edt_description=findViewById(R.id.description);
        //bt_image_url=findViewById(R.id.btn_image_url);
        main_price=findViewById(R.id.main_price);
        front_price=findViewById(R.id.front_price);
        back_price=findViewById(R.id.back_price);
        spinner=findViewById(R.id.spinner_location);
        bt_price_publish=findViewById(R.id.bt_price_publish);
        array=getApplicationContext().getResources().getStringArray(R.array.locations);
        category=getIntent().getExtras().getString("category");

        spinner.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,array);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);

        main_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImageMain();
            }
        });
        front_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImageFront();
            }
        });
        back_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImageBack();
            }
        });

        bt_price_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImageFront();

            }
        });
    }
    private void SelectImageMain()
    {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST_MAIN);
    }

    private void SelectImageFront()
    {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST_FRONT);
    }

    private void SelectImageBack()
    {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST_BACK);
    }

    // Override onActivityResult method
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST_MAIN
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePathMain = data.getData();
            //uploadImage();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePathMain);
                imageViewMain.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }

        if (requestCode == PICK_IMAGE_REQUEST_FRONT
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePathFront = data.getData();
            //uploadImage();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePathFront);
                imageViewFront.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }

        if (requestCode == PICK_IMAGE_REQUEST_BACK
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePathBack = data.getData();
            //uploadImage();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePathBack);
                imageViewBack.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    // UploadImage method
    private void uploadImageMain()
    {
        if (filePathMain != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference.child("images/" + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePathMain)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            imageUriMain=uri.toString();
                                            Log.d("myApp",imageUriMain);
                                            addToFirebase(edt_price.getText().toString(),edt_book_title.getText().toString(),edt_description.getText().toString(),location, FirebaseAuth.getInstance().getCurrentUser().getUid(),category,imageUriMain,imageUriFront,imageUriBack);


                                        }
                                    });

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();


                                    Toast.makeText(PriceActivity.this,
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
                                    .makeText(PriceActivity.this,
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
                            Intent intent=new Intent(PriceActivity.this, DashboardActivity.class);
                            Toast.makeText(getApplicationContext(),"Main Image Uploaded",Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            finish();

                        }
                    });
        }
    }
    private void uploadImageFront()
    {
        if (filePathFront != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference.child("images/" + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePathFront)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            imageUriFront=uri.toString();
                                            Log.d("myApp",imageUriFront);
                                            //addToFirebase(edt_book_title.getText().toString(),edt_description.getText().toString(),location, FirebaseAuth.getInstance().getCurrentUser().getUid(),category,imageUriMain,imageUriFront,imageUriBack);


                                        }
                                    });

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();


                                    Toast.makeText(PriceActivity.this,
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
                                    .makeText(PriceActivity.this,
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
                            Toast.makeText(getApplicationContext(),"Front Image Uploaded",Toast.LENGTH_SHORT).show();
                            uploadImageBack();
                            /*Intent intent=new Intent(FreeActivity.this, DashboardActivity.class);
                            startActivity(intent);
                            finish();*/

                        }
                    });
        }
        else{
            uploadImageBack();
        }
    }
    private void uploadImageBack()
    {
        if (filePathBack != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference.child("images/" + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePathBack)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            imageUriBack=uri.toString();
                                            Log.d("myApp",imageUriBack);
                                            //addToFirebase(edt_book_title.getText().toString(),edt_description.getText().toString(),location, FirebaseAuth.getInstance().getCurrentUser().getUid(),category,imageUriMain,imageUriFront,imageUriBack);


                                        }
                                    });

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();


                                    Toast.makeText(PriceActivity.this,
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
                                    .makeText(PriceActivity.this,
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
                            Toast.makeText(getApplicationContext(),"Back Image Saved",Toast.LENGTH_SHORT).show();
                            uploadImageMain();
                            /*Intent intent=new Intent(FreeActivity.this, DashboardActivity.class);
                            startActivity(intent);
                            finish();*/

                        }
                    });
        }else{
            uploadImageMain();
        }
    }
    private void addToFirebase(String price,String book_title,String description,String location,String userID,String category,String imageUriMain,String imageUriFront,String imageUriBack){
        ModelPrice modelPrice=new ModelPrice(price,book_title,description,location,userID,category,imageUriMain,imageUriFront,imageUriBack);
        database.child("Prices").child(UUID.randomUUID().toString()).setValue(modelPrice);
        Log.d("myApp",database.getRoot().toString());


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        location=array[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}