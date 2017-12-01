package ch.hes.santour;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import BLL.POIManager;


public class CreatePoiFragment extends Fragment {

    FragmentManager fragmentManager;
    Fragment fragment;
    FragmentTransaction transaction ;

    private EditText poiDescription;
    private EditText poiName;
    private POIManager poiManager;

    public CreatePoiFragment() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_create_poi, container, false);

        //We set up poi informations
        poiName = rootView.findViewById(R.id.et_poi_name);
        poiDescription = rootView.findViewById(R.id.et_poi_description);
        poiManager = new POIManager();

        //set the title on the app
        getActivity().setTitle(R.string.create_poi);

        //button CANCEL
        Button bt_poi_cancel =  rootView.findViewById(R.id.bt_poi_cancel);
        bt_poi_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });


        ImageButton ib_poi_take_picture = rootView.findViewById(R.id.ib_poi_take_picture);
        ib_poi_take_picture.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivity(intent);

            }
        });




        return rootView;
    }


}
