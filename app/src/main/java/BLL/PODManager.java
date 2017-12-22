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
import java.util.ArrayList;
import java.util.List;

import Models.Coordinate;
import Models.Difficulty;
import Models.POD;

import static BLL.FirebaseClass.getStorage;

/**
 * Created by lucien on 01.12.2017.
 */

public class PODManager{
    public final String TAG = "TAG";

    private POD pod;
    private Coordinate coordinate;
    private TrackManager trackManager;
    private StorageReference mStorageRef;
    //This function will create a POD
    public void createPOD(String poiName, String poiDescription, Bitmap photo, List<Difficulty> difficultyList){
        trackManager = new TrackManager();
        mStorageRef = getStorage().getReference();
        coordinate = CurrentRecordingTrack.getTrack().getCoordinates().get(CurrentRecordingTrack.getTrack().getCoordinates().size()-1);
        pod = new POD(poiName, poiDescription, photo, coordinate);
        for (Difficulty difficulty: difficultyList) {
            pod.addDifficulty(difficulty);
        }
        CurrentRecordingTrack.getTrack().addPod(pod);
        addPictureToStorage();
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
        trackManager.updateTrack();
    }

    //This method will add the POD picture to the storage
    public void addPictureToStorage(){
        StorageReference PodRef = mStorageRef.child("images/"+CurrentRecordingTrack.getTrack().getId_track()+"/POD/picture"+(CurrentRecordingTrack.getTrack().getPODs().size()-1)+".jpg");
        Bitmap bitmap = pod.getPicture();
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
