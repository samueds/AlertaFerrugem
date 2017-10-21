package br.edu.ifsuldeminas.muz.alertaferrugem.model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * Created by samuel on 19/06/2017.
 */

public class Feedback implements Serializable, KvmSerializable
{
    private Integer ID;

    private String nome;
    private String obs;
    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }



    public Integer getID()
    {
        return ID;
    }

    public void setID(Integer iD)
    {
        ID = iD;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    @Override
    public Object getProperty(int index)
    {
        switch (index) {
            case 0:
                return ID;
            case 1:
                return nome;
            case 2:
                return obs;

        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 3;
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
                name = "nome";
            case 2:
                type = String.class;
                name = "obs";
        }

        pi.type = type;
        pi.name = name;
    }

}
