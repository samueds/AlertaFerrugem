package br.edu.ifsuldeminas.muz.alertaferrugem.dao;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Vector;

import br.edu.ifsuldeminas.muz.alertaferrugem.model.EstacaoMet;
import br.edu.ifsuldeminas.muz.alertaferrugem.model.Lavoura;
import br.edu.ifsuldeminas.muz.alertaferrugem.model.Usuario;

/**
 * Created by samuel on 12/07/2017.
 */

public class LavouraDAO
{
    private static  final String URL = "http://ferrugem.azurewebsites.net/services/LavouraDAO?wsdl";
    private static  final String NAMESPACE = "http://utils.ifsuldeminas.edu.br";

    private static final String BUSCAR_TODOS = "buscarTodasLavouras";
    private static final String INSERIR = "inserirLavoura";
    private static final String ATUALIZAR = "atualizar";
    private static final String REMOVER = "remover";


    public static ArrayList<Lavoura> buscarTodos(String usu)
    {
        ArrayList<Lavoura> lista = new ArrayList<>();

        SoapObject buscaLavouras = new SoapObject(NAMESPACE,BUSCAR_TODOS);
        SoapObject EST = new SoapObject(NAMESPACE, "y");

        EST.addProperty("sucesso", usu);

        buscaLavouras.addSoapObject(EST);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(buscaLavouras);
        envelope.implicitTypes = true;
        envelope.setAddAdornments(false);

        HttpTransportSE http = new HttpTransportSE(URL);


        try {
            http.call("urn:" + BUSCAR_TODOS,envelope);

            Vector<SoapObject> resposta = new Vector<SoapObject>();

            if(envelope.getResponse() == null)
                return null;
            else if(envelope.getResponse() instanceof  SoapObject)
                resposta.add((SoapObject) envelope.getResponse());
            else
                resposta = (Vector<SoapObject>) envelope.getResponse();


            for(SoapObject s : resposta)
            {

                Lavoura usr = new Lavoura();
                usr.setNome(s.getProperty("nome").toString());
                usr.setLatitude(Double.parseDouble(s.getProperty("latitude").toString()));
                usr.setLongitude(Double.parseDouble(s.getProperty("longitude").toString()));
                usr.setStatus(s.getProperty("sta").toString());
                usr.setID(Integer.parseInt(s.getProperty("ID").toString()));
            //    if(usr.getStatus().equals("Amarelo"))
                    usr.setDataPrev(Date.valueOf(s.getProperty("dataPrev").toString()));

                SoapObject q = (SoapObject) s.getProperty("usu");

                Integer aux = Integer.parseInt(q.getProperty(0).toString());

                Usuario us = new Usuario(); us.setID(aux);
                usr.setUsu(us);

                q = null;
                q = (SoapObject) s.getProperty("est");
                System.out.println("Estacao " + (SoapObject) s.getProperty("est"));
                aux =  Integer.parseInt(q.getProperty(0).toString());

                EstacaoMet es = new EstacaoMet(); es.setID(aux);

                usr.setEst(es);

                usr.setPeriodo(Integer.parseInt(s.getProperty("periodo").toString()));
                usr.setAltaCarga(Boolean.parseBoolean(s.getProperty("altaCarga").toString()));
                lista.add(usr);

            }

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
        return lista;
    }

    public static boolean inserirLavoura(Lavoura u)
    {

        SoapObject inserirLavoura = new SoapObject(NAMESPACE, INSERIR);
        SoapObject usr = new SoapObject(NAMESPACE, "u");

        u.setNome(u.getNome()+ ";" + u.getUsu().getID() + ";" + u.getEst().getID() + ";" + u.getLatitude() + ";" + u.getLongitude());
        u.setLatitude(null);
        u.setLongitude(null);

        usr.addProperty("nome", u.getNome());
        usr.addProperty("id", u.getID());
        usr.addProperty("altacarga", u.getAltaCarga());
        usr.addProperty("sta", u.getSta());
        if(u.getPeriodo() != null)
            usr.addProperty("periodo", u.getPeriodo());

        inserirLavoura.addSoapObject(usr);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(inserirLavoura);

        envelope.implicitTypes = true;

        HttpTransportSE http = new HttpTransportSE(URL);

        try {
            http.call("urn:" + INSERIR, envelope);

            SoapPrimitive resposta = (SoapPrimitive) envelope.getResponse();

            return Boolean.parseBoolean(resposta.toString());

        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    public static boolean removerLavoura(Lavoura u)
    {

        SoapObject inserirLavoura = new SoapObject(NAMESPACE, REMOVER);
        SoapObject usr = new SoapObject(NAMESPACE, "u");

        usr.addProperty("id", u.getID());

        inserirLavoura.addSoapObject(usr);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(inserirLavoura);

        envelope.implicitTypes = true;

        HttpTransportSE http = new HttpTransportSE(URL);

        try {
            http.call("urn:" + REMOVER, envelope);

            SoapPrimitive resposta = (SoapPrimitive) envelope.getResponse();

            return Boolean.parseBoolean(resposta.toString());

        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    public static boolean atualizarLavoura(Lavoura u)
    {

        SoapObject inserirLavoura = new SoapObject(NAMESPACE, ATUALIZAR);
        SoapObject usr = new SoapObject(NAMESPACE, "u");

        u.setNome(u.getNome()+ ";" + u.getUsu().getID() + ";" + u.getEst().getID() + ";" + u.getLatitude() + ";" + u.getLongitude()+ ";"+ u.getDataPrev().toString());
        u.setLatitude(null);
        u.setLongitude(null);

        usr.addProperty("nome", u.getNome());
        usr.addProperty("id", u.getID());
        usr.addProperty("altacarga", u.getAltaCarga());
        usr.addProperty("sta", u.getSta());
        usr.addProperty("periodo",u.getPeriodo());

        inserirLavoura.addSoapObject(usr);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(inserirLavoura);

        envelope.implicitTypes = true;

        HttpTransportSE http = new HttpTransportSE(URL);

        try {
            http.call("urn:" + ATUALIZAR, envelope);

            SoapPrimitive resposta = (SoapPrimitive) envelope.getResponse();

            return Boolean.parseBoolean(resposta.toString());

        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }
}
