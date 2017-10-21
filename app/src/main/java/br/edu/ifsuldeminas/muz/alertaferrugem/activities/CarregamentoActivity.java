package br.edu.ifsuldeminas.muz.alertaferrugem.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import br.edu.ifsuldeminas.muz.alertaferrugem.R;
import br.edu.ifsuldeminas.muz.alertaferrugem.model.Usuario;

public class CarregamentoActivity extends AppCompatActivity {

    private static int Tempo_SPLASH = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carregamento);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent i = new Intent(CarregamentoActivity.this, MapaActivity.class);
                Bundle extras = getIntent().getExtras();
                if(extras != null)
                {
                    i.putExtra("user", getIntent().getSerializableExtra("user"));
                    i.putExtra("nome", getIntent().getStringExtra("nome"));
                }
                startActivity(i);
                finish();
            }

        }, Tempo_SPLASH);
    }
}
