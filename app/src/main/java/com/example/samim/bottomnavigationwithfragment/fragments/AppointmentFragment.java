package com.example.samim.bottomnavigationwithfragment.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.samim.bottomnavigationwithfragment.AppointmentActivityForm;
import com.example.samim.bottomnavigationwithfragment.AppointmentId;
import com.example.samim.bottomnavigationwithfragment.IMaintActivity;
import com.example.samim.bottomnavigationwithfragment.Models.Appointments;
import com.example.samim.bottomnavigationwithfragment.R;
import com.example.samim.bottomnavigationwithfragment.adapter.AppointmentAdaptercOPY;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class AppointmentFragment extends Fragment  implements IMaintActivity{
    private static final String TAG ="AppointmentFragment";
    private ArrayList<Appointments> lstAppointments = new ArrayList<>();
    private AppointmentAdaptercOPY adapter;
    private FloatingActionButton mFloatingActionButton;
    private FirebaseFirestore mFireStore;
    private DocumentSnapshot mLastQueriedDocument;


    private ArrayList<Appointments> mAppointments = new ArrayList<>();
    private RelativeLayout parent;
    private IMaintActivity mIMaintActivity;

    public AppointmentFragment() {
        //  empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_appointment, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();
        appointmentFloatingButton();
    }

    public void initData() {

        initRecyclerView();
        fireStoreAppointment();
        //getAppointments();


    }


    public void initRecyclerView(){
        RecyclerView recyclerView = getView().findViewById(R.id.appontment_recyclerview_id);

        adapter = new AppointmentAdaptercOPY(this,lstAppointments);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    //for the floating action button
    private void appointmentFloatingButton(){
        mFloatingActionButton = getView().findViewById(R.id.floating_button_id);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AppointmentActivityForm.class);
                startActivity(intent);

            }
        });
    }

//    FireStore retrieve data for Appointments
    private void fireStoreAppointment(){
        mFireStore = FirebaseFirestore.getInstance();
        mFireStore.collection("appointments")
                .whereEqualTo("user_id", FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (e != null  ){
                    Log.d(TAG, "onEvent: " + e.getMessage());
                }
                for (DocumentChange documentChange: queryDocumentSnapshots.getDocumentChanges()){
                    if( documentChange.getType() == documentChange.getType().ADDED){
                        String appointmentId = documentChange.getDocument().getId();
                        Appointments appointments = documentChange.getDocument().toObject(Appointments.class);
                        lstAppointments.add(appointments);

                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }




    private void getAppointments(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference mCollectionRef = db
                .collection("appointments");
        Query apptQuery = mCollectionRef
                .whereEqualTo("user_id", FirebaseAuth.getInstance().getCurrentUser().getUid())
                .orderBy("date", Query.Direction.ASCENDING);

        apptQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (DocumentSnapshot document: task.getResult()){
                        Appointments appointments = document.toObject(Appointments.class);
                        lstAppointments.add(appointments);
                    }
                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(getContext(), "Query Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    @Override
    public void crateNewAppointment(

            String name,
            String time,
            String date,
            String iphoneModel,
            String price,
            String color,
            String address,
            String quantity,
            String phoneNumber,
            String total
    ) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference newAppointRef = db
                .collection("appointments")
                .document();

        Appointments appointments = new Appointments();


        appointments.setName(name);
        appointments.setTime(time);
        appointments.setDate(date);
        appointments.setIphoneModel(iphoneModel);
        appointments.setPrice(price);
        appointments.setColor(color);
        appointments.setAddress(address);
        appointments.setQuantity(quantity);
        appointments.setPhoneNumber(phoneNumber);
        appointments.setAppointment_id(newAppointRef.getId());
        appointments.setUser_id(userId);

        newAppointRef.set(appointments).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    //getAppointments();
                    Toast.makeText(getContext(), "Query sucess", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "Query Failed", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onComplete: FAield to query data ");
                }
            }
        });

    }



    @Override
    public void deleteAppointment(final Appointments appointments) {
           FirebaseFirestore db = FirebaseFirestore.getInstance();



           final DocumentReference AppontmentRef = db
                   .collection("appointments")
                   .document();
           AppontmentRef.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
               @Override
               public void onComplete(@NonNull Task<Void> task) {
                   if (task.isSuccessful()){

                       Toast.makeText(getContext(), "Deleted " , Toast.LENGTH_SHORT).show();
                       adapter.removeAppointment(appointments);
                       Log.d(TAG, "onComplete: " );
                   }else {
                       Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                   }
               }
           });

    }

    @Override
    public void updateAppointment(final Appointments appointments) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference appointRef = db
                .collection("appointments")
                .document(appointments.getAppointment_id());

        appointRef.update(
                "name",appointments.getName(),
                "date", appointments.getDate(),
                "time", appointments.getTime(),
                "address", appointments.getAddress(),
                "color", appointments.getColor(),
                "iphoneModel", appointments.getIphoneModel(),
                "phoneNumber", appointments.getPhoneNumber(),
                "price", appointments.getPrice(),
                "quantity", appointments.getQuantity()
        ).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getContext(), "Appointment Updated", Toast.LENGTH_SHORT).show();

                    adapter.updateAppointment(appointments);
                }else {
                    Toast.makeText(getContext(), "Failed to update", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onAppointmentSelected(Appointments appointments) {

//        Toast.makeText(getContext(),"Selected " + appointments.getAppointment_id(), Toast.LENGTH_SHORT).show();
//        Log.d(TAG, "onAppointmentSelected: selected");
//        ViewDialogEdit dialog = ViewDialogEdit.newInstance(appointments);
//        if (getFragmentManager() != null) {
//           dialog.show(getFragmentManager(),"Edit Appt");
//
//        }


    }




    public ArrayList<Appointments> getLstAppointments() {
        return lstAppointments;
    }

    public AppointmentAdaptercOPY getAdapter() {
        return adapter;
    }

    public FloatingActionButton getmFloatingActionButton() {
        return mFloatingActionButton;
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        mIMaintActivity = (IMaintActivity)context;
//    }
}
