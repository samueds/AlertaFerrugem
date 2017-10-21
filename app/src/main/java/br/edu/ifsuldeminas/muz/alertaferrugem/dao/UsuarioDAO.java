package br.edu.ifsuldeminas.muz.alertaferrugem.dao;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import br.edu.ifsuldeminas.muz.alertaferrugem.model.Usuario;
import br.edu.ifsuldeminas.muz.alertaferrugem.utils.Utils;

public class UsuarioDAO
{

    private static  final String URL = "http://ferrugem.azurewebsites.net/services/UsuarioDAO?wsdl";
    private static  final String NAMESPACE = "http://utils.ifsuldeminas.edu.br";

    private static final String INSERIR = "inserirUsuario";
    private static final String BUSCAR_TODOS = "buscarTodosUsuarios";

    public static Integer inserirUsuario(Usuario u) throws Exception
    {

        SoapObject inserirUsuario = new SoapObject(NAMESPACE, INSERIR);
        SoapObject usr = new SoapObject(NAMESPACE, "u");

        usr.addProperty("email", u.getEmail());
        usr.addProperty("id", u.getID());
        usr.addProperty("nome", u.getNome());
        usr.addProperty("senha", Utils.toMD5(u.getSenha()));



        inserirUsuario.addSoapObject(usr);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(inserirUsuario);

        envelope.implicitTypes = true;

        HttpTransportSE http = new HttpTransportSE(URL);

        try {
            http.call("urn:" + INSERIR, envelope);

            SoapPrimitive resposta = (SoapPrimitive) envelope.getResponse();

            return Integer.parseInt(resposta.toString());

        } catch (IOException e ) {
            e.printStackTrace();

            return 3;
        }
        catch (XmlPullParserException e)
        {
            e.printStackTrace();
            return  3;
        }
    }
    public static ArrayList<Usuario> buscarTodosUsuarios() throws java.lang.NullPointerException
    {
        ArrayList<Usuario> lista = new ArrayList<Usuario>();

        SoapObject buscaUsuarios = new SoapObject(NAMESPACE,BUSCAR_TODOS);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.implicitTypes = true;

        HttpTransportSE http = new HttpTransportSE(URL);


        try {
            http.call("urn:" + BUSCAR_TODOS,envelope);


            Vector<SoapObject> resposta = (Vector<SoapObject>)envelope.getResponse();


            for(SoapObject s : resposta)
            {

                Usuario usr = new Usuario();
                usr.setNome((s.getProperty("email").toString()));
                usr.setID(Integer.parseInt(s.getProperty("ID").toString()));
                usr.setEmail(s.getProperty("nome").toString());

                usr.setSenha((s.getProperty("senha").toString()));

                lista.add(usr);

            }

        } catch (IOException e ) {
            e.printStackTrace();

            return null;
        }
        catch (XmlPullParserException e)
        {
            e.printStackTrace();
            return  null;
        }
        return lista;
    }


}
