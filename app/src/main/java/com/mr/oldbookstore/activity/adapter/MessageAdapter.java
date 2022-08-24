package com.mr.oldbookstore.activity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.mr.oldbookstore.R;
import com.mr.oldbookstore.model.ModelMessage;

public class MessageAdapter extends FirebaseRecyclerAdapter<
        ModelMessage, MessageAdapter.MessageViewholder> {

    public MessageAdapter(
            @NonNull FirebaseRecyclerOptions<ModelMessage> options)
    {
        super(options);
    }

    // Function to bind the view in Card view(here
    // "person.xml") iwth data in
    // model class(here "person.class")
    @Override
    protected void onBindViewHolder(@NonNull MessageViewholder holder,
                     int position, @NonNull ModelMessage model)
    {

        // Add firstname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")

        if(model.getMessage().contains("To:")) {
            holder.messageFrom.setVisibility(View.GONE);
            holder.messageTo.setVisibility(View.VISIBLE);
            String message = model.getMessage().split(":")[1];
            holder.messageTo.setText(message);
            //holder.messageFrom.setVisibility(View.GONE);
        }
        /*}else{
            holder.messageTo.setVisibility(View.GONE);
        }*/

        if(model.getMessage().contains("From:")){
            holder.messageTo.setVisibility(View.GONE);
            holder.messageFrom.setVisibility(View.VISIBLE);
            String message=model.getMessage().split(":")[1];
            holder.messageFrom.setText(message);
            //holder.messageTo.setVisibility(View.GONE);

        }/*else{
            holder.messageFrom.setVisibility(View.GONE);
        }*/

            //String message=model.getMessageFrom().split(":")[1];



        // Add lastname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")


        // Add age from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
    }

    // Function to tell the class about the Card view (here
    // "person.xml")in
    // which the data will be shown
    @NonNull
    @Override
    public MessageViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message, parent, false);
        return new MessageAdapter.MessageViewholder(view);
    }

    // Sub Class to create references of the views in Crad
    // view (here "person.xml")
    class MessageViewholder extends RecyclerView.ViewHolder {
        TextView messageTo,messageFrom;
        public MessageViewholder(@NonNull View itemView)
        {
            super(itemView);

            //id = itemView.findViewById(R.id.name);
            messageTo = itemView.findViewById(R.id.txt_to);
            messageFrom = itemView.findViewById(R.id.txt_from);
        }
    }
}

