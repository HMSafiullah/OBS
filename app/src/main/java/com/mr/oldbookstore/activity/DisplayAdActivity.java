package com.mr.oldbookstore.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions;
import com.google.firebase.ml.modeldownloader.DownloadType;
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader;
import com.mr.oldbookstore.R;
import com.mr.oldbookstore.activity.ui.inbox.InboxFragment;
import com.mr.oldbookstore.model.ModelMessage;
import com.mr.oldbookstore.model.ModelPaid;
import com.mr.oldbookstore.model.ModelPrice;
import com.mr.oldbookstore.model.ModelUsersData;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.tensorflow.lite.support.label.Category;
import org.tensorflow.lite.task.text.nlclassifier.NLClassifier;

public class DisplayAdActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    DatabaseReference mRef_Reports,mRef_Messages;
    private static ModelUsersData modelUsersData;

    private static double neg=0.0;
    private static double pos=0.0;


    private static final String TAG = "TextClassificationDemo";

    //private TextView resultTextView;
    //private EditText inputEditText;
    private ExecutorService executorService;
    //private Button predictButton;

    // TODO 5: Define a NLClassifier variable
    private NLClassifier textClassifier;


    private String price;
    private String imageUriMain;
    private String imageUriFront;
    private String imageUriBack;
    private String book_title;
    private String location;
    private String category;
    private String userID;
    private String description;




    /*private boolean flagbad=false;
    private boolean flaggood=false;
    private String[] badWordsArray={"atrocious","awful","cheap quality","crummy","dreadful","lousy",
            "poor","rough","bad","sad","unacceptable","bummer","blah","diddly",
            "downer","garbage","gross","imperfect","inferior","junky","synthetic","abominable",
            "amiss","bad news","beastly","careless","cheesy","crappy","cruddy",
            "defective","deficient","dissatisfactory","dissatisfaction",
            "erroneous","fallacious","faulty","godawful","grody","grungy","icky",
            "inadequate","incorrect","not good","off","raunchy","slipshod","stinking",
            "substandard","the pits","unsatisfactory"};

    private String[] goodWordsArray={"good","useful","adequate","acceptable", "advantageous",
            "appropriate", "beneficial","convenient","decent","desirable","favorable","fruitful",
            "healthy","helpful","profitable","proper","respectable","satisfying","suitable","approving",
            "brave", "common", "fit", "fitting", "meet", "right", "all right", "ample", "apt", "auspicious", "becoming",
            "benefic", "benignant",
            "commendatory", "commending", "conformable", "congruous", "favoring", "healthful", "hygienic", "needed", "opportune",
            "propitious","authentic","real","honest", "legitimate", "proper", "reliable", "true", "valid",
            "kosher", "regular", "sound", "bona fide", "conforming", "dependable", "genuine", "justified", "loyal", "orthodox", "strict", "trustworthy",
            "well-founded","useful"};*/


    private ImageView imageViewMain;
    private ImageView imageViewFront;
    private ImageView imageViewBack;
    private TextView txt_price;
    private TextView txt_book_title;
    private TextView txt_description;
    private TextView txt_location;
    private TextView txt_category;
    private ImageView profile_pic;
    private TextView txt_city;
    private TextView txt_email;
    private TextView txt_name;
    private TextView txt_phoneNumber;
    private EditText edt_feeback;
    private Button btn_chat;
    private Button btn_report;
    private Button btn_dial;
    private Button btn_submit;
    private TextView txt_feedback;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_ad);

        executorService = Executors.newSingleThreadExecutor();
        //resultTextView = findViewById(R.id.result_text_view);
        //inputEditText = findViewById(R.id.input_text);

        databaseReference = FirebaseDatabase.getInstance("https://old-book-store-144a2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Users");
        mRef_Reports = FirebaseDatabase.getInstance("https://old-book-store-144a2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Reports");
        mRef_Messages = FirebaseDatabase.getInstance("https://old-book-store-144a2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Messages");


        price=getIntent().getStringExtra("price");
        imageUriMain=getIntent().getStringExtra("imageUriMain");
        imageUriFront=getIntent().getStringExtra("imageUriFront");
        imageUriBack=getIntent().getStringExtra("imageUriBack");
        book_title=getIntent().getStringExtra("book_title");
        location=getIntent().getStringExtra("location");
        category=getIntent().getStringExtra("category");
        userID=getIntent().getStringExtra("userID");
        description=getIntent().getStringExtra("description");




        imageViewMain=findViewById(R.id.display_ad_image_main);
        imageViewFront=findViewById(R.id.display_ad_image_front);
        imageViewBack=findViewById(R.id.display_ad_image_back);
        txt_price=findViewById(R.id.display_ad_price);
        txt_book_title=findViewById(R.id.display_ad_book_title);
        txt_description=findViewById(R.id.display_ad_description);
        txt_location=findViewById(R.id.display_ad_location);
        txt_category=findViewById(R.id.display_ad_category);
        txt_city=findViewById(R.id.display_ad_city);
        txt_email=findViewById(R.id.display_ad_email);
        txt_name=findViewById(R.id.display_ad_name);
        txt_phoneNumber=findViewById(R.id.display_ad_phoneNumber);
        btn_chat=findViewById(R.id.chat_display_ad);
        btn_report=findViewById(R.id.report_display_ad);
        profile_pic=findViewById(R.id.profile_pic_display_ad);
        btn_dial=findViewById(R.id.dial_display_ad);
        edt_feeback=findViewById(R.id.feedback_display_ad);
        btn_submit=findViewById(R.id.display_ad_submit_activity);
        txt_feedback=findViewById(R.id.txt_feedback);

        if (userID.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
            btn_dial.setVisibility(View.GONE);
            btn_chat.setVisibility(View.GONE);
            btn_report.setVisibility(View.GONE);
            edt_feeback.setVisibility(View.GONE);
            txt_feedback.setVisibility(View.GONE);
            btn_submit.setVisibility(View.GONE);

        }

        if (imageUriMain != null && !imageUriMain.isEmpty()) {
            Picasso.get().load(imageUriMain.toString()).fit().into(imageViewMain);
        }
        if (imageUriFront != null && !imageUriFront.isEmpty()) {
            Picasso.get().load(imageUriFront.toString()).fit().into(imageViewFront);
        }
        if (imageUriBack != null && !imageUriBack.isEmpty()) {
            Picasso.get().load(imageUriBack.toString()).fit().into(imageViewBack);
        }
        txt_price.setText(price.toString());
        txt_book_title.setText(book_title.toString());
        txt_description.setText(description.toString());
        txt_location.setText(location.toString());
        txt_category.setText(category.toString());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if(userID.equals(dataSnapshot.getKey())){
                        modelUsersData=dataSnapshot.getValue(ModelUsersData.class);
                        for (DataSnapshot childSnapshot: dataSnapshot.getChildren()){
                            if(childSnapshot.getKey().equals("city")){
                                txt_city.setText(childSnapshot.getValue().toString());
                            }
                            if(childSnapshot.getKey().equals("email")){
                                txt_email.setText(childSnapshot.getValue().toString());
                            }
                            if(childSnapshot.getKey().equals("name")){
                                txt_name.setText(childSnapshot.getValue().toString());
                            }
                            if(childSnapshot.getKey().equals("phoneNumber")){
                                txt_phoneNumber.setText(childSnapshot.getValue().toString());
                            }
                            if(childSnapshot.getKey().equals("imageUri")){
                                Picasso.get().load(childSnapshot.getValue().toString()).fit().into(profile_pic);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
                if (mRef_Messages == null) {
                    addToMessages(modelUsersData.getUserID(), "Chat Initiated");
                }
                else {
                    mRef_Messages.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChildren()){
                                mRef_Messages.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                            //String path=dataSnapshot.getKey().toString().split("_")[0];
                                            Log.d("myApp", String.valueOf(dataSnapshot.getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid() + "_" + modelUsersData.getUserID())));
                                            if (!dataSnapshot.getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid() + "_" + modelUsersData.getUserID())) {
                                                addToMessages(modelUsersData.getUserID(), "Chat Initiated");
                                            } else{
                                                Toast.makeText(getApplicationContext(),"Go to Inbox to chat",Toast.LENGTH_SHORT).show();
                                                break;
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                            else {
                                addToMessages(modelUsersData.getUserID(), "Chat Initiated");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
            }
        });

        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"User Reported. Admin will deal with it",Toast.LENGTH_SHORT).show();
                addToReportList(modelUsersData.getPhoneNumber(),modelUsersData.getName(),modelUsersData.getEmail(),modelUsersData.getCity(),modelUsersData.getPoint(),modelUsersData.getUserID(),modelUsersData.getImageUri());
            }
        });
        btn_dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+modelUsersData.getPhoneNumber()));
                startActivity(intent);
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myApp",edt_feeback.getText().toString());
                classify(edt_feeback.getText().toString());





                /*for (int i = 0; i <= badWordsArray.length - 1; i++) {

                    if (edt_feeback.getText().toString().contains(badWordsArray[i])) {
                        flagbad = true;

                    }
                }
                for (int i = 0; i <= goodWordsArray.length - 1; i++) {

                    if (edt_feeback.getText().toString().contains(goodWordsArray[i])) {
                        flaggood = true;

                    }
                }
                if (flagbad) {
                    Toast.makeText(getApplicationContext(),"Feedback Submitted",Toast.LENGTH_SHORT).show();
                    int point=Integer.parseInt(modelUsersData.getPoint());
                    if(point>1) {
                        point--;
                    }
                    Log.d("myApp",modelUsersData.getPoint());
                    modelUsersData.setPoint(String.valueOf(point));
                    addToFirebase(modelUsersData.getPhoneNumber(),modelUsersData.getName(),modelUsersData.getEmail(),modelUsersData.getCity(),modelUsersData.getPoint(),modelUsersData.getUserID(),modelUsersData.getImageUri());
                }
                if (flaggood) {
                    Toast.makeText(getApplicationContext(), "Feedback Submitted", Toast.LENGTH_SHORT).show();
                    int point = Integer.parseInt(modelUsersData.getPoint());
                    if (point < 5){
                        point++;
                    }
                    Log.d("myApp",modelUsersData.getPoint());
                    modelUsersData.setPoint(String.valueOf(point));
                    addToFirebase(modelUsersData.getPhoneNumber(),modelUsersData.getName(),modelUsersData.getEmail(),modelUsersData.getCity(),modelUsersData.getPoint(),modelUsersData.getUserID(),modelUsersData.getImageUri());
                }
                else {
                    Toast.makeText(getApplicationContext(),"Feedback Submitted",Toast.LENGTH_SHORT).show();
                }*/
            }
        });
        downloadModel("sentiment_analysis");

    }
    public void addToFirebase(String phoneNumber,String name,String email,String city,String point,String userID,String imageUri){
        ModelUsersData modelUsersData=new ModelUsersData(phoneNumber,name,email,city,point,userID,imageUri);
        Log.d("myApp",modelUsersData.getCity());
        databaseReference.child(userID).setValue(modelUsersData);
        Log.d("myApp",databaseReference.getRoot().toString());
        //Toast.makeText(this,"User Reported",Toast.LENGTH_SHORT).show();
    }
    public void addToReportList(String phoneNumber,String name,String email,String city,String point,String userID,String imageUri){
        ModelUsersData modelUsersData=new ModelUsersData(phoneNumber,name,email,city,point,userID,imageUri);
        Log.d("myApp",modelUsersData.getCity());
        mRef_Reports.child(userID).setValue(modelUsersData);
        Log.d("myApp",mRef_Reports.getRoot().toString());
        Toast.makeText(this,"User Reported",Toast.LENGTH_SHORT).show();
    }
    public void addToMessages(String id,String message){
        //Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
        //ModelUsersData modelUsersData=new ModelUsersData(phoneNumber,name,email,city,point,userID,imageUri);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        ModelMessage modelMessage1=new ModelMessage(id,"To:"+message,String.valueOf(timestamp.getTime()));
        ModelMessage modelMessage2=new ModelMessage(FirebaseAuth.getInstance().getCurrentUser().getUid(),"From:"+message,String.valueOf(timestamp.getTime()));
        Log.d("myApp",modelMessage1.getMessage());
        Log.d("myApp",modelMessage2.getMessage());

        if(!FirebaseAuth.getInstance().getCurrentUser().getUid().equals(id)) {
            Log.d("myApp",id);
            mRef_Messages.child(FirebaseAuth.getInstance().getCurrentUser().getUid() + "_" + id).child(UUID.randomUUID().toString()).setValue(modelMessage1);
            mRef_Messages.child(id+"_"+FirebaseAuth.getInstance().getCurrentUser().getUid()).child(UUID.randomUUID().toString()).setValue(modelMessage2);

            Toast.makeText(this, "Chat initiated", Toast.LENGTH_SHORT).show();
            /*Intent intent=new Intent(DisplayAdActivity.this, ChatActivity.class);
            startActivity(intent);*/
            Log.d("myApp", mRef_Messages.getRoot().toString());
        }
        else{
            Toast.makeText(getApplicationContext(),"This is your own ad",Toast.LENGTH_SHORT).show();
        }
        Intent intent=new Intent(DisplayAdActivity.this, ChatActivity.class);
        intent.putExtra("id",modelUsersData.getUserID());
        startActivity(intent);
    }

    private void classify(final String text) {
        executorService.execute(
                () -> {
                    // TODO 7: Run sentiment analysis on the input text
                    List<Category> results = textClassifier.classify(text);

                    // TODO 8: Convert the result to a human-readable text
                    String textToShow = "Input: " + text + "\nOutput:\n";
                    for (int i = 0; i < results.size(); i++) {
                        Category result = results.get(i);
                        textToShow +=
                                String.format("    %s: %s\n", result.getLabel(), result.getScore());
                        if(result.getLabel().equals("Negative")){
                            neg=result.getScore();
                        }
                        if(result.getLabel().equals("Positive")){
                            pos=result.getScore();
                        }
                        Log.d("myApp", String.valueOf(pos));
                        Log.d("myApp", String.valueOf(neg));
                    }
                    textToShow += "---------\n";

                    if (pos<neg) {
                        //Toast.makeText(getApplicationContext(),"Feedback Submitted",Toast.LENGTH_SHORT).show();
                        int point=Integer.parseInt(modelUsersData.getPoint());
                        if(point>1) {
                            point--;
                        }
                        Log.d("myApp",modelUsersData.getPoint());
                        modelUsersData.setPoint(String.valueOf(point));
                        addToFirebase(modelUsersData.getPhoneNumber(),modelUsersData.getName(),modelUsersData.getEmail(),modelUsersData.getCity(),modelUsersData.getPoint(),modelUsersData.getUserID(),modelUsersData.getImageUri());
                    }
                    if (pos>neg) {
                        //Toast.makeText(getApplicationContext(), "Feedback Submitted", Toast.LENGTH_SHORT).show();
                        int point = Integer.parseInt(modelUsersData.getPoint());
                        if (point < 5){
                            point++;
                        }
                        Log.d("myApp",modelUsersData.getPoint());
                        modelUsersData.setPoint(String.valueOf(point));
                        addToFirebase(modelUsersData.getPhoneNumber(),modelUsersData.getName(),modelUsersData.getEmail(),modelUsersData.getCity(),modelUsersData.getPoint(),modelUsersData.getUserID(),modelUsersData.getImageUri());
                    }
                    else {
                        //Toast.makeText(getApplicationContext(),"Feedback Submitted",Toast.LENGTH_SHORT).show();
                    }



                    if(pos>neg){
                        Log.d("myApp","pos");
                    }
                    if(pos<neg){
                        Log.d("myApp","neg");
                    }
                    // Show classification result on screen
                    showResult(textToShow);
                });
    }

    /** Show classification result on the screen. */
    private void showResult(final String textToShow) {
        // Run on UI thread as we'll updating our app UI
        runOnUiThread(
                () -> {
                    // Append the result to the UI.
                    //resultTextView.append(textToShow);
                    Log.d("myApp",textToShow);
                    Toast.makeText(getApplicationContext(),"Feedback Submitted",Toast.LENGTH_SHORT).show();


                    // Clear the input text.
                    edt_feeback.getText().clear();

                    // Scroll to the bottom to show latest entry's classification result.
                    /*scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));*/
                });
    }

    // TODO 2: Implement a method to download TFLite model from Firebase
    /** Download model from Firebase ML. */
    private synchronized void downloadModel(String modelName) {
        CustomModelDownloadConditions conditions = new CustomModelDownloadConditions.Builder()
                .requireWifi()
                .build();
        FirebaseModelDownloader.getInstance()
                .getModel("sentiment_analysis", DownloadType.LOCAL_MODEL, conditions)
                .addOnSuccessListener(model -> {
                    try {
                        // TODO 6: Initialize a TextClassifier with the downloaded model
                        textClassifier = NLClassifier.createFromFile(model.getFile());
                        btn_submit.setEnabled(true);
                    } catch (IOException e) {
                        Log.e(TAG, "Failed to initialize the model. ", e);
                        Toast.makeText(
                                        DisplayAdActivity.this,
                                        "Model initialization failed.",
                                        Toast.LENGTH_LONG)
                                .show();
                        btn_submit.setEnabled(false);
                    }
                })
                .addOnFailureListener(e -> {
                            Log.e(TAG, "Failed to download the model. ", e);
                            Toast.makeText(
                                            DisplayAdActivity.this,
                                            "Model download failed, please check your connection.",
                                            Toast.LENGTH_LONG)
                                    .show();
                    Toast.makeText(
                                    DisplayAdActivity.this,
                                    "Model redownloading...",
                                    Toast.LENGTH_LONG)
                            .show();
                    downloadModel("sentiment_analysis");


                        }
                );
    }


}