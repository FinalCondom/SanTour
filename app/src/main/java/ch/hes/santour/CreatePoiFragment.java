package ch.hes.santour;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class CreatePoiFragment extends Fragment {


    public CreatePoiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_create_poi, container, false);

        //set the title on the app
        getActivity().setTitle(R.string.create_poi);

        return rootView;
    }


}
