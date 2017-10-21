package br.edu.ifsuldeminas.muz.alertaferrugem.activities;

import android.animation.StateListAnimator;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import java.io.Serializable;
import java.util.List;

import br.edu.ifsuldeminas.muz.alertaferrugem.R;
import br.edu.ifsuldeminas.muz.alertaferrugem.model.Usuario;
import br.edu.ifsuldeminas.muz.alertaferrugem.dao.UsuarioDAO;
import br.edu.ifsuldeminas.muz.alertaferrugem.utils.Utils;

public class TelaInicialActivity extends AppCompatActivity implements View.OnClickListener
{


    private Button botaoLogin;
    private Button botaoCadastrar;
    private EditText campoEmail;
    private EditText campoSenha;
    MediaPlayer sound;
    private List<Usuario> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        sound = MediaPlayer.create(this, R.raw.button_29);

        setContentView(R.layout.activity_tela_inicial);
        Button Login = (Button) findViewById(R.id.botaoLogar);
        Login.setOnClickListener(this);
        Button Cadastrar = (Button) findViewById(R.id.botaoCadastrar);
        Cadastrar.setOnClickListener(this);

        campoEmail = (EditText) findViewById(R.id.campoemail);
        campoSenha = (EditText) findViewById(R.id.camposenha);


        if(android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy =  new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

    }
    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.botaoLogar)
        {
            final ProgressDialog dialog  = new ProgressDialog(TelaInicialActivity.this);
            dialog.setMessage("Verificando login...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.setIndeterminate(true);

            sound.start();
            String email = campoEmail.getText().toString();
            String senha = campoSenha.getText().toString();

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    lista = UsuarioDAO.buscarTodosUsuarios();


                    TelaInicialActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run()
                        {
                            boolean cadastrado = false;
                            if (lista != null)
                            {
                                String email = campoEmail.getText().toString();
                                String senha = campoSenha.getText().toString();
                                senha = Utils.toMD5(senha);
                                Usuario user = null;

                                for(Usuario q : lista)
                                {


                                    System.out.println(q);
                                    if(q.getEmail().equals(email) && q.getSenha().equals(senha))
                                    {
                                        user = q;
                                        cadastrado = true;
                                        break;
                                    }
                                }
                                dialog.dismiss();
                                if(cadastrado)
                                {
                                    Toast.makeText(TelaInicialActivity.this, "Logado com sucesso!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), LavourasActivity.class);
                                    campoSenha.setText("");
                                    campoEmail.setText("");
                                    intent.putExtra("user", (Serializable) user);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Toast.makeText(TelaInicialActivity.this, "Login errado! Tente novamente", Toast.LENGTH_LONG).show();
                                }
                            }
                            else
                                Toast.makeText(TelaInicialActivity.this, "Impossivel logar, servidor OFF", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
            t.start();
            dialog.show();
        }
        else
        {
            sound.start();
            campoSenha.setText("");
            campoEmail.setText("");
            Intent intent = new Intent(TelaInicialActivity.this, CadastroActivity.class);
            startActivity(intent);
        }
    }


}
