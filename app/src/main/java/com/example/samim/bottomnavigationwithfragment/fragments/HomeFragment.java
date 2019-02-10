package com.example.samim.bottomnavigationwithfragment.fragments;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.FontRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.samim.bottomnavigationwithfragment.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    BarChart barChart = null;
    TextView tv, mTotalSales, mMonth_Year;
    String Today;
    String[] xValues;
    ArrayList<BarEntry> yValues;
    float sum = 0;
    String dateVal;
    String yVal;
    double price;
    String daysOfTheWeek;
    String month;

    public HomeFragment() {
        // Required empty public constructor
        xValues = new String[7];
        yValues = new ArrayList<BarEntry>();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/dd/yyyy", Locale.US);
        Date d = new Date();
        Today = simpleDateFormat.format(d.getTime());


        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("M", Locale.US);
        Date date1 = new Date();
        month = simpleDateFormat1.format(date1);

        super.onViewCreated(view, savedInstanceState);
        tv = getView().findViewById(R.id.txt_text_total_sales);
        barChart = getView().findViewById(R.id.piechart_id);
        mTotalSales = getView().findViewById(R.id.txt_total_sales);
        mMonth_Year = view.findViewById(R.id.txt_date_month);

        mMonth_Year.setText(dateAndYear());

        Log.d(TAG, "onViewCreated: barenter is called");

        BarChartEntery();
        getTotal();

    }

    public BarChart getBarChart() {
        return barChart;
    } //Chart method dummy data

    public void BarChartEntery() {

// final ArrayList<BarEntry>
        yValues = new ArrayList<>(); // y-xes
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("appointments");
        collectionReference
                .whereEqualTo("user_id", FirebaseAuth.getInstance().getCurrentUser().getUid())
                .whereLessThanOrEqualTo("date",Today)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {

                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            String yVal = document.getString("price");
                            dateVal = document.getString("date");
                            double price = Double.parseDouble(yVal);
                           if (dateVal.equals(Today)) {
                               sum += price;

                               Log.d(TAG, "onEvent: Today is -> " + Today + " FireStore Date -> " + dateVal + " Sum -> " + sum);
                           }
                            //getting the date for the bar chart
                            //getDataForBar();
                    }
                            xValues = new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

                        float yValues1 [] = {10, 20, 30, 40, 50};
                        String xValues1 [] = {"first", "second", "third", "four", "five"};

                        int i = 0;
                        while (i < xValues.length) {
                            yValues.add(new BarEntry(i, sum));
                           i++;
                            Log.d(TAG, "onEvent: While i -> " + i);
                        }

                            XAxis xAxis = barChart.getXAxis(); // the x-x (xValues)
                            xAxis.setGranularity(1);
                            xAxis.setGranularityEnabled(true);
                            xAxis.setValueFormatter(new myXAxisValueFormatter(xValues));
                            xAxis.setTextColor(Color.argb(255, 231, 192, 251));
                            xAxis.setTextSize(12f);
                            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


                            BarDataSet barDataSet = new BarDataSet(yValues, "");

                            List<IBarDataSet> dataSets = new ArrayList<>();
                            dataSets.add(barDataSet);
                            BarData data = new BarData(dataSets);

                            data.setBarWidth(0.9f);

                            barDataSet.setColor(Color.argb(255, 231, 192, 251));
                            barDataSet.setValueTextSize(12f);
                            barDataSet.setValueTextColor(Color.argb(255, 231, 192, 251));

                            barChart.setFitBars(true);
                            barChart.invalidate();
                            barChart.setNoDataTextColor(Color.WHITE);
                            barChart.setDoubleTapToZoomEnabled(false);
                            barChart.setPinchZoom(false);
                            barChart.setDrawGridBackground(false);
                            barChart.setGridBackgroundColor(Color.TRANSPARENT);
                            barChart.setDrawValueAboveBar(true);
                            barChart.animateY(1500);
                            barChart.setTouchEnabled(false);
                            barChart.setDragEnabled(false);
                            barChart.setScaleEnabled(false);
                            barDataSet.setDrawValues(true);
                            barChart.getDescription().setEnabled(false);
                            barChart.getLegend().setEnabled(false);
                            barChart.getAxisLeft().setDrawGridLines(false);
                            barChart.getXAxis().setDrawGridLines(false);
                            YAxis leftAxis = barChart.getAxisLeft();
                            YAxis righttAxis = barChart.getAxisRight();
                            righttAxis.setEnabled(false);
                            leftAxis.setTextColor(Color.argb(255, 231, 192, 251));

                            leftAxis.setTextSize(12f);
                            leftAxis.setDrawGridLines(false);
                            leftAxis.setLabelCount(7);
                            leftAxis.setAxisMaximum(500);
                            leftAxis.setAxisMinimum(10);
                            barChart.setData(data);
                           // drawBarGraph(yValues1,xValues1);

                    }
                });

    }


    //Getting the total sales from the firestore
    private void getTotal() {
        Log.d(TAG, "getTotal: called");

        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        mFirestore.collection("appointments").whereEqualTo("user_id", FirebaseAuth.getInstance()
                .getCurrentUser().getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                        double sum = 0;
                        for (DocumentSnapshot doc : queryDocumentSnapshots) {
                            String pValue = doc.getString("total");
                            double mTotl = Double.parseDouble(pValue);
                            sum += mTotl;

                            mTotalSales.setText(String.valueOf("$" + sum));
                        }
                    }
                });
    }


    //set the date and the year for the graph header
    private String dateAndYear() {
        Calendar c = Calendar.getInstance();
        String month = new SimpleDateFormat("MMMM", Locale.US).format(c.getTime());
        String year = new SimpleDateFormat("yyyy", Locale.US).format(c.getTime());
        return month + " - " + year;

    }

    //formatter x values
    public class myXAxisValueFormatter implements IAxisValueFormatter {

        public myXAxisValueFormatter(String[] vlaues) {
            this.mValues = vlaues;
        }

        private String[] mValues;

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mValues[(int) value];
        }
    }

    //for getting values from the db
    private void drawBarGraph(float [] yValues, String[] xValues) {
        ArrayList<BarEntry> yData = new ArrayList<>();
        for (int i = 0; i < yValues.length; i++){
            yData.add(new BarEntry(i, yValues[i]));
        }
        for (int i = 0; i < xValues.length; i++){

        }

    }
}