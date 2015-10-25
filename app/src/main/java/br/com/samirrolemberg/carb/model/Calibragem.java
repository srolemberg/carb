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

    private Calibragem(Builder builder) {
        id = builder.id;
        audio = builder.audio;
        video = builder.video;
        descricao = builder.descricao;
        titulo = builder.titulo;
        dataCriacao = builder.dataCriacao;
        ultimaAtualizacao = builder.ultimaAtualizacao;
        tipo = builder.tipo;
        dispositivo = builder.dispositivo;
    }

    @Override
    public String toString() {
        return "Calibragem{" +
                "id=" + id +
                ", audio=" + audio +
                ", video=" + video +
                ", descricao='" + descricao + '\'' +
                ", titulo='" + titulo + '\'' +
                ", dataCriacao=" + dataCriacao +
                ", ultimaAtualizacao=" + ultimaAtualizacao +
                ", tipo=" + tipo +
                ", dispositivo=" + dispositivo +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public Integer getAudio() {
        return audio;
    }

    public Integer getVideo() {
        return video;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public Date getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public Integer getTipo() {
        return tipo;
    }

    public Dispositivo getDispositivo() {
        return dispositivo;
    }


    public static final class Builder {
        private Integer id;
        private Integer audio;
        private Integer video;
        private String descricao;
        private String titulo;
        private Date dataCriacao;
        private Date ultimaAtualizacao;
        private Integer tipo;
        private Dispositivo dispositivo;

        public Builder() {
        }

        public Builder withId(Integer val) {
            id = val;
            return this;
        }

        public Builder withAudio(Integer val) {
            audio = val;
            return this;
        }

        public Builder withVideo(Integer val) {
            video = val;
            return this;
        }

        public Builder withDescricao(String val) {
            descricao = val;
            return this;
        }

        public Builder withTitulo(String val) {
            titulo = val;
            return this;
        }

        public Builder withDataCriacao(Date val) {
            dataCriacao = val;
            return this;
        }

        public Builder withUltimaAtualizacao(Date val) {
            ultimaAtualizacao = val;
            return this;
        }

        public Builder withTipo(Integer val) {
            tipo = val;
            return this;
        }

        public Builder withDispositivo(Dispositivo val) {
            dispositivo = val;
            return this;
        }

        public Calibragem build() {
            return new Calibragem(this);
        }
    }
}
