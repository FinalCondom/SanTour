package ch.hes.santour;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class PoiPodListFragment extends Fragment {

    FragmentManager fragmentManager;
    Fragment fragment;
    FragmentTransaction transaction ;

    public PoiPodListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_poi_pod_list, container, false);


        // button POD
        ImageButton ib_pod_add_list =  rootView.findViewById(R.id.ib_pod_add_list);
        ib_pod_add_list.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                fragmentManager = getFragmentManager();
                fragment = new CreatePodFragment();
                transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_container, fragment).commit();
            }
        });

        // button POI
        ImageButton ib_poi_add_list =  rootView.findViewById(R.id.ib_poi_add_list);
        ib_poi_add_list.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                fragmentManager = getFragmentManager();
                fragment = new CreatePoiFragment();
                transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_container, fragment).commit();

            }
        });

        return rootView;
    }

}
