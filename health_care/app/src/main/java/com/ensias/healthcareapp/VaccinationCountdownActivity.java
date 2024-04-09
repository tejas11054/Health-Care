package com.ensias.healthcareapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.ensias.healthcareapp.adapter.PatientAppointmentsAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class VaccinationCountdownActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination_countdown);

        String appointmentDate = getIntent().getStringExtra("appointmentDate");

        // Get the current date and time
        Calendar currentDateTime = Calendar.getInstance();

        // Parse the appointment date
        SimpleDateFormat format = new SimpleDateFormat("HH:mm-HH:mm'at'dd/MM/yyyy");
        Date appointmentDateTime;
        try {
            appointmentDateTime = format.parse(appointmentDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

        // Set the parsed appointment date and time
        Calendar appointmentCalendar = Calendar.getInstance();
        appointmentCalendar.setTime(appointmentDateTime);

        // Calculate the time difference
        long timeDifferenceMillis = appointmentCalendar.getTimeInMillis() - currentDateTime.getTimeInMillis();

        // Convert time difference to years, months, days, hours, and minutes
        long years = timeDifferenceMillis / (1000L * 60L * 60L * 24L * 365L);
        long months = timeDifferenceMillis / (1000L * 60L * 60L * 24L * 30L) % 12L;
        long days = timeDifferenceMillis / (1000L * 60L * 60L * 24L) % 30L;
        long hours = timeDifferenceMillis / (1000L * 60L * 60L) % 24L;
        long minutes = (timeDifferenceMillis / (1000L * 60L)) % 60L;

        // Prepare the output message
        String outputMessage = "Time left For Appointment: \n" +
                minutes + " Minutes, \n"+
                hours + " Hours, \n" +
                days + " Days, \n" +
                months + " Months, \n  " +
                years + " Years" ;

        // Display the output in a dialog box
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Time Left");
        builder.setMessage(outputMessage);
        builder.setPositiveButton("OK", (dialog, which) -> {
            // Redirect to PatientAppointmentsAdapter
            Intent intent = new Intent(VaccinationCountdownActivity.this, PatientAppointmentsAdapter.class);
            startActivity(intent);
            finish(); // Optional: Finish current activity if needed
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
