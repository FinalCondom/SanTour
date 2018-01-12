package ch.hes.santour;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Chronometer;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Locale;

import BLL.CoordinateManager;
import BLL.CurrentRecordingTrack;
import BLL.TrackManager;

/*
 * this is the main activity of our programm
 */
public class MainActivity extends AppCompatActivity implements LocationListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private static final int REQUEST_LOCATION_CODE = 9;
    private static boolean isRecording = false;
    private LocationRequest locationRequest;
    private GoogleApiClient client;
    private Location actualLocation;
    private Location lastLocation;
    private Chronometer chronometer;
    private double distance;
    private TrackManager trackManager;
    private CoordinateManager coordinateManager;
    private PolylineOptions rectOptions = new PolylineOptions().width(10).color(Color.BLUE);

    //Change location precision and delay
    private int choosedTime = 10000;
    private int choosedPrecision = locationRequest.PRIORITY_BALANCED_POWER_ACCURACY;
    private int intPrecision;
    private int intChoosedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        loadLastLanguage();

        trackManager = new TrackManager();
        coordinateManager = new CoordinateManager();
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = new HomeFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, fragment).commit();

        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
    }

    //This is the function called when we change the language
    public void changeLanguage(String toLoad) {
        Locale locale = new Locale(toLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        //noinspection deprecation
        config.locale = locale;
        //noinspection deprecation
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("LANGUAGE", toLoad).commit();
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    //Method that is called when we click on an item
    public boolean onOptionsItemSelected(MenuItem item) {
        String languageToLoad;
        //We check which item has been selected with is ID and we start an action
        switch (item.getItemId()) {
            case R.id.action_bar_settings:
                return true;
            case R.id.french:
                languageToLoad = "fr";
                changeLanguage(languageToLoad);
                return true;

            case R.id.german:
                languageToLoad = "de";
                changeLanguage(languageToLoad);

                return true;
            case R.id.english:
                languageToLoad = "en";
                changeLanguage(languageToLoad);
                return true;

        }
        return false;
    }

    //This function will return the last loaded language
    private void loadLastLanguage() {
        String language = PreferenceManager.getDefaultSharedPreferences(this).getString("LANGUAGE", "en");
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        //noinspection deprecation
        config.locale = locale;
        //noinspection deprecation
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    //Ask the permission to use the localisation of the device
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            }
            return false;
        } else {
            return true;
        }
    }

    //When the location of the device change its update the variables related to the position
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onLocationChanged(Location location) {

        Fragment frag = getFragmentManager().findFragmentByTag("track");

        actualLocation = location;

        if (lastLocation == null)
            lastLocation = actualLocation;

        //Check if a track is currently being recording
        if (isRecording) {
            calculeDistance();
            trackManager.addCoordinate(coordinateManager.createCoordonateFromLocation(actualLocation));
            SetPolyline(location);
        }

        if (frag != null) {
            //if a track is currently recording we update the map otherwise we zoom on the map
            if (!isRecording) {
                ((CreateTrackFragment) frag).ZoomMap(location);
            } else {
                ((CreateTrackFragment) frag).updateMap(location);
            }
        }

    }

    //Set the precision and the timing between two points of the GPS
    public void DefineLocalisation(int precision, int time) {

        //Remove the old location request
        if (locationRequest != null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(client, this);
        }
        switch (precision) {
            case 1:
                choosedPrecision = locationRequest.PRIORITY_LOW_POWER;
                intPrecision = 1;
                break;
            case 2:
                choosedPrecision = locationRequest.PRIORITY_BALANCED_POWER_ACCURACY;
                intPrecision = 2;
                break;
            case 3:
                choosedPrecision = locationRequest.PRIORITY_HIGH_ACCURACY;
                intPrecision = 3;
                break;
        }

        switch (time) {
            case 1:
                choosedTime = 5000;
                intChoosedTime = 1;
                break;
            case 2:
                choosedTime = 10000;
                intChoosedTime = 2;
                break;
            case 3:
                choosedTime = 15000;
                intChoosedTime = 3;
                break;
        }

        //Create the new request Location who update the location of the user
        if (locationRequest != null) {

            locationRequest.setInterval(choosedTime);
            locationRequest.setFastestInterval(choosedTime);
            locationRequest.setPriority(choosedPrecision);

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
        }
    }


    //Override the method onConnect of the LocationListener
    //It set the request location if it's not already set
    @Override
    public void onConnected(@Nullable Bundle bundle) {
    if(locationRequest == null)
        {
            locationRequest = new LocationRequest();
            locationRequest.setInterval(choosedTime);
            locationRequest.setFastestInterval(choosedTime);
            locationRequest.setPriority(choosedPrecision);

            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                LocationServices.FusedLocationApi.requestLocationUpdates(client,locationRequest,this);
            }
        }
    }

    //Create the client for the GoogleAPI
    protected synchronized void buildGoogleAPIClient()
    {
        client = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build(); {
    }

        client.connect();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    //Distance Functions
    public double convertRad(double input){
        return (Math.PI * input)/180;
    }

    //Calculated the distance we made with the GPS points
    public void calculeDistance()
    {
        int Rayon = 6378000; //Radius of the earth
        double dist;

        double lat_a = convertRad(lastLocation.getLatitude());
        double lon_a = convertRad(lastLocation.getLongitude());
        double lat_b = convertRad(actualLocation.getLatitude());
        double lon_b = convertRad(actualLocation.getLongitude());

        dist = Rayon * (Math.PI/2 - Math.asin( Math.sin(lat_b) * Math.sin(lat_a) + Math.cos(lon_b - lon_a) * Math.cos(lat_b) * Math.cos(lat_a)));

        distance += dist/1000;
        distance = round(distance,2);

        lastLocation = actualLocation;
    }

    //Round the values of the distance made
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    //timer
    public void pauseTimer(){
        //we pause the timer
        if(CurrentRecordingTrack.getTrack()!=null) {
            CurrentRecordingTrack.getTrack().setTimer(SystemClock.elapsedRealtime() - chronometer.getBase());
            chronometer.stop();
            trackManager.updateTrack();
            setIsRecording(false);
        }
    }
    public void restartTimer(){
        //we restart the timer
        if(CurrentRecordingTrack.getTrack()!=null) {
            chronometer.setBase(SystemClock.elapsedRealtime() - CurrentRecordingTrack.getTrack().getTimer());
            chronometer.start();
            setIsRecording(true);
        }
    }

    //Set the polyline who display the track on the map
    public void SetPolyline(Location location)
    {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        //Add the location to the polyline
        rectOptions.add(latLng);
        rectOptions.visible(true);
    }
    //Get the polyline who display the track on the map
    public PolylineOptions GetPolyline()
    {
        return rectOptions;
    }


    //Getter and Setter
    public static boolean isIsRecording() {
        return isRecording;
    }

    public static void setIsRecording(boolean isRecording) {
        MainActivity.isRecording = isRecording;
    }

    public GoogleApiClient getClient() {
        return client;
    }

    public void setClient(GoogleApiClient client) {
        this.client = client;
    }

    public Location getActualLocation() {
        return actualLocation;
    }

    public void setActualLocation(Location actualLocation) {
        this.actualLocation = actualLocation;
    }

    public Chronometer getChronometer() {
        return chronometer;
    }

    public void setChronometer(Chronometer chronometer) {
        this.chronometer = chronometer;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }


    public int getIntPrecision() {
        return intPrecision;
    }

    public int getIntChoosedTime() {
        return intChoosedTime;
    }

}
