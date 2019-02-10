package com.example.samim.bottomnavigationwithfragment;

import com.example.samim.bottomnavigationwithfragment.Models.NewStock;

import java.util.ArrayList;

public interface IStockActivity {
    void createStock(String model,
            String color,
            String price,
            String quantity);
}
