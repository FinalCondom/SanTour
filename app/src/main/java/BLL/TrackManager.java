package BLL;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Models.Track;

/**
 * Created by lucien on 24.11.2017.
 */

public class TrackManager {
    public final String TAG = "TAG";

    private DatabaseReference mRootRef;
    private DatabaseReference mTrackRef;

    private Track track;

    public TrackManager(){
        //We get the instance of the firebase DB and we keep data if we are offline
        mRootRef = FirebaseDatabase.getInstance().getReference();
        //We get the reference to a track child
        mTrackRef = mRootRef.child("tracks").push();
    }

    //This function will create a track
    public void createTrack(String trackName){
        track = new Track(trackName, 0, 0.0, 1);
        mTrackRef.setValue(track);
        track.setId_track(mTrackRef.getKey());
    }

    public long getSeconds(){
        long second = getMinutes()%60000;
        return second/1000;
    }

    public long getMinutes(){
        long minute = getHours()%3600000;
        return minute/60000;
    }

    public long getHours(){
        return track.getTimer()/3600000;
    }


}
