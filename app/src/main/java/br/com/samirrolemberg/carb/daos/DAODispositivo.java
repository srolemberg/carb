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
		values.put("dataCriacao", dispositivo.getDataCriacao()==null?null:dispositivo.getDataCriacao().getTime());

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
                dispositivo = new Dispositivo.Builder()
                        .withId(cursor.getInt(cursor.getColumnIndex("id")))
						.withDataCriacao(new Date(cursor.getLong(cursor.getColumnIndex("dataCriacao"))))
						.withNome(cursor.getString(cursor.getColumnIndex("nome")))
						.withTipo(cursor.getInt(cursor.getColumnIndex("tipo")))
						.build();
            }
            cursor.close();
        } catch (Exception e) {
            Log.i("DAOs", e.getLocalizedMessage(),e);
        }
        return dispositivo;
    }

	public int atualiza(Dispositivo dispositivo, int id){
		ContentValues values = new ContentValues();
		values.put("id", dispositivo.getId());
		values.put("nome", dispositivo.getNome());
		values.put("dataCriacao", dispositivo.getDataCriacao()==null?null:dispositivo.getDataCriacao().getTime());
		values.put("tipo", dispositivo.getTipo());

		String[] args = {id+""};
		return database.update(TABLE, values, "id=?", args);
	}

	public List<Dispositivo> listarTudo(){
		List<Dispositivo> dispositivos = new ArrayList<Dispositivo>();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select * from "+TABLE+" order by id asc ");
			Cursor cursor = database.rawQuery(sql.toString(), null);
			while (cursor.moveToNext()) {

				Dispositivo dispositivo = new Dispositivo.Builder()
						.withId(cursor.getInt(cursor.getColumnIndex("id")))
						.withDataCriacao(new Date(cursor.getLong(cursor.getColumnIndex("dataCriacao"))))
						.withNome(cursor.getString(cursor.getColumnIndex("nome")))
						.withTipo(cursor.getInt(cursor.getColumnIndex("tipo")))
						.build();
				
				dispositivos.add(dispositivo);
			}
			cursor.close();
		} catch (Exception e) {
			Log.i("DAOs", e.getLocalizedMessage(),e);
		}
		return dispositivos;
	}
	
	public void remover(Dispositivo dispositivo){
		String[] args = {dispositivo.getId()+""};
		database.delete(TABLE, "id=?", args);
	}
}
