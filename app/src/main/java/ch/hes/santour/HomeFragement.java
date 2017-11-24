package ch.hes.santour;

import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragement extends Fragment {

    FragmentManager fragmentManager ;
    Fragment fragment ;
    FragmentTransaction transaction ;

    public HomeFragement() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);


        //set the title on the app
        getActivity().setTitle(R.string.app_name);


        // button createTrack
        Button btnCreateTrack =  rootView.findViewById(R.id.createTrack);
        btnCreateTrack.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                fragmentManager = getFragmentManager();
                fragment = new CreateTrackFragement();
                transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_container, fragment).commit();
            }
        });


        // button PoIDPODList
        Button POIPODList =  rootView.findViewById(R.id.POIPODList);
        POIPODList.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                fragmentManager = getFragmentManager();
                fragment = new PoiPodListFragment();
                transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_container, fragment).commit();
            }
        });

        // button about
        Button buttonAbout =  rootView.findViewById(R.id.about);
        buttonAbout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                fragmentManager = getFragmentManager();
                fragment = new AboutFragment();
                transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_container, fragment).commit();

            }
        });

        return rootView ;
    }

}
