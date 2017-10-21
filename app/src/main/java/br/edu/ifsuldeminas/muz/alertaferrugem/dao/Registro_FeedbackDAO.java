package br.edu.ifsuldeminas.muz.alertaferrugem.dao;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Vector;

import br.edu.ifsuldeminas.muz.alertaferrugem.model.EstacaoMet;
import br.edu.ifsuldeminas.muz.alertaferrugem.model.Feedback;
import br.edu.ifsuldeminas.muz.alertaferrugem.model.Registro_Feedback;
import br.edu.ifsuldeminas.muz.alertaferrugem.model.Usuario;

/**
 * Created by samuel on 19/06/2017.
 */

public class Registro_FeedbackDAO
{
    private static  final String URL = "http://ferrugem.azurewebsites.net/services/Registro_FeedbackDAO?wsdl";
    private static  final String NAMESPACE = "http://utils.ifsuldeminas.edu.br";

    private static final String INSERIR = "inserirRegistro";



    public static boolean inserirRegistro(Registro_Feedback u)
    {

        SoapObject inserirRegistro = new SoapObject(NAMESPACE, INSERIR);
        SoapObject usr = new SoapObject(NAMESPACE, "u");

        u.setObs(u.getObs() +";"+ u.getEst().getID() + ";" + u.getFeed().getID() + ";" + u.getUsu().getID() + ";" + u.getLatitude() + ";" + u.getLongitude());
        u.setLatitude(null);
        u.setLongitude(null);
        System.out.println(u.getObs());
        PropertyInfo pi = new PropertyInfo();
        pi.name = "Registro_Feedback";
        pi.setValue(u);
        pi.setType(Registro_Feedback.class);
        pi.setNamespace(NAMESPACE);
        usr.addProperty(pi);

        usr.addProperty("id", u.getID());
        usr.addProperty("latitude", 33);
        usr.addProperty("longitude",33);
        usr.addProperty("obs", u.getObs());

        pi = new PropertyInfo();
        pi.name = "usu";
        pi.setValue(u.getUsu());
        pi.setType(Usuario.class);
        pi.setNamespace(NAMESPACE);
        usr.addProperty(pi);



        pi = new PropertyInfo();
        pi.name = "feed";
        pi.setValue(u.getFeed());
        pi.setType(Feedback.class);
        pi.setNamespace(NAMESPACE);
        usr.addProperty(pi);


        pi = new PropertyInfo();
        pi.name = "est";
        pi.setValue(u.getEst());
        pi.setType(EstacaoMet.class);
        pi.setNamespace(NAMESPACE);
        usr.addProperty(pi);
        System.out.println(pi.getValue());
        System.out.println(usr);



        inserirRegistro.addSoapObject(usr);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(inserirRegistro);

        envelope.implicitTypes = true;

        HttpTransportSE http = new HttpTransportSE(URL);
        envelope.addMapping(NAMESPACE, "Registro_Feedback", Registro_Feedback.class);

        envelope.addMapping(NAMESPACE, "EstacaoMet", EstacaoMet.class);
        envelope.addMapping(NAMESPACE, "Feedback", Feedback.class);
        envelope.addMapping(NAMESPACE, "Usuario", Usuario.class);

        try {
            http.call("urn:" + INSERIR, envelope);

            SoapPrimitive resposta = (SoapPrimitive) envelope.getResponse();

            return Boolean.parseBoolean(resposta.toString());

        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

}
