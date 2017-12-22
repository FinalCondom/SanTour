package BLL;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import Models.Coordinate;
import Models.POI;

import static BLL.FirebaseClass.getStorage;

/**
 * Created by lucien on 01.12.2017.
 */

public class POIManager {
    public final String TAG = "TAG";

    private POI poi;
    private Coordinate coordinate;
    private TrackManager trackManager;
    private StorageReference mStorageRef;

    //This function will create a POI
    public void createPOI(String poiName, String poiDescription, Bitmap photo){
        mStorageRef = getStorage().getReference();
        trackManager = new TrackManager();
        coordinate = CurrentRecordingTrack.getTrack().getCoordinates().get(CurrentRecordingTrack.getTrack().getCoordinates().size()-1);
        poi = new POI(poiName, poiDescription, photo, coordinate);
        CurrentRecordingTrack.getTrack().addPoi(poi);
        addPictureToStorage();
        trackManager.updateTrack();
    }

    //This function update a POI
    public void updatePoi(String poiName, String poiDescription, POI poi) {
        poi.setName(poiName);
        poi.setDescription(poiDescription);
        trackManager = new TrackManager();
        trackManager.updateTrack();
    }

    //This method will add the POD picture to the storage
    public void addPictureToStorage(){
        StorageReference PodRef = mStorageRef.child("images/"+CurrentRecordingTrack.getTrack().getId_track()+"/POI/picture"+(CurrentRecordingTrack.getTrack().getPOIs().size()-1)+".jpg");
        Bitmap bitmap = poi.getPicture();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = PodRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Log.d("downloadUrl-->", "" + downloadUrl);
            }
        });
    }
}