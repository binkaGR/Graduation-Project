package com.example.parkingspotapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Time;
import java.util.List;

public class ActivityParkingSpot extends AppCompatActivity implements OnConectionCompleate{
    private User user;
    private Button buttonProfile;
    private Button buttonBackMyActivity;
    private Button buttonMyReservation;
    private Button buttonCreateReservation;
    private TextView editStartTime;
    private TextView editEndTime;
    private TextView editCarNumber;
    private  String API_URL ;
    private RecyclerView LocationParkingSpot;
    private List<Company> company;
    private int companyID;

    private  String CarNumber;
    private String StartRegistrationTime;
    private String EndRegistrationTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = (User) getIntent().getSerializableExtra("User");

        setContentView(R.layout.activity_parking_spot);
        GetDataServer GetDateServer = new GetDataServer(ActivityParkingSpot.this);
        API_URL = "http://192.168.253.130:8080/parkingspot/AllCompany";
        GetDateServer.execute(API_URL);
        editStartTime = (TextView) findViewById(R.id.editStartTime);
        editEndTime = (TextView) findViewById(R.id.editEndTime);
        editCarNumber = (TextView) findViewById(R.id.editCarNumber);

        buttonProfile = (Button) findViewById(R.id.buttonProfieActivity);
        buttonBackMyActivity = (Button) findViewById(R.id.buttonBackMainActivity);
        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProfileActivity();
            }
        });
        buttonBackMyActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActivity();
            }
        });
        buttonMyReservation = (Button) findViewById(R.id.ButtonMyReservation);
        buttonMyReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMyReservation();
            }
        });
        buttonCreateReservation = (Button) findViewById(R.id.buttonCreateReservation);
        buttonCreateReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CarNumber = editCarNumber.getText().toString();
                StartRegistrationTime = editStartTime.getText().toString();
                EndRegistrationTime = editEndTime.getText().toString();
                UpdateDataServer updateDataServer = new UpdateDataServer(ActivityParkingSpot.this);
                API_URL = "http://192.168.253.130:8080/parkingspot/GetParkingSpot/"+companyID+"/" + StartRegistrationTime+ "/" + EndRegistrationTime+"/" +CarNumber ;
                updateDataServer.execute(API_URL);
                //Toast.makeText(ActivityParkingSpot.this, ResponseFromServer, Toast.LENGTH_SHORT).show();
            }
        });

    }
    public  void openProfileActivity()
    {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("User" , user);
        startActivity(intent);
    }

    public  void openMainActivity()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public  void openMyReservation()
    {
        Intent intent = new Intent(this , AcivityMyReservation.class);
        startActivity(intent);
    }
    public void onTakeStringConnection(String response) {
        Gson gson = new Gson();

        Type companyListType = new TypeToken<List<Company>>(){}.getType();
        company = gson.fromJson(response, companyListType);
        LocationParkingSpot = findViewById(R.id.recycler_view);
        LocationParkingSpot.setLayoutManager(new LinearLayoutManager(this));

        AdapterParkingSpot adapter = new AdapterParkingSpot(company);
        adapter.setOnItemClickListener(new AdapterParkingSpot.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Обработка на избора на елемент
                Company selectedCompany = company.get(position);
                Toast.makeText(ActivityParkingSpot.this, "Избрана компания: " + selectedCompany.GetCompanyName(), Toast.LENGTH_SHORT).show();
                companyID = selectedCompany.GetIdCompany();
            }
        });
        LocationParkingSpot.setAdapter(adapter);
    }


}