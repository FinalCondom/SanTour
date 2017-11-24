package ch.hes.santour;


import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class CreatePoiFragment extends Fragment {

    FragmentManager fragmentManager;
    Fragment fragment;
    FragmentTransaction transaction ;

    public CreatePoiFragment() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_create_poi, container, false);

        //set the title on the app
        getActivity().setTitle(R.string.create_poi);

        //button CANCEL
        Button bt_poi_cancel =  rootView.findViewById(R.id.bt_poi_cancel);
        bt_poi_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        return rootView;
    }


}
