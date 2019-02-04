package org.androidstudio.santillop.quotescolla;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by santi on 11/07/17.
 */

public class QuotesActivity extends AppCompatActivity {
    ArrayList <Membre> membres;
    ArrayList <Quota> quotes;
    TextView [] arrMesos;
    EditText [] arrQuantitat;
    TextView deuteMembre;
    CheckBox [] checkBoxes;
    Button actualitzaQuotes;
    String nom, cognoms, correu, telefon, gener, febrer, març, abril, maig, juny, juliol, agost, septembre, octubre, novembre, desembre;
    Spinner spinner;
    SharedPreferences pref;
    String [] arrPrefQuotes;
    private String[] arrQuantitatStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);

        //Declarem els mesos
        arrMesos = new TextView[12];
        arrMesos[0] = (TextView) findViewById(R.id.Gener);
        arrMesos[1] = (TextView) findViewById(R.id.Febrer);
        arrMesos[2] = (TextView) findViewById(R.id.Març);
        arrMesos[3] = (TextView) findViewById(R.id.Abril);
        arrMesos[4] = (TextView) findViewById(R.id.Maig);
        arrMesos[5] = (TextView) findViewById(R.id.Juny);
        arrMesos[6] = (TextView) findViewById(R.id.Juliol);
        arrMesos[7] = (TextView) findViewById(R.id.Agost);
        arrMesos[8] = (TextView) findViewById(R.id.Setembre);
        arrMesos[9] = (TextView) findViewById(R.id.Octubre);
        arrMesos[10] = (TextView) findViewById(R.id.Novembre);
        arrMesos[11] = (TextView) findViewById(R.id.Desembre);

        //deuteMembre = (TextView) findViewById(R.id.deuteMembre);
        //Declarem les quantitats de cada mes
        arrQuantitat = new EditText[12];
        arrQuantitat[0] = (EditText) findViewById(R.id.QuotaGener);
        arrQuantitat[1] = (EditText) findViewById(R.id.QuotaFebrer);
        arrQuantitat[2] = (EditText) findViewById(R.id.QuotaMarç);
        arrQuantitat[3] = (EditText) findViewById(R.id.QuotaAbril);
        arrQuantitat[4] = (EditText) findViewById(R.id.QuotaMaig);
        arrQuantitat[5] = (EditText) findViewById(R.id.QuotaJuny);
        arrQuantitat[6] = (EditText) findViewById(R.id.QuotaJuliol);
        arrQuantitat[7] = (EditText) findViewById(R.id.QuotaAgost);
        arrQuantitat[8] = (EditText) findViewById(R.id.QuotaSetembre);
        arrQuantitat[9] = (EditText) findViewById(R.id.QuotaOctubre);
        arrQuantitat[10] = (EditText) findViewById(R.id.QuotaNovembre);
        arrQuantitat[11] = (EditText) findViewById(R.id.QuotaDesembre);

        //Declarem les quantitats les caselles
        checkBoxes = new CheckBox[12];
        checkBoxes[0] = (CheckBox) findViewById(R.id.CheckGener);
        checkBoxes[1] = (CheckBox) findViewById(R.id.CheckFebrer);
        checkBoxes[2] = (CheckBox) findViewById(R.id.CheckMarç);
        checkBoxes[3] = (CheckBox) findViewById(R.id.CheckAbril);
        checkBoxes[4] = (CheckBox) findViewById(R.id.CheckMaig);
        checkBoxes[5] = (CheckBox) findViewById(R.id.CheckJuny);
        checkBoxes[6] = (CheckBox) findViewById(R.id.CheckJuliol);
        checkBoxes[7] = (CheckBox) findViewById(R.id.CheckAgost);
        checkBoxes[8] = (CheckBox) findViewById(R.id.CheckSetembre);
        checkBoxes[9] = (CheckBox) findViewById(R.id.CheckOctubre);
        checkBoxes[10] = (CheckBox) findViewById(R.id.CheckNovembre);
        checkBoxes[11] = (CheckBox) findViewById(R.id.CheckDesembre);

        actualitzaQuotes = (Button) findViewById(R.id.actualitzaQuotes);

        /*for(EditText quantitat : arrQuantitat){
            quantitat.setEnabled(false);
            quantitat.setFocusable(false);
        }*/
        //Obtenim el nom i cognoms
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        nom = extras.getString("nom");
        cognoms = extras.getString("cognoms");
        //correu = extras.getString("correu");
        //telefon = extras.getString("telefon");
        toolbar.setTitle("Deutes de " + nom + " " + cognoms);
        setSupportActionBar(toolbar);
        //deuteMembre.setText("Deutes de " + nom + " " + cognoms);
        creaMembre();
        //carregaPagina();
        //premerCasella();
        //actualitzaQuotes();
        //arrPrefQuotes = new String[arrQuantitat.length];



    }
    //Metode per crear una llista de quotes
    private void creaQuotes(){
        quotes = new ArrayList <Quota>();

        //Connectem a la BBDD en mode lectura
        SQLiteGestor bdg = null;
        bdg = new SQLiteGestor(this,"Agenda.sqlite",null,1);
        SQLiteDatabase bd = bdg.getReadableDatabase();
        //Fem la consulta de les quotes del membre
        Cursor rs = bd.rawQuery("SELECT mes, quantitat FROM quota, membre WHERE nom = '" + nom
                 + "' AND cognoms = '" + cognoms
                 + "' AND membre.ID = id_membre",null);

        while (rs.moveToNext()){
            //assignem en variables el mes i quantitat del membre
            int mes = rs.getInt(0);
            int quantitat = rs.getInt(1);
            //deuteMembre.append(""+mes + " " + quantitat);
            //omplim en una llista el mes i quantitat
            quotes.add(new Quota(mes, quantitat));
        }
        //Recorrem els mesos i assignem als camps de text les quantitats de cada mes
        for(int i=0; i<12; i++){
            arrQuantitat[i].setText(""+quotes.get(i).getQuantitat());
        }

        //Tanquem la consulta i la BBDD
        rs.close();
        bd.close();
        bdg.close();
    }
    //Metode per crear el membre
    private void creaMembre(){
        //Cridem a la funcio creaQuotes();


        membres = new ArrayList<>();
        creaQuotes();
        membres.add(new Membre(nom, cognoms, quotes));
    }
    //Funció per marcar o desmarcar les caselles depenent si el membre a pagat o no la penya
    private void carregaPagina(final String [] valorPref){
        //quan anem a la pagina si te valor 0 es marcara checkbox

        for(int i=0; i<checkBoxes.length; i++){

            //Si el valor del camp de text es menor al de les preferencies
            if (Integer.parseInt(arrQuantitat[i].getText().toString()) < Integer.parseInt(valorPref[i])){
                checkBoxes[i].setChecked(false);
                checkBoxes[i].setText(nom + " no ha pagat");
            }
            else{
                checkBoxes[i].setChecked(true);
                checkBoxes[i].setText(nom + " ha pagat");
            }
        }

    }
    //Funcio que canvia el valor dels camps de text si la casella està marcada o desmarcada
    private void premerCasella(final String [] arrPrefQuotes){
        //event quan premem un checkbox
        for(int i=0; i<checkBoxes.length; i++){
            final int finalI = i;


            checkBoxes[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Si desmarquem la casella el valor pasara a 0 i posem missatge no pagat



                        if (!checkBoxes[finalI].isChecked()){
                            quotes.get(finalI).setQuantitat(0);
                            checkBoxes[finalI].setText(nom + " no ha pagat");
                        }
                        else{
                            //Si la marquem el camp de text posara la quantitat que s'ha pagat
                            quotes.get(finalI).setQuantitat(Integer.parseInt(arrPrefQuotes[finalI]));
                            checkBoxes[finalI].setText(nom + " ha pagat");  //posem el missatge pagat
                        }

                        arrQuantitat[finalI].setText(""+quotes.get(finalI).getQuantitat()); //posem en camps de text les quotes
                    }




            });



        }
    }
    //Funció quan es prem el botó d'actualitzar les quotes
    private void actualitzaQuotes(final String [] valorPref){
        actualitzaQuotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Connectem a la BBDD en mode d'escriptura
                SQLiteGestor bdg = new SQLiteGestor(QuotesActivity.this,"Agenda.sqlite",null,1);
                SQLiteDatabase bd = bdg.getWritableDatabase();
                arrPrefQuotes = new String[arrQuantitat.length];

                //Recorem les caselles
                for(int i=0; i<checkBoxes.length; i++){

                    Log.d("Pref " + i, valorPref[i]);
                    //Si la quantitat és menor que el valor desmarquem la casella i missatge no pagat
                    if (Integer.parseInt(arrQuantitat[i].getText().toString()) < Integer.parseInt(valorPref[i])){
                        checkBoxes[i].setChecked(false);
                        checkBoxes[i].setText(nom + " no ha pagat");
                    }
                    //Si quantitat és major a 0 marcarem la casella i missatge pagat
                    else{
                        checkBoxes[i].setChecked(true);
                        checkBoxes[i].setText(nom + " ha pagat");
                    }
                    Log.d("arrQuant " + i, arrQuantitat[i].getText().toString());
                    //actualitzem les quotes de tots els mesos
                    bd.execSQL("UPDATE quota SET quantitat = " + arrQuantitat[i].getText().toString()
                            + " WHERE id_membre = (SELECT ID FROM membre WHERE nom = '" + nom
                            + "' AND cognoms = '" + cognoms + "') AND mes = " + quotes.get(i).getMes());
                }

                //Tanquem la BBDD
                bd.close();
                bdg.close();
                String cad = "Les quotes de " + nom + " s'han actualitzat amb èxit!"; //Missatge d'exit
                Toast.makeText(getApplicationContext(), cad, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void onResume() {
        super.onResume();

        arrPrefQuotes = new String[arrQuantitat.length];
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        gener = pref.getString("gener", "20");
        febrer = pref.getString("febrer", "20");
        març = pref.getString("març", "20");
        abril = pref.getString("abril", "20");
        maig = pref.getString("maig", "20");
        juny = pref.getString("juny", "20");
        juliol = pref.getString("juliol", "20");
        agost = pref.getString("agost", "20");
        septembre = pref.getString("septembre", "20");
        octubre = pref.getString("octubre", "20");
        novembre = pref.getString("novembre", "20");
        desembre = pref.getString("desembre", "20");

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

        Log.d("JOJOJOJOJ", arrPrefQuotes[0]);
        boolean semaf = true;
        for(int i=0; i<checkBoxes.length; i++) {
            if (arrPrefQuotes[i].equals("") && semaf) {
                String cad = "ERROR! Ves a la pàgina de preferències i ompli tots els camps de les quotes.";
                Toast.makeText(getApplicationContext(), cad, Toast.LENGTH_SHORT).show();
                semaf = false;

            }
        }
        if (!semaf){
            actualitzaQuotes.setEnabled(false);
            for(int i=0; i<checkBoxes.length; i++){
                checkBoxes[i].setEnabled(false);
            }
        }
        else{
            actualitzaQuotes.setEnabled(true);
            for(int i=0; i<checkBoxes.length; i++){
                checkBoxes[i].setEnabled(true);
            }
            //Passem els edittext a Strings
            arrQuantitatStr = new String[arrQuantitat.length];

            carregaPagina(arrPrefQuotes);
            premerCasella(arrPrefQuotes);
            actualitzaQuotes(arrPrefQuotes);
        }

        /*for (int i = 0; i < arrPrefQuotes.length; i++) {
            arrQuantitat[i].setText(arrPrefQuotes[i]);
        }*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ppal_quotes, menu);
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
        if (id == R.id.action_configuracio)
        {
            Intent intent = new Intent(this, MyPreferencesActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
