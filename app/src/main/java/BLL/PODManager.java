package BLL;

import android.graphics.Bitmap;

import Models.Coordinate;
import Models.POD;

/**
 * Created by lucien on 01.12.2017.
 */

public class PODManager{
    public final String TAG = "TAG";

    private POD pod;
    private Coordinate coordinate;
    private TrackManager trackManager;

    //This function will create a POD
    public void createPOD(String poiName, String poiDescription, Bitmap photo){
        trackManager = new TrackManager();
        coordinate = CurrentRecordingTrack.getTrack().getCoordinates().get(CurrentRecordingTrack.getTrack().getCoordinates().size()-1);
        pod = new POD(poiName, poiDescription, photo, coordinate);
        CurrentRecordingTrack.getTrack().addPod(pod);
        trackManager.updateTrack();
    }
}
