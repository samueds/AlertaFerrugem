package br.edu.ifsuldeminas.muz.alertaferrugem.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.location.Address;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import br.edu.ifsuldeminas.muz.alertaferrugem.R;
import br.edu.ifsuldeminas.muz.alertaferrugem.dao.EstacaoMetDAO;
import br.edu.ifsuldeminas.muz.alertaferrugem.model.EstacaoMet;

public class MapaActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView latitude, longitude, cidade, pais, estado;
    private Location location;
    private Address endereco;
    private LocationManager mLocationManager;
    private double longitudeD;
    private double latitudeD;
    private TextView estacao;
    private Button btm,btm2;
    private EstacaoMet best =null;
    List<EstacaoMet> lista;
    MediaPlayer sound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);


        best = null;
        sound = MediaPlayer.create(this, R.raw.button_29);
        latitude = (TextView) findViewById(R.id.txtLatitude);
        longitude = (TextView) findViewById(R.id.txtLongitude);
        pais = (TextView) findViewById(R.id.txtPais);
        estado = (TextView) findViewById(R.id.txtEstado);
        cidade = (TextView) findViewById(R.id.txtCidade);
        estacao = (TextView) findViewById(R.id.txtEstacao);
        btm = (Button) findViewById(R.id.btnEnviar);
        btm.setOnClickListener(this);
        btm2 = (Button) findViewById(R.id.btnCalculo);
        btm2.setOnClickListener(this);
        while((location = getLastKnownLocation()) == null) {}


            longitudeD = location.getLongitude();
            latitudeD = location.getLatitude();

            latitude.setText("Latitude: " + latitudeD);
            longitude.setText("Longitude: " + longitudeD);

            try
            {
                endereco = buscaEndereco(latitudeD, longitudeD);

                cidade.setText("Cidade: " + endereco.getLocality());
                estado.setText("Estado: " + endereco.getAdminArea());
                pais.setText("Pais: " + endereco.getCountryName());
            }catch (IOException e)
            {
                Log.i("GPS", e.getMessage());
            }
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.btnEnviar)
        {
            sound.start();
            lista = null;

            final ProgressDialog dialog2 = new ProgressDialog(this);
            dialog2.setMessage("Buscando...");
            dialog2.setCanceledOnTouchOutside(false);
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    List<EstacaoMet> a = EstacaoMetDAO.buscarTodos();
                    lista = a;
                    dialog2.dismiss();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run()
                        {

                            System.out.println(lista.size());

                            EstacaoMet melhorEst = new EstacaoMet();
                            melhorEst.setNome("invalid");
                            double min = 0x3f3f3f3f;

                            for(EstacaoMet q : lista)
                            {
                                System.out.println(q.getCidade());
                                Double x1 = latitudeD;
                                Double y1 = longitudeD;
                                Double x2 = q.getLatitude();
                                Double y2 = q.getLongitude();

                                double dist =  Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
                                System.out.println(q.getNome() + " " + dist);
                                if(min > dist)
                                {
                                    min = dist;
                                    melhorEst = q;
                                    best = melhorEst;
                                }

                            }
                            estacao.setGravity(Gravity.CENTER);
                            estacao.setText("Estação mais próxima: \n" + melhorEst.getNome());
                            estacao.setTextColor(Color.GREEN);

                        }
                    });

                }

            });
            t.start();
            dialog2.show();



        }
        else
        {
            sound.start();
            if(best == null)
            {
                Toast.makeText(MapaActivity.this, "Procure a melhor estação antes de prosseguir!", Toast.LENGTH_LONG);
                return;
            }
            Intent intent = new Intent(MapaActivity.this, FeedbackActivity.class);
            Bundle extras = getIntent().getExtras();
            if(extras != null)
            {
                intent.putExtra("user", getIntent().getSerializableExtra("user"));
                intent.putExtra("nome", getIntent().getSerializableExtra("nome"));
            }

            intent.putExtra("est", (Serializable) best);
            intent.putExtra("lat", latitudeD);
            intent.putExtra("long", longitudeD);
            startActivity(intent);
        }

    }

    private Location getLastKnownLocation() {
        mLocationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }
    public Address buscaEndereco(double latitude, double longitude)throws IOException
    {
        Geocoder geocoder;
        Address address = null;
        List<Address> addresses;

        geocoder = new Geocoder(getApplicationContext());

        addresses = geocoder.getFromLocation(latitude, longitude,1);
        if(addresses.size() > 0)
            address = addresses.get(0);

        return address;
    }


}




















