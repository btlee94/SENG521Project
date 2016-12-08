package com.example.seng521.seng521project;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.Date;


/**
 * Created by brand on 2016-12-07.
 */

public class ActivityTrip extends Activity implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener{
    private static Storage localDB;
    private GoogleApiClient mGoogleApiClient;
    private Location currentLocation;
    private  LocationRequest locationRequest;
    private double longitude;
    private double latitude;
    private double speed;
    private TextView latitudeText;
    private TextView longitudeText;
    private TextView speedText;
    private String startTime;
    private String endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_trip);
        localDB = Storage.getInstance(this.getBaseContext());

        latitudeText = (TextView) findViewById(R.id.coordinateX);
        longitudeText = (TextView) findViewById(R.id.coordinateY);
        speedText = (TextView) findViewById(R.id.speed);
        createLocationRequest();
        if(mGoogleApiClient == null){
            mGoogleApiClient = new GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build();
        }

        startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public void stopFabOnClick(View stopButton){
        endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        final Dialog popUp = new Dialog(this);
        popUp.setContentView(R.layout.save_trip_popup);
        final EditText input = (EditText) popUp.findViewById(R.id.editText);
        final Button save = (Button) popUp.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = input.getText().toString();
                if(name.isEmpty())
                    return;
                else{
                    Trip trip = new Trip(name, startTime, endTime);
                    localDB.addTrip(trip);
                    popUp.dismiss();
                    final Intent intent  = new Intent(ActivityTrip.this, ActivityMain.class);
                    startActivity(intent);
                }
            }
        });
        popUp.show();
    }

    public void updateUI(){
        latitudeText.setText("Latitude: " + String.valueOf(latitude));
        longitudeText.setText("Longitude: " + String.valueOf(longitude));
        speedText.setText(String.valueOf(speed) + "m/s");
    }

    public void createLocationRequest(){
        //Request location updates in realtime
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(500);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public void startLocationRequests(){
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
    }

    @Override
    public void onConnected(Bundle connectionHint){
       startLocationRequests();
    }

    @Override
    public void onLocationChanged(Location location){
        currentLocation = location;
        latitude = currentLocation.getLatitude();
        longitude = currentLocation.getLongitude();
        speed = currentLocation.getSpeed();
        updateUI();
    }

    @Override
    public void onConnectionSuspended(int x){}

    @Override
    public void onConnectionFailed(ConnectionResult result){}

    @Override
    public void onStart(){
        mGoogleApiClient.connect();;
        super.onStart();
    }

    @Override
    public void onStop(){
        mGoogleApiClient.disconnect();;
        super.onStop();
    }

    @Override
    public void onPause(){
        super.onPause();
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    public void onResume(){
        super.onResume();
        if(mGoogleApiClient.isConnected())
            startLocationRequests();
    }
}
