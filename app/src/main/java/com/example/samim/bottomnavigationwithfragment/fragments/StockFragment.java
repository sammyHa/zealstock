package com.example.samim.bottomnavigationwithfragment.fragments;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.samim.bottomnavigationwithfragment.Models.Product;
import com.example.samim.bottomnavigationwithfragment.R;
import com.example.samim.bottomnavigationwithfragment.StockActivity;
import com.example.samim.bottomnavigationwithfragment.adapter.StockAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class StockFragment extends Fragment  implements View.OnClickListener{
    FloatingActionButton mFloatingActionStock;
    ArrayList<Product> lstProduct = new ArrayList<>();
    StockAdapter adapter;

    public StockFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stock, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFloatingActionStock = getView().findViewById(R.id.floating_button_stock_id);
        mFloatingActionStock.setOnClickListener(this);

        initData();
    }

    public void initData(){
        initRecyclerView();
        getStock();
    }

    public void initRecyclerView() {
        RecyclerView recyclerView = getView().findViewById(R.id.mRecyclerview);

        adapter = new StockAdapter(this,lstProduct);
        recyclerView.setAdapter(adapter);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        }else {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        }
    }


    private void getStock(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("products").whereEqualTo("user_id", FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                        if (e != null){
                            Log.d(TAG, "onEvent: " + e.getMessage());
                        }
                        for (DocumentChange doc: queryDocumentSnapshots.getDocumentChanges()){
                            if (doc.getType() == doc.getType().ADDED){
                                Product product = doc.getDocument().toObject(Product.class);
                                lstProduct.add(product);
                                adapter.notifyDataSetChanged();
                            }

                        }

                    }
                });
    }

    //for the floating action button
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.floating_button_stock_id:{
                Intent intent = new Intent(getContext(), StockActivity.class);
                startActivity(intent);
            }
        }
    }

    public StockAdapter getAdapter() {
        return adapter;
    }


    public void setAdapter(StockAdapter adapter) {
        this.adapter = adapter;
    }



}
