package com.example.samim.bottomnavigationwithfragment.adapter;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.example.samim.bottomnavigationwithfragment.IMaintActivity;
import com.example.samim.bottomnavigationwithfragment.Models.Appointments;
import com.example.samim.bottomnavigationwithfragment.R;
import com.example.samim.bottomnavigationwithfragment.fragments.AppointmentFragment;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class AppointmentAdaptercOPY extends RecyclerSwipeAdapter<AppointmentAdaptercOPY.ViewHolder> {

    private Fragment mContext;
    private ArrayList<Appointments> mAppointments;
    private Appointments mAppoint;
    private int mSelectedAppointmentIndex;
    private IMaintActivity mIMaintActivity;
    private RelativeLayout mParentLayout;

    public AppointmentAdaptercOPY(Fragment mContext, ArrayList<Appointments> mAppointments) {
        this.mContext = mContext;
        this.mAppointments = mAppointments;
    }
    public AppointmentAdaptercOPY(){

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointments_cardview_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
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

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_id;
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
        SwipeLayout swipeLayout;
        ImageView mDelete, mPhone,mComplete,mEdit;



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
            swipeLayout = itemView.findViewById(R.id.swipe_id);
            mDelete = itemView.findViewById(R.id.ic_delete);
            mPhone = itemView.findViewById(R.id.ic_phone);
            mEdit = itemView.findViewById(R.id.ic_edit);
            mComplete = itemView.findViewById(R.id.ic_complete);
            mParentLayout = itemView.findViewById(R.id.parent_layout_appt_card_id);

            itemView.setOnClickListener(this);
            mDelete.setOnClickListener(this);
            mPhone.setOnClickListener(this);
            mComplete.setOnClickListener(this);
            mEdit.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
//


            switch (view.getId()){
                case R.id.ic_delete:{
                    mIMaintActivity.deleteAppointment(mAppoint);
                    removeAppointment(mAppointments.get(getLayoutPosition()));
                    notifyItemRemoved(getLayoutPosition());
                    notifyItemRangeChanged(getLayoutPosition(), mAppointments.size());
                    notifyDataSetChanged();
                    makeSnakBarMessage("Deleted");
                    break;
                }
                case R.id.ic_complete:{
                    Log.d(TAG, "onClick: Complete is clicked");
                    break;
                }
                case R.id.ic_phone:{
                    Log.d(TAG, "onClick: Call is clicked");
                    break;
                }
                case R.id.ic_edit:{
                    Log.d(TAG, "onClick: Edit is clicked");
                    mSelectedAppointmentIndex = getAdapterPosition();
                   // mIMaintActivity.onAppointmentSelected(mAppointments.get(mSelectedAppointmentIndex));
                    break;
                }
            }
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
        mIMaintActivity = (IMaintActivity)mContext;
    }

    private void makeSnakBarMessage(String message){
        Snackbar.make(mParentLayout, message,Snackbar.LENGTH_LONG).show();
    }



}
