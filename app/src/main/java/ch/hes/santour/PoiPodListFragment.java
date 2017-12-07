package ch.hes.santour;

import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import BLL.CurrentRecordingTrack;

public class PoiPodListFragment extends Fragment {
    ListView mListView;
    FragmentManager fragmentManager;
    Fragment fragment;
    FragmentTransaction transaction ;
    public final String TAG = "TAG";
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

    private void showPoiList() {
        if(CurrentRecordingTrack.getTrack().getPOIs().size()>0) {
            ListPoiAdapter adapter = new ListPoiAdapter(getActivity(), CurrentRecordingTrack.getTrack().getPOIs());
            mListView.setAdapter(adapter);
        }
        /*mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView arg0, View view, int position, long id) {
                Intent intent = new Intent(HomeOiseaux.this, show_oiseau.class);
                finish();

                POD pod = (POD) arg0.getItemAtPosition(position);
                intent.putExtra("ID", ois.getId());
                intent.putExtra("NOM", ois.getNom());
                intent.putExtra("COLOR", ois.getColor());
                intent.putExtra("POIDS", ois.getPoids());
                intent.putExtra("TEXT", ois.getText());
                intent.putExtra("TAILLE", ois.getTaille());
                intent.putExtra("ID_USER", ID_USER);


                startActivity(intent);

            }
        });
*/
    }
    private void showPodList() {
        if(CurrentRecordingTrack.getTrack().getPODs().size()>0){
            ListPodAdapter adapter = new ListPodAdapter(getActivity(), CurrentRecordingTrack.getTrack().getPODs());
            mListView.setAdapter(adapter);
        }

        /*mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView arg0, View view, int position, long id) {
                Intent intent = new Intent(HomeOiseaux.this, show_oiseau.class);
                finish();

                POD pod = (POD) arg0.getItemAtPosition(position);
                intent.putExtra("ID", ois.getId());
                intent.putExtra("NOM", ois.getNom());
                intent.putExtra("COLOR", ois.getColor());
                intent.putExtra("POIDS", ois.getPoids());
                intent.putExtra("TEXT", ois.getText());
                intent.putExtra("TAILLE", ois.getTaille());
                intent.putExtra("ID_USER", ID_USER);


                startActivity(intent);

            }
        });
*/
    }
}
