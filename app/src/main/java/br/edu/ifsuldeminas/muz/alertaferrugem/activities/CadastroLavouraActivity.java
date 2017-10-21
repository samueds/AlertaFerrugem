package br.edu.ifsuldeminas.muz.alertaferrugem.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.edu.ifsuldeminas.muz.alertaferrugem.R;

public class CadastroLavouraActivity extends AppCompatActivity implements View.OnClickListener
{

    private EditText nome;
    private Button Localidade;
    MediaPlayer sound;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_lavoura);

        nome = (EditText) findViewById(R.id.camponome);
        Localidade = (Button)findViewById(R.id.botaoLocalidade);
        sound = MediaPlayer.create(this, R.raw.button_29);

        Localidade.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(nome.getText().toString().length() != 0)
        {
            Intent intent = new Intent(this, SetaLocalidadeActivity.class);
            Bundle extras = getIntent().getExtras();
            if(extras != null)
            {
                intent.putExtra("user", getIntent().getSerializableExtra("user"));
                intent.putExtra("nome", nome.getText().toString());
            }

            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Preencha o campo nome!", Toast.LENGTH_LONG).show();
        }
    }
}
