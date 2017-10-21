package br.edu.ifsuldeminas.muz.alertaferrugem.model;


import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.io.Serializable;
import java.util.Hashtable;

public class Usuario implements Serializable , KvmSerializable
{

    private Integer ID;
    private String nome;
    private String email;
    private String senha;


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
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    public String getSenha()
    {
        return senha;
    }
    public void setSenha(String senha)
    {
        this.senha = senha;
    }


    @Override
    public String toString() {
        return "Usuario{" +
                "ID=" + ID +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                '}';
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
                return email;
            case 3:
                return senha;
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
                name = "nome";
            case 2:
                type = String.class;
                name = "email";
            case 3:
                type = String.class;
                name = "senha";
        }

        pi.type = type;
        pi.name = name;
    }
}

