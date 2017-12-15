package ch.hes.santour;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import BLL.CurrentRecordingTrack;

public class PoiPodListFragment extends Fragment {
    private ListView mListView;
    private FragmentManager fragmentManager;
    private Fragment fragment;
    private FragmentTransaction transaction ;
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
                fragment = new UpdatePodFragment();
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
        if (CurrentRecordingTrack.getTrack().getPOIs().size() > 0) {
            ListPoiAdapter adapter = new ListPoiAdapter(getActivity(), CurrentRecordingTrack.getTrack().getPOIs());
            mListView.setAdapter(adapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView arg0, View view, int position, long id) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setTitle("Details of "+CurrentRecordingTrack.getTrack().getPOIs().get(position).getName());
                    alert.setMessage("Coordinates" +"\n"+
                                    "Latitude : " + CurrentRecordingTrack.getTrack().getPOIs().get(position).getCoordinate().getLatitude()+"\n"+
                                    "Longitude : "+CurrentRecordingTrack.getTrack().getPOIs().get(position).getCoordinate().getLongitude()+"\n"+"\n"+
                                    "Description"+"\n"+
                                            CurrentRecordingTrack.getTrack().getPOIs().get(position).getDescription()
                    );
                   Drawable d = new BitmapDrawable(getResources(), CurrentRecordingTrack.getTrack().getPOIs().get(position).getPicture());
                    alert.setIcon(d);
                    alert.setPositiveButton(R.string.ok,
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alert.show();
                }
            });
           /* mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    final Bitmap pic = CurrentRecordingTrack.getTrack().getPOIs().get(position).getPicture();
                    final AlertDialog dialog = builder.create();

                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    final View dialogLayout = inflater.inflate(R.layout.dialog_image, null);

                    dialog.setView(dialogLayout);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCanceledOnTouchOutside(true);

                    dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface d) {
                            ImageView image = dialogLayout.findViewById(R.id.imageLongPressed);
                            image.setImageBitmap(pic);
                            float imageWidthInPX = (float)image.getWidth();

                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Math.round(imageWidthInPX),
                                    Math.round(imageWidthInPX * (float)pic.getHeight() / (float)pic.getWidth()));
                            image.setLayoutParams(layoutParams);


                        }});
                    dialog.show();
                    return true;
                }
            });*/
        }
    }
    private void showPodList() {
        if(CurrentRecordingTrack.getTrack().getPODs().size()>0){
            ListPodAdapter adapter = new ListPodAdapter(getActivity(), CurrentRecordingTrack.getTrack().getPODs());
            mListView.setAdapter(adapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView arg0, View view, int position, long id) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setTitle("Details of "+CurrentRecordingTrack.getTrack().getPODs().get(position).getName());
                    alert.setMessage("Coordinates" +"\n"+
                            "Latitude : " + CurrentRecordingTrack.getTrack().getPODs().get(position).getCoordinate().getLatitude()+"\n"+
                            "Longitude : "+CurrentRecordingTrack.getTrack().getPODs().get(position).getCoordinate().getLongitude()+"\n"+"\n"+
                            "Description"+"\n"+
                            CurrentRecordingTrack.getTrack().getPODs().get(position).getDescription()
                    );
                    Drawable d = new BitmapDrawable(getResources(), CurrentRecordingTrack.getTrack().getPODs().get(position).getPicture());
                    alert.setIcon(d);
                    alert.setPositiveButton(R.string.ok,
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alert.show();
                }
            });
           /* mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    AlertDialog dialog = builder.create();

                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    View dialogLayout = inflater.inflate(R.layout.dialog_image, null);

                    dialog.setView(dialogLayout);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCanceledOnTouchOutside(true);

                    ImageView image = dialogLayout.findViewById(R.id.imageLongPressed);
                    Log.d(TAG,CurrentRecordingTrack.getTrack().getPODs().get(0).getPicture().getHeight()+" TESTEST");
                    image.setImageBitmap(CurrentRecordingTrack.getTrack().getPODs().get(position).getPicture());

                    dialog.show();


                    return true;
                }
            });*/
        }
    }
}
