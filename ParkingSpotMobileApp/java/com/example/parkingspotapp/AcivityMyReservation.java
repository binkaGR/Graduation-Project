package com.example.parkingspotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.sql.Time;
import java.text.SimpleDateFormat;

public class AcivityMyReservation extends AppCompatActivity implements OnConectionCompleate {

    private Button checkReservation;
    private Button backParkingSpot;
    private TextView CarName;
    private TextView ParkingSpotName;
    private TextView Location;
    private TextView StartReservation;
    private TextView EndReservation;
    private  String API_URL ;
    private  ParkingReservationInformationJson parkingReservationInformationJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acivity_my_reservation);
        checkReservation = (Button) findViewById(R.id.buttonChekParkingSpot);
        backParkingSpot = (Button) findViewById(R.id.buttonBackMyReservation);
        CarName = (TextView) findViewById(R.id.CarNumber);
        ParkingSpotName = (TextView) findViewById(R.id.viewParkingSpotName);
        Location  = (TextView) findViewById(R.id.viewLocation);
        StartReservation = (TextView) findViewById(R.id.viewStartTime);
        EndReservation = (TextView) findViewById(R.id.viewEndTime);
        backParkingSpot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityParkingSpot();
            }
        });
        checkReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String carName = CarName.getText().toString();
                GetDataServer GetDateServer = new GetDataServer(AcivityMyReservation.this);
                API_URL = "http://192.168.253.130:8080/parkingspot/GetParkingSpotInformation/" + carName;
                GetDateServer.execute(API_URL);
            }
        });
    }
    public  void openActivityParkingSpot()
    {
        Intent intent = new Intent(this, ActivityParkingSpot.class);
        startActivity(intent);
    }

    public void onTakeStringConnection(String response) {

        Gson gson = new Gson();
        parkingReservationInformationJson = gson.fromJson(response, ParkingReservationInformationJson.class);
        ParkingSpotName.setText(parkingReservationInformationJson.getParkingSpotName());
        Location.setText(parkingReservationInformationJson.getLocation());
        ParkingSpotName.setText(parkingReservationInformationJson.getParkingSpotName());
        Location.setText(parkingReservationInformationJson.getLocation());
        StartReservation.setText(parkingReservationInformationJson.getStartReservation());
        EndReservation.setText(parkingReservationInformationJson.getEndtReservation());
    }
}