package ch.hes.santour;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;



public class SettingsFragment extends Fragment {

    Fragment fragment;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        //to see the menu on the top
        setHasOptionsMenu(true);

        //set the title on the app
        getActivity().setTitle(R.string.settings);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.language, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

}
