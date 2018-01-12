package ch.hes.santour;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import BLL.CurrentRecordingTrack;
import BLL.PODManager;
import Models.POD;

/*
 * this class will be called once we update a POD
 */
public class UpdatePodFragment extends Fragment {

    FragmentManager fragmentManager;
    Fragment fragment;
    FragmentTransaction transaction ;
    private EditText podDescription;
    private EditText podName;
    private TextView podLatitude;
    private TextView podLongitude;
    private PODManager podManager;
    private ImageButton imageButton;
    private POD updatedPOD;
    private int podIndex;

    public UpdatePodFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach (Context context) {
        super.onAttach(context);
        ((MainActivity)getActivity()).pauseTimer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_update_pod, container, false);

        //We pause the recording
        ((MainActivity)getActivity()).pauseTimer();

        //to see the menu on the top
        setHasOptionsMenu(true);

        //We set up pod informations
        podName = rootView.findViewById(R.id.et_pod_name);
        podDescription = rootView.findViewById(R.id.et_pod_description);
        imageButton = rootView.findViewById(R.id.ib_pod_picture);
        podManager = new PODManager();
        podLatitude = rootView.findViewById(R.id.tv_pod_gps_data_latitude);
        podLongitude = rootView.findViewById(R.id.tv_pod_gps_data_longitude);
        Bundle bundle = getArguments();

        if (bundle != null){
            podIndex= bundle.getInt("podIndex");
            updatedPOD = CurrentRecordingTrack.getTrack().getPODs().get(podIndex);
            podName.setText(updatedPOD.getName());
            podDescription.setText(updatedPOD.getDescription());
            imageButton.setImageBitmap(updatedPOD.getPicture());
            podLatitude.setText(String.valueOf(updatedPOD.getCoordinate().getLatitude()));
            podLongitude.setText(String.valueOf(updatedPOD.getCoordinate().getLongitude()));
        }

        //set the title on the app
        getActivity().setTitle(R.string.update_pod);

        // button CANCEL
        Button bt_pod_cancel =  rootView.findViewById(R.id.bt_pod_cancel);
        bt_pod_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                //We restart the timer
                ((MainActivity)getActivity()).restartTimer();

                getFragmentManager().popBackStack();
            }
        });

        // button NEXT
        Button bt_pod_next =  rootView.findViewById(R.id.bt_pod_next);
        bt_pod_next.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //TODO remove the comments to manage errors
                if (podName.getText().toString().equals("")){
                    //if no name has been written, we will display a message
                    Toast.makeText(rootView.getContext(), R.string.pod_no_name_msg, Toast.LENGTH_SHORT).show();
                }
                else if(podDescription.getText().toString().equals("")){
                    //if no name has been written, we will display a message
                    Toast.makeText(rootView.getContext(), R.string.pod_no_description_msg, Toast.LENGTH_SHORT).show();
                }
                else{
                    if(podIndex!=-1) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("podIndex", podIndex);
                        bundle.putString("podName", podName.getText().toString());
                        bundle.putString("podDescription", podDescription.getText().toString());
                        fragmentManager = getFragmentManager();
                        fragment = new UpdateDetailsPodFragment();
                        fragment.setArguments(bundle);
                        transaction = fragmentManager.beginTransaction();
                        transaction.addToBackStack(null);
                        transaction.replace(R.id.main_container, fragment).commit();
                    }
                }
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
