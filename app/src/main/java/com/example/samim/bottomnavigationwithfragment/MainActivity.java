package com.example.samim.bottomnavigationwithfragment;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.samim.bottomnavigationwithfragment.fragments.AccountFragment;
import com.example.samim.bottomnavigationwithfragment.fragments.AppointmentFragment;
import com.example.samim.bottomnavigationwithfragment.fragments.HomeFragment;
import com.example.samim.bottomnavigationwithfragment.fragments.StockFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_id);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        //this will load the home fragment by default
        loadFragment(new HomeFragment());
    }




    private boolean loadFragment(Fragment fragment){

        if(fragment !=null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
            return true;
        }

        return false;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.ic_home_id:
                fragment = new HomeFragment();
                break;
            case R.id.ic_appointment_id:
                fragment = new AppointmentFragment();
                break;
            case R.id.ic_stock_id:
                fragment = new StockFragment();
                break;
//            case R.id.ic_account:
//                fragment = new AccountFragment();
//                break;

        }
        return loadFragment(fragment);
    }
}
