package com.example.samim.bottomnavigationwithfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.samim.bottomnavigationwithfragment.adapter.StockAdapter;
import com.example.samim.bottomnavigationwithfragment.fragments.StockFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
@IgnoreExtraProperties
public class StockActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "StockActivity";


    private EditText mModel,mColor,mPrice,mQuantity;
    private Button mStocking, mUpadte;
    private String stock_id;
    private StockAdapter adapter;
    private StockFragment mStockFragment;

    private RelativeLayout mParentLayout;
    private IStockActivity mIStockActivity;

    //Firestore
    private FirebaseFirestore mFireStore;
    DocumentSnapshot mLastQueryDocument;
    private FirebaseAuth.AuthStateListener mAuthListener;


    public StockActivity(){
        //default emapyt constractor for the firebase to not crush
    }
//
//    public StockActivity(EditText mModel, EditText mColor, EditText mPrice, EditText mQuantity, String stock_id) {
//        this.mModel = mModel;
//        this.mColor = mColor;
//        this.mPrice = mPrice;
//        this.mQuantity = mQuantity;
//        this.stock_id = stock_id;
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_form);
        mModel = findViewById(R.id.st_iphone_model_id);
        mColor = findViewById(R.id.st_iphone_color_id);
        mPrice = findViewById(R.id.st_price_id);
        mQuantity = findViewById(R.id.et_quantity_id);
        mStocking = findViewById(R.id.btn_stock_now_id);

        mParentLayout = findViewById(R.id.parent_stock_form_id);


        setupFirebaseAuth();
        mStocking.setOnClickListener(this);
    }




    //FireStore to insert data into the database document.
    private void insertToFireStore(){


        String iphonemodel = mModel.getText().toString().trim();
        String price = mPrice.getText().toString().trim();
        String color = mColor.getText().toString().trim();
        String quantity = mQuantity.getText().toString().trim();

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mFireStore = FirebaseFirestore.getInstance();


        Map<String, String> stock = new HashMap<>();


        stock.put("miphonemodel",iphonemodel);
        stock.put("mprice",price);
        stock.put("mcolor",color);
        stock.put("mquantity",quantity);
        stock.put("user_id", userId);

        mFireStore.collection("products").add(stock).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String error = e.getMessage();

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_stock_now_id:{

                String model = mModel.getText().toString();
                String color = mColor.getText().toString();
                String quantity = mQuantity.getText().toString();
                String price = mPrice.getText().toString();

                if (!model.equals("") && !color.equals("")){

                    insertToFireStore();
                   // mIStockActivity.createStock(model,color,price,quantity);

                    makeSnackBarMessage("Product Stocked!");
                    FirebaseFirestore mfirestore = FirebaseFirestore.getInstance();
                    mfirestore.collection("products").addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {

                            if (e !=null){
                                Log.d(TAG, "onEvent: " + e.getMessage());
                            }
                            for (DocumentChange doc: queryDocumentSnapshots.getDocumentChanges()){
                                if (doc.getType() == DocumentChange.Type.ADDED){
                                    mStockFragment = new StockFragment();

                                }
                            }
                        }
                    });

                }else {
                    makeSnackBarMessage("All Fields Required");
                }

                break;
            }

        }

    }

    private void makeSnackBarMessage(String message){
        Snackbar.make(mParentLayout,message,Snackbar.LENGTH_LONG).show();
    }


    /**
     *  ******************** setting up the firebase Auth ****************
     */

    private void setupFirebaseAuth(){
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    //do nothing stay signin or signin
                }else {
                    Intent intent = new Intent(StockActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener !=null){
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        mIStockActivity = (IStockActivity) fragment;
    }



}
