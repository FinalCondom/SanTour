package BLL;

import android.graphics.Bitmap;

import java.util.List;

import Models.Coordinate;
import Models.Difficulty;
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
    public void createPOD(String poiName, String poiDescription, Bitmap photo, List<Difficulty> difficultyList){
        trackManager = new TrackManager();
        coordinate = CurrentRecordingTrack.getTrack().getCoordinates().get(CurrentRecordingTrack.getTrack().getCoordinates().size()-1);
        pod = new POD(poiName, poiDescription, photo, coordinate);
        for (Difficulty difficulty: difficultyList) {
            pod.addDifficulty(difficulty);
        }
        CurrentRecordingTrack.getTrack().addPod(pod);
        trackManager.updateTrack();
    }
}
