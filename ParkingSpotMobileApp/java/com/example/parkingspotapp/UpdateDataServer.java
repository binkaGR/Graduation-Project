package com.example.parkingspotapp;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateDataServer extends AsyncTask<String, Void, String> {


    private Context context;
    public  UpdateDataServer (Context _context)
    {
        this.context=_context;
    }
    @Override
    protected String doInBackground(String... urls) {
        String responseServer = "";
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urls[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
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
        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
    }
}
