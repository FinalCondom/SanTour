package ch.hes.santour;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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

import java.io.ByteArrayOutputStream;

import BLL.CurrentRecordingTrack;
import BLL.PODManager;


public class CreatePodFragment extends Fragment {

    FragmentManager fragmentManager;
    Fragment fragment;
    FragmentTransaction transaction ;
    private EditText podDescription;
    private EditText podName;
    private TextView podLatitude;
    private TextView podLongitude;
    private PODManager podManager;
    private final int CAMERA_REQUEST = 1;
    private ImageButton imageButton;
    private Bitmap photo;

    public CreatePodFragment() {
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

        final View rootView = inflater.inflate(R.layout.fragment_create_pod, container, false);

        //We pause the recording
        ((MainActivity)getActivity()).pauseTimer();

        //to see the menu on the top
        setHasOptionsMenu(true);


        imageButton = rootView.findViewById(R.id.ib_pod_take_picture);
        //We set up pod informations
        podName = rootView.findViewById(R.id.et_pod_name);
        podDescription = rootView.findViewById(R.id.et_pod_description);
        podManager = new PODManager();
        podLatitude = rootView.findViewById(R.id.tv_pod_gps_data_latitude);
        podLongitude = rootView.findViewById(R.id.tv_pod_gps_data_longitude);

        if(CurrentRecordingTrack.getTrack()!=null){
            podLatitude.setText(String.valueOf(((MainActivity)getActivity()).getActualLocation().getLatitude()));
            podLongitude.setText(String.valueOf(((MainActivity)getActivity()).getActualLocation().getLongitude()));
        }

        //set the title on the app
        getActivity().setTitle(R.string.create_pod);


        // button CANCEL
        Button bt_pod_cancel =  rootView.findViewById(R.id.bt_pod_cancel);
        bt_pod_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                //We restart the timer
                ((MainActivity)getActivity()).restartTimer();

                getFragmentManager().popBackStack();
            }
        });

        // button Picture
        ImageButton ib_pod_take_picture =  rootView.findViewById(R.id.ib_pod_take_picture);
        ib_pod_take_picture.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,CAMERA_REQUEST);
            }
        });



        // button NEXT
        Button bt_pod_next =  rootView.findViewById(R.id.bt_pod_next);
        bt_pod_next.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //TODO remove the comments to manage errors
                if (podName.getText().toString().equals("")){
                    //if no name has been written, we will display a message
                    Toast.makeText(rootView.getContext(), R.string.pod_no_name_msg, Toast.LENGTH_SHORT).show();
                }
                else if(podDescription.getText().toString().equals("")){
                    //if no name has been written, we will display a message
                    Toast.makeText(rootView.getContext(), R.string.pod_no_description_msg, Toast.LENGTH_SHORT).show();
                }
                else if(photo == null) {
                    //if no name has been written, we will display a message
                    Toast.makeText(rootView.getContext(), R.string.pod_no_image_msg, Toast.LENGTH_SHORT).show();
                }
                else{
                    Bundle bundle = new Bundle();
                    bundle.putString("podName", podName.getText().toString());
                    bundle.putString("podDescription", podDescription.getText().toString());
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    bundle.putByteArray("photo", byteArray);
                    fragmentManager = getFragmentManager();
                    fragment = new DetailsPodFragment();
                    fragment.setArguments(bundle);
                    transaction = fragmentManager.beginTransaction();
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.main_container, fragment).commit();
                }
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
