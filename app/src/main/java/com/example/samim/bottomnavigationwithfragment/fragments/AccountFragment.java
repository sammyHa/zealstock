package com.example.samim.bottomnavigationwithfragment.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.samim.bottomnavigationwithfragment.LoginActivity;
import com.example.samim.bottomnavigationwithfragment.R;

public class AccountFragment extends Fragment {

    // TODO: 6/18/2018 needs to be implemented
    Button mSignOut;

    public AccountFragment(){}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account,container,false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         mSignOut = view.findViewById(R.id.btn_signIn);
    }
}
