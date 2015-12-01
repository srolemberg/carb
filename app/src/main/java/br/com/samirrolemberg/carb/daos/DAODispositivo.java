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
import br.com.samirrolemberg.carb.model.Dispositivo;

public class DAODispositivo extends DAO{

	public final static String TABLE = "dispositivo";
	public final static String TAG = "DAO"+DAODispositivo.TABLE;

	private SQLiteDatabase database = null;

	public DAODispositivo(Context context) {
		super(context);
		database = DatabaseManager.getInstance().openDatabase();
	}

	public long inserir(Dispositivo dispositivo){
		ContentValues values = new ContentValues();
		values.put("id", dispositivo.getId());
		values.put("nome", dispositivo.getNome());
		values.put("tipo", dispositivo.getTipo());
		values.put("dataCriacao", dispositivo.getDataCriacao() == null ? null : dispositivo.getDataCriacao().getTime());
		values.put("ultimaAtualizacao", dispositivo.getUltimaAtualizacao()==null?null:dispositivo.getUltimaAtualizacao().getTime());

		long id = database.insert(TABLE, null, values);
		
		return id;
	}

    public Dispositivo buscar(Long id){
		Dispositivo dispositivo = null;
        try {
            String[] args = {id.toString()};
            StringBuffer sql = new StringBuffer();
            sql.append("select * from "+TABLE+" where id = ?");
            Cursor cursor = database.rawQuery(sql.toString(), args);
            if (cursor.moveToNext()) {
                dispositivo = new Dispositivo();
                        dispositivo.setId(cursor.getInt(cursor.getColumnIndex("id")));
						dispositivo.setDataCriacao(new Date(cursor.getLong(cursor.getColumnIndex("dataCriacao"))));
						dispositivo.setNome(cursor.getString(cursor.getColumnIndex("nome")));
						dispositivo.setTipo(cursor.getInt(cursor.getColumnIndex("tipo")));
						dispositivo.setDataCriacao(cursor.getLong(cursor.getColumnIndex("dataCriacao")) > 0 ? new Date(cursor.getLong(cursor.getColumnIndex("dataCriacao"))) : null);
            }
            cursor.close();
        } catch (Exception e) {
			Log.i(TAG, e.getLocalizedMessage(),e);
        }
        return dispositivo;
    }

	public List<Dispositivo> listarTudo(){
		List<Dispositivo> dispositivos = new ArrayList<Dispositivo>();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select * from "+TABLE+" order by id asc ");
			Cursor cursor = database.rawQuery(sql.toString(), null);
			while (cursor.moveToNext()) {

				Dispositivo dispositivo = new Dispositivo();
						dispositivo.setId(cursor.getInt(cursor.getColumnIndex("id")));
						dispositivo.setNome(cursor.getString(cursor.getColumnIndex("nome")));
						dispositivo.setTipo(cursor.getInt(cursor.getColumnIndex("tipo")));
						dispositivo.setDataCriacao(cursor.getLong(cursor.getColumnIndex("dataCriacao")) > 0 ? new Date(cursor.getLong(cursor.getColumnIndex("dataCriacao"))) : null);
						dispositivo.setUltimaAtualizacao(cursor.getLong(cursor.getColumnIndex("ultimaAtualizacao")) > 0 ? new Date(cursor.getLong(cursor.getColumnIndex("ultimaAtualizacao"))) : null);

				dispositivos.add(dispositivo);
			}
			cursor.close();
		} catch (Exception e) {
			Log.i(TAG, e.getLocalizedMessage(),e);
		}
		return dispositivos;
	}
	
	public void remover(Dispositivo dispositivo){
		String[] args = {dispositivo.getId()+""};
		database.delete(TABLE, "id=?", args);
	}

	public int atualiza(Dispositivo dispositivo){
		ContentValues values = new ContentValues();
		values.put("id", dispositivo.getId());
		values.put("nome", dispositivo.getNome());
		values.put("ultimaAtualizacao", dispositivo.getUltimaAtualizacao().getTime());
		values.put("tipo", dispositivo.getTipo());

		String[] args = {dispositivo.getId().toString()+""};
		return database.update(TABLE, values, "id=?", args);
	}

}
