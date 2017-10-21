package br.edu.ifsuldeminas.muz.alertaferrugem.activities;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.edu.ifsuldeminas.muz.alertaferrugem.R;
import br.edu.ifsuldeminas.muz.alertaferrugem.dao.LavouraDAO;
import br.edu.ifsuldeminas.muz.alertaferrugem.dao.ProdutoDAO;
import br.edu.ifsuldeminas.muz.alertaferrugem.model.Lavoura;
import br.edu.ifsuldeminas.muz.alertaferrugem.model.Produto;

public class TratamentoActivity extends AppCompatActivity  implements View.OnClickListener
{
    private AlertDialog alerta;


    private Lavoura lavoura;
    private List<Produto> lista = new ProdutoDAO().buscarTodos();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tratamento);
        lavoura = (Lavoura) getIntent().getSerializableExtra("lavoura");

        Button volta = (Button) findViewById(R.id.btnBack);
        volta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TratamentoActivity.this, VerLavouraActivity.class);
                intent.putExtra("lavoura",getIntent().getSerializableExtra("lavoura"));
                intent.putExtra("user", getIntent().getSerializableExtra("user"));
                startActivity(intent);
            }
        });

        Button confirmar = (Button) findViewById(R.id.btnConfimar);
        confirmar.setOnClickListener(this);

        List<Produto> lista = new ProdutoDAO().buscarTodos();
        ListView listaProdutos = (ListView) findViewById(R.id.lista);

        ArrayAdapter<Produto> adapter = new ArrayAdapter<Produto>(this, android.R.layout.simple_list_item_1,lista);
        listaProdutos.setAdapter(adapter);

        listaProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                exemplo_layout(i);
            }
        });


    }

    private void exemplo_layout(int prod)
    {
        final int  q = prod;
        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.confirmatratamento, null);

        TextView periodo = (TextView) view.findViewById(R.id.periodo);
        TextView obs = (TextView) view.findViewById(R.id.obs);
        periodo.setText("Periodo de Tramento: " + lista.get(prod).getPeriodo().toString());

        if(lista.get(prod).getObs() == "")
        {
            obs.setText("");
        }
        else
            obs.setText(obs.getText() + lista.get(prod).getObs());


        //definimos para o botão do layout um clickListener
        view.findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //exibe um Toast informativo.

                try
                {
                    lavoura.setSta("Amarelo");
                    lavoura.setPeriodo(lista.get(q).getPeriodo());
                    java.util.Date dataUtil = new java.util.Date();

                    lavoura.setDataPrev(new java.sql.Date(dataUtil.getTime()));
                    new LavouraDAO().atualizarLavoura(lavoura);
                    Toast.makeText(TratamentoActivity.this, "Confirmado com sucesso", Toast.LENGTH_SHORT).show();
                    alerta.dismiss();
                    Intent i = new Intent(TratamentoActivity.this, LavourasActivity.class);
                    i.putExtra("user", getIntent().getSerializableExtra("user"));
                    startActivity(i);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                //desfaz o alerta.
              //  alerta.dismiss();
            }
        });

        view.findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //desfaz o alerta.
                alerta.dismiss();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Fazer tratamento com esse produto ?");
        builder.setView(view);
        alerta = builder.create();
        alerta.show();
    }

    @Override
    public void onClick(View view)
    {

    }
}
