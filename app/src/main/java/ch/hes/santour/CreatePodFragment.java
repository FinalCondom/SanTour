package ch.hes.santour;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import BLL.PODManager;


public class CreatePodFragment extends Fragment {

    FragmentManager fragmentManager;
    Fragment fragment;
    FragmentTransaction transaction ;
    private EditText podDescription;
    private EditText podName;
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
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_create_pod, container, false);
        imageButton = rootView.findViewById(R.id.ib_pod_take_picture);
        //We set up pod informations
        podName = rootView.findViewById(R.id.et_pod_name);
        podDescription = rootView.findViewById(R.id.et_pod_description);
        podManager = new PODManager();


        //set the title on the app
        getActivity().setTitle(R.string.create_pod);


        // button CANCEL
        Button bt_pod_cancel =  rootView.findViewById(R.id.bt_pod_cancel);
        bt_pod_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
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
                podManager.createPOD(podName.getText().toString(), podDescription.getText().toString(), photo);

                fragmentManager = getFragmentManager();
                fragment = new DetailsPodFragment();
                transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_container, fragment).commit();
                getFragmentManager().popBackStack();
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
}
