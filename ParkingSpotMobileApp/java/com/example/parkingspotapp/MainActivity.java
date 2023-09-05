package com.example.parkingspotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity  implements OnConectionCompleate{
    private Button buttonLogin;
    private TextView textRegistration;
    private User user = new User();
    private  TextView UsernameText ;
    private  TextView PasswordText ;
    private  String API_URL ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonLogin = (Button) findViewById(R.id.ButtonLoggin);
        UsernameText =  (TextView) findViewById(R.id.TextUsername);
        PasswordText =  (TextView) findViewById(R.id.TextPassword);

        buttonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                user.SetUsername(UsernameText.getText().toString());
                user.SetUPassword(PasswordText.getText().toString());

                GetDataServer GetDateServer = new GetDataServer(MainActivity.this);
                API_URL = "http://192.168.253.130:8080/users/getAccess/username=" + user.GetUseraname()+"&password=" + user.GetPassword();
                GetDateServer.execute(API_URL);
            }
        });
        textRegistration = (TextView) findViewById(R.id.NewRegistrationView);
        textRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegistrationActivity();
            }
        });
    }

    public void onTakeStringConnection(String response) {

        if (!response.equals("Access denied ")) {
            openActivityParkingSpotMain();
        }
        else {
            Toast.makeText(MainActivity.this ,response , Toast.LENGTH_SHORT).show();
        }
    }
    public void openActivityParkingSpotMain(){
        Intent intent = new Intent(this, ActivityParkingSpot.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("User" , user);
        startActivity(intent);
    }

    public  void openRegistrationActivity()
    {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }
}