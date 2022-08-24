package com.mr.oldbookstore.activity.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mr.oldbookstore.R;
import com.mr.oldbookstore.activity.ui.ads.AdsFragment;
import com.mr.oldbookstore.activity.ui.home.HomeFragment;
import com.mr.oldbookstore.activity.ui.inbox.InboxFragment;
import com.mr.oldbookstore.activity.ui.profile.ProfileFragment;
import com.mr.oldbookstore.activity.ui.sell.SellFragment;
import com.mr.oldbookstore.databinding.FragmentDashboardBinding;


public class DashboardFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {
    public DashboardFragment(){

    }

    private FragmentDashboardBinding binding;
    private HomeFragment homeFragment= new HomeFragment();
    private InboxFragment inboxFragment=new InboxFragment();
    private ProfileFragment profileFragment=new ProfileFragment();
    private SellFragment sellFragment=new SellFragment();
    private AdsFragment adsFragment=new AdsFragment();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(DashboardFragment.this);
        binding.bottomNavigationView.setSelectedItemId(R.id.home);
        View root = binding.getRoot();

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, homeFragment).commit();
                return true;

            case R.id.inbox:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, inboxFragment).commit();
                return true;

            case R.id.sell:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, sellFragment).commit();
                return true;

            case R.id.ads:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, adsFragment).commit();
                return true;
            case R.id.profile:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, profileFragment).commit();
                return true;

        }
        return false;
    }
}