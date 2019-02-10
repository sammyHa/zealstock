package com.example.samim.bottomnavigationwithfragment.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.example.samim.bottomnavigationwithfragment.fragments.AppointmentFragment;
import com.example.samim.bottomnavigationwithfragment.IMaintActivity;
import com.example.samim.bottomnavigationwithfragment.Models.Appointments;
import com.example.samim.bottomnavigationwithfragment.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {

    private Fragment mContext;
    private ArrayList<Appointments> mAppointments;
    private int mSelectedAppointmentIndex;
    private IMaintActivity mIMaintActivity;

    private TextView mName,
            mTime,
            mDate,
            mIphoneModel,
            mPrice,
            mColor,
            mAddress,
            mPhoneNumber;

    public AppointmentAdapter(Fragment mContext, ArrayList<Appointments> mAppointments) {
        this.mContext = mContext;
        this.mAppointments = mAppointments;
    }
    public AppointmentAdapter(){

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointments_cardview_item,parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.mName.setText(mAppointments.get(position).getName());
        holder.mTime.setText(mAppointments.get(position).getTime());
        holder.mDate.setText(mAppointments.get(position).getDate());
        holder.mIphoneModel.setText(mAppointments.get(position).getIphoneModel());
        holder.mPrice.setText(mAppointments.get(position).getPrice());
        holder.mColor.setText(mAppointments.get(position).getColor());
        holder.mAddress.setText(mAppointments.get(position).getAddress());
        holder.mPhoneNumber.setText(mAppointments.get(position).getPhoneNumber());


    }


    @Override
    public int getItemCount() {
        return mAppointments.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //declare all the variables


        TextView mName,
                mTime,
                mDate,
                mIphoneModel,
                mPrice,
                mColor,
                mAddress,
                mPhoneNumber;
        Button mDelete;



        public ViewHolder(View itemView) {
            super(itemView);


            mName = itemView.findViewById(R.id.txt_name_id);
            mTime = itemView.findViewById(R.id.txt_time_id);
            mDate = itemView.findViewById(R.id.txt_date_id);
            mIphoneModel = itemView.findViewById(R.id.txt_Iphone_model_number_id);
            mPrice = itemView.findViewById(R.id.txt_price_id);
            mColor = itemView.findViewById(R.id.txt_color_id);
            mAddress = itemView.findViewById(R.id.txt_address_id);
            mPhoneNumber = itemView.findViewById(R.id.txt_phone_number_id);

            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
//            mSelectedAppointmentIndex = getAdapterPosition();
//            mIMaintActivity.onAppointmentSelected(mAppointments.get(mSelectedAppointmentIndex));
        }
    }

    public void removeAppointment(Appointments appointments){
        mAppointments.remove(appointments);
        notifyDataSetChanged();
    }

    public void updateAppointment(Appointments appointments){
        mAppointments.get(mSelectedAppointmentIndex).setName(appointments.getName());
        mAppointments.get(mSelectedAppointmentIndex).setDate(appointments.getDate());
        mAppointments.get(mSelectedAppointmentIndex).setIphoneModel(appointments.getIphoneModel());
        mAppointments.get(mSelectedAppointmentIndex).setPhoneNumber(appointments.getPhoneNumber());
        mAppointments.get(mSelectedAppointmentIndex).setColor(appointments.getColor());
        mAppointments.get(mSelectedAppointmentIndex).setPrice(appointments.getPrice());
        mAppointments.get(mSelectedAppointmentIndex).setQuantity(appointments.getQuantity());
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mIMaintActivity = (IMaintActivity) mContext;
    }

    public TextView getmName() {
        return mName;
    }

    public void setmName(TextView mName) {
        this.mName = mName;
    }

    public TextView getmTime() {
        return mTime;
    }

    public void setmTime(TextView mTime) {
        this.mTime = mTime;
    }

    public TextView getmDate() {
        return mDate;
    }

    public void setmDate(TextView mDate) {
        this.mDate = mDate;
    }

    public TextView getmIphoneModel() {
        return mIphoneModel;
    }

    public void setmIphoneModel(TextView mIphoneModel) {
        this.mIphoneModel = mIphoneModel;
    }

    public TextView getmPrice() {
        return mPrice;
    }

    public void setmPrice(TextView mPrice) {
        this.mPrice = mPrice;
    }

    public TextView getmColor() {
        return mColor;
    }

    public void setmColor(TextView mColor) {
        this.mColor = mColor;
    }

    public TextView getmAddress() {
        return mAddress;
    }

    public void setmAddress(TextView mAddress) {
        this.mAddress = mAddress;
    }

    public TextView getmPhoneNumber() {
        return mPhoneNumber;
    }

    public void setmPhoneNumber(TextView mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }



}
