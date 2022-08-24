package com.mr.oldbookstore.activity.ui.sell;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mr.oldbookstore.R;
import com.mr.oldbookstore.activity.FreeActivity;
import com.mr.oldbookstore.activity.PriceActivity;
import com.mr.oldbookstore.databinding.FragmentSellBinding;
import com.mr.oldbookstore.model.ModelFree;
import com.mr.oldbookstore.model.ModelPrice;

import java.util.UUID;


public class SellFragment extends Fragment {
    private static String category="school";
    //private DatabaseReference database;
    private String type="Price";

    /*TextView textViewSchool;
    TextView textViewMatric;
    TextView textViewInter;
    TextView textViewBachelor;
    TextView textViewMaster;
    TextView textViewIslamic;
    TextView textViewNovels;
    TextView textViewOther;*/
    public SellFragment(){

    }

    private FragmentSellBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSellBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("Price")){
                    //addToFirebasePrice("", "", "", "", "", category);
                    Intent intent = new Intent(getActivity().getApplicationContext(), PriceActivity.class);
                    intent.putExtra("category",category);
                    startActivity(intent);
                }
                else if(type.equals("Donation")) {
                    //addToFirebaseFree("", "", "", "", "", category);
                    Intent intent = new Intent(getActivity().getApplicationContext(), FreeActivity.class);
                    intent.putExtra("category",category);
                    Log.d("myApp",category);
                    startActivity(intent);
                }
            }
        });
        binding.radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton=(RadioButton)group.findViewById(checkedId);
                Toast.makeText(getContext(),radioButton.getText().toString(),Toast.LENGTH_SHORT).show();
                type=radioButton.getText().toString();
            }
        });
        binding.school.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp_selected));
        binding.school.setTextColor(getActivity().getResources().getColor(R.color.white));
        /*binding.school.setSelected(false);
        binding.matric.setSelected(false);
        binding.inter.setSelected(false);
        binding.bachelor.setSelected(false);
        binding.Islamic.setSelected(false);
        binding.Novels.setSelected(false);
        binding.master.setSelected(false);
        binding.other.setSelected(false);*/

        binding.school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="school";
                Toast.makeText(getContext(),"Category Selected",Toast.LENGTH_SHORT).show();
                binding.school.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp_selected));
                binding.school.setTextColor(getActivity().getResources().getColor(R.color.white));
                //////////////////////////////////////////////////////////////////////////////////////////
                binding.matric.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.inter.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));;
                binding.bachelor.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.Islamic.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.Novels.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.master.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.other.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));

                ////////////////////////////////////////////////////////////////////////////////////////

                binding.matric.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.inter.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.bachelor.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.Islamic.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.Novels.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.master.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.other.setTextColor(getActivity().getResources().getColor(R.color.red));
            }
        });

        binding.matric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="matric";
                Toast.makeText(getContext(),"Category Selected",Toast.LENGTH_SHORT).show();
                binding.matric.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp_selected));
                binding.matric.setTextColor(getActivity().getResources().getColor(R.color.white));
                //////////////////////////////////////////////////////////////////////////////////////////
                binding.school.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.inter.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));;
                binding.bachelor.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.Islamic.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.Novels.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.master.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.other.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));

                ////////////////////////////////////////////////////////////////////////////////////////

                binding.school.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.inter.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.bachelor.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.Islamic.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.Novels.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.master.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.other.setTextColor(getActivity().getResources().getColor(R.color.red));
            }
        });
        binding.inter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="inter";
                Toast.makeText(getContext(),"Category Selected",Toast.LENGTH_SHORT).show();
                binding.inter.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp_selected));
                binding.inter.setTextColor(getActivity().getResources().getColor(R.color.white));
                //////////////////////////////////////////////////////////////////////////////////////////
                binding.matric.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.school.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));;
                binding.bachelor.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.Islamic.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.Novels.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.master.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.other.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));

                ////////////////////////////////////////////////////////////////////////////////////////

                binding.matric.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.school.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.bachelor.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.Islamic.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.Novels.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.master.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.other.setTextColor(getActivity().getResources().getColor(R.color.red));
            }
        });

        binding.bachelor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="bachelor";
                Toast.makeText(getContext(),"Category Selected",Toast.LENGTH_SHORT).show();
                binding.bachelor.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp_selected));
                binding.bachelor.setTextColor(getActivity().getResources().getColor(R.color.white));
                //////////////////////////////////////////////////////////////////////////////////////////
                binding.matric.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.inter.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));;
                binding.school.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.Islamic.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.Novels.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.master.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.other.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));

                ////////////////////////////////////////////////////////////////////////////////////////

                binding.matric.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.inter.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.school.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.Islamic.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.Novels.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.master.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.other.setTextColor(getActivity().getResources().getColor(R.color.red));
            }
        });

        binding.Islamic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="islamic";
                Toast.makeText(getContext(),"Category Selected",Toast.LENGTH_SHORT).show();
                binding.Islamic.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp_selected));
                binding.Islamic.setTextColor(getActivity().getResources().getColor(R.color.white));
                //////////////////////////////////////////////////////////////////////////////////////////
                binding.matric.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.inter.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));;
                binding.bachelor.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.school.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.Novels.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.master.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.other.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));

                ////////////////////////////////////////////////////////////////////////////////////////

                binding.matric.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.inter.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.bachelor.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.school.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.Novels.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.master.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.other.setTextColor(getActivity().getResources().getColor(R.color.red));
            }
        });
        binding.Novels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="novels";
                Toast.makeText(getContext(),"Category Selected",Toast.LENGTH_SHORT).show();
                binding.Novels.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp_selected));
                binding.Novels.setTextColor(getActivity().getResources().getColor(R.color.white));
                //////////////////////////////////////////////////////////////////////////////////////////
                binding.matric.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.inter.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));;
                binding.bachelor.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.Islamic.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.school.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.master.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.other.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));

                ////////////////////////////////////////////////////////////////////////////////////////

                binding.matric.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.inter.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.bachelor.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.Islamic.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.school.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.master.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.other.setTextColor(getActivity().getResources().getColor(R.color.red));
            }
        });

        binding.master.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="master";
                Toast.makeText(getContext(),"Category Selected",Toast.LENGTH_SHORT).show();
                binding.master.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp_selected));
                binding.master.setTextColor(getActivity().getResources().getColor(R.color.white));
                //////////////////////////////////////////////////////////////////////////////////////////
                binding.matric.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.inter.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));;
                binding.bachelor.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.Islamic.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.Novels.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.school.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.other.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));

                ////////////////////////////////////////////////////////////////////////////////////////

                binding.matric.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.inter.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.bachelor.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.Islamic.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.Novels.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.school.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.other.setTextColor(getActivity().getResources().getColor(R.color.red));
            }
        });

        binding.other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="other";
                Toast.makeText(getContext(),"Category Selected",Toast.LENGTH_SHORT).show();
                binding.other.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp_selected));
                binding.other.setTextColor(getActivity().getResources().getColor(R.color.white));
                //////////////////////////////////////////////////////////////////////////////////////////
                binding.matric.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.inter.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));;
                binding.bachelor.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.Islamic.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.Novels.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.master.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));
                binding.school.setBackground(getActivity().getResources().getDrawable(R.drawable.circular_shaped_otp));

                ////////////////////////////////////////////////////////////////////////////////////////

                binding.matric.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.inter.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.bachelor.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.Islamic.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.Novels.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.master.setTextColor(getActivity().getResources().getColor(R.color.red));
                binding.school.setTextColor(getActivity().getResources().getColor(R.color.red));
            }
        });

        /*textViewSchool.setSelected(false);
        textViewMatric.setSelected(false);
        textViewInter.setSelected(false);
        textViewBachelor.setSelected(false);
        textViewIslamic.setSelected(false);
        textViewNovels.setSelected(false);
        textViewMaster.setSelected(false);
        textViewOther.setSelected(false);*/

/*
        if(binding.school.isSelected()||binding.matric.isSelected()||binding.inter.isSelected()||binding.bachelor.isSelected()||binding.Islamic.isSelected()||binding.Novels.isSelected()||binding.master.isSelected()||binding.other.isSelected()){
            if(binding.school.isSelected()){
                Toast.makeText(getContext(),"Category Selected",Toast.LENGTH_LONG).show();
                binding.matric.setSelected(false);
                binding.inter.setSelected(false);
                binding.bachelor.setSelected(false);
                binding.Islamic.setSelected(false);
                binding.Novels.setSelected(false);
                binding.master.setSelected(false);
                binding.other.setSelected(false);
            }

            if(binding.matric.isSelected()){
                Toast.makeText(getContext(),"Category Selected",Toast.LENGTH_LONG).show();
                binding.school.setSelected(false);
                binding.inter.setSelected(false);
                binding.bachelor.setSelected(false);
                binding.Islamic.setSelected(false);
                binding.Novels.setSelected(false);
                binding.master.setSelected(false);
                binding.other.setSelected(false);
            }
            if(binding.inter.isSelected()){
                Toast.makeText(getContext(),"Category Selected",Toast.LENGTH_LONG).show();
                binding.matric.setSelected(false);
                binding.school.setSelected(false);
                binding.bachelor.setSelected(false);
                binding.Islamic.setSelected(false);
                binding.Novels.setSelected(false);
                binding.master.setSelected(false);
                binding.other.setSelected(false);
            }
            if(binding.bachelor.isSelected()){
                Toast.makeText(getContext(),"Category Selected",Toast.LENGTH_LONG).show();
                binding.matric.setSelected(false);
                binding.inter.setSelected(false);
                binding.school.setSelected(false);
                binding.Islamic.setSelected(false);
                binding.Novels.setSelected(false);
                binding.master.setSelected(false);
                binding.other.setSelected(false);
            }
            if(binding.Islamic.isSelected()){
                Toast.makeText(getContext(),"Category Selected",Toast.LENGTH_LONG).show();
                binding.matric.setSelected(false);
                binding.inter.setSelected(false);
                binding.bachelor.setSelected(false);
                binding.school.setSelected(false);
                binding.Novels.setSelected(false);
                binding.master.setSelected(false);
                binding.other.setSelected(false);
            }
            if(binding.Novels.isSelected()){
                Toast.makeText(getContext(),"Category Selected",Toast.LENGTH_LONG).show();
                binding.matric.setSelected(false);
                binding.inter.setSelected(false);
                binding.bachelor.setSelected(false);
                binding.Islamic.setSelected(false);
                binding.school.setSelected(false);
                binding.master.setSelected(false);
                binding.other.setSelected(false);
            }
            if(binding.master.isSelected()){
                Toast.makeText(getContext(),"Category Selected",Toast.LENGTH_LONG).show();
                binding.matric.setSelected(false);
                binding.inter.setSelected(false);
                binding.bachelor.setSelected(false);
                binding.Islamic.setSelected(false);
                binding.Novels.setSelected(false);
                binding.school.setSelected(false);
                binding.other.setSelected(false);
            }
            if(binding.other.isSelected()){
                Toast.makeText(getContext(),"Category Selected",Toast.LENGTH_LONG).show();
                binding.matric.setSelected(false);
                binding.inter.setSelected(false);
                binding.bachelor.setSelected(false);
                binding.Islamic.setSelected(false);
                binding.Novels.setSelected(false);
                binding.master.setSelected(false);
                binding.school.setSelected(false);
            }

        }
*/


        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //database= FirebaseDatabase.getInstance("https://old-book-store-144a2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

        /*textViewSchool=getActivity().findViewById(R.id.school);
        textViewMatric=getActivity().findViewById(R.id.matric);
        textViewInter=getActivity().findViewById(R.id.inter);
        textViewBachelor=getActivity().findViewById(R.id.bachelor);
        textViewIslamic=getActivity().findViewById(R.id.Islamic);
        textViewNovels=getActivity().findViewById(R.id.Novels);
        textViewMaster=getActivity().findViewById(R.id.master);
        textViewOther=getActivity().findViewById(R.id.other);*/


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    /*private void addToFirebaseFree(String book_title,String description,String imageUri,String location,String userID,String category){
        ModelFree modelFree=new ModelFree(book_title,description,imageUri,location,userID,category);
        database.child("Free").child(UUID.randomUUID().toString()).setValue(modelFree);
        Log.d("myApp",database.getRoot().toString());
        Toast.makeText(getContext(),"Category Stored",Toast.LENGTH_SHORT).show();


    }
    private void addToFirebasePrice(String book_title,String description,String imageUri,String location,String userID,String category){
        ModelPrice modelPrice=new ModelPrice(book_title,description,imageUri,location,userID,category);
        database.child("Prices").child(UUID.randomUUID().toString()).setValue(modelPrice);
        Log.d("myApp",database.getRoot().toString());
        Toast.makeText(getContext(),"Category Stored",Toast.LENGTH_SHORT).show();


    }*/

}