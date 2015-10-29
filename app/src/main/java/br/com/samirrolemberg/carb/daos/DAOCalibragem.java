package br.com.samirrolemberg.carb.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.samirrolemberg.carb.conn.DatabaseManager;
import br.com.samirrolemberg.carb.model.Calibragem;
import br.com.samirrolemberg.carb.model.Dispositivo;

public class DAOCalibragem extends DAO{

	public final static String TABLE = "calibragem";
	private SQLiteDatabase database = null;

	public DAOCalibragem(Context context) {
		super(context);
		database = DatabaseManager.getInstance().openDatabase();
	}
	
	public long inserir(Calibragem calibragem, long idDispositivo){
		ContentValues values = new ContentValues();
		values.put("audio", calibragem.getAudio());
		values.put("video", calibragem.getVideo());
		values.put("descricao", calibragem.getDescricao());
		values.put("titulo", calibragem.getTitulo());
		values.put("dataCriacao", calibragem.getDataCriacao()==null?null:calibragem.getDataCriacao().getTime());
		values.put("tipo", calibragem.getTipo());
		values.put("idDispositivo", idDispositivo);

		long id = database.insert(TABLE, null, values);
		
		return id;
		
	}

	public Calibragem buscar(Long id, Dispositivo dis){
		Calibragem calibragem = null;
		try {
			String[] args = {id.toString()};
			StringBuffer sql = new StringBuffer();
			sql.append("select * from "+TABLE+" where id = ?");
			Cursor cursor = database.rawQuery(sql.toString(), args);
			if (cursor.moveToNext()) {
				calibragem = new Calibragem.Builder()
						.withId(cursor.getInt(cursor.getColumnIndex("id")))
						.withTipo(cursor.getInt(cursor.getColumnIndex("tipo")))
						.withTitulo(cursor.getString(cursor.getColumnIndex("titulo")))
						.withDescricao(cursor.getString(cursor.getColumnIndex("descricao")))
						.withAudio(cursor.getInt(cursor.getColumnIndex("audio")))
						.withVideo(cursor.getInt(cursor.getColumnIndex("video")))
						.withDispositivo(dis)
						.withDataCriacao(new Date(cursor.getLong(cursor.getColumnIndex("dataCriacao"))))
						.withUltimaAtualizacao(new Date(cursor.getLong(cursor.getColumnIndex("ultimaAtualizacao"))))
						.build();
			}
			cursor.close();
		} catch (Exception e) {
			Log.i("DAOs", e.getLocalizedMessage(),e);
		}
		return calibragem;
	}

	public List<Calibragem> listarTudo(Dispositivo dispositivo){
		List<Calibragem> calibragens = new ArrayList<>();
		try {
			String[] args = {dispositivo.getId()+""};
			StringBuffer sql = new StringBuffer();
			sql.append("select * from "+TABLE+" where idDispositivo = ? order by dataCriacao asc");
			Cursor cursor = database.rawQuery(sql.toString(), args);
			while (cursor.moveToNext()) {
				Calibragem calibragem = new Calibragem.Builder()
						.withAudio(cursor.getInt(cursor.getColumnIndex("audio")))
						.withDataCriacao(new Date(cursor.getLong(cursor.getColumnIndex("dataCriacao"))))
						.withDescricao(cursor.getString(cursor.getColumnIndex("descricao")))
						.withDispositivo(dispositivo)
						.withId(cursor.getInt(cursor.getColumnIndex("id")))
						.withTipo(cursor.getInt(cursor.getColumnIndex("tipo")))
						.withTitulo(cursor.getString(cursor.getColumnIndex("titulo")))
						.withVideo(cursor.getInt(cursor.getColumnIndex("video")))
						.build();

				calibragens.add(calibragem);
			}
			cursor.close();
		} catch (Exception e) {
			Log.i("DAOs", e.getLocalizedMessage(),e);
		}
		return calibragens;
	}

    public long size(Dispositivo dispositivo){
		long resultado = 0;
		try {
			String[] args = {dispositivo.getId()+""};
			StringBuffer sql = new StringBuffer();
			sql.append("select count(id) total from "+TABLE+" where idDispositivo = ?");
			Cursor cursor = database.rawQuery(sql.toString(), args);
			if (cursor.moveToNext()) {
				resultado = cursor.getLong(cursor.getColumnIndex("total"));
			}
			cursor.close();
		} catch (Exception e) {
			Log.i("DAOs", e.getLocalizedMessage(),e);
		}
		
		return resultado;
	}

	public int remover(Calibragem calibragem){
		String[] args = {calibragem.getId()+""};
		return database.delete(TABLE, "id=?", args);
	}


	public long atualiza(Calibragem calibragem) {
		ContentValues values = new ContentValues();

		values.put("audio", calibragem.getAudio());
		values.put("video", calibragem.getVideo());
		values.put("descricao", calibragem.getDescricao());
		values.put("titulo", calibragem.getTitulo());
		values.put("ultimaAtualizacao", calibragem.getUltimaAtualizacao()==null?null:calibragem.getUltimaAtualizacao().getTime());
		values.put("tipo", calibragem.getTipo());

		String[] args = {calibragem.getId()+""};

		return database.update(TABLE, values, "id = ?", args);
	}

}
