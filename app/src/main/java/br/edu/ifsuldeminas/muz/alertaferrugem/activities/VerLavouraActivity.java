package br.edu.ifsuldeminas.muz.alertaferrugem.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import br.edu.ifsuldeminas.muz.alertaferrugem.dao.LavouraDAO;
import br.edu.ifsuldeminas.muz.alertaferrugem.model.Feedback;
import br.edu.ifsuldeminas.muz.alertaferrugem.model.Lavoura;
import br.edu.ifsuldeminas.muz.alertaferrugem.R;
import br.edu.ifsuldeminas.muz.alertaferrugem.utils.TrazFeedback;

public class VerLavouraActivity extends AppCompatActivity implements View.OnClickListener {



    private AlertDialog alerta;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_lavoura);

        Lavoura lavoura  = (Lavoura) getIntent().getSerializableExtra("lavoura");

        TextView nome = (TextView)findViewById(R.id.nomeLav);
        TextView dias = (TextView)findViewById(R.id.dias);
        TextView status = (TextView)findViewById(R.id.cor);
        Button voltar = (Button) findViewById(R.id.button);
        voltar.setOnClickListener(this);
        Button produto = (Button) findViewById(R.id.button2);
        produto.setOnClickListener(this);
        Button excluir = (Button)findViewById(R.id.excluir);
        excluir.setOnClickListener(this);



        nome.setText("Lavoura: " + lavoura.getNome());


        String q = null;
        if(lavoura.getSta().equals("Vermelho"))
        {
            q = "Em perigo";
            status.setTextColor(Color.RED);
            produto.setVisibility(View.VISIBLE);
        }

        else if(lavoura.getSta().equals( "Verde"))
        {
            status.setTextColor(Color.GREEN);
            q = "Sem perigo";
        }

        else
        {
            java.util.Date dataUtil = new java.util.Date();

            String aux[] = new String[3];
            String aux2[] = new String[3];
            aux2 = (new java.sql.Date(new Date().getTime())).toString().split("-");
            aux = lavoura.getDataPrev().toString().split("-");


            int ano = Integer.parseInt(aux2[0]);
            int mes = Integer.parseInt(aux2[1]);
            int dia = Integer.parseInt(aux2[2]);


            Integer DIAS = dateToInt(mes,dia,ano);

            ano = Integer.parseInt(aux[0]);
            mes = Integer.parseInt(aux[1]);
            dia = Integer.parseInt(aux[2]);


            DIAS -= dateToInt(mes,dia,ano);

            if(lavoura.getPeriodo() - DIAS <= 5)
            {
                Toast.makeText(VerLavouraActivity.this, "Atualizamos o status da lavoura, pois esta estava no fim do período de tratamento", Toast.LENGTH_LONG).show();
                TrazFeedback tr = new TrazFeedback();
                Feedback trab = tr.Traz(lavoura);

                if(trab.getNome() == "SIM!")
                {
                    lavoura.setStatus("Vermelho");
                    q = "Em perigo";
                    status.setTextColor(Color.RED);
                    produto.setVisibility(View.VISIBLE);
                }
                else
                {
                    status.setTextColor(Color.GREEN);
                    q = "Sem perigo";
                    lavoura.setStatus("Verde");
                }
                new LavouraDAO().atualizarLavoura(lavoura);
            }
            else
            {
                dias.setVisibility(View.VISIBLE);

                Integer ans = lavoura.getPeriodo() - DIAS;

                dias.setText(dias.getText() + ans.toString());

                q = "Em tratamento";
                status.setTextColor(Color.YELLOW);
            }
        }
        status.setText(q);
    }
    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.button)
        {
            Intent intent = new Intent(VerLavouraActivity.this, LavourasActivity.class);
            intent.putExtra("user", getIntent().getSerializableExtra("user"));
            startActivity(intent);
        }
        else if(v.getId() == R.id.excluir)
        {

            LayoutInflater li = getLayoutInflater();
            View view = li.inflate(R.layout.confirmaremocao, null);

            view.findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    try
                    {
                        final ProgressDialog dialog2 = new ProgressDialog(VerLavouraActivity.this);
                        dialog2.setMessage("Excluindo...");
                        dialog2.setCanceledOnTouchOutside(false);
                        Thread t  = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                new LavouraDAO().removerLavoura((Lavoura)getIntent().getSerializableExtra("lavoura"));
                               // Toast.makeText(VerLavouraActivity.this, "Lavoura removida com sucesso!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(VerLavouraActivity.this, LavourasActivity.class);
                                intent.putExtra("user",getIntent().getSerializableExtra("user"));
                                startActivity(intent);
                                dialog2.dismiss();
                            }
                        });
                        t.start();
                        dialog2.show();


                    }catch (Exception e)
                    {
                        Toast.makeText(VerLavouraActivity.this, "Não foi possivel remover!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            });
            view.findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    //desfaz o alerta.
                    alerta.dismiss();
                }
            });
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
           // builder.setTitle("Remoção de Lavoura");
            builder.setView(view);
            alerta = builder.create();
            alerta.show();

        }
        else
        {
            Intent intent = new Intent(VerLavouraActivity.this, TratamentoActivity.class);
            intent.putExtra("user", getIntent().getSerializableExtra("user"));

            intent.putExtra("lavoura", getIntent().getSerializableExtra("lavoura"));

            startActivity(intent);
        }

    }

    public int dateToInt (int m, int d, int y){
        return
                1461 * (y + 4800 + (m - 14) / 12) / 4 +
                        367 * (m - 2 - (m - 14) / 12 * 12) / 12 -
                        3 * ((y + 4900 + (m - 14) / 12) / 100) / 4 +
                        d - 32075;
    }
}
