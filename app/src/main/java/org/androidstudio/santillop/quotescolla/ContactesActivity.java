package org.androidstudio.santillop.quotescolla;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by santi on 4/09/17.
 */

public class ContactesActivity extends AppCompatActivity {
    ListView lstOpciones;
    ArrayList<Membre> membres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        toolbar.setTitle("Contactes");
        setSupportActionBar(toolbar);

        //reiniciarActivity(this);
        //Cridem funcio consultaMembres per omplir la llista de membres
        consultaMembres();

        //Creem l'adaptador
        AdaptadorTitulares adaptador =
                new AdaptadorTitulares(this, membres);


        lstOpciones = (ListView)findViewById(R.id.LstOpciones);
        lstOpciones.setAdapter(adaptador);
        //Quan premem sobre un membre anirem a l'activitat per consultar les seues dades
        lstOpciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(ContactesActivity.this, membres.get(i).getNom(), Toast.LENGTH_SHORT).show();
                //Anem a l'activitat DadesContactesActivity
                Intent intent = new Intent(ContactesActivity.this, MenuContactesActivity.class);
                //Pasem a l'altra activitat el nom i cognoms
                intent.putExtra("ID", membres.get(i).getID());
                intent.putExtra("nom", membres.get(i).getNom());
                intent.putExtra("cognoms", membres.get(i).getCognoms());
                startActivity(intent);
                finish();
            }
        });
    }

    //Metode per crear una llista de membres
    private void consultaMembres(){


        membres = new ArrayList<>();

        //Connectem a la BBDD en mode lectura
        SQLiteGestor bdg = new SQLiteGestor(this,"Colla.sqlite",null,1);
        SQLiteDatabase bd = bdg.getReadableDatabase();
        //Fem la consulta dels membres
        Cursor rs = bd.rawQuery("SELECT * FROM membre ORDER BY nom, cognoms",null);

        while (rs.moveToNext()){
            //Desem en variables les dades de cada membre
            int ID = rs.getInt(0);
            String nom = rs.getString(1);
            String cognoms = rs.getString(2);
            String correu = rs.getString(3);
            String telefon = rs.getString(4);
            //Desem les dades en una llista
            membres.add(new Membre(ID, nom, cognoms, correu, telefon));
        }
        rs.close(); //Tanquem la consulta
        bd.close(); //Tanquem la BBDD
        bdg.close();
        Log.d("TAG", ""+membres.size());
        //Log.d("TAG", membres.get(1).getNom());
    }
    public void onResume() {
        super.onResume();
        //Cridem funcio consultaMembres per omplir la llista de membres
        consultaMembres();
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
