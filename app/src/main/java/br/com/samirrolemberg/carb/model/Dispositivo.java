package br.com.samirrolemberg.carb.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Samir on 17/10/2015.
 */
public class Dispositivo implements Serializable {

    private Integer id;
    private String nome;
    private Date dataCriacao;
    private Date ultimaAtualizacao;
    private Integer tipo;
    private List<Calibragem> calibragens;


    public Dispositivo() {
        super();
    }

    public Dispositivo(Integer id, String nome, Date dataCriacao, Date ultimaAtualizacao, Integer tipo, List<Calibragem> calibragens) {
        super();
        this.id = id;
        this.nome = nome;
        this.dataCriacao = dataCriacao;
        this.ultimaAtualizacao = ultimaAtualizacao;
        this.tipo = tipo;
        this.calibragens = calibragens;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Dispositivo{");
        sb.append("id=").append(id);
        sb.append(", nome='").append(nome).append('\'');
        sb.append(", dataCriacao=").append(dataCriacao);
        sb.append(", ultimaAtualizacao=").append(ultimaAtualizacao);
        sb.append(", tipo=").append(tipo);
        sb.append(", calibragens=").append(calibragens);
        sb.append('}');
        return sb.toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(Date ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public List<Calibragem> getCalibragens() {
        return calibragens;
    }

    public void setCalibragens(List<Calibragem> calibragens) {
        this.calibragens = calibragens;
    }
}
