package BLL;

import Models.Track;

/**
 * Created by lucien on 01.12.2017.
 * This class have a static Track that will ensure that only one is recording
 */

public class CurrentRecordingTrack {
    private static Track track;

    public static Track getTrack() {
        return track;
    }

    public static void setTrack(Track track) {
        CurrentRecordingTrack.track = track;
    }
}
