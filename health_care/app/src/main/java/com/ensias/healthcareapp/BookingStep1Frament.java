/*
package com.ensias.healthcareapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ensias.healthcareapp.fragment.BookingStep1Fragment;

public class BookingStep1Frament extends Activity {
    static public Spinner spinner;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmed_appointements);
        Spinner spinner = findViewById(R.id.spinner);

        // Define an array of values for the Spinner
        String[] planets = {"Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune"};

        // Create an ArrayAdapter using the array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, planets);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // Example of how to retrieve the selected value from the Spinner
        String selectedValue = spinner.getSelectedItem().toString();
        System.out.println("Selected value: " + selectedValue);
    }

    @Override

    }
}*/
