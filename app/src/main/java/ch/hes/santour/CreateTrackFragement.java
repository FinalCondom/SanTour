package ch.hes.santour;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import BLL.TrackManager;

public class CreateTrackFragement extends Fragment implements OnMapReadyCallback {

    FragmentManager fragmentManager;
    Fragment fragment;
    FragmentTransaction transaction ;

    //Google Map
    private MapView mapView;
    private GoogleMap map;

    private TrackManager trackManager = new TrackManager();

    public final String TAG = "TAG";
    private ImageButton playButton;
    private EditText trackNameEditText;


    public CreateTrackFragement() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_create_track, container, false);

        //MAP
        mapView = rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        // button POD
        ImageButton ibPod =  rootView.findViewById(R.id.IbPod);
        ibPod.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                fragmentManager = getFragmentManager();
                fragment = new CreatePodFragment();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, fragment).commit();
                transaction.addToBackStack(null);
            }
        });

        // button POI
        ImageButton ibPoi =  rootView.findViewById(R.id.IbPoi);
        ibPoi.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                fragmentManager = getFragmentManager();
                fragment = new CreatePoiFragment();
                transaction = fragmentManager.beginTransaction();

                transaction.replace(R.id.main_container, fragment).commit();
                transaction.addToBackStack(null);

            }
        });

        //Start track
        trackNameEditText = rootView.findViewById(R.id.et_track_name);
        playButton = rootView.findViewById(R.id.ib_play);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String trackName = trackNameEditText.getText().toString();
                if(!trackName.equals("")){
                    trackManager.createTrack(trackName);
                }else{
                    //TODO show a dialog that inform the user to choose a name
                }
            }
        });

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) throws SecurityException {
        map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(false);
        LatLng coordinate = new LatLng(86, 20);
        map.moveCamera(CameraUpdateFactory.newLatLng(coordinate));

    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
