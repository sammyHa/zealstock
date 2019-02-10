package com.example.samim.bottomnavigationwithfragment;

import com.example.samim.bottomnavigationwithfragment.Models.Appointments;

public interface IMaintActivity {

   void crateNewAppointment(
           String name,
           String date,
           String time,
           String address,
           String phoneNumber,
           String iphoneModel,
           String color,
           String quantity,
           String price,
           String total

   );
//    void updateStock(StockActivity stock);
    void deleteAppointment(Appointments appointments);
    void updateAppointment(Appointments appointments);
    void onAppointmentSelected(Appointments appointments);
}
