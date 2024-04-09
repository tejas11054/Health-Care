package com.ensias.healthcareapp.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.ensias.healthcareapp.fireStoreApi.PatientHelper;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.HttpResponse;
import com.ensias.healthcareapp.MainActivity;
import com.ensias.healthcareapp.PatientRequestPage;
import com.ensias.healthcareapp.R;
import com.ensias.healthcareapp.model.Doctor;
import com.ensias.healthcareapp.model.Patient;
import com.ensias.healthcareapp.model.Request;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static androidx.core.content.ContextCompat.startActivities;
import static androidx.core.content.ContextCompat.startActivity;

import javax.net.ssl.HttpsURLConnection;

public class PatRequestAdapter extends FirestoreRecyclerAdapter<Request, PatRequestAdapter.PatRequesteHolder> {
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static CollectionReference addRequest = db.collection("Request");

    public PatRequestAdapter(@NonNull FirestoreRecyclerOptions<Request> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final PatRequesteHolder RequestHolder, final int i, @NonNull final Request request) {
        final TextView t = RequestHolder.title ;
        final String idPat = request.getId_patient();
        final String idDoc = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
        final String HourPath = request.getHour_path();
        final String tel = request.tel();

        db.collection("Doctor").document(idDoc).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                final Doctor onligneDoc = documentSnapshot.toObject(Doctor.class);
                db.collection("Patient").document(idPat).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        final Patient pat= documentSnapshot.toObject(Patient.class);
                        RequestHolder.title.setText(pat.getName());
                        RequestHolder.specialite.setText("Want to be your patient");
                        RequestHolder.addDoc.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(final View v) {
                                db.collection("Patient").document(idPat).collection("MyDoctors").document(idDoc).set(onligneDoc);
                                db.collection("Doctor").document(idDoc+"").collection("MyPatients").document(idPat).set(pat);
                                addRequest.whereEqualTo("id_doctor",idDoc+"").whereEqualTo("id_patient",idPat+"").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        for (QueryDocumentSnapshot documentSnapshot1 : queryDocumentSnapshots){
                                            addRequest.document(documentSnapshot1.getId()).delete();

                                        }
                                    }
                                });
                                db.document(HourPath).update("choosen","true");
                                Snackbar.make(t, "Patient added", Snackbar.LENGTH_SHORT).show();
                                RequestHolder.addDoc.setVisibility(View.INVISIBLE);
                                String apiKey="GUOMZ41zBTW2Ltn8yuvePb3Yl6jSdkpsmgh7KQrANw5DJFafH0XmkNjrsluEzPy02fY6cCv8BeL7IDhx";
                                String sendId="FSTSMS";
                                //important step...

                                String language="english";

                                String route="p";


                                String myUrl="https://www.fast2sms.com/dev/bulk?authorization="+apiKey+"&sender_id="+sendId+"&message="+"&language="+language+"&route="+route+"&numbers="+tel;

                                //sending get request using java..

                                URL url= null;
                                try {
                                    url = new URL(myUrl);
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }

                                HttpsURLConnection con= null;
                                try {
                                    con = (HttpsURLConnection)url.openConnection();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                                try {
                                    con.setRequestMethod("GET");
                                } catch (ProtocolException e) {
                                    e.printStackTrace();
                                }

                                con.setRequestProperty("User-Agent", "Mozilla/5.0");
                                con.setRequestProperty("cache-control", "no-cache");
                                System.out.println("Wait..............");


                            }
                        });
                    }
                });
            }
        });


    }

    public void deleteItem(int position) {
        String hour =getSnapshots().getSnapshot(position).getString("hour_path");
        db.document(hour).delete();
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    @NonNull
    @Override
    public PatRequesteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pat_request_item,
                parent, false);
        return new PatRequesteHolder(v);
    }

    class PatRequesteHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView specialite;
        ImageView image;
        Button addDoc;
        public PatRequesteHolder(@NonNull View itemView) {
            super(itemView);
            addDoc = itemView.findViewById(R.id.pat_request_accept_btn);
            title= itemView.findViewById(R.id.pat_request_title);
            specialite=itemView.findViewById(R.id.pat_request_description);
            image=itemView.findViewById(R.id.pat_request_item_image);

        }
    }
}
