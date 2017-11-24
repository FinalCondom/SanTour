package ch.hes.santour;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;


public class CreatePodFragment extends Fragment {

    FragmentManager fragmentManager;
    Fragment fragment;
    FragmentTransaction transaction ;

    public CreatePodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_create_pod, container, false);

        //set the title on the app
        getActivity().setTitle(R.string.create_pod);


        // button CANCEL
        Button bt_pod_cancel =  rootView.findViewById(R.id.bt_pod_cancel);
        bt_pod_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        /* button Picture
        ImageButton ib_pod_take_picture =  rootView.findViewById(R.id.ib_pod_take_picture);
        bt_pod_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, 100);
            }
        });
        */


        // button NEXT
        Button bt_pod_next =  rootView.findViewById(R.id.bt_pod_next);
        bt_pod_next.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                fragmentManager = getFragmentManager();
                fragment = new DetailsPodFragment();
                transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_container, fragment).commit();


            }
        });

        return rootView;
    }

}
