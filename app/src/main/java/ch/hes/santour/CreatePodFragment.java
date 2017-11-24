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


        // button POI
        Button bt_pod_next =  rootView.findViewById(R.id.bt_pod_next);
        bt_pod_next.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                fragmentManager = getFragmentManager();
                fragment = new PoiPodListFragment();
                transaction = fragmentManager.beginTransaction();

                transaction.replace(R.id.main_container, fragment).commit();
                transaction.addToBackStack(null);

            }
        });

        return rootView;
    }

}
