package com.mr.oldbookstore.activity.ui.inbox;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mr.oldbookstore.R;
import com.mr.oldbookstore.activity.adapter.ChatPeopleAdapter;
import com.mr.oldbookstore.activity.adapter.MessageAdapter;
import com.mr.oldbookstore.databinding.FragmentInboxBinding;
import com.mr.oldbookstore.model.ModelChatPeople;
import com.mr.oldbookstore.model.ModelMessage;
import com.mr.oldbookstore.model.ModelUsersData;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;


public class InboxFragment extends Fragment {

    private FragmentInboxBinding binding;
    private static RecyclerView recyclerView;
    private static ArrayList<ModelChatPeople> chatPeopleArrayList;
    private static ChatPeopleAdapter chatPeopleAdapter=null;
    private static ArrayList<ModelUsersData> modelUsersDataArrayList;
    DatabaseReference databaseReference;
    DatabaseReference database;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInboxBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        modelUsersDataArrayList=new ArrayList<>();
        chatPeopleArrayList=new ArrayList<>();
        recyclerView=binding.recyclerInbox;

        database=FirebaseDatabase.getInstance("https://old-book-store-144a2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Users");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    ModelUsersData modelUsersData=dataSnapshot.getValue(ModelUsersData.class);
                    modelUsersDataArrayList.add(modelUsersData);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        databaseReference=FirebaseDatabase.getInstance("https://old-book-store-144a2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Messages");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatPeopleArrayList.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String id=dataSnapshot.getKey().split("_")[1];
                    String id2=dataSnapshot.getKey().split("_")[0];
                    for(int i=0;i<modelUsersDataArrayList.size();i++){
                        if(id.equals(modelUsersDataArrayList.get(i).getUserID()) && !id.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())&&id2.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                            ModelChatPeople chatPeople=new ModelChatPeople(modelUsersDataArrayList.get(i).getName(),modelUsersDataArrayList.get(i).getImageUri(),modelUsersDataArrayList.get(i).getUserID());
                            Log.d("myApp",chatPeople.getName());
                            chatPeopleArrayList.add(chatPeople);
                        }
                    }

                }
                chatPeopleAdapter=new ChatPeopleAdapter(chatPeopleArrayList,getActivity());
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(chatPeopleAdapter);
                chatPeopleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        return  root;
    }


    @Override
    public void onStart()
    {
        super.onStart();

    }

    // Function to tell the app to stop getting
    // data from database on stopping of the activity

    @Override
    public void onDestroy() {
        super.onDestroy();

    }




}