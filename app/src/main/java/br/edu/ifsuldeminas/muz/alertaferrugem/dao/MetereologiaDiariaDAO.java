package br.edu.ifsuldeminas.muz.alertaferrugem.dao;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Vector;

import br.edu.ifsuldeminas.muz.alertaferrugem.model.MetereologiaDiaria;

public class MetereologiaDiariaDAO
{
    private static  final String URL = "http://ferrugem.azurewebsites.net/services/MetereologiaDiariaDAO?wsdl";
    private static  final String NAMESPACE = "http://utils.ifsuldeminas.edu.br";

    private static final String BUSCAR_TODOS = "buscarMetereologiaPorEst";

    public static ArrayList<MetereologiaDiaria> buscarTodos(String est)
    {
        ArrayList<MetereologiaDiaria> lista = new ArrayList<>();

        SoapObject buscaUsuarios = new SoapObject(NAMESPACE,BUSCAR_TODOS);
        SoapObject EST = new SoapObject(NAMESPACE, "u");

        EST.addProperty("sucesso", est);

        buscaUsuarios.addSoapObject(EST);

        System.out.println(est);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(buscaUsuarios);
        envelope.implicitTypes = true;
        envelope.setAddAdornments(false);

        HttpTransportSE http = new HttpTransportSE(URL);


        try {
            http.call("urn:" + BUSCAR_TODOS,envelope);


            Vector<SoapObject> resposta = (Vector<SoapObject>)envelope.getResponse();

            for(SoapObject s : resposta)
            {

                MetereologiaDiaria usr = new MetereologiaDiaria();
                usr.setUmidade(Double.parseDouble(s.getProperty("umidade").toString()));
                usr.setTemperatura(Double.parseDouble(s.getProperty("temperatura").toString()));
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
