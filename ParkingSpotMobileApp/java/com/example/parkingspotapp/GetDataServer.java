package com.example.parkingspotapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetDataServer extends AsyncTask<String, Void, String> {
    private  OnConectionCompleate listener;
    public GetDataServer(OnConectionCompleate listener) {
        this.listener = listener;
    }
    @Override
    protected String doInBackground(String... urls) {
        String responseServer = "";
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urls[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStreamReader = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStreamReader));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                responseServer += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return responseServer;
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        listener.onTakeStringConnection(response);
    }
}
