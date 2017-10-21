package br.edu.ifsuldeminas.muz.alertaferrugem.dao;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Vector;

import br.edu.ifsuldeminas.muz.alertaferrugem.model.EstacaoMet;

/**
 * Created by samuel on 07/06/2017.
 */

public class EstacaoMetDAO
{
    private static  final String URL = "http://ferrugem.azurewebsites.net/services/EstacaoMetDAO?wsdl";
    private static  final String NAMESPACE = "http://utils.ifsuldeminas.edu.br";


    private static final String BUSCAR_TODOS = "buscarTodos";


    public static ArrayList<EstacaoMet> buscarTodos()
    {
        ArrayList<EstacaoMet> lista = new ArrayList<EstacaoMet>();

        SoapObject buscaUsuarios = new SoapObject(NAMESPACE,BUSCAR_TODOS);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.implicitTypes = true;

        HttpTransportSE http = new HttpTransportSE(URL);


        try {
            http.call("urn:" + BUSCAR_TODOS,envelope);


            Vector<SoapObject> resposta = (Vector<SoapObject>)envelope.getResponse();

            for(SoapObject s : resposta)
            {

                EstacaoMet usr = new EstacaoMet();
                usr.setNome((s.getProperty("nome").toString()));
                usr.setCidade((s.getProperty("cidade").toString()));
                usr.setEstado((s.getProperty("estado").toString()));
                usr.setID(Integer.parseInt(s.getProperty("ID").toString()));
                usr.setLatitude(Double.parseDouble(s.getProperty("latitude").toString()));
                usr.setLongitude(Double.parseDouble(s.getProperty("longitude").toString()));

                lista.add(usr);

            }

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
        return lista;
    }


}
