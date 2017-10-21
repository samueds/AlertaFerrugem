package br.edu.ifsuldeminas.muz.alertaferrugem.model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.Hashtable;

/**
 * Created by samuel on 12/07/2017.
 */

public class Lavoura implements Serializable, KvmSerializable
{
    private Integer ID;
    private String nome;
    private Double latitude;
    private Double longitude;
    private String sta;
    private Date dataPrev;
    private Integer periodo;
    private Boolean altaCarga;

    public Date getDataPrev() {
        return dataPrev;
    }

    public void setDataPrev(Date date) {
        this.dataPrev= date;
    }

    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }

    public Boolean getAltaCarga() {
        return altaCarga;
    }

    public void setAltaCarga(Boolean altaCarga) {
        this.altaCarga = altaCarga;
    }

    public String getStatus() {
        return sta;
    }

    public void setStatus(String status) {
        this.sta = status;
    }
    private Usuario usu;

    private EstacaoMet est;

    public String getSta() {
        return sta;
    }

    public void setSta(String sta) {
        this.sta = sta;
    }

    public EstacaoMet getEst() {
        return est;
    }

    public void setEst(EstacaoMet est) {
        this.est = est;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer iD) {
        ID = iD;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public Usuario getUsu() {
        return usu;
    }

    public void setUsu(Usuario usu) {
        this.usu = usu;
    }

    @Override
    public String toString() {
        return "->" + getNome();
    }

    @Override
    public Object getProperty(int i) {
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 0;
    }

    @Override
    public void setProperty(int i, Object o) {

    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

    }
}
