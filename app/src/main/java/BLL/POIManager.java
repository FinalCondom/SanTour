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

    protected DatabaseReference mRootRef;
    protected DatabaseReference mTrackRef;
    protected Track track;
    protected POI poi;
    protected Coordinate coordinate;

    public POIManager(){
        //We get the instance of the firebase DB and we keep data if we are offline
        mRootRef = FirebaseDatabase.getInstance().getReference();
        //We get the reference to a track child
        track = CurrentRecordingTrack.getTrack();
        mTrackRef = mRootRef.child("tracks").child(track.getId_track()).child("POI").push();
    }

    //This function will create a track
    public void createPOI(String poiName, String poiDescription){
        coordinate = new Coordinate(0, 0, 0, 0,0, 0, new Date());
        poi = new POI(poiName, poiDescription, "nice picture here", coordinate);
        mTrackRef.setValue(poi);
        poi.setId_POI(mTrackRef.getKey());
    }
}