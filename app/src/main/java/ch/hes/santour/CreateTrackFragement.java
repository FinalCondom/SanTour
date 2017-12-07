package ch.hes.santour;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import BLL.CoordinateManager;
import BLL.CurrentRecordingTrack;
import BLL.TrackManager;

public class CreateTrackFragement extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    FragmentManager fragmentManager;
    Fragment fragment;
    FragmentTransaction transaction ;

    //Google Map
    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker currentLocationMarker;
    private static final int REQUEST_LOCATION_CODE = 9;
    private MapView mapView;



    private TrackManager trackManager = new TrackManager();
    public final String TAG = "TAG";
    private ImageButton playButton;
    private ImageButton stopButton;
    private Button kmButton;
    private EditText trackNameEditText;
    private Chronometer chronometer;
    private boolean isRecording = false;
    private long time;
    private CoordinateManager coordinateManager;

    private Location actualLocation;
    private double distance;



    public CreateTrackFragement() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_create_track, container, false);

        //to see the menu on the top
        setHasOptionsMenu(true);

        coordinateManager = new CoordinateManager();


        //set the title on the app
        getActivity().setTitle(R.string.create_track);


        //MAP
        mapView = rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        //KM
        kmButton = rootView.findViewById(R.id.bt_km);
        chronometer = rootView.findViewById(R.id.timer);

        //Set up name if something is currently recording
        trackNameEditText = rootView.findViewById(R.id.et_track_name);

        if(CurrentRecordingTrack.getTrack()!=null){
            isRecording = true;
            trackNameEditText.setText(CurrentRecordingTrack.getTrack().getName());
            chronometer.setBase(SystemClock.elapsedRealtime() - CurrentRecordingTrack.getTrack().getTimer());
            chronometer.start();

        }

        // button POD
        ImageButton ib_create_track_pod =  rootView.findViewById(R.id.ib_create_track_pod);
        ib_create_track_pod.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(isRecording) {
                    CurrentRecordingTrack.getTrack().setTimer(SystemClock.elapsedRealtime() - chronometer.getBase());
                    isRecording = false;
                    fragmentManager = getFragmentManager();
                    fragment = new CreatePodFragment();
                    transaction = fragmentManager.beginTransaction();
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.main_container, fragment).commit();
                }
            }
        });

        // button POI
        ImageButton ib_create_track_poi =  rootView.findViewById(R.id.ib_create_track_poi);
        ib_create_track_poi.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(isRecording) {
                    CurrentRecordingTrack.getTrack().setTimer(SystemClock.elapsedRealtime() - chronometer.getBase());
                    isRecording = false;
                    fragmentManager = getFragmentManager();
                    fragment = new CreatePoiFragment();
                    transaction = fragmentManager.beginTransaction();
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.main_container, fragment).commit();
                }
            }
        });

        //Button Start track
        playButton = rootView.findViewById(R.id.ib_play);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String trackName = trackNameEditText.getText().toString();
                if(!isRecording) {
                    //TODO remove the comments to manage errors
//                    if(!trackName.equals("")){
                        trackManager.createTrack(trackName, actualLocation);
                        isRecording = true;
                        chronometer.setBase(SystemClock.elapsedRealtime());
                        chronometer.start();
                        kmButton.setText(distance+"");
//                    }else{
//                        //if no name has been written, we will display a message
//                        Toast.makeText(rootView.getContext(), R.string.track_no_name_msg, Toast.LENGTH_SHORT).show();
//                    }
                }
            }
        });



        //Button stop track
        stopButton = rootView.findViewById(R.id.ib_stop);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isRecording){
                    time = (SystemClock.elapsedRealtime() - chronometer.getBase());
                    chronometer.stop();
                    isRecording = false;
                    trackManager.endTrack(time, distance);
                    CurrentRecordingTrack.setTrack(null);
                    trackManager.clearTrack();
                }
            }
        });
        return rootView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case REQUEST_LOCATION_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //permission is granted
                    if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    {
                        if(client == null)
                        {
                            buildGoogleAPIClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                    else //permission is denied
                    {
                        Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
        }
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {
            buildGoogleAPIClient();
            mMap.setMyLocationEnabled(true);
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        }

    }

    protected synchronized void buildGoogleAPIClient()
    {
        client = new GoogleApiClient.Builder(getActivity()).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build(); {
    }

        client.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();

        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(locationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if(ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(client,locationRequest,this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if(isAdded()) {


            if (currentLocationMarker != null) {
                currentLocationMarker.remove();
            }

            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());


            actualLocation = location;

            if(lastLocation == null)
                lastLocation = actualLocation;


            if (isRecording == true) {

                calculeDistance();
                kmButton.setText(String.valueOf(distance));

                trackManager.addCoordinate(coordinateManager.createCoordonateFromLocation(actualLocation));
            }

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(getResources().getString(R.string.track_current_location));
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

            currentLocationMarker = mMap.addMarker(markerOptions);

            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.language, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
