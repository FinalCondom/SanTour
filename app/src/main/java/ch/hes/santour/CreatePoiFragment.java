package ch.hes.santour;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
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


public class CreatePoiFragment extends Fragment {
    private final int CAMERA_REQUEST = 1;
    private TextView poiLatitude;
    private TextView poiLongitude;
    private EditText poiDescription;
    private EditText poiName;
    private POIManager poiManager;
    private ImageButton imageButton;
    private Bitmap photo;
    public CreatePoiFragment() {

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

        final View rootView = inflater.inflate(R.layout.fragment_create_poi, container, false);

        //We pause the recording
        ((MainActivity)getActivity()).pauseTimer();
        //to see the menu on the top
        setHasOptionsMenu(true);


        imageButton = rootView.findViewById(R.id.ib_poi_take_picture);
        //We set up poi informations
        poiName = rootView.findViewById(R.id.et_poi_name);
        poiDescription = rootView.findViewById(R.id.et_poi_description);
        poiManager = new POIManager();
        poiLatitude = rootView.findViewById(R.id.tv_poi_gps_data_latitude);
        poiLongitude = rootView.findViewById(R.id.tv_poi_gps_data_longitude);

        if(CurrentRecordingTrack.getTrack()!=null){
            poiLatitude.setText(String.valueOf(((MainActivity)getActivity()).getActualLocation().getLatitude()));
            poiLongitude.setText(String.valueOf(((MainActivity)getActivity()).getActualLocation().getLongitude()));
        }

        //set the title on the app
        getActivity().setTitle(R.string.create_poi);

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
                else if(photo == null) {
                    //if no name has been written, we will display a message
                    Toast.makeText(rootView.getContext(), R.string.poi_no_image_msg, Toast.LENGTH_SHORT).show();
                }
                else{
                //We restart the timer

                ((MainActivity)getActivity()).restartTimer();

                poiManager.createPOI(poiName.getText().toString(), poiDescription.getText().toString(), photo);
                getFragmentManager().popBackStack();
                Toast.makeText(rootView.getContext(), R.string.poi_added, Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageButton ib_poi_take_picture = rootView.findViewById(R.id.ib_poi_take_picture);
        ib_poi_take_picture.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST);


            }
        });


        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {

            Bundle extras = data.getExtras();
            photo = (Bitmap) extras.get("data");

            imageButton.setImageBitmap(photo);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.language, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

}
