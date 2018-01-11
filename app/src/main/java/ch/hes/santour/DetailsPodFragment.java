package ch.hes.santour;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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

import BLL.FirebaseClass;
import BLL.PODManager;
import Models.Difficulty;


public class DetailsPodFragment extends Fragment {
    private String TAG = "TAG";
    private ListView mListView;
    private PODManager podManager;
    private DatabaseReference mRootRef;
    private List<Difficulty> listDifficultiesBD = new ArrayList<Difficulty>();


    public DetailsPodFragment() {
        // Required empty public constructor
        mRootRef = FirebaseClass.getDatabase().getReference();
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

        mListView = rootView.findViewById(R.id.listViewDetailsPod);
        showDifficultiesList();

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
                //We restart the timer
                ((MainActivity) getActivity()).restartTimer();

                if (bundle != null) {
                    String podName = bundle.getString("podName");
                    String podDescription = bundle.getString("podDescription");
                    byte[] byteArray = bundle.getByteArray("photo");
                    Bitmap photo = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                    List<Difficulty>difficulties = getDifficulties();

                    podManager.createPOD(podName, podDescription, photo, difficulties);
                }

                getFragmentManager().popBackStack();
                getFragmentManager().popBackStack();
                Toast.makeText(rootView.getContext(), R.string.pod_added, Toast.LENGTH_SHORT).show();
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

    //This function will return the list of difficulties selected
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

    //Thsi function will show the difficulties list as an asynchronous method
    private void showDifficultiesList() {
        mRootRef.child("difficulties").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listDifficultiesBD = new ArrayList<>();
                for (DataSnapshot diffDataSnapShot : dataSnapshot.getChildren()) {
                    Difficulty difficulty = diffDataSnapShot.getValue(Difficulty.class);
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
