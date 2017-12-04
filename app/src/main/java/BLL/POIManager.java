package BLL;

import android.graphics.Bitmap;

import Models.Coordinate;
import Models.POI;

/**
 * Created by lucien on 01.12.2017.
 */

public class POIManager {
    public final String TAG = "TAG";

    private POI poi;
    private Coordinate coordinate;

    //This function will create a POI
    public void createPOI(String poiName, String poiDescription, Bitmap photo){
        coordinate = CurrentRecordingTrack.getTrack().getCoordinates().get(CurrentRecordingTrack.getTrack().getCoordinates().size()-1);
        poi = new POI(poiName, poiDescription, photo, coordinate);
        CurrentRecordingTrack.getTrack().addPoi(poi);
    }
}