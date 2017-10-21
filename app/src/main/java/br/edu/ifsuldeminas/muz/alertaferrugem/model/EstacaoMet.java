package br.edu.ifsuldeminas.muz.alertaferrugem.model;


import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.io.Serializable;
import java.util.Hashtable;

public class EstacaoMet implements Serializable, KvmSerializable
{

    private Integer ID;
    private String nome;
    private Double latitude,longitude;
    private String cidade;
    private String estado;

    @Override
    public String toString() {
        return "EstacaoMet{" +
                "ID=" + ID +
                ", nome='" + nome + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", cidade='" + cidade + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }



    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }



    public Integer getID() {
        return ID;
    }

    public String getNome() {
        return nome;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    @Override
    public Object getProperty(int index)
    {
        switch (index) {
            case 0:
                return ID;
            case 1:
                return cidade;
            case 2:
                return estado;
            case 3:
                return nome;
        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 4;
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
                name = "cidade";
            case 2:
                type = String.class;
                name = "estado";
            case 3:
                type = String.class;
                name = "nome";



        }

        pi.type = type;
        pi.name = name;
    }

}

