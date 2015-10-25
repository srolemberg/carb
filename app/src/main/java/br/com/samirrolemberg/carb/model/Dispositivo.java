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

    private Dispositivo(Builder builder) {
        id = builder.id;
        nome = builder.nome;
        dataCriacao = builder.dataCriacao;
        ultimaAtualizacao = builder.ultimaAtualizacao;
        tipo = builder.tipo;
        calibragens = builder.calibragens;
    }

    @Override
    public String toString() {
        return "Dispositivo{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", dataCriacao=" + dataCriacao +
                ", ultimaAtualizacao=" + ultimaAtualizacao +
                ", tipo=" + tipo +
                ", calibragens=" + calibragens +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
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

    public List<Calibragem> getCalibragens() {
        return calibragens;
    }


    public static final class Builder {
        private Integer id;
        private String nome;
        private Date dataCriacao;
        private Date ultimaAtualizacao;
        private Integer tipo;
        private List<Calibragem> calibragens;

        public Builder() {
        }

        public Builder withId(Integer val) {
            id = val;
            return this;
        }

        public Builder withNome(String val) {
            nome = val;
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

        public Builder withCalibragens(List<Calibragem> val) {
            calibragens = val;
            return this;
        }

        public Dispositivo build() {
            return new Dispositivo(this);
        }
    }
}
