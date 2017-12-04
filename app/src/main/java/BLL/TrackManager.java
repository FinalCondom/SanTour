package BLL;

import android.location.Location;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import Models.Coordinate;
import Models.Track;

/**
 * Created by lucien on 24.11.2017.
 */

public class TrackManager {
    public final String TAG = "TAG";

    private DatabaseReference mRootRef;
    private DatabaseReference mTrackRef;

    private Track track;
    private CoordinateManager coordinateManager;
    public TrackManager(){
        //We get the instance of the firebase DB and we keep data if we are offline
        mRootRef = FirebaseDatabase.getInstance().getReference();
        coordinateManager = new CoordinateManager();
    }

    public void clearTrack(){
        track = null;
    }

    //This function will create a track
    public void createTrack(String trackName, Location location){
        //We get the reference to a track child
        mTrackRef = mRootRef.child("tracks").push();

        CurrentRecordingTrack.setTrack(new Track(trackName, 0, 0.0, 1));
        CurrentRecordingTrack.getTrack().addCoordinate(coordinateManager.createCoordonateFromLocation(location));
        mTrackRef.setValue(CurrentRecordingTrack.getTrack());
        CurrentRecordingTrack.getTrack().setId_track(mTrackRef.getKey());
    }

    public void updateTrack(){
        mTrackRef.setValue(CurrentRecordingTrack.getTrack());
    }

    public void endTrack(long time, double km){
        CurrentRecordingTrack.getTrack().setTimer(time);
        CurrentRecordingTrack.getTrack().setPODs(CurrentRecordingTrack.getTrack().getPODs());
        CurrentRecordingTrack.getTrack().setPOIs(CurrentRecordingTrack.getTrack().getPOIs());
        CurrentRecordingTrack.getTrack().setLength(km);
        mTrackRef.setValue(CurrentRecordingTrack.getTrack());
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

    public void addCoordinate(Coordinate coordinate){
        CurrentRecordingTrack.getTrack().addCoordinate(coordinate);
    }
}
