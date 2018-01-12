package ch.hes.santour;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import BLL.CurrentRecordingTrack;
import Models.Difficulty;

public class PoiPodListFragment extends Fragment {
    private ListView mListView;
    private FragmentManager fragmentManager;
    private Fragment fragment;
    private FragmentTransaction transaction ;

    public PoiPodListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_poi_pod_list, container, false);


        //to see the menu on the top
        setHasOptionsMenu(true);

        //set the title on the app
        getActivity().setTitle(R.string.poi_pod_list);

        mListView = rootView.findViewById(R.id.listViewPoi);
        showPoiList();
        mListView = rootView.findViewById(R.id.listViewPod);
        showPodList();

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.language, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //function to see the list of POI
    private void showPoiList() {
        if (CurrentRecordingTrack.getTrack().getPOIs().size() > 0) {
            ListPoiAdapter adapter = new ListPoiAdapter(getActivity(), CurrentRecordingTrack.getTrack().getPOIs());
            //Set the adapter for poi list
            mListView.setAdapter(adapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView arg0, View view, final int position, long id) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    //Title of the dialog
                    alert.setTitle("Details of "+CurrentRecordingTrack.getTrack().getPOIs().get(position).getName());
                    alert.setMessage("Coordinates" +"\n"+
                                    "Latitude : " + CurrentRecordingTrack.getTrack().getPOIs().get(position).getCoordinate().getLatitude()+"\n"+
                                    "Longitude : "+CurrentRecordingTrack.getTrack().getPOIs().get(position).getCoordinate().getLongitude()+"\n"+"\n"+
                                    "Description"+"\n"+
                                            CurrentRecordingTrack.getTrack().getPOIs().get(position).getDescription()
                    );
                   Drawable d = new BitmapDrawable(getResources(), CurrentRecordingTrack.getTrack().getPOIs().get(position).getPicture());
                    alert.setIcon(d);
                    //Set the back button
                    alert.setPositiveButton(R.string.ok,
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    //Set the edit button, we need to pass information in the bundle
                    alert.setNeutralButton("Edit",
                            new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Bundle bundle = new Bundle();
                                    bundle.putInt("poiIndex", position);
                                    fragmentManager = getFragmentManager();
                                    fragment = new UpdatePoiFragment();
                                    fragment.setArguments(bundle);
                                    transaction = fragmentManager.beginTransaction();
                                    transaction.addToBackStack(null);
                                    transaction.replace(R.id.main_container, fragment).commit();
                                    dialog.dismiss();
                                }
                            });
                    alert.show();
                }
            });
        }
    }
    //function to see the list of POD
    private void showPodList() {
        if(CurrentRecordingTrack.getTrack().getPODs().size()>0){
            ListPodAdapter adapter = new ListPodAdapter(getActivity(), CurrentRecordingTrack.getTrack().getPODs());
            //Set the adapter for pod list
            mListView.setAdapter(adapter);
            //Fill the dialog
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView arg0, View view, final int position, long id) {
                    String message = "Coordinates" +"\n"+
                            "Latitude : " + CurrentRecordingTrack.getTrack().getPODs().get(position).getCoordinate().getLatitude()+"\n"+
                            "Longitude : "+CurrentRecordingTrack.getTrack().getPODs().get(position).getCoordinate().getLongitude()+"\n"+"\n"+
                            "Description"+"\n"+
                            CurrentRecordingTrack.getTrack().getPODs().get(position).getDescription()+"\n"+"\n"+
                            "Categories"+"\n";
                    for (Difficulty difficulty: CurrentRecordingTrack.getTrack().getPODs().get(position).getDifficulties()
                         ) {
                        message+= difficulty.getName()+" : " + difficulty.getGradient()+"\n";
                    }
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    //Title of the dialog
                    alert.setTitle("Details of "+CurrentRecordingTrack.getTrack().getPODs().get(position).getName());
                    alert.setMessage(message);
                    Drawable d = new BitmapDrawable(getResources(), CurrentRecordingTrack.getTrack().getPODs().get(position).getPicture());
                    alert.setIcon(d);
                    //Set the back button
                    alert.setPositiveButton(R.string.ok,
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    //Set the edit button, we need to pass information in the bundle
                    alert.setNeutralButton("Edit",
                            new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Bundle bundle = new Bundle();
                                    bundle.putInt("podIndex", position);
                                    fragmentManager = getFragmentManager();
                                    fragment = new UpdatePodFragment();
                                    fragment.setArguments(bundle);
                                    transaction = fragmentManager.beginTransaction();
                                    transaction.addToBackStack(null);
                                    transaction.replace(R.id.main_container, fragment).commit();
                                    dialog.dismiss();
                                }
                    });
                    alert.show();
                }
            });
        }
    }
}
