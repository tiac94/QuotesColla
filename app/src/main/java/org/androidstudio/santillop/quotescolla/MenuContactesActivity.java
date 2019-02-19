package org.androidstudio.santillop.quotescolla;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;




/**
 * Created by santi on 11/11/17.
 */

public class MenuContactesActivity extends AppCompatActivity {
    int IDExtra;
    //reinicia una Activity
    public  void reiniciarActivity(Activity actividad){
        Intent intent=new Intent();
        intent.setClass(actividad, actividad.getClass());
        intent.putExtra("nom", IDExtra);
        //llamamos a la actividad
        actividad.startActivity(intent);
        //finalizamos la actividad actual
        actividad.finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menucontacte);

        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);


        Button dades = (Button) findViewById(R.id.dades);
        Button quotes = (Button) findViewById(R.id.quotes);
        Button borrar = (Button) findViewById(R.id.borrar);
        //Arrepleguem el nom i cognoms de l'altra activitat
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        IDExtra = extras.getInt("ID");
        final String nomExtra = extras.getString("nom");
        final String cognomsExtra = extras.getString("cognoms");
        toolbar.setTitle("Menu de " + nomExtra);
        setSupportActionBar(toolbar);
        dades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DadesContactesActivity.class);
                //Pasem a l'altra activitat el nom i cognoms
                intent.putExtra("nom", nomExtra);
                intent.putExtra("cognoms", cognomsExtra);
                startActivity(intent);
            }
        });
        quotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), QuotesActivity.class);
                //Pasem a l'altra activitat el nom i cognoms
                intent.putExtra("nom", nomExtra);
                intent.putExtra("cognoms", cognomsExtra);
                startActivity(intent);
            }
        });
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Cridem a la funció diàleg
                createDialog(IDExtra).show();

                //finish();
                //reiniciarActivity(MenuContactesActivity.this);
            }
        });
    }
    public Dialog createDialog(final int ID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_warning_black_24dp)
                .setTitle("Esta vosté segur de tirar al carrer aquest membre?")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //Si l'usuari prem l'opcio OK s'esborrarà el membre
                                esborraMembre(ID);
                            }
                        }
                )
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(getBaseContext(),
                                        "Esborrament de membre cancel·lat!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), ContactesActivity.class);
                                intent.putExtra("cancel","cancel");
                                startActivity(intent);
                                finish();
                            }
                        }
                );
        return builder.create();
    }
    public void esborraMembre(int ID){
        //Connectem al gestor de la BBDD en mode escriptura
        SQLiteGestor bdg = new SQLiteGestor(getApplicationContext(),"Colla.sqlite",null,1);
        SQLiteDatabase bd = bdg.getWritableDatabase();

        bd.execSQL("DELETE FROM membre WHERE membre.ID = " + ID);
        bd.execSQL("DELETE FROM quota WHERE quota.id_membre = " + ID);
        bd.close();
        bdg.close();

        final ProgressDialog dialog = ProgressDialog.show(MenuContactesActivity.this, "S'està esborrant l'usuari", "Espera si us plau...", true);
        new Thread(new Runnable() {
            public void run() {
                try {
                    //---simulate doing something lengthy---
                    Thread.sleep(5000);
                    //---dismiss the dialog---
                    dialog.dismiss();

                    MenuContactesActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(MenuContactesActivity.this, "L'usuari s'ha esborrat amb èxit!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), ContactesActivity.class);
                            startActivity(intent);
                            finish();


                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {
            Intent intent = new Intent(getApplicationContext(), ContactesActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
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