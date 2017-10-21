package br.edu.ifsuldeminas.muz.alertaferrugem.dao;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Vector;

import br.edu.ifsuldeminas.muz.alertaferrugem.model.Feedback;

/**
 * Created by samuel on 19/06/2017.
 */

public class FeedbackDAO
{

    private static  final String URL = "http://ferrugem.azurewebsites.net/services/FeedbackDAO?wsdl";
    private static  final String NAMESPACE = "http://utils.ifsuldeminas.edu.br";

    private static final String BUSCAR_TODOS = "buscarTodos";

    public static ArrayList<Feedback> buscarTodos()
    {
        ArrayList<Feedback> lista = new ArrayList<>();

        SoapObject buscaFeedback = new SoapObject(NAMESPACE,BUSCAR_TODOS);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.implicitTypes = true;

        HttpTransportSE http = new HttpTransportSE(URL);


        try {
            http.call("urn:" + BUSCAR_TODOS,envelope);


            Vector<SoapObject> resposta = (Vector<SoapObject>)envelope.getResponse();

            for(SoapObject s : resposta)
            {

                Feedback usr = new Feedback();
                usr.setNome(s.getProperty("nome").toString());
                usr.setObs(s.getProperty("obs").toString());
                usr.setID(Integer.parseInt(s.getProperty("ID").toString()));


                lista.add(usr);

            }

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
        return lista;
    }
}
