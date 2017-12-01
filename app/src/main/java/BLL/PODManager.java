package BLL;

/**
 * Created by lucien on 01.12.2017.
 */

public class PODManager extends POIManager {

    public PODManager() {
        mTrackRef = mRootRef.child("tracks").child(track.getId_track()).child("POD").push();
    }

    public void createPOD(String name, String description) {
        createPOI(name, description);
    }
}
