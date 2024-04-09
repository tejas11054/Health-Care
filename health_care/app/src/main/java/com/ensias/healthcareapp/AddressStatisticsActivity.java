package com.ensias.healthcareapp;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import android.util.Log;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressStatisticsActivity extends AppCompatActivity {

    private static final String TAG = "AddressStatisticsActivity";

    private FirebaseFirestore db;
    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_statistics);

        pieChart = findViewById(R.id.pc);
        db = FirebaseFirestore.getInstance();

        // Fetch data from Firestore
        db.collection("Patient")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Map<String, Integer> addressCountMap = new HashMap<>();

                        // Iterate through the documents and collect addresses
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            String address = document.getString("adresse");
                            if (address != null) {
                                // Count occurrences of each address
                                int count = addressCountMap.containsKey(address) ? addressCountMap.get(address) + 1 : 1;
                                addressCountMap.put(address, count);
                            }
                        }

                        // Populate pie chart
                        List<PieEntry> entries = new ArrayList<>();
                        List<Integer> colors = new ArrayList<>();
                        int totalRecords = 0;

                        // Calculate total records
                        for (Map.Entry<String, Integer> entry : addressCountMap.entrySet()) {
                            totalRecords += entry.getValue();
                        }

                        // Populate entries and colors
                        for (Map.Entry<String, Integer> entry : addressCountMap.entrySet()) {
                            String address = entry.getKey();
                            int count = entry.getValue();
                            float percentage = (count / (float) totalRecords) * 100;

                            entries.add(new PieEntry(percentage, address + " (" + count + ")"));

                            // Assign different colors to each address
                            int color = getRandomColor();
                            colors.add(color);
                        }

                        // Apply colors to the pie chart dataset
                        PieDataSet dataSet = new PieDataSet(entries, "");
                        dataSet.setColors(colors);

                        // Configure other properties of the dataset as needed
                        dataSet.setValueFormatter(new PercentFormatter(pieChart));
                        dataSet.setValueTextColor(Color.BLACK);
                        dataSet.setValueTextSize(12f);

                        PieData data = new PieData(dataSet);
                        pieChart.setData(data);
                        pieChart.setCenterText("Patient Distribution");
                        pieChart.setCenterTextSize(20f);
                        pieChart.getDescription().setEnabled(false);
                        pieChart.invalidate();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error fetching documents: ", e);
                    }
                });
    }

    private int getRandomColor() {
        // Generate a random color
        return Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
    }
}
