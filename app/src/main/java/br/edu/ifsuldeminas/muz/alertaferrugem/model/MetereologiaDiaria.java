package br.edu.ifsuldeminas.muz.alertaferrugem.model;

import java.util.Calendar;

public class MetereologiaDiaria
{


    private Integer ID;

    private Calendar dtCadastro = Calendar.getInstance();
    private Double umidade;
    private Double temperatura;

    private EstacaoMet estacao;
    public EstacaoMet getEstacao() {
        return estacao;
    }
    public void setEstacao(EstacaoMet estacao) {
        this.estacao = estacao;
    }


    public Integer getID() {
        return ID;
    }
    public void setID(Integer iD) {
        ID = iD;
    }
    public Calendar getDtCadastro() {
        return dtCadastro;
    }
    public void setDtCadastro(Calendar dtCadastro) {
        this.dtCadastro = dtCadastro;
    }
    public Double getUmidade() {
        return umidade;
    }
    public void setUmidade(Double umidade) {
        this.umidade = umidade;
    }
    public Double getTemperatura() {
        return temperatura;
    }
    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }

}
