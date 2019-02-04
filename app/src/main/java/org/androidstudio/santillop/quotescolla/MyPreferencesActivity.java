package org.androidstudio.santillop.quotescolla;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MyPreferencesActivity extends AppCompatActivity {

    public static class MyPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.my_preferences_screen);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, new MyPreferenceFragment());
        fragmentTransaction.commit();
        //Toast.makeText(this, "Les quotes s'han desat amb èxit.", Toast.LENGTH_SHORT).show();
    }
    public void onDestroy() {
        super.onDestroy();
        String[] arrPrefQuotes = new String[12];
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String gener = pref.getString("gener", "20");
        String febrer = pref.getString("febrer", "20");
        String març = pref.getString("març", "20");
        String abril = pref.getString("abril", "20");
        String maig = pref.getString("maig", "20");
        String juny = pref.getString("juny", "20");
        String juliol = pref.getString("juliol", "20");
        String agost = pref.getString("agost", "20");
        String septembre = pref.getString("septembre", "20");
        String octubre = pref.getString("octubre", "20");
        String novembre = pref.getString("novembre", "20");
        String desembre = pref.getString("desembre", "20");

        arrPrefQuotes[0] = gener;
        arrPrefQuotes[1] = febrer;
        arrPrefQuotes[2] = març;
        arrPrefQuotes[3] = abril;
        arrPrefQuotes[4] = maig;
        arrPrefQuotes[5] = juny;
        arrPrefQuotes[6] = juliol;
        arrPrefQuotes[7] = agost;
        arrPrefQuotes[8] = septembre;
        arrPrefQuotes[9] = octubre;
        arrPrefQuotes[10] = novembre;
        arrPrefQuotes[11] = desembre;

        boolean semaf = true;
        for (int i = 0; i < arrPrefQuotes.length; i++) {
            if (arrPrefQuotes[i].equals("") && semaf) {
                String cad = "ERROR! Ves a la pàgina de preferències i ompli tots els camps de les quotes.";
                Toast.makeText(getApplicationContext(), cad, Toast.LENGTH_SHORT).show();
                semaf = false;

            }
        }
        if(semaf){
            String cad = "Els camps de les quotes s'han omplit amb èxit.";
            Toast.makeText(getApplicationContext(), cad, Toast.LENGTH_SHORT).show();
        }
    }
}