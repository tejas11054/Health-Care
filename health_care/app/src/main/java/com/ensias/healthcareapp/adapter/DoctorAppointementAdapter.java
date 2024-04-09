package com.ensias.healthcareapp.adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.Context;

import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ensias.healthcareapp.ChatActivity;
import com.ensias.healthcareapp.DoctorAppointementActivity;
import com.ensias.healthcareapp.MainActivity;
import com.ensias.healthcareapp.R;
import com.ensias.healthcareapp.model.ApointementInformation;
import com.ensias.healthcareapp.model.Doctor;
import com.ensias.healthcareapp.model.Patient;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DoctorAppointementAdapter extends FirestoreRecyclerAdapter<ApointementInformation, DoctorAppointementAdapter.MyDoctorAppointementHolder> {
    StorageReference pathReference ;
    String tel;





    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.
     * @param options
     */
    public DoctorAppointementAdapter(@NonNull FirestoreRecyclerOptions<ApointementInformation> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyDoctorAppointementHolder myDoctorAppointementHolder, @SuppressLint("RecyclerView") int position, @NonNull final ApointementInformation apointementInformation) {
        myDoctorAppointementHolder.dateAppointement.setText(apointementInformation.getTime());
        myDoctorAppointementHolder.patientName.setText(apointementInformation.getPatientName());
        myDoctorAppointementHolder.appointementType.setText(apointementInformation.getApointementType());



//        myDoctorAppointementHolder.approveBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                apointementInformation.setType("Accepted");
//                FirebaseFirestore.getInstance().collection("Patient").document(apointementInformation.getPatientId()).collection("calendar")
//                        .document(apointementInformation.getTime().replace("/","_")).set(apointementInformation);
//                FirebaseFirestore.getInstance().document(apointementInformation.getChemin()).update("type","Accepted");
//                FirebaseFirestore.getInstance().collection("Doctor").document(apointementInformation.getDoctorId()).collection("calendar")
//                        .document(apointementInformation.getTime().replace("/","_")).set(apointementInformation);
//
//
//
//
////////////// here add patient friend to doctor
//
//                FirebaseFirestore.getInstance().document("Patient/"+apointementInformation.getPatientId()).get()
//                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                            @Override
//                            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                FirebaseFirestore.getInstance().collection("Doctor").document(apointementInformation.getDoctorId()+"")
//                                        .collection("MyPatients").document(apointementInformation.getPatientId()).set(documentSnapshot.toObject(Patient.class));
//                            }
//                        });
//                FirebaseFirestore.getInstance().document("Doctor/"+apointementInformation.getDoctorId()).get()
//                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                            @Override
//                            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                FirebaseFirestore.getInstance().collection("Patient").document(apointementInformation.getPatientId()+"")
//                                        .collection("MyDoctors").document(apointementInformation.getPatientId()).set(documentSnapshot.toObject(Doctor.class));
//                            }
//                        });
//
//
//
//
////
//            }
//
//
//        });


        myDoctorAppointementHolder.approveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Assuming 'apointementInformation' contains the patient's ID
                String patientId = apointementInformation.getPatientId();

                // Retrieve the patient's document from Firestore
                FirebaseFirestore.getInstance().collection("Patient").document(patientId).get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    // Retrieve the patient's phone number
//                                    String phoneNumber = documentSnapshot.getString("phoneNumber");
//
//                                    // Use the retrieved phone number to send a message
//                                    String message = "Dear " + apointementInformation.getPatientName() + ", Your appointment is accepted and scheduled for " + apointementInformation.getApointementType() + " on " + apointementInformation.getTime() + ". Please check the status in the application.";
//                                    sendSMS(phoneNumber, message);

                                    // Proceed with updating Firestore and other tasks
                                    apointementInformation.setType("Accepted");
                                    FirebaseFirestore.getInstance().collection("Patient").document(apointementInformation.getPatientId()).collection("calendar")
                                            .document(apointementInformation.getTime().replace("/", "_")).set(apointementInformation);
                                    FirebaseFirestore.getInstance().document(apointementInformation.getChemin()).update("type", "Accepted");
                                    FirebaseFirestore.getInstance().collection("Doctor").document(apointementInformation.getDoctorId()).collection("calendar")
                                            .document(apointementInformation.getTime().replace("/", "_")).set(apointementInformation);

                                    // Add patient as a friend to doctor

                                    FirebaseFirestore.getInstance().document("Patient/"+apointementInformation.getPatientId()).get()
                                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    FirebaseFirestore.getInstance().collection("Doctor").document(apointementInformation.getDoctorId()+"")
                                                            .collection("MyPatients").document(apointementInformation.getPatientId()).set(documentSnapshot.toObject(Patient.class));
                                                }
                                            });
                                    FirebaseFirestore.getInstance().document("Doctor/"+apointementInformation.getDoctorId()).get()
                                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    FirebaseFirestore.getInstance().collection("Patient").document(apointementInformation.getPatientId()+"")
                                                            .collection("MyDoctors").document(apointementInformation.getPatientId()).set(documentSnapshot.toObject(Doctor.class));
                                                }
                                            });

//                                    addPatientAsFriendToDoctor(apointementInformation.getDoctorId(), apointementInformation.getPatientId());

                                    // Delete the appointment document
                                    getSnapshots().getSnapshot(position).getReference().delete();
                                } else {
                                    Log.d("TAG", "No such document");
                                }
                            }

                            private void addPatientAsFriendToDoctor(String doctorId, String patientId) {
                            }

                            private void sendSMS(String phoneNumber, String message) {
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("TAG", "Error getting documents: " + e);

//                                Toast.makeText(, "Your message here", Toast.LENGTH_SHORT).show();


                            }
                        });
            }
        });



        myDoctorAppointementHolder.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apointementInformation.setType("Refused");
                FirebaseFirestore.getInstance().collection("Patient").document(apointementInformation.getPatientId()).collection("calendar")
                        .document(apointementInformation.getTime().replace("/","_")).set(apointementInformation);
                FirebaseFirestore.getInstance().document(apointementInformation.getChemin()).delete();
                getSnapshots().getSnapshot(position).getReference().delete();
            }
        });

        String imageId = apointementInformation.getPatientId()+".jpg"; //add a title image
        pathReference = FirebaseStorage.getInstance().getReference().child("DoctorProfile/"+ imageId); //storage the image
        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(myDoctorAppointementHolder.patient_image.getContext())
                        .load(uri)
                        .placeholder(R.mipmap.ic_launcher)
                        .fit()
                        .centerCrop()
                        .into(myDoctorAppointementHolder.patient_image);//Image location

                // profileImage.setImageURI(uri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

    }



    private void openPage(Context wf, Doctor d){
        Intent i = new Intent(wf, ChatActivity.class);
        i.putExtra("key1",d.getEmail()+"_"+ FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());
        i.putExtra("key2",FirebaseAuth.getInstance().getCurrentUser().getEmail().toString()+"_"+d.getEmail());
        wf.startActivity(i);
    }

    @NonNull
    @Override
    public MyDoctorAppointementHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_appointement_item, parent, false);
        return new MyDoctorAppointementHolder(v);
    }

    class MyDoctorAppointementHolder extends RecyclerView.ViewHolder{
        //Here we hold the MyDoctorAppointementItems
        TextView dateAppointement;
        TextView patientName;
        Button approveBtn;
        Button cancelBtn;
        TextView appointementType;
        ImageView patient_image;
        TextView firstsignTel;
        public MyDoctorAppointementHolder(@NonNull View itemView) {
            super(itemView);
            dateAppointement = itemView.findViewById(R.id.appointement_date);
            patientName = itemView.findViewById(R.id.patient_name);
            approveBtn = itemView.findViewById(R.id.btn_accept);
            cancelBtn = itemView.findViewById(R.id.btn_decline);
            appointementType = itemView.findViewById(R.id.appointement_type);
            patient_image = itemView.findViewById(R.id.patient_image);
            firstsignTel = itemView.findViewById(R.id.firstSignTel);
        }
    }




}
