package BLL;

import android.text.method.DateTimeKeyListener;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import Models.Coordinate;
import Models.POI;
import Models.Track;

/**
 * Created by lucien on 01.12.2017.
 */

public class POIManager {
    public final String TAG = "TAG";

    private POI poi;
    private Coordinate coordinate;

    //This function will create a track
    public void createPOI(String poiName, String poiDescription){
        coordinate = new Coordinate(0, 0, 0, 0,0, 0, new Date());
        poi = new POI(poiName, poiDescription, "nice picture here", coordinate);
        CurrentRecordingTrack.getTrack().addPoo(poi);
    }
}