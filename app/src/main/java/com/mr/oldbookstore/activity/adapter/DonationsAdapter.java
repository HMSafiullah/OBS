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

import com.mr.oldbookstore.ItemClickListener;
import com.mr.oldbookstore.R;
import com.mr.oldbookstore.model.ModelDonations;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DonationsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private Activity mActivity;
    private ArrayList<ModelDonations> mContentList;
    private static ItemClickListener clickListener;
    public DonationsAdapter(Context mContext, Activity mActivity, ArrayList<ModelDonations> mContentList) {
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.mContentList = mContentList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.free, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CardView cardView;
        private ImageView imageUri;
        private TextView book_title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Find all views ids
            cardView = (CardView) itemView.findViewById(R.id.free_cardView);
            imageUri = (ImageView) itemView.findViewById(R.id.image_free);
            book_title = (TextView) itemView.findViewById(R.id.book_title_free);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //if (clickListener != null) clickListener.onClickPaid(view, getAdapterPosition());
            if (clickListener != null) clickListener.onClickFree(view, getAdapterPosition());
        }
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder mainHolder, int position) {
        ViewHolder holder = (ViewHolder) mainHolder;
        final ModelDonations model = mContentList.get(position);
        // setting data over views
        String imgUrl = model.getImageUri();
        Log.d("myApp", imgUrl.toString());
        if (imgUrl != null && !imgUrl.isEmpty()) {
            Picasso.get().load(imgUrl.toString()).fit().into(holder.imageUri);
        }
        holder.book_title.setText(model.getBook_title());
    }
    @Override
    public int getItemCount() {
        if(mContentList !=null) {
            return mContentList.size();
        }else
            return 0;
    }

}
