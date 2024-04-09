package com.ensias.healthcareapp.Common;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Common {
    public static final String KEY_DISPLAY_TIME_SLOT = "DISPLAY_TIME_SLOT";

    public static final Object DISABLE_TAG = "DISABLE";
    public static final String KEY_ENABLE_BUTTON_NEXT = "ENABLE_BUTTON_NEXT";
    public static final String KEY_TIME_SLOT = "TIME_SLOT" ;
    public static final String KEY_STEP = "STEP" ;
    public static final String KEY_CONFIRM_BOOKING = "CONFIRM_BOOKING" ;
    public static String CurrentUserid ;
    public static  String CurrentUserName;
    public static String CurrentUserType = "patient";
    public static int step = 0;
    public static String CurreentDoctor = "testdoc@testdoc.com";
    public static String Currentaappointementatype;
    public static int currentTimeSlot = -1;
    public static String CurrentDoctorName = "yassine";
    public static Calendar currentDate = Calendar.getInstance();
    public static String CurrentPhone = "062912361255";
    public static SimpleDateFormat simpleFormat = new SimpleDateFormat("dd_MM_yyyy");
    public static String CurrentDoctor;

    public static int convertBloodToInt(String s){
        switch (s){
            case "A+":
                return 0;
            case "A-":
                return 1;
            case "B+":
                return 2;
            case "B-":
                return 3;
            case "AB+":
                return 4;
            case "AB-":
                return 5;
            case "O+":
                return 6;
            case "O-":
                return 7;
            default:
                return 0;
        }
    }
    public static String convertTimeSlotToString(int slot) {
        switch(slot) {
            case 0:
                return "9:00-9:15";
            case 1:
                return "9:15-09:30";
            case 2:
                return "09:30-09:45";
            case 3:
                return "09:45-10:00";
            case 4:
                return "10:00-10:15";
            case 5:
                return "10:15-10:30";
            case 6:
                return "10:30-10:45";
            case 7:
                return "10:45-11:00";
            case 8:
                return "11:00-11:15";
            case 9:
                return "11:15-11:30";
            case 10:
                return "11:30-11:45";
            case 11:
                return "11:45-12:00";
            case 12:
                return "12:00-12:15";
            case 13:
                return "12:15-12:30";
            case 14:
                return "12:30-12:45";
            case 15:
                return "12:45-13:00";
            case 16:
                return "14:00-14:15";
            case 17:
                return "14:15-14:30";
            case 18:
                return "14:30-14:45";
            case 19:
                return "14:45-15:00";
            case 20:
                return "15:00-15:15";
            case 21:
                return "15:15-15:30";
            default:
                return "Closed";
        }
    }
}
