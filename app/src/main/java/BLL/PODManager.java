package BLL;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import Models.Coordinate;
import Models.POD;
import Models.POI;
import Models.Track;

/**
 * Created by lucien on 01.12.2017.
 */

public class PODManager{
    public final String TAG = "TAG";

    private POD pod;
    private Coordinate coordinate;

    //This function will create a track
    public void createPOD(String poiName, String poiDescription){
        coordinate = new Coordinate(0, 0, 0, 0,0, 0, new Date());
        pod = new POD(poiName, poiDescription, "nice picture here", coordinate);
        CurrentRecordingTrack.getTrack().addPod(pod);
    }
}
