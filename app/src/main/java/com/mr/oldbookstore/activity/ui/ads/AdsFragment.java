package com.mr.oldbookstore.activity.ui.ads;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mr.oldbookstore.ItemClickListener;
import com.mr.oldbookstore.R;
import com.mr.oldbookstore.activity.DisplayAdActivity;
import com.mr.oldbookstore.activity.ViewProfileActivity;
import com.mr.oldbookstore.activity.adapter.DonationsAdapter;
import com.mr.oldbookstore.activity.adapter.PaidAdapter;
import com.mr.oldbookstore.activity.ui.home.HomeFragment;
import com.mr.oldbookstore.databinding.FragmentAdsBinding;
import com.mr.oldbookstore.model.ModelDonations;
import com.mr.oldbookstore.model.ModelFree;
import com.mr.oldbookstore.model.ModelPaid;
import com.mr.oldbookstore.model.ModelPrice;
import com.mr.oldbookstore.model.ModelUsersData;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class AdsFragment extends Fragment implements ItemClickListener {
    private static String category="";
    private static final String TAG = "MainActivity";
    CircularProgressIndicator progressCircular;
    RecyclerView paidRecyclerView;
    DatabaseReference databaseReferencePaid;
    private Context mContext;
    private Activity mActivity;
    @NonNull
    private ArrayList<ModelPaid> paidItemsList;
    private ArrayList<ModelPrice> priceItemsList;
    private PaidAdapter paidAdapter = null;
    /////////////////////////////////////////////////////////////////////////////////////////
    RecyclerView freeRecyclerView;
    DatabaseReference databaseReferenceDonations;
    @NonNull
    private ArrayList<ModelDonations> donationsItemsList;
    private ArrayList<ModelFree> freeItemsList;
    private DonationsAdapter donationsAdapter = null;
    public AdsFragment(){

    }

    private FragmentAdsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAdsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mActivity = getActivity();
        mContext = getContext();
        FirebaseApp.initializeApp(getContext());
        paidRecyclerView=binding.paidAdsRecyclerView;
        freeRecyclerView=binding.freeAdsRecyclerView;
        //recyclerView = findViewById(R.id.recycler_view);
        progressCircular=binding.progressCircular;
        paidRecyclerView.setHasFixedSize(true);
        paidRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
        paidRecyclerView.setNestedScrollingEnabled(false);
        paidItemsList = new ArrayList<>();
        priceItemsList=new ArrayList<>();
        ////////////////////////////////////////////////////////////////////////
        freeRecyclerView.setHasFixedSize(true);
        freeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
        freeRecyclerView.setNestedScrollingEnabled(false);
        donationsItemsList = new ArrayList<>();
        freeItemsList=new ArrayList<>();
        ///////////////////////////////////////////////////////////////////////////////
        databaseReferencePaid = FirebaseDatabase.getInstance("https://old-book-store-144a2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Prices");
        databaseReferencePaid.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                paidItemsList.clear();
                priceItemsList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        ModelPrice pricemodel = dataSnapshot.getValue(ModelPrice.class);
                        if(pricemodel.getUserID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                            Log.d("myApp","current USER: "+FirebaseAuth.getInstance().getCurrentUser().getUid());
                            ModelPaid modelPaid = new ModelPaid(pricemodel.getImageUriMain().toString(), pricemodel.getBook_title().toString(), pricemodel.getPrice().toString());
                            paidItemsList.add(modelPaid);
                            priceItemsList.add(pricemodel);
                        }

                }

                paidAdapter = new PaidAdapter(mContext, mActivity, (ArrayList<ModelPaid>) paidItemsList);
                paidRecyclerView.setAdapter(paidAdapter);
                paidAdapter.setClickListener(AdsFragment.this);
                paidAdapter.notifyDataSetChanged();

                //binding.progressCircular.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Error:" + error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        //////////////////////////////////////////////////////////////////////////////////////
        databaseReferenceDonations = FirebaseDatabase.getInstance("https://old-book-store-144a2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Free");
        databaseReferenceDonations.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                donationsItemsList.clear();
                freeItemsList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        ModelFree freemodel = dataSnapshot.getValue(ModelFree.class);
                        if(freemodel.getUserID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                            ModelDonations modelDonations = new ModelDonations(freemodel.getImageUriMain().toString(), freemodel.getBook_title().toString());
                            donationsItemsList.add(modelDonations);
                            freeItemsList.add(freemodel);
                        }
                }
                donationsAdapter = new DonationsAdapter(mContext, mActivity, (ArrayList<ModelDonations>) donationsItemsList);
                Log.d("myApp",donationsAdapter.toString());
                if(freeRecyclerView.toString().isEmpty()){

                }else {
                    freeRecyclerView.setAdapter(donationsAdapter);
                    donationsAdapter.setClickListener(AdsFragment.this);
                    donationsAdapter.notifyDataSetChanged();
                    progressCircular.setVisibility(View.GONE);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Error:" + error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClickPaid(View view, int position) {
        final ModelPrice modelPrice=priceItemsList.get(position);
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.prompt_ads, null);

        final AlertDialog alertD = new AlertDialog.Builder(requireActivity()).create();

        Button delete = (Button) promptView.findViewById(R.id.delete_ads);
        Button cancel = (Button) promptView.findViewById(R.id.cancel_ads);
        Button viewProfile= (Button) promptView.findViewById(R.id.view_ads);

        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                databaseReferencePaid.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                            ModelPrice modelPrice1=dataSnapshot.getValue(ModelPrice.class);
                            if(modelPrice.getUserID().equals(modelPrice1.getUserID())){
                                dataSnapshot.getRef().removeValue();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Toast.makeText(getActivity().getApplicationContext(), "User Deleted",Toast.LENGTH_SHORT).show();
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
                Intent i = new Intent(getActivity(), DisplayAdActivity.class);
                i.putExtra("price", modelPrice.getPrice());
                i.putExtra("imageUriMain", modelPrice.getImageUriMain());
                i.putExtra("imageUriFront", modelPrice.getImageUriFront());
                i.putExtra("imageUriBack", modelPrice.getImageUriBack());
                i.putExtra("book_title", modelPrice.getBook_title());
                i.putExtra("location", modelPrice.getLocation());
                i.putExtra("category", modelPrice.getCategory());
                i.putExtra("userID", modelPrice.getUserID());
                i.putExtra("description", modelPrice.getDescription());
                alertD.dismiss();
                //Log.i("description", modelPrice.getDescription());
                startActivity(i);
            }
        });

        alertD.setView(promptView);

        alertD.show();
    }

    @Override
    public void onClickFree(View view, int position) {
        final ModelFree modelFree=freeItemsList.get(position);
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.prompt_ads, null);

        final AlertDialog alertD = new AlertDialog.Builder(requireActivity()).create();

        Button delete = (Button) promptView.findViewById(R.id.delete_ads);
        Button cancel = (Button) promptView.findViewById(R.id.cancel_ads);
        Button viewProfile= (Button) promptView.findViewById(R.id.view_ads);

        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                databaseReferenceDonations.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                            ModelFree modelfree1=dataSnapshot.getValue(ModelFree.class);
                            if(modelFree.getUserID().equals(modelfree1.getUserID())){
                                dataSnapshot.getRef().removeValue();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Toast.makeText(getActivity().getApplicationContext(), "User Deleted",Toast.LENGTH_SHORT).show();
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
                Intent i = new Intent(getActivity(), DisplayAdActivity.class);
                i.putExtra("price", "");
                i.putExtra("imageUriMain", modelFree.getImageUriMain());
                i.putExtra("imageUriFront", modelFree.getImageUriFront());
                i.putExtra("imageUriBack", modelFree.getImageUriBack());
                i.putExtra("book_title", modelFree.getBook_title());
                i.putExtra("location", modelFree.getLocation());
                i.putExtra("category", modelFree.getCategory());
                i.putExtra("userID", modelFree.getUserID());
                i.putExtra("description", modelFree.getDescription());
                alertD.dismiss();
                //Log.i("description", modelPrice.getDescription());
                startActivity(i);
            }
        });

        alertD.setView(promptView);

        alertD.show();
    }
}