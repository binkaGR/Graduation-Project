package com.example.parkingspotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;


public class ProfileActivity extends AppCompatActivity implements OnConectionCompleate {

    private TextView txtFirstName;
    private TextView txtLastName;
    private TextView txtEmail;
    private TextView textUsername;
    private TextView textPassword;
    private TextView textNewPassword;
    private User user;

    private Button backButton;
    private Button ChangeUserInformationButton;


    private UserInformation userInformation;
    private String API_URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        textNewPassword = (TextView) findViewById(R.id.editTextPasswordNew);
        user = (User) getIntent().getSerializableExtra("User");
        SetView();
        GetDataServer GetDateServer = new GetDataServer(ProfileActivity.this);
        API_URL = "http://192.168.253.130:8080/users/GetUserInformation/username=" + user.GetUseraname()+"&password=" + user.GetPassword();
        GetDateServer.execute(API_URL);
        backButton = (Button) findViewById(R.id.buttonBackParkingSpot);
        ChangeUserInformationButton = (Button) findViewById(R.id.buttonChangeProfile);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityParkingSpot();
            }
        });
        ChangeUserInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPassword=null;
                user.SetUsername(textUsername.getText().toString());
                user.SetUPassword(textPassword.getText().toString());
                userInformation.SetFirstname(txtFirstName.getText().toString());
                userInformation.SetLastname(txtLastName.getText().toString());
                userInformation.SetEmail(txtEmail.getText().toString());
                newPassword = textNewPassword.getText().toString();
                API_URL = "http://192.168.253.130:8080/users/UpdateUserInformationClient/" + user.GetUseraname()+ "/" + user.GetPassword()+ "/"+ newPassword + "/"+ userInformation.GetFirstname() +"/"+ userInformation.GetLastname() + "/"  + userInformation.GetEmail();
                UpdateDataServer UpdateDateServer = new UpdateDataServer(ProfileActivity.this);
                UpdateDateServer.execute(API_URL);

            }
        });

    }
    public  void SetView()
    {
        TextView viewUsername ;
        TextView viewOldPassword;

        TextView viewFirstName;
        TextView viewLastName;
        TextView viewEmail;
        viewUsername  = (TextView) findViewById(R.id.viewtUsername);
        viewUsername.setText("Username");
        viewOldPassword  = (TextView) findViewById(R.id.viewPasswordOld);
        viewOldPassword.setText("Old Password");
        viewOldPassword  = (TextView) findViewById(R.id.viewPasswordNew);
        viewOldPassword.setText("New Password");
        textUsername = (TextView) findViewById(R.id.textUsername);
        textUsername.setText(user.GetUseraname());
        textPassword = (TextView) findViewById(R.id.editTextPasswordOld);
        textPassword.setText(user.GetPassword());
        viewFirstName = (TextView) findViewById(R.id.viewFirstName);
        viewFirstName.setText("First Name");
        viewLastName = (TextView) findViewById(R.id.viewLastName);
        viewLastName.setText("Last name");
        viewEmail = (TextView) findViewById(R.id.viewEmail);
        viewEmail.setText("Email");
    }
    public void onTakeStringConnection(String response) {

        Gson gson = new Gson();
        userInformation = gson.fromJson(response, UserInformation.class);


        txtFirstName = (TextView) findViewById(R.id.editTextFirstName);
        txtLastName = (TextView) findViewById(R.id.editTextLastName);
        txtEmail = (TextView) findViewById(R.id.editTxtEmail);

        txtFirstName.setText(userInformation.GetFirstname());
        txtLastName.setText(userInformation.GetLastname());
        txtEmail.setText(userInformation.GetEmail());
    }
    public  void openActivityParkingSpot()
    {
        Intent intent = new Intent(this, ActivityParkingSpot.class);
        startActivity(intent);
    }

}