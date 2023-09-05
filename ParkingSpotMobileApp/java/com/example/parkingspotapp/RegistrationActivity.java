package com.example.parkingspotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity implements OnConectionCompleate{
    private Button buttonBackMain;
    private Button buttonCreateUser;

    private TextView txtUsername;
    private TextView txtPassword;
    private TextView txtFirstName;
    private TextView txtLastName;
    private TextView txtEmail;
    private User user;
    private UserInformation userInformation;
    private  String API_URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        SetView();
        txtUsername = (TextView) findViewById(R.id.txtUsernameRegistration);
        txtPassword = (TextView) findViewById(R.id.editTextPassword);
        txtFirstName= (TextView) findViewById(R.id.editTextFirstNameRegistration);
        txtLastName = (TextView) findViewById(R.id.editTextLastNameRegistration);
        txtEmail = (TextView) findViewById(R.id.editTxtEmailRegistration);
        buttonCreateUser = (Button) findViewById(R.id.buttonCreateRegistration);

        buttonCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GetDataServer GetDateServer = new GetDataServer(RegistrationActivity.this);
                user = new User();
                userInformation = new UserInformation();
                user.SetUsername(txtUsername.getText().toString());
                user.SetUPassword(txtPassword.getText().toString());
                userInformation.SetFirstname(txtFirstName.getText().toString());
                userInformation.SetLastname(txtLastName.getText().toString());
                userInformation.SetEmail(txtEmail.getText().toString());

                API_URL = "http://192.168.253.130:8080/users/CreateUserMobile/" + user.GetUseraname()+"/" + user.GetPassword()+"/"+
                        userInformation.GetFirstname()+"/"+userInformation.GetLastname()+"/"+userInformation.GetEmail();
                GetDateServer.execute(API_URL);
            }
        });
        buttonBackMain = (Button) findViewById(R.id.buttonBackMainRegistration);
        buttonBackMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActivity();
            }
        });
    }
    public  void SetView()
    {
        TextView viewUsername;
        TextView viewPassword;
        TextView viewFirstName;
        TextView viewLastName;
        TextView viewEmail;
        viewUsername = (TextView) findViewById(R.id.textUsernameRegistration);
        viewPassword = (TextView) findViewById(R.id.textPasswordRegistration);
        viewFirstName = (TextView) findViewById(R.id.textFirstNameRegistration);
        viewLastName = (TextView) findViewById(R.id.textLastNameRegistration);
        viewEmail = (TextView) findViewById(R.id.textEmailRegistration);
        viewUsername.setText("Username");
        viewPassword.setText("Password");
        viewFirstName.setText("First name");
        viewLastName.setText("Last name");
        viewEmail.setText("Email");
    }
    public  void openMainActivity()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onTakeStringConnection(String response) {

        Toast.makeText(RegistrationActivity.this ,response , Toast.LENGTH_SHORT).show();
    }
}