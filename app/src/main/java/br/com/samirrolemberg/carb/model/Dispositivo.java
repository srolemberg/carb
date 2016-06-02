package br.com.samirrolemberg.carb.model;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * Created by Samir on 17/10/2015.
 */
@RealmClass
public class Dispositivo implements Serializable, RealmModel {

	@PrimaryKey
	@Required
	private String id;
	@Required
	private String nome;
	@Required
	private Date dataCriacao;
	private Date ultimaAtualizacao;
	@Required
	private Integer tipo;
	private RealmList<Calibragem> calibragens;


	public Dispositivo() {
		super();
	}


	public Dispositivo(String id, String nome, Date dataCriacao, Date ultimaAtualizacao, Integer tipo, RealmList<Calibragem> calibragens) {
		this.id = id;
		this.nome = nome;
		this.dataCriacao = dataCriacao;
		this.ultimaAtualizacao = ultimaAtualizacao;
		this.tipo = tipo;
		this.calibragens = calibragens;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
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


	public void setCalibragens(RealmList<Calibragem> calibragens) {
		this.calibragens = calibragens;
	}


	@Override public String toString() {
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


	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Dispositivo that = (Dispositivo) o;
		if (!id.equals(that.id))
			return false;
		if (!nome.equals(that.nome))
			return false;
		if (!dataCriacao.equals(that.dataCriacao))
			return false;
		if (ultimaAtualizacao != null ? !ultimaAtualizacao.equals(that.ultimaAtualizacao) : that.ultimaAtualizacao != null)
			return false;
		if (!tipo.equals(that.tipo))
			return false;
		return calibragens != null ? calibragens.equals(that.calibragens) : that.calibragens == null;
	}


	@Override public int hashCode() {
		int result = id.hashCode();
		result = 31 * result + nome.hashCode();
		result = 31 * result + dataCriacao.hashCode();
		result = 31 * result + (ultimaAtualizacao != null ? ultimaAtualizacao.hashCode() : 0);
		result = 31 * result + tipo.hashCode();
		result = 31 * result + (calibragens != null ? calibragens.hashCode() : 0);
		return result;
	}
}
