package ch.hes.santour;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private  static final int REQUEST_LOCATION_CODE = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        super.onCreate(savedInstanceState);

        loadLastLanguage();


        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = new HomeFragement();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, fragment).commit();

        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkLocationPermission();
        }

    }

    public void changeLanguage (String toLoad) {
        Locale locale = new Locale(toLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        //noinspection deprecation
        config.locale= locale;
        //noinspection deprecation
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("LANGUAGE", toLoad).commit();
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    //Méthode qui se déclenchera au clic sur un item
    public boolean onOptionsItemSelected(MenuItem item) {
        String languageToLoad;
        //On regarde quel item a été cliqué grâce à son id et on déclenche une action
        switch (item.getItemId()) {
            case R.id.action_bar_settings:
                return true;
            case R.id.french:
                languageToLoad = "fr";
                changeLanguage(languageToLoad);
                return true;

            case R.id.german:
                languageToLoad = "de";
                changeLanguage(languageToLoad);

                return true;
            case R.id.english:
                languageToLoad = "en";
                changeLanguage(languageToLoad);
                return true;

        }
        return false;
    }

    private void loadLastLanguage() {
        String language = PreferenceManager.getDefaultSharedPreferences(this).getString("LANGUAGE", "en");
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        //noinspection deprecation
        config.locale = locale;
        //noinspection deprecation
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    public boolean checkLocationPermission()
     {
         if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION))
                {
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
                }
                else
                {
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION_CODE);
                }
            return false;
            }
            else
            {
                return true;
            }
     }

}
