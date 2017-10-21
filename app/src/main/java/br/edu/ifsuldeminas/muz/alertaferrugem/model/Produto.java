package br.edu.ifsuldeminas.muz.alertaferrugem.model;

/**
 * Created by samuel on 14/07/2017.
 */

public class Produto
{

    private Integer ID;
    private String nome;
    private Integer periodo;
    private String obs;
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
    public Integer getPeriodo() {
        return periodo;
    }
    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }
    public String getObs() {
        return obs;
    }
    public void setObs(String obs) {
        this.obs = obs;
    }

    @Override
    public String toString() {
        return getNome();
    }
}
