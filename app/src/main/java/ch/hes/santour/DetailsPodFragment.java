package ch.hes.santour;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import BLL.PODManager;


public class DetailsPodFragment extends Fragment {


    private PODManager podManager;


    public DetailsPodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_details_pod, container, false);

        //to see the menu on the top
        setHasOptionsMenu(true);


        podManager = new PODManager();

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
                Bundle bundle = getArguments();
                if(bundle!=null){
                    String podName = bundle.getString("podName");
                    String podDescription =bundle.getString("podDescription");
                    byte[] byteArray = bundle.getByteArray("photo");
                    Bitmap photo = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

                    podManager.createPOD(podName, podDescription, photo);

                }

                getFragmentManager().popBackStack();
                getFragmentManager().popBackStack("track",0);

            }
        });

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.language, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
