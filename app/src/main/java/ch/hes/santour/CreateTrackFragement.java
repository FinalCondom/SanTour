package ch.hes.santour;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
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

import BLL.CurrentRecordingTrack;
import BLL.TrackManager;
import Models.Coordinate;

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
    private  static final int REQUEST_LOCATION_CODE = 9;
    private MapView mapView;


    private TrackManager trackManager = new TrackManager();
    private CurrentRecordingTrack track;
    public final String TAG = "TAG";
    private ImageButton playButton;
    private ImageButton stopButton;
    private Button kmButton;
    private EditText trackNameEditText;
    private Chronometer chronometer;
    private boolean isRecording = false;
    private long time;

    private LatLng FirstPoint;
    private LatLng ActualPoint;



    public CreateTrackFragement() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_create_track, container, false);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkLocationPermission();
        }

        //set the title on the app
        getActivity().setTitle(R.string.create_track);


        //MAP
        mapView = rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        //KM
        kmButton = rootView.findViewById(R.id.bt_km);

        chronometer = rootView.findViewById(R.id.timer);

        // button POD
        ImageButton ib_create_track_pod =  rootView.findViewById(R.id.ib_create_track_pod);
        ib_create_track_pod.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(isRecording) {
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
                    fragmentManager = getFragmentManager();
                    fragment = new CreatePoiFragment();
                    transaction = fragmentManager.beginTransaction();
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.main_container, fragment).commit();
                }
            }
        });

        //Button Start track
        trackNameEditText = rootView.findViewById(R.id.et_track_name);
        playButton = rootView.findViewById(R.id.ib_play);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String trackName = trackNameEditText.getText().toString();
                if(!isRecording) {
                    if(!trackName.equals("")){
                        trackManager.createTrack(trackName);
                        track.setTrack(trackManager.getTrack());
                        isRecording = true;
                        chronometer.setBase(SystemClock.elapsedRealtime());
                        chronometer.start();
                        kmButton.setText(calculeDistance()+"");
                        trackManager.addCoordinate(new Coordinate(FirstPoint.latitude, FirstPoint.longitude));


                    }else{
                        //if no name has been written, we will display a message
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                        builder.setMessage(R.string.track_no_name_msg)
                                .setTitle(R.string.track_no_name_title);

                        builder.setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            }
        });



        //Button stop track
        stopButton = rootView.findViewById(R.id.ib_stop);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isRecording){
                    chronometer.stop();
                    time = (SystemClock.elapsedRealtime() - chronometer.getBase());
                    isRecording = false;
                    trackManager.endTrack(time, calculeDistance());
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
        }
    }

    protected synchronized void buildGoogleAPIClient()
    {
        client = new GoogleApiClient.Builder(getActivity()).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build(); {
    }

        client.connect();
        Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();

        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(locationRequest.PRIORITY_HIGH_ACCURACY);

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

    public boolean checkLocationPermission()
    {
        if(ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION_CODE);

            }
            else
            {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION_CODE);

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
        lastLocation = location;

        if(currentLocationMarker != null)
        {
            currentLocationMarker.remove();
        }

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        if(FirstPoint == null)
            FirstPoint = latLng;

        ActualPoint = latLng;

        if(isRecording == true)
        {
            kmButton.setText(calculeDistance()+"");

            trackManager.addCoordinate(new Coordinate(latLng.latitude, latLng.longitude));
        }


        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Votre position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

        currentLocationMarker = mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(0));

        if(client != null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(client,this);
        }

    }

    public double convertRad(double input){
        return (Math.PI * input)/180;
    }

    public double calculeDistance()
    {

       double Rayon = 6378000; //Rayon de la terre en mètre


        double lat_a_degre = FirstPoint.latitude;
        double lon_a_degre = FirstPoint.longitude;
        double lat_b_degre = ActualPoint.latitude;
        double lon_b_degre = ActualPoint.longitude;


        double lat_a = convertRad(lat_a_degre);
        double  lon_a = convertRad(lon_a_degre);
        double  lat_b = convertRad(lat_b_degre);
        double lon_b = convertRad(lon_b_degre);

        double theta = lon_a-lon_b;
        double rtheta = Math.PI * theta/180;

        double dist = Math.sin(lat_a_degre) * Math.sin(lat_b_degre) + Math.cos(lat_a_degre) * Math.cos(lat_b_degre) * Math.cos(rtheta);
        dist = Math.acos(dist);
        dist = dist * 180/Math.PI;
        dist = dist * 60 * 1.1515;


        dist = dist * 1.609344;

        return dist;

    }
}
