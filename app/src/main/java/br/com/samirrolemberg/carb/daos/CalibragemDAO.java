package br.com.samirrolemberg.carb.daos;

import java.util.ArrayList;
import java.util.List;

import br.com.samirrolemberg.carb.model.Calibragem;
import br.com.samirrolemberg.carb.model.Dispositivo;
import br.com.samirrolemberg.carb.utils.Coluna;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;


/**
 * Created by srolemberg on 09/05/16.
 */
public class CalibragemDAO {

	private Realm realm;


	public CalibragemDAO(Realm realm) {
		super();
		this.realm = realm;
	}


	public List<Calibragem> findAll() {
		RealmResults<Calibragem> results = realm.where(Calibragem.class)
			.findAllSorted(Coluna.Calibragem.DATA_CRIACAO, Sort.ASCENDING);
		List<Calibragem> lista = new ArrayList<>();
		if (results.size() > 0) {
			for (int i = 0; i < results.size(); i++) {
				lista.add(results.get(i));
			}
		}
		return lista;
	}


	public List<Calibragem> findAllByDispositivo(Dispositivo dispositivo) {
		RealmResults<Calibragem> results = realm.where(Calibragem.class)
			.equalTo(Coluna.Dispositivo.ID, dispositivo.getId())
			.findAllSorted(Coluna.Calibragem.DATA_CRIACAO, Sort.ASCENDING);
		List<Calibragem> lista = new ArrayList<>();
		if (results.size() > 0) {
			for (int i = 0; i < results.size(); i++) {
				lista.add(results.get(i));
			}
		}
		return lista;
	}

	public boolean remove(Calibragem calibragem){
		RealmResults<Calibragem> results = realm.where(Calibragem.class)
				.equalTo(Coluna.Calibragem.ID, calibragem.getId())
				.findAll();
		return results.deleteFirstFromRealm();
	}
}
