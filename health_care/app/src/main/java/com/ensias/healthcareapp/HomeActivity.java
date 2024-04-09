package com.ensias.healthcareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ensias.healthcareapp.Common.Common;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {
    Button SignOutBtn;
    Button searchBtn;
    Button myDoctors;
    Button BtnRequst;
    Button profile;
    Button appointment;
    Button remindmeappoint;
    Button chatbotBtn;
    Button aware;
    Button ps;
    Button ds;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        appointment = findViewById(R.id.appointement2);
        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(HomeActivity.this, PatientAppointementsActivity.class);
                startActivity(k);
            }
        });

        ps = findViewById(R.id.patient_statistics);
        ps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(HomeActivity.this, AddressStatisticsActivity.class);
                startActivity(k);
            }
        });


        ds = findViewById(R.id.doctor_statistics);
        ds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(HomeActivity.this, CountStatisticsActivity.class);
                startActivity(k);
            }
        });

        chatbotBtn = findViewById(R.id.chatbot);
        chatbotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://mediafiles.botpress.cloud/47ff0fa8-c3fa-4a87-ac21-d2fe3bb52ad8/webchat/bot.html "; // Replace with your URL
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        aware = findViewById(R.id.vaccaware);
        aware.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(HomeActivity.this, vaccawarness.class);
                startActivity(k);

            }
        });



        searchBtn = findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(HomeActivity.this, SearchPatActivity.class);
                startActivity(k);
            }
        });
        SignOutBtn=findViewById(R.id.signOutBtn);
        SignOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        myDoctors = (Button)findViewById(R.id.myDoctors);
        myDoctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(HomeActivity.this, MyDoctorsAvtivity.class);
                startActivity(k);
            }
        });
        BtnRequst = findViewById(R.id.btnRequst);
        BtnRequst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DossierMedical.class);
                intent.putExtra("patient_email",FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());
                startActivity(intent);
            }
        });

        profile = findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(HomeActivity.this, ProfilePatientActivity.class);
                startActivity(k);
            }
        });

//        chatbotBtn = findViewById(R.id.chatbotBtn);
//        chatbotBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent k = new Intent(HomeActivity.this, chatbot.class);
//                startActivity(k);
//            }
//        });

        Common.CurrentUserid= FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
        FirebaseFirestore.getInstance().collection("User").document(Common.CurrentUserid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Common.CurrentUserName = documentSnapshot.getString("name");
            }
        });

    }
}
