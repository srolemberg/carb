package br.com.samirrolemberg.carb.daos;

import br.com.samirrolemberg.carb.model.Dispositivo;
import br.com.samirrolemberg.carb.utils.Coluna;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by srolemberg on 09/05/16.
 */
public class DispositivoDAO {

	private Realm realm;


	public DispositivoDAO(Realm realm) {
		super();
		this.realm = realm;
	}


	public List<Dispositivo> findAll() {
		RealmResults<Dispositivo> results = realm.where(Dispositivo.class).findAllSorted(Coluna.Dispositivo.DATA_CRIACAO, Sort.ASCENDING);
		List<Dispositivo> lista = new ArrayList<>();
		if (results.size() > 0) {
			for (int i = 0; i < results.size(); i++) {
				lista.add(results.get(i));
			}
		}
		return lista;
	}
}
