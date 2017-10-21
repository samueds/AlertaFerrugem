package br.edu.ifsuldeminas.muz.alertaferrugem.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import br.edu.ifsuldeminas.muz.alertaferrugem.R;
import br.edu.ifsuldeminas.muz.alertaferrugem.activities.CarregamentoActivity;

public class SetaLocalidadeActivity extends AppCompatActivity
{

    private Button botalLocal;
    MediaPlayer sound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seta_localidade);
        sound = MediaPlayer.create(this, R.raw.button_29);
        botalLocal = (Button) findViewById(R.id.botaoLocal);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);

        }
        botalLocal.setOnClickListener(Localizar());

    }
    private View.OnClickListener Localizar()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                    sound.start();
                    Intent intent = new Intent(getApplicationContext(), CarregamentoActivity.class);
                    Bundle extras = getIntent().getExtras();
                    if(extras != null)
                    {
                        intent.putExtra("user", getIntent().getSerializableExtra("user"));
                        intent.putExtra("nome", getIntent().getStringExtra("nome"));
                    }

                    startActivity(intent);

            }
        };
    }
}
