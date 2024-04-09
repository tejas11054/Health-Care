 package com.ensias.healthcareapp;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.ensias.healthcareapp.fragment.BookingStep1Fragment;

 public class fragment_booking_step_one extends Activity {
     protected void onCreate(Bundle savedInstanceState){
         super.onCreate(savedInstanceState);
         setContentView(R.layout.fragment_booking_step_one);

         Log.i(TAG, "This is an info log message");

     }




}
