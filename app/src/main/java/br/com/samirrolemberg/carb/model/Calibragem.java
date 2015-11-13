package br.com.samirrolemberg.carb.model;

import java.util.Date;

/**
 * Created by Samir on 17/10/2015.
 */
public class Calibragem {


    private Integer id;
    private Integer audio;
    private Integer video;
    private String descricao;
    private String titulo;
    private Date dataCriacao;
    private Date ultimaAtualizacao;
    private Integer tipo;
    private Dispositivo dispositivo;

    public Calibragem() {
        super();
    }

    public Calibragem(Integer id, Integer audio, Integer video, String descricao, String titulo, Date dataCriacao, Date ultimaAtualizacao, Integer tipo, Dispositivo dispositivo) {
        super();
        this.id = id;
        this.audio = audio;
        this.video = video;
        this.descricao = descricao;
        this.titulo = titulo;
        this.dataCriacao = dataCriacao;
        this.ultimaAtualizacao = ultimaAtualizacao;
        this.tipo = tipo;
        this.dispositivo = dispositivo;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Calibragem{");
        sb.append("id=").append(id);
        sb.append(", audio=").append(audio);
        sb.append(", video=").append(video);
        sb.append(", descricao='").append(descricao).append('\'');
        sb.append(", titulo='").append(titulo).append('\'');
        sb.append(", dataCriacao=").append(dataCriacao);
        sb.append(", ultimaAtualizacao=").append(ultimaAtualizacao);
        sb.append(", tipo=").append(tipo);
        sb.append(", dispositivo=").append(dispositivo);
        sb.append('}');
        return sb.toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAudio() {
        return audio;
    }

    public void setAudio(Integer audio) {
        this.audio = audio;
    }

    public Integer getVideo() {
        return video;
    }

    public void setVideo(Integer video) {
        this.video = video;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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

    public Dispositivo getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
    }
}
