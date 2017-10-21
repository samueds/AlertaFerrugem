package br.edu.ifsuldeminas.muz.alertaferrugem.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.edu.ifsuldeminas.muz.alertaferrugem.R;

public class InicioActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        Button continaur = (Button) findViewById(R.id.button);
        continaur.setOnClickListener(this);



    }

    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.button)
        {
            Intent i = new Intent(this, TelaInicialActivity.class);
            startActivity(i);
        }
    }
}
