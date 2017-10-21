package br.edu.ifsuldeminas.muz.alertaferrugem.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import br.edu.ifsuldeminas.muz.alertaferrugem.dao.MetereologiaDiariaDAO;
import br.edu.ifsuldeminas.muz.alertaferrugem.model.Feedback;
import br.edu.ifsuldeminas.muz.alertaferrugem.model.Lavoura;
import br.edu.ifsuldeminas.muz.alertaferrugem.dao.LavouraDAO;
import br.edu.ifsuldeminas.muz.alertaferrugem.R;
import br.edu.ifsuldeminas.muz.alertaferrugem.model.MetereologiaDiaria;
import br.edu.ifsuldeminas.muz.alertaferrugem.model.Usuario;
import br.edu.ifsuldeminas.muz.alertaferrugem.utils.AdapterListaLavouras;
import br.edu.ifsuldeminas.muz.alertaferrugem.utils.TrazFeedback;

public class LavourasActivity extends AppCompatActivity implements View.OnClickListener{


    private Button cadastrar;
    private Button atualizar;
    private List<Lavoura> atualizaLavs,lavouras;
    ListView listaLavouras;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lavouras);

        cadastrar = (Button) findViewById(R.id.btnCadastraLavoura);
        cadastrar.setOnClickListener(this);

        atualizar = (Button) findViewById(R.id.btnAtualizar);
        atualizar.setOnClickListener(this);

        listaLavouras = (ListView) findViewById(R.id.lista);
        Usuario usu = (Usuario)  getIntent().getSerializableExtra("user");


        final ProgressDialog dialog2 = new ProgressDialog(LavourasActivity.this);
        dialog2.setMessage("Atualizando perfil...");
        dialog2.setCanceledOnTouchOutside(false);

        final String g = usu.getID().toString();
        //final List<Lavoura> lavouras = LavouraDAO.buscarTodos(usu.getID().toString());


        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                final List <Lavoura > lavouras2 = new LavouraDAO().buscarTodos(g);
                lavouras = lavouras2;
                atualizaLavs = lavouras2;
                dialog2.dismiss();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {



                        atualizaLavs = lavouras;

                        TextView msg = (TextView) findViewById(R.id.msg);

                        if(lavouras == null)
                        {
                            msg.setText("Usuário não possui lavouras");
                            atualizar.setVisibility(View.INVISIBLE);
                        }
                        else
                        {
                            AdapterListaLavouras adapter = new AdapterListaLavouras(lavouras, LavourasActivity.this);
                            listaLavouras.setAdapter(adapter);

                            listaLavouras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                                {
                                    //  Toast.makeText(LavourasActivity.this,lavouras.get(i).getID().toString(), Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(LavourasActivity.this, VerLavouraActivity.class);
                                    Bundle extras = getIntent().getExtras();
                                    if(extras != null)
                                    {
                                        intent.putExtra("user", getIntent().getSerializableExtra("user"));
                                        intent.putExtra("lavoura",  (Serializable) lavouras.get(i));
                                    }
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                });
            }

        });
        t2.start();
        dialog2.show();



    }

    @Override
    public void onClick(View v)
    {
            if(v.getId() == R.id.btnCadastraLavoura)
            {
                Intent intent = new Intent(this, CadastroLavouraActivity.class);
                Bundle extras = getIntent().getExtras();
                if(extras != null)
                    intent.putExtra("user", getIntent().getSerializableExtra("user"));
                startActivity(intent);
            }
            else if(v.getId() == R.id.btnAtualizar)
            {

                final ProgressDialog dialog = new ProgressDialog(LavourasActivity.this);

                dialog.setMessage("Atualizando...");
                dialog.setIndeterminate(false);

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(Lavoura g : atualizaLavs)
                        {
                            if(g.getStatus() != "Amarelo")
                            {
                                Feedback mito = new TrazFeedback().Traz(g);
                                if(mito.getNome().equals("SIM!") && g.getStatus().equals("Verde"))
                                {
                                    g.setStatus("Vermelho");
                                    new LavouraDAO().atualizarLavoura(g);
                                }
                                else if(mito.getNome().equals("Não!") && g.getStatus().equals("Vermelho"))
                                {
                                    g.setStatus("Verde");
                                    new LavouraDAO().atualizarLavoura(g);
                                }
                            }
                        }
                        dialog.dismiss();
                        Intent intent = new Intent(LavourasActivity.this, LavourasActivity.class);
                        Bundle extras = getIntent().getExtras();
                        if(extras != null)
                        {
                            intent.putExtra("user", getIntent().getSerializableExtra("user"));
                        }
                        startActivity(intent);
                    }
                });
                t.start();
                dialog.show();

            }
    }
}
