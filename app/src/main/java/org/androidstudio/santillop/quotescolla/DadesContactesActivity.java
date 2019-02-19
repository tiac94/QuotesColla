package org.androidstudio.santillop.quotescolla;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by santi on 4/09/17.
 */
public class DadesContactesActivity extends AppCompatActivity {
    TextView correu, telefon, text;
    String nomStr, cognomsStr, correuStr, telefonStr;
    String nomExtra, cognomsExtra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dadescontacte);

        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);


        text = (TextView) findViewById(R.id.text);
        /*nom = (TextView) findViewById(R.id.nom);
        cognoms = (TextView) findViewById(R.id.cognoms);*/
        correu = (TextView) findViewById(R.id.correu);
        telefon = (TextView) findViewById(R.id.telefon);

        //Arrepleguem el nom i cognoms de l'altra activitat
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        nomExtra = extras.getString("nom");
        cognomsExtra = extras.getString("cognoms");

        toolbar.setTitle(nomExtra + " " + cognomsExtra);
        setSupportActionBar(toolbar);
        //text.setText("Dades de " + nomExtra + " " + cognomsExtra);
        //Connectem a la BBDD
        SQLiteGestor bdg = new SQLiteGestor(this,"Colla.sqlite",null,1);
        SQLiteDatabase bd = bdg.getWritableDatabase();
        //Fem la consulta per traure el correu i telefon del membre
        Cursor rs = bd.rawQuery("SELECT * FROM membre WHERE nom = '" + nomExtra
                + "' AND cognoms = '" + cognomsExtra + "'",null);
        if (rs.moveToNext()){
            String nomRS, cognomsRS, correuRS, telefonRS;
            /*nomRS = rs.getString(0);
            cognomsRS = rs.getString(1);*/
            correuRS = rs.getString(3);
            telefonRS =rs.getString(4);

            /*nom.setText(nomRS);
            cognoms.setText(cognomsRS);*/
            //assignem el correu i telefon als camps
            correu.setText(correuRS);
            telefon.setText(telefonRS);
        }
        //Tanquem la consulta i la BBDD
        rs.close();
        bd.close();
        bdg.close();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            finish();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
