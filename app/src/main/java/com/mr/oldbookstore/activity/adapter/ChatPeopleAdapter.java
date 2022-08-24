package com.mr.oldbookstore.activity.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.mr.oldbookstore.R;
import com.mr.oldbookstore.activity.ChatActivity;
import com.mr.oldbookstore.model.ModelChatPeople;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChatPeopleAdapter extends RecyclerView.Adapter<ChatPeopleAdapter.ViewHolder>{
    private ArrayList<ModelChatPeople> listdata;
    private Activity activity;

    // RecyclerView recyclerView;
    public ChatPeopleAdapter(ArrayList<ModelChatPeople> listdata, Activity activity) {
        this.listdata = listdata;
        this.activity=activity;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_chats, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ModelChatPeople myListData = listdata.get(position);
        holder.textView.setText(myListData.getName());
        String imgUrl=myListData.getImageUri();
        if (imgUrl != null && !imgUrl.isEmpty()) {
            Picasso.get().load(imgUrl.toString()).fit().into(holder.imageView);
        }
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"click on item: "+myListData.getName(),Toast.LENGTH_LONG).show();
                Intent intent=new Intent(activity, ChatActivity.class);
                intent.putExtra("id",myListData.getId());
                activity.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView_chatpeople);
            this.textView = (TextView) itemView.findViewById(R.id.textView_chatpeople);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout_chatpeople);
        }
    }
}