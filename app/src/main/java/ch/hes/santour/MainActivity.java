package ch.hes.santour;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import java.util.Locale;

import BLL.CoordinateManager;
import BLL.CurrentRecordingTrack;
import BLL.TrackManager;

public class MainActivity extends AppCompatActivity implements LocationListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private  static final int REQUEST_LOCATION_CODE = 9;
    private static boolean isRecording = false;
    private LocationRequest locationRequest;
    private GoogleApiClient client;
    private Location actualLocation;
    private Location lastLocation;
    private Chronometer chronometer;
    private double distance;
    private TrackManager trackManager;
    private CoordinateManager coordinateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        loadLastLanguage();

        trackManager = new TrackManager();
        coordinateManager = new CoordinateManager();
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = new HomeFragement();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, fragment).commit();

        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkLocationPermission();
        }
    }

    public void changeLanguage (String toLoad) {
        Locale locale = new Locale(toLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        //noinspection deprecation
        config.locale= locale;
        //noinspection deprecation
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("LANGUAGE", toLoad).commit();
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    //Méthode qui se déclenchera au clic sur un item
    public boolean onOptionsItemSelected(MenuItem item) {
        String languageToLoad;
        //On regarde quel item a été cliqué grâce à son id et on déclenche une action
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

    //Demande la localisation
    public boolean checkLocationPermission()
     {
         if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION))
                {
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
                }
                else
                {
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION_CODE);
                }
            return false;
            }
            else
            {
                return true;
            }
     }

    @Override
    public void onLocationChanged(Location location) {
        actualLocation = location;

        if(lastLocation == null)
            lastLocation = actualLocation;

        if (isRecording == true) {
            calculeDistance();

            trackManager.addCoordinate(coordinateManager.createCoordonateFromLocation(actualLocation));
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000);
        locationRequest.setPriority(locationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(client,locationRequest,this);
        }
    }

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
    public void calculeDistance()
    {
        int Rayon = 6378000; //Rayon de la terre en mètre
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

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    //timer
    public void pauseTimer(){
        if(CurrentRecordingTrack.getTrack()!=null) {
            CurrentRecordingTrack.getTrack().setTimer(SystemClock.elapsedRealtime() - chronometer.getBase());
            chronometer.stop();
            trackManager.updateTrack();
            setIsRecording(false);
        }
    }
    public void restartTimer(){
        if(CurrentRecordingTrack.getTrack()!=null) {
            chronometer.setBase(SystemClock.elapsedRealtime() - CurrentRecordingTrack.getTrack().getTimer());
            chronometer.start();
            setIsRecording(true);
        }
    }

    public void endTrack(){
        CurrentRecordingTrack.getTrack().setTimer(SystemClock.elapsedRealtime() - chronometer.getBase());
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
}
