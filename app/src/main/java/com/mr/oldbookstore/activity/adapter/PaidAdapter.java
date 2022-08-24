package com.mr.oldbookstore.activity.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.mr.oldbookstore.ItemClickListener;
import com.mr.oldbookstore.R;
import com.mr.oldbookstore.model.ModelPaid;
import com.mr.oldbookstore.model.ModelPrice;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PaidAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private Activity mActivity;
    private ArrayList<ModelPaid> mContentList;
    private static ItemClickListener clickListener;
    public PaidAdapter(Context mContext, Activity mActivity, ArrayList<ModelPaid> mContentList) {
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.mContentList = mContentList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.paid, parent, false);
        return new ViewHolder(view, viewType);
    }
    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private CardView cardView;
        private ImageView imageUri;
        private TextView book_title;
        private TextView price;
        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            // Find all views ids
            cardView = (CardView) itemView.findViewById(R.id.paid_cardView);
            imageUri = (ImageView) itemView.findViewById(R.id.image_paid);
            book_title = (TextView) itemView.findViewById(R.id.book_title_paid);
            price=(TextView) itemView.findViewById(R.id.price_paid);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClickPaid(view, getAdapterPosition());
            //if (clickListener != null) clickListener.onClickFree(view, getAdapterPosition());
        }
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder mainHolder, int position) {
        ViewHolder holder = (ViewHolder) mainHolder;
        final ModelPaid model = mContentList.get(position);
        // setting data over views
        String imgUrl = model.getImageUri();
        Log.d("myApp", imgUrl.toString());
        if (imgUrl != null && !imgUrl.isEmpty()) {
            Picasso.get().load(imgUrl.toString()).fit().into(holder.imageUri);
        }
        holder.book_title.setText(model.getBook_title());
        holder.price.setText(model.getPrice());
    }
    @Override
    public int getItemCount() {
        return mContentList.size();
    }
}