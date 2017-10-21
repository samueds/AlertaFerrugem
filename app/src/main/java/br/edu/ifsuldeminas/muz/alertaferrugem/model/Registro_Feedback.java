package br.edu.ifsuldeminas.muz.alertaferrugem.model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * Created by samuel on 19/06/2017.
 */

public class Registro_Feedback implements Serializable, KvmSerializable
{
    private Integer ID;
    private String obs;
    private Double latitude;
    private Double longitude;
    private Feedback feed;
    private Usuario usu;
    private EstacaoMet est;

    public Integer getID() {
        return ID;
    }
    public void setID(Integer iD) {
        ID = iD;
    }
    public String getObs() {
        return obs;
    }
    public void setObs(String obs) {
        this.obs = obs;
    }
    public Double getLatitude() {
        return latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public Double getLongitude() {
        return longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    public Feedback getFeed() {
        return feed;
    }
    public void setFeed(Feedback feed) {
        this.feed = feed;
    }
    public Usuario getUsu() {
        return usu;
    }
    public void setUsu(Usuario usu) {
        this.usu = usu;
    }
    public EstacaoMet getEst() {
        return est;
    }
    public void setEst(EstacaoMet est) {
        this.est = est;
    }

    @Override
    public Object getProperty(int index)
    {
        switch (index) {
            case 0:
                return ID;
            case 1:
                return obs;
            case 2:
                return latitude;
            case 3:
                return longitude;
            case 4:
                return feed;
            case 5:
                return usu;
            case 6:
                return est;
        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 7;
    }

    @Override
    public void setProperty(int i, Object o) {

    }

    @SuppressWarnings("rawtypes")
    public void getPropertyInfo(int index, Hashtable properties, PropertyInfo pi)
    {
        String name = null;
        Class<?> type = null;

        switch (index) {
            case 0:
                type = Integer.class;
                name = "ID";
            case 1:
                type = String.class;
                name = "obs";
            case 2:
                type = Double.class;
                name = "latitude";
            case 3:
                type = Double.class;
                name = "longitude";
            case 4:
                type = Feedback.class;
                name = "feed";
            case 5:
                type = Usuario.class;
                name = "usu";
            case 6:
                type = EstacaoMet.class;
                name = "est";

        }

        pi.type = type;
        pi.name = name;
    }
}
