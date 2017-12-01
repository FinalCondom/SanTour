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
    }

    public void clearTrack(){
        track = null;
    }

    //This function will create a track
    public void createTrack(String trackName){
        //We get the reference to a track child
        mTrackRef = mRootRef.child("tracks").push();

        track = new Track(trackName, 0, 0.0, 1);
        mTrackRef.setValue(track);
        track.setId_track(mTrackRef.getKey());
    }

    public void endTrack(long time){
        track.setTimer(time);
        track.setPODs(CurrentRecordingTrack.getTrack().getPODs());
        track.setPOIs(CurrentRecordingTrack.getTrack().getPOIs());
        mTrackRef.setValue(track);
    }

    public Track getTrack(){
        return track;
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
