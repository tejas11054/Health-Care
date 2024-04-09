package com.ensias.healthcareapp.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ensias.healthcareapp.R;
import com.ensias.healthcareapp.VaccinationCountdownActivity;
import com.ensias.healthcareapp.model.ApointementInformation;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static androidx.core.content.ContextCompat.startActivity;

public class PatientAppointmentsAdapter extends FirestoreRecyclerAdapter<ApointementInformation, PatientAppointmentsAdapter.PatientAppointmentsHolder> {
    StorageReference pathReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef;
    DocumentSnapshot documentSnapshot;
    final String doctorID = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();

    public PatientAppointmentsAdapter(@NonNull FirestoreRecyclerOptions<ApointementInformation> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PatientAppointmentsHolder patientAppointmentsHolder, int position, @NonNull final ApointementInformation apointementInformation) {
        patientAppointmentsHolder.dateAppointement.setText(apointementInformation.getTime());
        patientAppointmentsHolder.patientName.setText(apointementInformation.getDoctorName());
        patientAppointmentsHolder.appointementType.setText(apointementInformation.getApointementType());
        patientAppointmentsHolder.type.setText(apointementInformation.getType());
        String doctorEmail = apointementInformation.getDoctorId();
        docRef = db.collection("Doctor").document("" + doctorEmail + "");
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document != null) {
                    patientAppointmentsHolder.phone.setText(document.getString("tel"));
                }
            }
        });

        String imageId = apointementInformation.getDoctorId();
        pathReference = FirebaseStorage.getInstance().getReference().child("DoctorProfile/" + imageId + ".jpg");
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Picasso.with(patientAppointmentsHolder.image.getContext())
                        .load(uri)
                        .placeholder(R.mipmap.ic_launcher)
                        .fit()
                        .centerCrop()
                        .into(patientAppointmentsHolder.image))
                .addOnFailureListener(exception -> {
                    // Handle any errors
                });

        if (apointementInformation.getApointementType().equals("Consultation")) {
            patientAppointmentsHolder.appointementType.setBackground(patientAppointmentsHolder.appointementType.getContext().getResources().getDrawable(R.drawable.button_radius_primary_color));
        }
        if (apointementInformation.getType().equals("Accepted")) {
            patientAppointmentsHolder.type.setTextColor(Color.parseColor("#20bf6b"));
            patientAppointmentsHolder.type.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), VaccinationCountdownActivity.class);
                intent.putExtra("appointmentDate", apointementInformation.getTime());
                v.getContext().startActivity(intent);
            });
        } else if (apointementInformation.getType().equals("Checked")) {
            patientAppointmentsHolder.type.setTextColor(Color.parseColor("#8854d0"));
        } else {
            patientAppointmentsHolder.type.setTextColor(Color.parseColor("#eb3b5a"));
        }
    }

    @NonNull
    @Override
    public PatientAppointmentsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_appointments_item, parent, false);
        return new PatientAppointmentsHolder(v);
    }

    static class PatientAppointmentsHolder extends RecyclerView.ViewHolder {
        TextView dateAppointement;
        TextView patientName;
        TextView appointementType;
        TextView type;
        TextView phone;
        ImageView image;

        PatientAppointmentsHolder(@NonNull View itemView) {
            super(itemView);
            dateAppointement = itemView.findViewById(R.id.appointement_date);
            patientName = itemView.findViewById(R.id.patient_name);
            appointementType = itemView.findViewById(R.id.appointement_type);
            type = itemView.findViewById(R.id.type);
            phone = itemView.findViewById(R.id.patient_phone);
            image = itemView.findViewById(R.id.patient_image);
        }
    }
}
