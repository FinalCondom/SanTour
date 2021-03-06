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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import BLL.CoordinateManager;
import BLL.CurrentRecordingTrack;
import BLL.TrackManager;

public class CreateTrackFragment extends Fragment implements OnMapReadyCallback {

    FragmentManager fragmentManager;
    Fragment fragment;
    FragmentTransaction transaction ;

    //Google Map
    private GoogleMap mMap;
    private Marker currentLocationMarker;
    private Polyline polyline;
    private static final int REQUEST_LOCATION_CODE = 9;
    private MapView mapView;
    private PolylineOptions rectOptions;

    private TrackManager trackManager = new TrackManager();
    public final String TAG = "TAG";
    private ImageButton playButton;
    private ImageButton stopButton;
    private Button kmButton;
    private EditText trackNameEditText;
    private CoordinateManager coordinateManager;
    private Chronometer chronometer;


    public CreateTrackFragment() {
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

        //Set up name if something is currently recording
        trackNameEditText = rootView.findViewById(R.id.et_track_name);

        //Fragment chronometer (ONLY FOR DISPLAY)
        chronometer = rootView.findViewById(R.id.timer);

        //Check if a track is currently being recorded
        if(CurrentRecordingTrack.getTrack()!=null) {
            ((MainActivity) getActivity()).setIsRecording(true);
            trackNameEditText.setText(CurrentRecordingTrack.getTrack().getName());
            kmButton.setText(((MainActivity)getActivity()).getDistance()+"");
            chronometer.setBase(((MainActivity)getActivity()).getChronometer().getBase());
            chronometer.start();
            trackNameEditText.setEnabled(false);

        }else{
            ((MainActivity)getActivity()).setChronometer(chronometer);
        }

        // button POD
        ImageButton ib_create_track_pod =  rootView.findViewById(R.id.ib_create_track_pod);
        ib_create_track_pod.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //Check that a track is being recorded
                if(((MainActivity)getActivity()).isIsRecording()) {
                    chronometer.stop();
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
                //Check that a track is being recorded
                if(((MainActivity)getActivity()).isIsRecording()) {
                    chronometer.stop();
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
                //Check that a track is being recorded
                if(!((MainActivity)getActivity()).isIsRecording()) {
                    //Check the track have a name
                    if(!trackName.equals("")){
                        //Check the GPS location is enable on the device
                        if(((MainActivity)getActivity()).getActualLocation()!=null)
                        {
                            trackManager.createTrack(trackName, ((MainActivity)getActivity()).getActualLocation());
                            ((MainActivity)getActivity()).setIsRecording(true);
                            ((MainActivity)getActivity()).getChronometer().setBase(SystemClock.elapsedRealtime());
                            ((MainActivity)getActivity()).getChronometer().start();
                            trackNameEditText.setEnabled(false);
                            kmButton.setText(String.valueOf(((MainActivity)getActivity()).getDistance()));
                        }
                        else
                        {
                            //if the GPS is not enable, we will display a message
                            Toast.makeText(rootView.getContext(), R.string.track_no_gps_msg, Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        //if no name has been written, we will display a message
                        Toast.makeText(rootView.getContext(), R.string.track_no_name_msg, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        //Button stop track
        stopButton = rootView.findViewById(R.id.ib_stop);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If a track was being recording we stop it and save it to the database
                //Then it reset all the variables we used for the track
                if(((MainActivity)getActivity()).isIsRecording()) {
                    ((MainActivity)getActivity()).setIsRecording(false);
                    ((MainActivity)getActivity()).pauseTimer();
                    chronometer.stop();
                    CurrentRecordingTrack.getTrack().setTimer(SystemClock.elapsedRealtime() - ((MainActivity)getActivity()).getChronometer().getBase());
                    CurrentRecordingTrack.getTrack().setLength(((MainActivity)getActivity()).getDistance());
                    trackNameEditText.setEnabled(true);
                    trackNameEditText.setText("");
                    kmButton.setText("KM");
                    trackManager.updateTrack();
                    CurrentRecordingTrack.setTrack(null);
                    trackManager.clearTrack();
                    ((MainActivity)getActivity()).getChronometer().setBase(SystemClock.elapsedRealtime());
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    if(polyline !=null)
                    {
                        polyline.remove();
                    }
                    Toast.makeText(rootView.getContext(), R.string.track_created, Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rootView;
    }

    //Method that check if the permission to access the device location is enable
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
                        if(((MainActivity)getActivity()).getClient() == null)
                        {
                            ((MainActivity)getActivity()).buildGoogleAPIClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                    else //permission is denied
                    {
                        Toast.makeText(getActivity(), R.string.permission_denied, Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
        }
    }

    //Method that is call when we come back to the fragment
    @Override
    public void onResume() {
        if(CurrentRecordingTrack.getTrack()!=null){
            ((MainActivity) getActivity()).setIsRecording(true);
            chronometer.setBase(((MainActivity) getActivity()).getChronometer().getBase());
            chronometer.start();
            trackNameEditText.setText(CurrentRecordingTrack.getTrack().getName());
            kmButton.setText(((MainActivity)getActivity()).getDistance()+"");
            trackNameEditText.setEnabled(false);
        }
        mapView.onResume();
        super.onResume();

    }

    //Method that is call when we initialise the map
    //Set the device location on the map and zoom on it
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {
            ((MainActivity)getActivity()).buildGoogleAPIClient();
            mMap.setMyLocationEnabled(true);
            if( ((MainActivity) getActivity()).getActualLocation()!= null)
            {
                ZoomMap( ((MainActivity) getActivity()).getActualLocation());
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.language, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //Define the zoom on the map
    public void ZoomMap(Location location)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude())));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    //Update the device location on the map
    public void updateMap(Location location)
    {
        if(isAdded()) {

            //remove the old marker
            if (currentLocationMarker != null) {
                currentLocationMarker.remove();
            }

            //remove the old polyline
            if(polyline !=null)
            {
                polyline.remove();
            }

            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

            //Create the marker for the map
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(getResources().getString(R.string.track_current_location));
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

            rectOptions = ((MainActivity)getActivity()).GetPolyline();

            if(rectOptions != null)
            {
                // Get back the mutable Polyline
                polyline = mMap.addPolyline(rectOptions);
            }

            //Add marker at the actual location
            currentLocationMarker = mMap.addMarker(markerOptions);

            //Set the text of the button
            if(CurrentRecordingTrack.getTrack()!=null){
                kmButton.setText(((MainActivity)getActivity()).getDistance()+"");
            }

            //Move the camera to the new location
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }
}
