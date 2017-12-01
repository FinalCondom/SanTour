package ch.hes.santour;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import BLL.CurrentRecordingTrack;
import BLL.POIManager;


public class CreatePoiFragment extends Fragment {
    private final int CAMERA_REQUEST = 1;
    FragmentManager fragmentManager;
    Fragment fragment;
    FragmentTransaction transaction;

    private EditText poiDescription;
    private EditText poiName;
    private POIManager poiManager;
    private ImageView imageView;
    private Bitmap photo;
    public CreatePoiFragment() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_create_poi, container, false);
        imageView = rootView.findViewById(R.id.iv_poi_img);
        //We set up poi informations
        poiName = rootView.findViewById(R.id.et_poi_name);
        poiDescription = rootView.findViewById(R.id.et_poi_description);
        poiManager = new POIManager();


        //set the title on the app
        getActivity().setTitle(R.string.create_poi);

        //button CANCEL
        Button bt_poi_cancel = rootView.findViewById(R.id.bt_poi_cancel);
        bt_poi_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        //button SAVE
        Button bt_poi_save = rootView.findViewById(R.id.bt_poi_save);
        bt_poi_save.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                poiManager.createPOI(poiName.getText().toString(), poiDescription.getText().toString(), photo);
                getFragmentManager().popBackStack();
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

            imageView.setImageBitmap(photo);
        }
    }


}
