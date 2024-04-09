package com.ensias.healthcareapp.model;

public class Request {
    private  String id_patient;
    private String id_doctor;
    private String hour_path;
    private String tel;
    public Request(){

    }

    public Request(String idPat, String idDoc) {

    }

    public String getHour_path() {
        return hour_path;
    }

    public void setHour_path(String hour_path) {
        this.hour_path = hour_path;
    }

    public String getId_patient() {
        return id_patient;
    }

    public void setId_patient(String id_patient) {
        this.id_patient = id_patient;
    }

    public String getId_doctor() {
        return id_doctor;
    }

    public void setId_doctor(String id_doctor) {
        this.id_doctor = id_doctor;
    }



    public void setTel(java.lang.String tel) {
        this.tel = tel;
    }

    public Request(String id_patient, String id_doctor, String tel) {
        this.id_patient = id_patient;
        this.id_doctor = id_doctor;
        this.tel = tel;
    }

    public String tel() {
        return tel;
    }
}
