package ch.hes.santour;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import BLL.CurrentRecordingTrack;
import BLL.FirebaseClass;
import BLL.PODManager;
import Models.Difficulty;
import Models.POD;


public class UpdateDetailsPodFragment extends Fragment {
    private String TAG = "TAG";
    private ListView mListView;
    private PODManager podManager;
    private DatabaseReference mRootRef;
    private List<Difficulty> listDifficultiesBD = new ArrayList<Difficulty>();
    private POD updatedPod;
    private int podIndex;


    public UpdateDetailsPodFragment() {
        // Required empty public constructor
        mRootRef = FirebaseClass.getDatabase().getReference();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_update_details_pod, container, false);
        //to see the menu on the top
        setHasOptionsMenu(true);


        podManager = new PODManager();

        //set the title on the app
        getActivity().setTitle(R.string.pod_details);

        mListView = rootView.findViewById(R.id.listViewDetailsPod);

        Bundle bundle = getArguments();
        if (bundle != null){
            podIndex= bundle.getInt("podIndex");
            updatedPod = CurrentRecordingTrack.getTrack().getPODs().get(podIndex);
        }
        //See the list of details
        showDetailsList();
        // button CANCEL
        Button bt_pod_details_cancel = rootView.findViewById(R.id.bt_pod_details_cancel);
        bt_pod_details_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        // button Save
        Button bt_pod_details_save = rootView.findViewById(R.id.bt_pod_details_save);
        bt_pod_details_save.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Bundle bundle = getArguments();
                if (bundle != null) {
                    int podIndex = bundle.getInt("podIndex");
                    updatedPod = CurrentRecordingTrack.getTrack().getPODs().get(podIndex);
                    String podName = bundle.getString("podName");
                    String podDescription = bundle.getString("podDescription");
                    List<Difficulty>difficulties = getDifficulties();
                    podManager.updatePod(podName, podDescription, difficulties, updatedPod);
                }

                //We restart the timer
                ((MainActivity) getActivity()).restartTimer();

                getFragmentManager().popBackStack();
                getFragmentManager().popBackStack();
                Toast.makeText(rootView.getContext(), R.string.pod_updated, Toast.LENGTH_SHORT).show();
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

    private List<Difficulty>getDifficulties(){
        List<Difficulty>difficulties = new ArrayList<>();
        for(int i=0; i<mListView.getCount(); i++){
            View v = mListView.getChildAt(i);
            CheckBox checkBox = v.findViewById(R.id.checkboxPodDetails);
            SeekBar seekBar = v.findViewById(R.id.sb_pod_details_horizontal);
            if(checkBox.isChecked()){
                difficulties.add(new Difficulty(
                        checkBox.getText().toString(),
                        seekBar.getProgress()
                ));
            }
        }
        return difficulties;
    }

    private void showDetailsList() {
        mRootRef.child("difficulties").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listDifficultiesBD = new ArrayList<>();
                List<Difficulty>difficulties = updatedPod.getDifficulties();
                for (DataSnapshot diffDataSnapShot : dataSnapshot.getChildren()) {
                    Difficulty difficulty = diffDataSnapShot.getValue(Difficulty.class);
                    for(int j=0; j<difficulties.size(); j++) {
                        if (difficulty.getName().equals(difficulties.get(j).getName())) {
                            difficulty.setGradient(difficulties.get(j).getGradient());
                        }
                    }
                    listDifficultiesBD.add(difficulty);
                }
                ListPodDifficulties adapter = new ListPodDifficulties(getActivity(), listDifficultiesBD);
                mListView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
