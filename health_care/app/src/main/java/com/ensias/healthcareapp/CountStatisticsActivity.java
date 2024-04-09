package com.ensias.healthcareapp;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CountStatisticsActivity extends AppCompatActivity {

    private static final String TAG = "CountStatisticsActivity";

    private FirebaseFirestore db;
    private PieChart pieChart;
    private Map<String, Integer> patientsCountMap;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_statistics);

        pieChart = findViewById(R.id.pc);
        db = FirebaseFirestore.getInstance();
        patientsCountMap = new HashMap<>();

        // Fetch data from Firestore
        db.collection("Doctor")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot doctorSnapshot) {
                        // Iterate through each doctor

                        for (QueryDocumentSnapshot doctorDoc : doctorSnapshot) {
                            String doctorName = doctorDoc.getString("name");
                            if (doctorName != null) {
                                // Count number of patients for this doctor
                                db.collection("Doctor").document(doctorDoc.getId())
                                        .collection("MyPatients")
                                        .get()
                                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                            @Override
                                            public void onSuccess(QuerySnapshot patientSnapshot) {

                                                int patientCount = patientSnapshot.size();
                                                patientsCountMap.put(doctorName, patientCount);

                                                // Create pie chart data after processing all doctors
                                                if (patientsCountMap.size() == doctorSnapshot.size()) {
                                                    createPieChart();
                                                }
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void

                                            onFailure(Exception e) {
                                                Log.e(TAG, "Error fetching patient documents: ", e);
                                                Toast.makeText(CountStatisticsActivity.this, "Error fetching patient documents", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Log.e(TAG, "Error fetching doctor documents: ", e);


                        Toast.makeText(CountStatisticsActivity.this, "Error fetching doctor documents", Toast.LENGTH_SHORT).show();
                    }
                });

        // Set up click listener for pie chart entries
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(com.github.mikephil.charting.data.Entry e, com.github.mikephil.charting.highlight.Highlight h) {
                if (e instanceof PieEntry) {
                    PieEntry pieEntry = (PieEntry) e;
                    String doctorName = pieEntry.getLabel();

                    int totalCount = (int) pieEntry.getValue();
                    Toast.makeText(CountStatisticsActivity.this, "Doctor: " + doctorName + ", Total Patients: " + totalCount, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected() {
                // Do nothing
            }
        });
    }

    private void createPieChart() {
        // Create pie chart data
        ArrayList<PieEntry> entries = new ArrayList<>();


        for (Map.Entry<String, Integer> entry : patientsCountMap.entrySet()) {
            entries.add(new PieEntry(entry.getValue(), entry.getKey()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Patients per Doctor");

        // Set colors for the pie chart
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(pieChart));
        pieChart.setData(data);

        // Apply formatting to the pie chart

        pieChart.getDescription().setEnabled(false);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTextSize(12f);
        pieChart.setCenterText("Patients per Doctor");
        pieChart.setCenterTextSize(16f);
        pieChart.animateY(1400);
        pieChart.invalidate();
    }
}

