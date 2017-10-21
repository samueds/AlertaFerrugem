package br.edu.ifsuldeminas.muz.alertaferrugem.activities;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import br.edu.ifsuldeminas.muz.alertaferrugem.model.EstacaoMet;
import br.edu.ifsuldeminas.muz.alertaferrugem.model.Feedback;
import br.edu.ifsuldeminas.muz.alertaferrugem.model.Lavoura;
import br.edu.ifsuldeminas.muz.alertaferrugem.dao.LavouraDAO;
import br.edu.ifsuldeminas.muz.alertaferrugem.model.MetereologiaDiaria;
import br.edu.ifsuldeminas.muz.alertaferrugem.dao.MetereologiaDiariaDAO;
import br.edu.ifsuldeminas.muz.alertaferrugem.R;
import br.edu.ifsuldeminas.muz.alertaferrugem.model.Registro_Feedback;
import br.edu.ifsuldeminas.muz.alertaferrugem.dao.Registro_FeedbackDAO;
import br.edu.ifsuldeminas.muz.alertaferrugem.model.Usuario;
import br.edu.ifsuldeminas.muz.alertaferrugem.utils.TrazFeedback;

import static java.lang.Character.isDigit;

public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mensagem, produto,dias;
    private AlertDialog alerta;
    private Integer VSDs = 0;
    private CheckBox alta,trata;
    private Button salvar,gerar;
    private EditText diasT;
    private Registro_Feedback registro = new Registro_Feedback();
    Boolean altaCarga = false;
    MediaPlayer sound;
    Feedback feed = null;

    private Lavoura lavoura = new Lavoura();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        sound = MediaPlayer.create(this, R.raw.button_29);
        Bundle extras = getIntent().getExtras();


        EstacaoMet est = (EstacaoMet) getIntent().getSerializableExtra("est");
        Usuario usu = (Usuario)  getIntent().getSerializableExtra("user");
        String nome = (String) getIntent().getStringExtra("nome");
        Double latitude = (Double) getIntent().getSerializableExtra("lat");
        Double longitude = (Double) getIntent().getSerializableExtra("long");

        lavoura.setEst(est);
        lavoura.setNome(nome);
        lavoura.setLatitude(latitude);
        lavoura.setLongitude(longitude);
        lavoura.setUsu(usu);
        lavoura.setAltaCarga(false);

        registro.setEst(est);
        registro.setUsu(usu);
        registro.setLongitude(longitude);
        registro.setLatitude(latitude);
        mensagem = (TextView) findViewById(R.id.msg);
        produto = (TextView) findViewById(R.id.msg3);
        alta = (CheckBox) findViewById(R.id.carga);
        trata = (CheckBox) findViewById(R.id.trata);
        salvar = (Button) findViewById(R.id.salvar);
        dias = (TextView) findViewById(R.id.dias);
        gerar = (Button) findViewById(R.id.gerar);
        gerar.setOnClickListener(this);
        salvar.setOnClickListener(this);
        diasT = (EditText) findViewById(R.id.campoDias);

        Button help = (Button) findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = getLayoutInflater();
                View view2 = li.inflate(R.layout.helper, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(FeedbackActivity.this);

                builder.setView(view2);
                builder.setTitle("O que é isso ?");
                alerta = builder.create();
                alerta.show();
            }
        });


    }
    public void onCheckboxClicked(View view)
    {

        // Is the view now checked?
        if(view.getId() == R.id.carga)
        {
            boolean checked = ((CheckBox) view).isChecked();
            altaCarga = checked;
            lavoura.setAltaCarga(altaCarga);
        }
        else if(view.getId() == R.id.trata)
        {
            boolean checked = ((CheckBox) view).isChecked();
            if(checked)
            {
                gerar.setVisibility(View.INVISIBLE);
                lavoura.setStatus("Amarelo");
                mensagem.setText("Amarelo");
                mensagem.setTextColor(Color.YELLOW);
                produto.setGravity(Gravity.CENTER);
                produto.setTextColor(Color.BLACK);
                produto.setText("Lavoura em tratamento!");
                dias.setVisibility(View.VISIBLE);
                diasT.setVisibility(View.VISIBLE);
                Feedback g = new Feedback();
                g.setID(3);
                registro.setFeed(g);
            }
            else
            {
                dias.setVisibility(View.INVISIBLE);
                diasT.setVisibility(View.INVISIBLE);
                gerar.setVisibility(View.VISIBLE);
                mensagem.setText("");
                produto.setText("");
            }

        }


    }

    Boolean verifica(String h)
    {

        for(int i = 0 ; i < h.length(); i++)
        {
            System.out.println(h.charAt(i));
            if(isDigit(h.charAt(i)))
            {
                System.out.println(h.charAt(i));
                continue;

            }

            else return false;

        }

        return true;

    }

    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.gerar)
        {
            sound.start();

            final TrazFeedback mito = new TrazFeedback();

            final ProgressDialog dialog2 = new ProgressDialog(this);
            dialog2.setMessage("Carregando..");
            dialog2.setCanceledOnTouchOutside(false);
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    feed = mito.Traz(lavoura);
                    dialog2.dismiss();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mensagem.setText(feed.getNome());

                            if(feed.getNome() == "SIM!")
                            {
                                lavoura.setStatus("Vermelho");
                                mensagem.setTextColor(Color.RED);

                            }
                            else
                            {
                                mensagem.setTextColor(Color.GREEN);
                                lavoura.setStatus("Verde");
                            }
                            produto.setGravity(Gravity.CENTER);
                            produto.setTextColor(Color.BLACK);
                            produto.setText(feed.getObs());

                            registro.setFeed(feed);
                            registro.setObs(feed.getObs());
                        }
                    });
                }
            });
            t.start();
            dialog2.show();




        }
        else if(v.getId() == R.id.salvar) {
            sound.start();

            if(feed == null && !lavoura.getStatus().equals("Amarelo"))
            {
                Toast.makeText(FeedbackActivity.this, "Feedback não gerado, clique o botão acima!", Toast.LENGTH_LONG).show();
            }
            else if(feed != null ||  lavoura.getStatus().equals("Amarelo"))
            {
                if(lavoura.getStatus().equals("Amarelo"))
                {

                    if(!diasT.getText().toString().equals(""))
                    {
                        if(verifica(diasT.getText().toString()))
                             lavoura.setPeriodo(Integer.parseInt(diasT.getText().toString()));
                        else
                        {
                            Toast.makeText(FeedbackActivity.this, "Digite um valor válido!", Toast.LENGTH_LONG).show();
                            return;
                        }

                    }
                    else
                    {
                        Toast.makeText(FeedbackActivity.this, "Defina a quantidade de dias para finalizar o tratamento!", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                final ProgressDialog dialog = new ProgressDialog(FeedbackActivity.this);

                dialog.setMessage("Salvando...");
                dialog.setIndeterminate(false);
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Registro_FeedbackDAO dao = new Registro_FeedbackDAO();

                        final boolean a1 = dao.inserirRegistro(registro);
                        LavouraDAO dao2 = new LavouraDAO();
                        boolean a2 = false;
                        if (a1)
                            a2 = dao2.inserirLavoura(lavoura);
                        dialog.dismiss();
                        final boolean a3 = a2;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (a1 && a3) {
                                    Toast.makeText(FeedbackActivity.this, "Registrado com sucesso! Voltando a tela inicial...", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(FeedbackActivity.this, LavourasActivity.class);
                                    Bundle extras = getIntent().getExtras();
                                    intent.putExtra("user", (Serializable) registro.getUsu());
                                    startActivity(intent);
                                } else
                                    Toast.makeText(FeedbackActivity.this, "Erro ao salvar, tente novamente!", Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                }
                );
                t.start();
                dialog.show();
            }


        }

    }
}
