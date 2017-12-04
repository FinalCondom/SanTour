package ch.hes.santour;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class DetailsPodFragment extends Fragment {


    FragmentManager fragmentManager;
    Fragment fragment;
    FragmentTransaction transaction ;


    public DetailsPodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_details_pod, container, false);

        //set the title on the app
        getActivity().setTitle(R.string.pod_details);

        // button CANCEL
        Button bt_pod_details_cancel =  rootView.findViewById(R.id.bt_pod_details_cancel);
        bt_pod_details_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                getFragmentManager().popBackStack();

            }
        });

        // button Save
        Button bt_pod_details_save =  rootView.findViewById(R.id.bt_pod_details_save);
        bt_pod_details_save.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // ICI AJOUTER LA METHODE POUR AJOUTER DANS LA BD
                getFragmentManager().popBackStack();

            }
        });


        return rootView;
    }


}
