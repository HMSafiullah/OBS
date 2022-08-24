package com.mr.oldbookstore.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mr.oldbookstore.R;
import com.mr.oldbookstore.activity.adapter.MessageAdapter;
import com.mr.oldbookstore.databinding.FragmentInboxBinding;
import com.mr.oldbookstore.model.ModelMessage;
import com.mr.oldbookstore.model.ModelUsersData;

import java.sql.Timestamp;
import java.util.UUID;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    MessageAdapter adapter; // Create Object of the Adapter class
    DatabaseReference mbase; // Create object of the
    // Firebase Realtime Database
    DatabaseReference databaseReference;
    private EditText message;
    private ImageView sendMessage;
    /*private TextView txt_to;
    private TextView txt_from;*/
    private static ModelUsersData modelUsersData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        String id=getIntent().getStringExtra("id");
        //txt_to=findViewById(R.id.)


        databaseReference = FirebaseDatabase.getInstance("https://old-book-store-144a2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Users");
        //mRef_Messages = FirebaseDatabase.getInstance("https://old-book-store-144a2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Messages");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    modelUsersData=dataSnapshot.getValue(ModelUsersData.class);
                    if(modelUsersData.getUserID().equals(id)){
                        break;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        mbase = FirebaseDatabase.getInstance("https://old-book-store-144a2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Messages");
/*
        mbase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String user1=dataSnapshot.getKey().split("_")[0];
                    String user2=dataSnapshot.getKey().split("_")[1];
                    if(id.equals(user2) && user1.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            ModelMessage modelMessage=childSnapshot.getValue(ModelMessage.class);
                            Toast.makeText(getApplicationContext(),"message: "+ modelMessage.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
*/


        recyclerView = findViewById(R.id.recycler_chat);


        // To display the Recycler view linearly
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL, false));

        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        FirebaseRecyclerOptions<ModelMessage> options
                = new FirebaseRecyclerOptions.Builder<ModelMessage>()
                .setQuery(mbase.child(FirebaseAuth.getInstance().getCurrentUser().getUid()+"_"+id).orderByChild("time"), ModelMessage.class)
                .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        adapter = new MessageAdapter(options);
        // Connecting Adapter class with the Recycler view*/
        recyclerView.setAdapter(adapter);

        message=findViewById(R.id.message_chat);
        sendMessage=findViewById(R.id.send_message_chat);

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mbase == null) {
                    addToMessages(id, message.getText().toString());
                    message.setText("");
                }
                else {
                    mbase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                //String path=dataSnapshot.getKey().toString().split("_")[0];
                                Log.d("myApp", String.valueOf(dataSnapshot.getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid() + "_" + modelUsersData.getUserID())));
                                if (dataSnapshot.getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid() + "_" + id)) {
                                    addToMessages(id,message.getText().toString());
                                    Log.d("myApp","reached or not");
                                    message.setText("");
                                    break;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });




                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    /*public void addToFirebase(String id, String messageTo,String messageFrom){
        ModelMessage modelMessage=new ModelMessage(id,messageTo,messageFrom);
        Log.d("myApp",modelMessage.getId());
        mbase.child(UUID.randomUUID().toString()).setValue(modelMessage);
    }*/
    public void addToMessages(String id,String message){
        //Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
        //ModelUsersData modelUsersData=new ModelUsersData(phoneNumber,name,email,city,point,userID,imageUri);
        if(!TextUtils.isEmpty(message)) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            ModelMessage modelMessage1 = new ModelMessage(id, "To:" + message,String.valueOf(timestamp.getTime()));
            ModelMessage modelMessage2 = new ModelMessage(FirebaseAuth.getInstance().getCurrentUser().getUid(), "From:" + message,String.valueOf(timestamp.getTime()));
            Log.d("myApp", modelMessage1.getMessage());
            Log.d("myApp", modelMessage2.getMessage());
            Log.d("myApp", id);
            mbase.child(FirebaseAuth.getInstance().getCurrentUser().getUid() + "_" + id).child(UUID.randomUUID().toString()).setValue(modelMessage1);
            mbase.child(id + "_" + FirebaseAuth.getInstance().getCurrentUser().getUid()).child(UUID.randomUUID().toString()).setValue(modelMessage2);

            //Toast.makeText(this, "Chat initiated", Toast.LENGTH_SHORT).show();
        /*Intent intent=new Intent(DisplayAdActivity.this, ChatActivity.class);
        startActivity(intent);*/
            Log.d("myApp", mbase.getRoot().toString());
        }
    }
}