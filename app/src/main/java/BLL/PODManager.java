package BLL;

import android.graphics.Bitmap;

import java.util.ArrayList;
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
    //This function update a POD
    public void updatePod(String poiName, String poiDescription, List<Difficulty> difficultyList, POD pod) {
        trackManager = new TrackManager();
        pod.setName(poiName);
        pod.setDescription(poiDescription);
        pod.setDifficulties(new ArrayList<Difficulty>());
        for (Difficulty difficulty: difficultyList) {
            pod.addDifficulty(difficulty);
        }
        CurrentRecordingTrack.getTrack().addPod(pod);
        trackManager.updateTrack();
    }
}
