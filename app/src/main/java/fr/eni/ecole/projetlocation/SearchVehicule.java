package fr.eni.ecole.projetlocation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.eni.ecole.projetlocation.adapter.VehiculeAdapter;
import fr.eni.ecole.projetlocation.dao.vehicule.VehiculeDao;
import fr.eni.ecole.projetlocation.models.Vehicule;

public class SearchVehicule extends AppCompatActivity {
    private VehiculeDao daoVehicule;
    private TextView txtPrix;
    private SeekBar sbPrix;
    private CheckBox cbType;
    private Spinner spMarque;
    private Spinner spCarburant;
    private Spinner spDiponibilite;
    private Button btRecherche;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_vehicule);
        daoVehicule = new VehiculeDao(this);
        sbPrix = (SeekBar) findViewById(R.id.sb_select_prix);
        txtPrix = (TextView) findViewById(R.id.txt_prix);
        sbPrix.setMax(150);
        sbPrix.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int position, boolean b) {
                txtPrix.setText(String.valueOf(position));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        String[] marques = daoVehicule.selectMarque();

        String[] carburants = {
                Vehicule.ESSENCE,
                Vehicule.DIESEL,
                Vehicule.GPL,
                Vehicule.ELECTRIQUE
        };
        String[] dispo = {
                "Disponible",
                "Non-Disponible"
        };

        spCarburant = (Spinner) findViewById(R.id.sp_carburant);
        spDiponibilite = (Spinner) findViewById(R.id.sp_dispo);
        spMarque = (Spinner) findViewById(R.id.sp_marque);
        cbType = (CheckBox) findViewById(R.id.cb_type);

        ArrayAdapter<String> adapterDisponibilite =  new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item ,dispo);
        spDiponibilite.setAdapter(adapterDisponibilite);

        ArrayAdapter<String> adapterCarburant =  new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item ,carburants);
        spCarburant.setAdapter(adapterCarburant);

        ArrayAdapter<String> adapterMarque =  new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item ,marques);
        spMarque.setAdapter(adapterMarque);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    public void showAddCar(MenuItem item) {
        Intent intent = new Intent(SearchVehicule.this, ManageVehicule.class);
        startActivity(intent);
    }
    public void showCarsList(MenuItem item) {
        Intent intent = new Intent(SearchVehicule.this, ListeVehiculeActivity.class);
        startActivity(intent);
    }

    public void onClickSearchVehicule(View view) {
        String dispo = spDiponibilite.getSelectedItem().toString();
        String marque = spMarque.getSelectedItem().toString();
        String carburant = spCarburant.getSelectedItem().toString();
        int type = 0;
        int disponibilite = 1;
        if(cbType.isChecked()){
            type=1;
        }
        if(dispo =="Disponible"){
            disponibilite = 0;
        }

        Log.wtf("WTF","DATA ::=> "+marque+" "+dispo+ " "+carburant+" "+type+" "+txtPrix.getText().toString());
        List<Vehicule> vehicules = new ArrayList<>();
        vehicules = daoVehicule.selectSearchVehicule(marque,carburant,Integer.parseInt(txtPrix.getText().toString()),type,disponibilite);

        for(int i =0; i<vehicules.size();i++){
            Log.wtf("WTF","LISTE DES VEHICULE RECHERCHE ==> "+vehicules.get(i).toString());
        }

        VehiculeAdapter vehiculeAdapter = new VehiculeAdapter(this,R.layout.list_vehicule,vehicules);
        final ListView listView = (ListView) findViewById(R.id.lv_liste_vehicule);
        listView.setAdapter(vehiculeAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Vehicule vehicule = (Vehicule) adapterView.getItemAtPosition(i);

                Intent intent = new Intent(SearchVehicule.this,SearchClient.class);
                intent.putExtra("vehicule",vehicule);
                startActivity(intent);


            }
        });
    }
}
