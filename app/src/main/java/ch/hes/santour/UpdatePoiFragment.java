package ch.hes.santour;


import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import BLL.CurrentRecordingTrack;
import BLL.POIManager;
import Models.POI;


public class UpdatePoiFragment extends Fragment {
    private TextView poiLatitude;
    private TextView poiLongitude;
    private EditText poiDescription;
    private EditText poiName;
    private POIManager poiManager;
    private POI updatedPOI;
    private ImageButton imageButton;
    private Bitmap photo;
    public UpdatePoiFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach (Context context) {
        super.onAttach(context);
        ((MainActivity)getActivity()).pauseTimer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_update_poi, container, false);

        //We pause the recording
        ((MainActivity)getActivity()).pauseTimer();
        //to see the menu on the top
        setHasOptionsMenu(true);
        imageButton = rootView.findViewById(R.id.ib_poi_picture);
        //We set up poi informations
        poiName = rootView.findViewById(R.id.et_poi_name);
        poiDescription = rootView.findViewById(R.id.et_poi_description);
        poiManager = new POIManager();
        poiLatitude = rootView.findViewById(R.id.tv_poi_gps_data_latitude);
        poiLongitude = rootView.findViewById(R.id.tv_poi_gps_data_longitude);
        Bundle bundle = getArguments();
        if (bundle != null){
            int poiIndex= bundle.getInt("poiIndex");
            updatedPOI = CurrentRecordingTrack.getTrack().getPOIs().get(poiIndex);
            poiName.setText(updatedPOI.getName());
            poiDescription.setText(updatedPOI.getDescription());
            ImageButton ib_pod_take_picture =  rootView.findViewById(R.id.ib_poi_picture);
            ib_pod_take_picture.setImageBitmap(updatedPOI.getPicture());
            poiLatitude.setText(String.valueOf(updatedPOI.getCoordinate().getLatitude()));
            poiLongitude.setText(String.valueOf(updatedPOI.getCoordinate().getLongitude()));
        }

        //set the title on the app
        getActivity().setTitle(R.string.update_poi);

        //button CANCEL
        Button bt_poi_cancel = rootView.findViewById(R.id.bt_poi_cancel);
        bt_poi_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //We restart the timer
                ((MainActivity)getActivity()).restartTimer();

                getFragmentManager().popBackStack();
            }
        });

        //button SAVE
        Button bt_poi_save = rootView.findViewById(R.id.bt_poi_save);
        bt_poi_save.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //TODO remove the comments to manage errors
                if (poiName.getText().toString().equals("")){
                    //if no name has been written, we will display a message
                    Toast.makeText(rootView.getContext(), R.string.poi_no_name_msg, Toast.LENGTH_SHORT).show();
                }
                else if(poiDescription.getText().toString().equals("")){
                    //if no name has been written, we will display a message
                    Toast.makeText(rootView.getContext(), R.string.poi_no_description_msg, Toast.LENGTH_SHORT).show();
                }
                else{
                    //We restart the timer

                    ((MainActivity)getActivity()).restartTimer();

                    poiManager.updatePoi(poiName.getText().toString(), poiDescription.getText().toString(), updatedPOI);
                    getFragmentManager().popBackStack();
                    Toast.makeText(rootView.getContext(), R.string.poi_updated, Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.language, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

}
