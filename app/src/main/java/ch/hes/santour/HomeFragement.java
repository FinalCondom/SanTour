package ch.hes.santour;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragement extends Fragment {

    FragmentManager fragmentManager ;
    Fragment fragment ;

    public HomeFragement() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_home_fragement, container, false);

        //button create track
        Button buttonCreate = rootView.findViewById(R.id.createTrack);
        buttonCreate.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), CreateTrackActivity.class);
                startActivity(intent);
            }
        });

        // button CreatePOIPOD
        Button CreatePoiPod =  rootView.findViewById(R.id.CreatePoiPod);
        CreatePoiPod.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

            }
        });

        // button PoIDPODList
        Button POIPODList =  rootView.findViewById(R.id.POIPODList);
        POIPODList.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                fragmentManager = getFragmentManager();
                fragment = new PoiPodListFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, fragment).commit();
                transaction.addToBackStack(null);
            }
        });
        // button about
        Button buttonAbout =  rootView.findViewById(R.id.about);
        buttonAbout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                fragmentManager = getFragmentManager();
                fragment = new AboutFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, fragment).commit();
                transaction.addToBackStack(null);

            }
        });

        return rootView ;
    }

}