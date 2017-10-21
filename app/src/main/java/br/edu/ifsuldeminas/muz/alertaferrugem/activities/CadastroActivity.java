package br.edu.ifsuldeminas.muz.alertaferrugem.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.edu.ifsuldeminas.muz.alertaferrugem.R;
import br.edu.ifsuldeminas.muz.alertaferrugem.model.Usuario;
import br.edu.ifsuldeminas.muz.alertaferrugem.dao.UsuarioDAO;

public class CadastroActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText nome;
    private EditText email;
    private EditText senha;
    private Button Cadastrar;
    MediaPlayer sound;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        nome = (EditText) findViewById(R.id.camponome);
        email = (EditText) findViewById(R.id.campoemail);
        senha = (EditText) findViewById(R.id.camposenha);

        sound = MediaPlayer.create(this, R.raw.button_29);


        Button Cadastrar = (Button) findViewById(R.id.botaoCadastrar);
        Cadastrar.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        sound.start();

        Usuario u = new Usuario();

        u.setNome(nome.getText().toString());
        u.setEmail(email.getText().toString());
        u.setSenha(senha.getText().toString());

        if(u.getNome().length() == 0 || u.getSenha().length() == 0 || u.getEmail().length() == 0) {
            Toast.makeText(CadastroActivity.this, "Preenha todos os campos por favor!", Toast.LENGTH_LONG).show();
        }
        else if(!isValidEmail(email.getText().toString()))
        {
            Toast.makeText(CadastroActivity.this, "E-mail inválido!", Toast.LENGTH_LONG).show();
        }
        else if(u.getSenha().length() < 5)
        {
            Toast.makeText(CadastroActivity.this, "Senha deve conter ao menos 5 caracteres!", Toast.LENGTH_LONG).show();

        }
        else
        {
            final Usuario g = u;

            final ProgressDialog dialog = new ProgressDialog(CadastroActivity.this);

            dialog.setMessage("Cadastrando...");
            dialog.setIndeterminate(false);
            dialog.setCanceledOnTouchOutside(false);

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    Integer q = 3;
                    boolean exce = false;

                    try
                    {

                        q = UsuarioDAO.inserirUsuario(g);
                        System.out.println(q);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    final Integer ans = q;

                    dialog.dismiss();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(ans == 1)
                            {
                                Toast.makeText(CadastroActivity.this, "Cadastro Feito com Sucesso!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(CadastroActivity.this, TelaInicialActivity.class);
                                startActivity(intent);
                            }
                            else if(ans == 2)
                            {
                                Toast.makeText(CadastroActivity.this, "E-mail já existente!", Toast.LENGTH_LONG).show();
                            }
                            else
                                Toast.makeText(CadastroActivity.this, "Falha ao cadastrar, tente novamente!", Toast.LENGTH_LONG).show();
                        }
                    });

                }
            });
            t.start();
            dialog.show();
        }


    }
    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
