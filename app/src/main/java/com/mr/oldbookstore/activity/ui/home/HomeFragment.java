package com.mr.oldbookstore.activity.ui.home;

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

import android.provider.ContactsContract;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.common.api.Status;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mr.oldbookstore.ItemClickListener;
import com.mr.oldbookstore.R;
import com.mr.oldbookstore.activity.DisplayAdActivity;
import com.mr.oldbookstore.activity.adapter.DonationsAdapter;
import com.mr.oldbookstore.activity.adapter.PaidAdapter;
import com.mr.oldbookstore.databinding.FragmentHomeBinding;
import com.mr.oldbookstore.model.ModelDonations;
import com.mr.oldbookstore.model.ModelFree;
import com.mr.oldbookstore.model.ModelPaid;
import com.mr.oldbookstore.model.ModelPrice;
import com.mr.oldbookstore.model.ModelUsersData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class HomeFragment extends Fragment implements ItemClickListener {
    private static String category="";
    private static final String TAG = "MainActivity";
    CircularProgressIndicator progressCircular;
    RecyclerView paidRecyclerView;
    DatabaseReference databaseReferencePaid;
    private Context mContext;
    private Activity mActivity;
    private static final int AUTOCOMPLETE_REQUEST_CODE=121;
//    private static ModelUsersData modelUsersData;
    @NonNull
    private static ArrayList<ModelUsersData> modelUsersDataArrayList;
    @NonNull
    private ArrayList<ModelPaid> paidItemsList1;
    @NonNull
    private ArrayList<ModelPaid> paidItemsList2;
    @NonNull
    private ArrayList<ModelPaid> paidItemsList3;
    @NonNull
    private ArrayList<ModelPaid> paidItemsList4;
    @NonNull
    private ArrayList<ModelPaid> paidItemsList5;
    @NonNull
    private static ArrayList<ModelPaid> paidItemsListFull;
    //////////////////////////////////////////////////////////////////////////////////////
    private ArrayList<ModelPrice> priceItemsList1;
    private ArrayList<ModelPrice> priceItemsList2;
    private ArrayList<ModelPrice> priceItemsList3;
    private ArrayList<ModelPrice> priceItemsList4;
    private ArrayList<ModelPrice> priceItemsList5;
    private static ArrayList<ModelPrice> priceItemsListFull;

    /////////////////////////////////////////////////////////////////////////////////////////

    private PaidAdapter paidAdapter = null;
    RecyclerView freeRecyclerView;
    DatabaseReference databaseReferenceDonations;
    @NonNull
    private ArrayList<ModelDonations> donationsItemsList1;
    @NonNull
    private ArrayList<ModelDonations> donationsItemsList2;
    @NonNull
    private ArrayList<ModelDonations> donationsItemsList3;
    @NonNull
    private ArrayList<ModelDonations> donationsItemsList4;
    @NonNull
    private ArrayList<ModelDonations> donationsItemsList5;
    @NonNull
    private static ArrayList<ModelDonations> donationsItemsListFull;
    ///////////////////////////////////////////////////////////////////////////////////////
    private ArrayList<ModelFree> freeItemsList1;
    private ArrayList<ModelFree> freeItemsList2;
    private ArrayList<ModelFree> freeItemsList3;
    private ArrayList<ModelFree> freeItemsList4;
    private ArrayList<ModelFree> freeItemsList5;
    private static ArrayList<ModelFree> freeItemsListFull;
    /////////////////////////////////////////////////////////////////////////////////////
    private DonationsAdapter donationsAdapter = null;

    /////////////////////////////////////////////////////////////////////////////////////

    DatabaseReference databaseReference;
    private static String point;

    //////////////////////////////////////////////////////////////////////////////////////




    public HomeFragment(){

    }

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /*String apiKey = getString(R.string.api_key);
        if (!Places.isInitialized()) {
            Places.initialize(getContext(), apiKey);
        }*/


        /*binding.schoolHome.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp_selected));
        binding.schoolHome.setTextColor(getActivity().getResources().getColor(R.color.white));*/
        mActivity = getActivity();
        mContext = getContext();
        modelUsersDataArrayList=new ArrayList<>();

        FirebaseApp.initializeApp(getContext());
        paidRecyclerView=binding.paidRecyclerView;
        freeRecyclerView=binding.freeRecyclerView;
        String[] countries = getResources().getStringArray(R.array.locations);
// Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, countries);
        binding.searchLocation.setAdapter(adapter);
        //recyclerView = findViewById(R.id.recycler_view);
        progressCircular=binding.progressCircular;
        paidRecyclerView.setHasFixedSize(true);
        paidRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
        paidRecyclerView.setNestedScrollingEnabled(false);
        paidItemsList1 = new ArrayList<>();
        paidItemsList2 = new ArrayList<>();
        paidItemsList3 = new ArrayList<>();
        paidItemsList4 = new ArrayList<>();
        paidItemsList5 = new ArrayList<>();
        paidItemsListFull = new ArrayList<>();
        //paidItemsList2 = new ArrayList<>();
        //priceItemsList1=new ArrayList<>();
        priceItemsList1=new ArrayList<>();
        priceItemsList2=new ArrayList<>();
        priceItemsList3=new ArrayList<>();
        priceItemsList4=new ArrayList<>();
        priceItemsList5=new ArrayList<>();
        priceItemsListFull=new ArrayList<>();

        ////////////////////////////////////////////////////////////////////////
        freeRecyclerView.setHasFixedSize(true);
        freeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
        freeRecyclerView.setNestedScrollingEnabled(false);
        //donationsItemsList1 = new ArrayList<>();
        donationsItemsList1= new ArrayList<>();
        freeItemsList1=new ArrayList<>();
        donationsItemsList2= new ArrayList<>();
        freeItemsList2=new ArrayList<>();
        donationsItemsList3= new ArrayList<>();
        freeItemsList3=new ArrayList<>();
        donationsItemsList4= new ArrayList<>();
        freeItemsList4=new ArrayList<>();
        donationsItemsList5= new ArrayList<>();
        freeItemsList5=new ArrayList<>();
        donationsItemsListFull= new ArrayList<>();
        freeItemsListFull=new ArrayList<>();


        //freeItemsList2=new ArrayList<>();

        /////////////////////////////////////////////////////////////////////////////

        databaseReferenceDonations = FirebaseDatabase.getInstance("https://old-book-store-144a2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Free");
        databaseReferencePaid = FirebaseDatabase.getInstance("https://old-book-store-144a2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Prices");
        databaseReference=FirebaseDatabase.getInstance("https://old-book-store-144a2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Users");

        /////////////////////////////////////////////////////////////////////////////////////////////////////
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    ModelUsersData modelUsersData=dataSnapshot.getValue(ModelUsersData.class);
                    modelUsersDataArrayList.add(modelUsersData);
                }
                Collections.sort(modelUsersDataArrayList,new UserComparator());
                displayPaidAds();
                displayFreeAds();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //////////////////////////////////////////////////////////////////////////////////////////////////
        binding.searchBookTitle.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_SEARCH){
                    book_title_search(v.getText().toString().toLowerCase().trim());
                    return true;
                }
                return false;
            }
        });
        /*binding.searchLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);
                if(actionId== EditorInfo.IME_ACTION_SEARCH){
                    return true;
                }
                // Start the autocomplete intent.
                *//*Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                        .build(getContext());
                //start activity result
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);*//*
            }
        });*/
        binding.searchLocation.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if(actionId== EditorInfo.IME_ACTION_SEARCH){
                    location(v.getText().toString().toLowerCase().trim());
                    return true;
                }
                return false;
            }
        });
        binding.schoolHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="school";
                Toast.makeText(getContext(),"Category Selected",Toast.LENGTH_SHORT).show();
                binding.schoolHome.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp_selected));
                binding.schoolHome.setTextColor(getActivity().getResources().getColor(R.color.white));
                //////////////////////////////////////////////////////////////////////////////////////////
                binding.matricHome.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.interHome.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));;
                binding.otherHome.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));

                ////////////////////////////////////////////////////////////////////////////////////////

                binding.matricHome.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.interHome.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.otherHome.setTextColor(getActivity().getResources().getColor(R.color.red));

                searchCategory(category.toLowerCase().trim());
            }
        });
        binding.matricHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="matric";
                Toast.makeText(getContext(),"Category Selected",Toast.LENGTH_SHORT).show();
                binding.matricHome.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp_selected));
                binding.matricHome.setTextColor(getActivity().getResources().getColor(R.color.white));
                //////////////////////////////////////////////////////////////////////////////////////////
                binding.schoolHome.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.interHome.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));;
                binding.otherHome.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));

                ////////////////////////////////////////////////////////////////////////////////////////

                binding.schoolHome.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.interHome.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.otherHome.setTextColor(getActivity().getResources().getColor(R.color.red));
                searchCategory(category.toLowerCase().trim());
            }
        });
        binding.interHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="inter";
                Toast.makeText(getContext(),"Category Selected",Toast.LENGTH_SHORT).show();
                binding.interHome.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp_selected));
                binding.interHome.setTextColor(getActivity().getResources().getColor(R.color.white));
                //////////////////////////////////////////////////////////////////////////////////////////
                binding.matricHome.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.schoolHome.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));;
                binding.otherHome.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));

                ////////////////////////////////////////////////////////////////////////////////////////

                binding.matricHome.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.schoolHome.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.otherHome.setTextColor(getActivity().getResources().getColor(R.color.red));

                searchCategory(category.toLowerCase().trim());
            }
        });
        binding.otherHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //category="other";
                Toast.makeText(getContext(),"Category Selected",Toast.LENGTH_SHORT).show();
                binding.otherHome.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp_selected));
                binding.otherHome.setTextColor(getActivity().getResources().getColor(R.color.white));
                //////////////////////////////////////////////////////////////////////////////////////////
                binding.matricHome.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.interHome.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));;
                binding.schoolHome.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));

                ////////////////////////////////////////////////////////////////////////////////////////

                binding.matricHome.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.interHome.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.schoolHome.setTextColor(getActivity().getResources().getColor(R.color.red));

                searchCategoryOther();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
       // binding = null;
    }
    public void book_title_search(String str){
        databaseReferencePaid.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                /*paidItemsList1.clear();
                priceItemsList1.clear();
                paidItemsList2.clear();
                priceItemsList2.clear();
                paidItemsList3.clear();
                priceItemsList3.clear();
                paidItemsList4.clear();
                priceItemsList4.clear();
                paidItemsList5.clear();
                priceItemsList5.clear();*/
                paidItemsListFull.clear();
                priceItemsListFull.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ModelPrice pricemodel = dataSnapshot.getValue(ModelPrice.class);
                    if(pricemodel.getBook_title().toLowerCase().equals(str)) {
                        ModelPaid modelPaid = new ModelPaid(pricemodel.getImageUriMain().toString(), pricemodel.getBook_title().toString(), pricemodel.getPrice().toString());
                        paidItemsListFull.add(modelPaid);
                        priceItemsListFull.add(pricemodel);
                    }
                }
                paidAdapter = new PaidAdapter(mContext, mActivity, (ArrayList<ModelPaid>) paidItemsListFull);
                paidRecyclerView.setAdapter(paidAdapter);
                paidAdapter.setClickListener(HomeFragment.this);
                paidAdapter.notifyDataSetChanged();
                //binding.progressCircular.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Error:" + error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        databaseReferenceDonations.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                donationsItemsListFull.clear();
                freeItemsListFull.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ModelFree freemodel = dataSnapshot.getValue(ModelFree.class);
                    if(freemodel.getBook_title().toLowerCase().equals(str)) {
                        ModelDonations modelDonations = new ModelDonations(freemodel.getImageUriMain().toString(), freemodel.getBook_title().toString());
                        donationsItemsListFull.add(modelDonations);
                        freeItemsListFull.add(freemodel);
                    }
                }
                donationsAdapter = new DonationsAdapter(mContext, mActivity, (ArrayList<ModelDonations>) donationsItemsListFull);
                freeRecyclerView.setAdapter(donationsAdapter);
                donationsAdapter.setClickListener(HomeFragment.this);
                donationsAdapter.notifyDataSetChanged();
                //binding.progressCircular.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Error:" + error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });



    }

    public void location(String str){
        databaseReferencePaid.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                paidItemsListFull.clear();
                priceItemsListFull.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ModelPrice pricemodel = dataSnapshot.getValue(ModelPrice.class);
                    if(pricemodel.getLocation().toLowerCase().equals(str)) {
                        ModelPaid modelPaid = new ModelPaid(pricemodel.getImageUriMain().toString(), pricemodel.getBook_title().toString(), pricemodel.getPrice().toString());
                        paidItemsListFull.add(modelPaid);
                        priceItemsListFull.add(pricemodel);
                    }
                }
                paidAdapter = new PaidAdapter(mContext, mActivity, (ArrayList<ModelPaid>) paidItemsListFull);
                paidRecyclerView.setAdapter(paidAdapter);
                paidAdapter.setClickListener(HomeFragment.this);
                paidAdapter.notifyDataSetChanged();
                //binding.progressCircular.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Error:" + error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        databaseReferenceDonations.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                donationsItemsListFull.clear();
                freeItemsListFull.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ModelFree freemodel = dataSnapshot.getValue(ModelFree.class);
                    if(freemodel.getLocation().toLowerCase().equals(str)) {
                        ModelDonations modelDonations = new ModelDonations(freemodel.getImageUriMain().toString(), freemodel.getBook_title().toString());
                        donationsItemsListFull.add(modelDonations);
                        freeItemsListFull.add(freemodel);
                    }
                }
                donationsAdapter = new DonationsAdapter(mContext, mActivity, (ArrayList<ModelDonations>) donationsItemsListFull);
                freeRecyclerView.setAdapter(donationsAdapter);
                donationsAdapter.setClickListener(HomeFragment.this);
                donationsAdapter.notifyDataSetChanged();
                //binding.progressCircular.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Error:" + error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void searchCategory(String str){
        databaseReferencePaid.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                paidItemsListFull.clear();
                priceItemsListFull.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ModelPrice pricemodel = dataSnapshot.getValue(ModelPrice.class);
                    Log.d("myApp","book title: "+pricemodel.getBook_title());
                    if(pricemodel.getCategory().toLowerCase().equals(str)) {
                        ModelPaid modelPaid = new ModelPaid(pricemodel.getImageUriMain().toString(), pricemodel.getBook_title().toString(), pricemodel.getPrice().toString());
                        paidItemsListFull.add(modelPaid);
                        priceItemsListFull.add(pricemodel);
                    }
                }
                paidAdapter = new PaidAdapter(mContext, mActivity, (ArrayList<ModelPaid>) paidItemsListFull);
                paidRecyclerView.setAdapter(paidAdapter);
                paidAdapter.setClickListener(HomeFragment.this);
                paidAdapter.notifyDataSetChanged();
                //binding.progressCircular.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Error:" + error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        databaseReferenceDonations.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                donationsItemsListFull.clear();
                freeItemsListFull.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ModelFree freemodel = dataSnapshot.getValue(ModelFree.class);
                    if(freemodel.getCategory().toLowerCase().equals(str)) {
                        ModelDonations modelDonations = new ModelDonations(freemodel.getImageUriMain().toString(), freemodel.getBook_title().toString());
                        donationsItemsListFull.add(modelDonations);
                        freeItemsListFull.add(freemodel);
                    }
                }
                donationsAdapter = new DonationsAdapter(mContext, mActivity, (ArrayList<ModelDonations>) donationsItemsListFull);
                freeRecyclerView.setAdapter(donationsAdapter);
                donationsAdapter.setClickListener(HomeFragment.this);
                donationsAdapter.notifyDataSetChanged();
                //binding.progressCircular.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Error:" + error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void searchCategoryOther(){
        databaseReferencePaid.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                paidItemsListFull.clear();
                priceItemsListFull.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ModelPrice pricemodel = dataSnapshot.getValue(ModelPrice.class);
                    Log.d("myApp","book title: "+pricemodel.getBook_title());
                    if(!pricemodel.getCategory().toLowerCase().equals("school") && !pricemodel.getCategory().toLowerCase().equals("inter") && !pricemodel.getCategory().toLowerCase().equals("matric")) {
                        ModelPaid modelPaid = new ModelPaid(pricemodel.getImageUriMain().toString(), pricemodel.getBook_title().toString(), pricemodel.getPrice().toString());
                        paidItemsListFull.add(modelPaid);
                        priceItemsListFull.add(pricemodel);
                    }
                }
                paidAdapter = new PaidAdapter(mContext, mActivity, (ArrayList<ModelPaid>) paidItemsListFull);
                paidRecyclerView.setAdapter(paidAdapter);
                paidAdapter.setClickListener(HomeFragment.this);
                paidAdapter.notifyDataSetChanged();
                //binding.progressCircular.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Error:" + error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        databaseReferenceDonations.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                donationsItemsListFull.clear();
                freeItemsListFull.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ModelFree freemodel = dataSnapshot.getValue(ModelFree.class);
                    if(!freemodel.getCategory().toLowerCase().equals("school") && !freemodel.getCategory().toLowerCase().equals("inter") && !freemodel.getCategory().toLowerCase().equals("matric")) {
                        ModelDonations modelDonations = new ModelDonations(freemodel.getImageUriMain().toString(), freemodel.getBook_title().toString());
                        donationsItemsListFull.add(modelDonations);
                        freeItemsListFull.add(freemodel);

                    }
                }
                donationsAdapter = new DonationsAdapter(mContext, mActivity, (ArrayList<ModelDonations>) donationsItemsListFull);
                freeRecyclerView.setAdapter(donationsAdapter);
                donationsAdapter.setClickListener(HomeFragment.this);
                donationsAdapter.notifyDataSetChanged();
                //binding.progressCircular.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Error:" + error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClickPaid(View view, int position) {
        final ModelPrice modelPrice=priceItemsListFull.get(position);
        Toast.makeText(getContext(),modelPrice.getPrice(),Toast.LENGTH_SHORT).show();
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
        //Log.i("description", modelPrice.getDescription());
        startActivity(i);
    }

    @Override
    public void onClickFree(View view, int position) {
        final ModelFree modelFree=freeItemsListFull.get(position);
        Toast.makeText(getContext(),"0",Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getActivity(), DisplayAdActivity.class);
        i.putExtra("price", "0");
        i.putExtra("imageUriMain", modelFree.getImageUriMain());
        i.putExtra("imageUriFront", modelFree.getImageUriFront());
        i.putExtra("imageUriBack", modelFree.getImageUriBack());
        i.putExtra("book_title", modelFree.getBook_title());
        i.putExtra("location", modelFree.getLocation());
        i.putExtra("category", modelFree.getCategory());
        i.putExtra("userID", modelFree.getUserID());
        i.putExtra("description", modelFree.getDescription());
        //Log.i("description", modelPrice.getDescription());
        startActivity(i);
    }
    class UserComparator implements Comparator<ModelUsersData> {


        // override the compare() method
        public int compare(@NonNull ModelUsersData s1,@NonNull ModelUsersData s2) {
            Log.d("myApp","s1 "+s1.getPoint());
            Log.d("myApp","s2 "+ s2.getPoint());

            if (Integer.parseInt(s1.getPoint()) == Integer.parseInt(s2.getPoint()))
                return 0;
            else if (Integer.parseInt(s1.getPoint()) < Integer.parseInt(s2.getPoint()))
                return 1;
            else
                return -1;
        }
    }
    private void displayPaidAds(){
        databaseReferencePaid.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                paidItemsList1.clear();
                priceItemsList1.clear();
                paidItemsList2.clear();
                priceItemsList2.clear();
                paidItemsList3.clear();
                priceItemsList3.clear();
                paidItemsList4.clear();
                priceItemsList4.clear();
                paidItemsList5.clear();
                priceItemsList5.clear();
                paidItemsListFull.clear();
                priceItemsListFull.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ModelPrice pricemodel = dataSnapshot.getValue(ModelPrice.class);
                    ModelPaid modelPaid=new ModelPaid(pricemodel.getImageUriMain().toString(),pricemodel.getBook_title().toString(),pricemodel.getPrice().toString());
                    for(int i=0;i<modelUsersDataArrayList.size();i++){
                        if(modelUsersDataArrayList.get(i).getUserID().equals(pricemodel.getUserID())){
                            if(modelUsersDataArrayList.get(i).getPoint().equals("5")) {
                                paidItemsList1.add(modelPaid);
                                priceItemsList1.add(pricemodel);
                            }
                            else if(modelUsersDataArrayList.get(i).getPoint().equals("4")) {
                                paidItemsList2.add(modelPaid);
                                priceItemsList2.add(pricemodel);
                            }
                            else if(modelUsersDataArrayList.get(i).getPoint().equals("3")) {
                                paidItemsList3.add(modelPaid);
                                priceItemsList3.add(pricemodel);
                            }
                            else if(modelUsersDataArrayList.get(i).getPoint().equals("2")) {
                                paidItemsList4.add(modelPaid);
                                priceItemsList4.add(pricemodel);
                            }
                            else if(modelUsersDataArrayList.get(i).getPoint().equals("1")) {
                                paidItemsList5.add(modelPaid);
                                priceItemsList5.add(pricemodel);
                            }
                        }
                    }
                }
                for(int i=0;i<paidItemsList1.size();i++){
                    paidItemsListFull.add(paidItemsList1.get(i));
                }
                for(int i=0;i<paidItemsList2.size();i++){
                    paidItemsListFull.add(paidItemsList2.get(i));
                }
                for(int i=0;i<paidItemsList3.size();i++){
                    paidItemsListFull.add(paidItemsList3.get(i));
                }
                for(int i=0;i<paidItemsList4.size();i++){
                    paidItemsListFull.add(paidItemsList4.get(i));
                }
                for(int i=0;i<paidItemsList5.size();i++){
                    paidItemsListFull.add(paidItemsList5.get(i));
                }
                for(int i=0;i<priceItemsList1.size();i++){
                    priceItemsListFull.add(priceItemsList1.get(i));
                }
                for(int i=0;i<priceItemsList2.size();i++){
                    priceItemsListFull.add(priceItemsList2.get(i));
                }
                for(int i=0;i<priceItemsList3.size();i++){
                    priceItemsListFull.add(priceItemsList3.get(i));
                }
                for(int i=0;i<priceItemsList4.size();i++){
                    priceItemsListFull.add(priceItemsList4.get(i));
                }
                for(int i=0;i<priceItemsList5.size();i++){
                    priceItemsListFull.add(priceItemsList5.get(i));
                }

                Log.d("myApp","True or False:  "+String.valueOf(paidItemsListFull.size()));
                paidAdapter = new PaidAdapter(mContext, mActivity, (ArrayList<ModelPaid>) paidItemsListFull);
                if(paidRecyclerView.toString().isEmpty()){

                }else {
                    paidRecyclerView.setAdapter(paidAdapter);
                    paidAdapter.setClickListener(HomeFragment.this);
                    paidAdapter.notifyDataSetChanged();
                }

                //binding.progressCircular.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Error:" + error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void displayFreeAds(){
        databaseReferenceDonations.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                donationsItemsList1.clear();
                freeItemsList1.clear();
                donationsItemsList2.clear();
                freeItemsList2.clear();
                donationsItemsList3.clear();
                freeItemsList3.clear();
                donationsItemsList4.clear();
                freeItemsList4.clear();
                donationsItemsList5.clear();
                freeItemsList5.clear();
                donationsItemsListFull.clear();
                freeItemsListFull.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ModelFree freemodel = dataSnapshot.getValue(ModelFree.class);
                    ModelDonations modelDonations=new ModelDonations(freemodel.getImageUriMain().toString(),freemodel.getBook_title().toString());
                    for(int i=0;i<modelUsersDataArrayList.size();i++){
                        if(modelUsersDataArrayList.get(i).getUserID().equals(freemodel.getUserID())){
                            if(modelUsersDataArrayList.get(i).getPoint().equals("5")) {
                                donationsItemsList1.add(modelDonations);
                                freeItemsList1.add(freemodel);
                            }
                            else if(modelUsersDataArrayList.get(i).getPoint().equals("4")) {
                                donationsItemsList2.add(modelDonations);
                                freeItemsList2.add(freemodel);
                            }
                            else if(modelUsersDataArrayList.get(i).getPoint().equals("3")) {
                                donationsItemsList3.add(modelDonations);
                                freeItemsList3.add(freemodel);
                            }
                            else if(modelUsersDataArrayList.get(i).getPoint().equals("2")) {
                                donationsItemsList4.add(modelDonations);
                                freeItemsList4.add(freemodel);
                            }
                            else if(modelUsersDataArrayList.get(i).getPoint().equals("1")) {
                                donationsItemsList5.add(modelDonations);
                                freeItemsList5.add(freemodel);
                            }
                        }
                    }
                }
                for(int i=0;i<donationsItemsList1.size();i++){
                    donationsItemsListFull.add(donationsItemsList1.get(i));
                }
                for(int i=0;i<donationsItemsList2.size();i++){
                    donationsItemsListFull.add(donationsItemsList2.get(i));
                }
                for(int i=0;i<donationsItemsList3.size();i++){
                    donationsItemsListFull.add(donationsItemsList3.get(i));
                }
                for(int i=0;i<donationsItemsList4.size();i++){
                    donationsItemsListFull.add(donationsItemsList4.get(i));
                }
                for(int i=0;i<donationsItemsList5.size();i++){
                    donationsItemsListFull.add(donationsItemsList5.get(i));
                }
                for(int i=0;i<freeItemsList1.size();i++){
                    freeItemsListFull.add(freeItemsList1.get(i));
                }
                for(int i=0;i<freeItemsList2.size();i++){
                    freeItemsListFull.add(freeItemsList2.get(i));
                }
                for(int i=0;i<freeItemsList3.size();i++){
                    freeItemsListFull.add(freeItemsList3.get(i));
                }
                for(int i=0;i<freeItemsList4.size();i++){
                    freeItemsListFull.add(freeItemsList4.get(i));
                }
                for(int i=0;i<freeItemsList5.size();i++){
                    freeItemsListFull.add(freeItemsList5.get(i));
                }

                donationsAdapter = new DonationsAdapter(mContext, mActivity, (ArrayList<ModelDonations>) donationsItemsListFull);
                Log.d("myApp",donationsAdapter.toString());
                if(freeRecyclerView.toString().isEmpty()){

                }else {
                    freeRecyclerView.setAdapter(donationsAdapter);
                    donationsAdapter.setClickListener(HomeFragment.this);
                    donationsAdapter.notifyDataSetChanged();
                    progressCircular.setVisibility(View.GONE);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Error:" + error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == getActivity().RESULT_OK) {
                //When success initialize place
                Place place = Autocomplete.getPlaceFromIntent(data);

                //set address on edittext



                *//*maddress.setText(place.getName());*//*
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                //Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == getActivity().RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }*/
}
