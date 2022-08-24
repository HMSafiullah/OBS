package com.mr.oldbookstore.activity.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mr.oldbookstore.R;
import com.mr.oldbookstore.activity.ChatActivity;
import com.mr.oldbookstore.activity.ViewProfileActivity;
import com.mr.oldbookstore.model.ModelUsersData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.UUID;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder>{
    private ArrayList<ModelUsersData> listdata;
    private Activity activity;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance("https://old-book-store-144a2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Blocked");

    // RecyclerView recyclerView;
    public UsersAdapter(ArrayList<ModelUsersData> listdata, Activity activity) {
        this.listdata = listdata;
        this.activity=activity;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.users_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ModelUsersData myListData = listdata.get(position);
        holder.name.setText(myListData.getName());
        holder.city.setText(myListData.getCity());
        holder.email.setText(myListData.getEmail());
        holder.phoneNumber.setText(myListData.getPhoneNumber());
        holder.points.setText(myListData.getPoint());
        String imgUrl=myListData.getImageUri();
        if (imgUrl != null && !imgUrl.isEmpty()) {
            Picasso.get().load(imgUrl.toString()).fit().into(holder.imageView);
        }
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"click on item: "+myListData.getName(),Toast.LENGTH_LONG).show();
                LayoutInflater layoutInflater = LayoutInflater.from(activity);
                View promptView = layoutInflater.inflate(R.layout.prompt, null);

                final AlertDialog alertD = new AlertDialog.Builder(activity).create();

                Button block = (Button) promptView.findViewById(R.id.block);
                Button cancel = (Button) promptView.findViewById(R.id.cancel);
                Button viewProfile= (Button) promptView.findViewById(R.id.view);

                block.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        // btnAdd1 has been clicked
                        ModelUsersData modelUsersData=new ModelUsersData(myListData.getPhoneNumber(),myListData.getName(),myListData.getEmail(),myListData.getCity(),myListData.getPoint(),myListData.getUserID(),myListData.getImageUri());
                        databaseReference.child(UUID.randomUUID().toString()).setValue(modelUsersData);
                        Toast.makeText(activity.getApplicationContext(), "User Blocked",Toast.LENGTH_SHORT).show();
                        alertD.dismiss();

                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        // btnAdd2 has been clicked
                        alertD.dismiss();
                    }
                });

                viewProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(activity, ViewProfileActivity.class);
                        intent.putExtra("name",myListData.getName());
                        intent.putExtra("phoneNumber",myListData.getPhoneNumber());
                        intent.putExtra("location",myListData.getCity());
                        intent.putExtra("image",myListData.getImageUri());
                        intent.putExtra("email",myListData.getEmail());
                        alertD.dismiss();
                        activity.startActivity(intent);
                    }
                });

                alertD.setView(promptView);

                alertD.show();

                /*Intent intent=new Intent(activity, ChatActivity.class);
                intent.putExtra("id",myListData.getId());
                activity.startActivity(intent);*/
            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView name;
        public TextView email;
        public TextView phoneNumber;
        public TextView city;
        public TextView points;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView_usersList);
            this.name = (TextView) itemView.findViewById(R.id.name_usersList);
            this.email = (TextView) itemView.findViewById(R.id.email_usersList);
            this.points = (TextView) itemView.findViewById(R.id.points_usersList);
            this.phoneNumber = (TextView) itemView.findViewById(R.id.phoneNumber_usersList);
            this.city = (TextView) itemView.findViewById(R.id.city_usersList);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout_userList);
        }
    }

}
