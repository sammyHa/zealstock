package com.example.samim.bottomnavigationwithfragment.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samim.bottomnavigationwithfragment.Models.Product;
import com.example.samim.bottomnavigationwithfragment.R;

import java.util.ArrayList;

/**
 * Created by SAMIM on 3/26/2018.
 */

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.MyViewHolder> {

    private static final String TAG = "StockAdapter";

    private Fragment mContext;
    private ArrayList<Product> mData;

    public StockAdapter(Fragment mContext, ArrayList<Product> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");


        holder.mTxtqty.setText(mData.get(position).getMquantity());
        holder.mTxtIphoneModel.setText(mData.get(position).getMiphonemodel());

       holder.parentLayout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mData.remove(position);
               notifyDataSetChanged();

               //Toast.makeText(mContext.getActivity(), "Clicked " + position  , Toast.LENGTH_SHORT).show();
           }
       });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    //create this first

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mTxtqty;
        TextView mTxtpcs;
        TextView mTxtIphoneModel;
        CardView parentLayout;

        public MyViewHolder(View itemView) {
            super(itemView);

            mTxtqty = itemView.findViewById(R.id.txt_quantity);
            mTxtpcs = itemView.findViewById(R.id.txt_pcs);
            mTxtIphoneModel = itemView.findViewById(R.id.txt_iphone_model);
            parentLayout = itemView.findViewById(R.id.parent_layout_appt_card_id);
        }
    }
}
