package com.mr.oldbookstore.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mr.oldbookstore.R;
import com.mr.oldbookstore.activity.adapter.ChatPeopleAdapter;
import com.mr.oldbookstore.activity.adapter.UsersAdapter;
import com.mr.oldbookstore.model.ModelChatPeople;
import com.mr.oldbookstore.model.ModelUsersData;

import java.util.ArrayList;

public class AllUsersActivity extends AppCompatActivity {
    private static RecyclerView recyclerView;
    private static UsersAdapter usersAdapter=null;
    private static ArrayList<ModelUsersData> modelUsersDataArrayList;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);
        modelUsersDataArrayList=new ArrayList<>();
        recyclerView=findViewById(R.id.recycler_all_users);

        database= FirebaseDatabase.getInstance("https://old-book-store-144a2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Users");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    ModelUsersData modelUsersData=dataSnapshot.getValue(ModelUsersData.class);
                    modelUsersDataArrayList.add(modelUsersData);
                }
                usersAdapter=new UsersAdapter(modelUsersDataArrayList,AllUsersActivity.this);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(usersAdapter);
                usersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}