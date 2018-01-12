package BLL;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by Colin on 12.12.2017.
 * This class is used to be sure that only one Firebase instance is called
 */

public class FirebaseClass {

    private static FirebaseDatabase mDatabase;
    private static FirebaseStorage mStorageRef;
    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }

    public static FirebaseStorage getStorage(){
        if(mStorageRef==null)
            mStorageRef = FirebaseStorage.getInstance();
        return mStorageRef;
    }
}
