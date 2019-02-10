package com.example.samim.bottomnavigationwithfragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.santalu.maskedittext.MaskEditText;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AppointmentActivityForm extends AppCompatActivity
        implements View.OnClickListener{
    private static final String TAG = "AppointmentActivityForm";

    //datepicker
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private FirebaseFirestore mFireStore;

    private Button btnBook;
    private MaskEditText etphone;
    private TextView etdate;
    private EditText
            fname,
            ettime,
            etiphonemodel,
            etprice,
            etcolor,
            etaddress,
            etQuantity,
            total;


    private RelativeLayout mParentLayout;
    private IMaintActivity mIMainActivity;

    public AppointmentActivityForm(){
        //default constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_form);



        fname = findViewById(R.id.et_name_id);
        ettime = findViewById(R.id.et_time_id);
        etdate = findViewById(R.id.et_date_id);
        etiphonemodel = findViewById(R.id.et_form_iphone_model_id);
        etprice = findViewById(R.id.et_form_price_id);
        etcolor = findViewById(R.id.et_form_iphone_color_id);
        etaddress = findViewById(R.id.et_address_id);
        etphone = findViewById(R.id.et_phoneNumber_id);
        etQuantity = findViewById(R.id.et_form_quantity_id);
        total = findViewById(R.id.etTotal);

        mParentLayout = findViewById(R.id.parent_layout_appt_card_id);
        btnBook = findViewById(R.id.btn_book_now_id);

        btnBook.setOnClickListener(this);
        etdate.setOnClickListener(this);


    }


    //Calculating the total : price * qty
    public void CalculateTotal(){

        try {
            if (etQuantity !=null || etprice.getText() !=null){
                total.setText(String.valueOf(Double.parseDouble(etprice.getText().toString()) *
                        Double.parseDouble(etQuantity.getText().toString())));
            }else {
                makeSnakBarMessage("Enter price & qty");
            }
        }catch (NumberFormatException e){
            makeSnakBarMessage("Error " + e);
        }

    }

    //FireStore to insert data into the database document.
    private void insertToFireStore(){

        String name = fname.getText().toString().trim();
        String time = ettime.getText().toString().trim();
        String date = etdate.getText().toString().trim();
        String iphonemodel = etiphonemodel.getText().toString().trim();
        String price = etprice.getText().toString().trim();
        String color = etcolor.getText().toString().trim();
        String address = etaddress.getText().toString().trim();
        String phone = etphone.getText().toString().trim();
        String quantity = etQuantity.getText().toString().trim();
        String mTotal = total.getText().toString().trim();
        String mUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mFireStore = FirebaseFirestore.getInstance();
        //using this for auto generated appoinment_id
        DocumentReference Ref = mFireStore.collection("appointments").document();

        final Map<String, String> appointments = new HashMap<>();

        appointments.put("name",name);
        appointments.put("time",time);
        appointments.put("date",date);
        appointments.put("iphoneModel",iphonemodel);
        appointments.put("price", price);
        appointments.put("color",color);
        appointments.put("address",address);
        appointments.put("phoneNumber",phone);
        appointments.put("quantity", quantity);
        appointments.put("user_id", mUserId);
        appointments.put("total",mTotal);
        appointments.put("appointment_id", Ref.getId());




        mFireStore.collection("appointments").add(appointments)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String error = e.getMessage();
                Toast.makeText(AppointmentActivityForm.this, error+"Failed!", Toast.LENGTH_SHORT).show();
            }
        });

    }



    //Date picker method
    private void pickAppointmentDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                AppointmentActivityForm.this,
                android.R.style.Theme_Holo_Light,
                mDateSetListener,
                year,month,day
        );

        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
        setmDateSetListener();
    }

    private void setmDateSetListener(){
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = month + "/" + dayOfMonth + "/" + year;
                etdate.setText(date);
            }

        };
        TextView etdate;

    }


    @Override
    public void onClick(View v) {
        String name = fname.getText().toString().trim();
        String time = ettime.getText().toString().trim();
        String date = etdate.getText().toString().trim();
        String iphonemodel = etiphonemodel.getText().toString().trim();
        String price = etprice.getText().toString().trim();
        String color = etcolor.getText().toString().trim();
        String address = etaddress.getText().toString().trim();
        String quantity = etQuantity.getText().toString().trim();
        String phone = etphone.getText().toString().trim();
        String ttotal = total.getText().toString().trim();

        switch (v.getId()){


            case R.id.btn_book_now_id:{
                Log.d(TAG, "onClick: Case is called");

                if (!quantity.equals("")) {
                    CalculateTotal();

                        mIMainActivity.crateNewAppointment(
                                name,
                                date,
                                time,
                                address,
                                phone,
                                iphonemodel,
                                color,
                                quantity,
                                price,
                                ttotal

                        );

                        Log.d(TAG, "onClick: Created");
                   // insertToFireStore();
                    makeSnakBarMessage("Appointment booked");

                }else {
                    makeSnakBarMessage("Fields are required");
                }
                    break;

            }
            case R.id.et_date_id:{
                pickAppointmentDate();
                break;
            }
        }
    }


    private void makeSnakBarMessage(String message){
        Snackbar.make(mParentLayout, message,Snackbar.LENGTH_LONG).show();
    }


    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment !=null){
            mIMainActivity = (IMaintActivity) fragment;
        }

    }



}
