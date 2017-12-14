package BLL;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Models.Difficulty;

/**
 * Created by Cento on 12.12.2017.
 */

public class DifficultyManager {
    private static String TAG = "TAG";
    private static DatabaseReference mRootRef;
    private List<Difficulty> listDifficultiesBD = new ArrayList<Difficulty>();
    public DifficultyManager(){
        mRootRef = FirebaseClass.getDatabase().getReference();
    }

}
