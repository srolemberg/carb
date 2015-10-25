package br.com.samirrolemberg.carb.conn;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Connection extends SQLiteOpenHelper{

	protected static final int VERSAO = 1;
	protected static final String DATABASE = "db_carb";
	
	public Connection(Context context) {
		super(context, DATABASE, null, VERSAO);
		DatabaseManager.initializeInstance(this);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i("DAOs", "onCreate");
		db.execSQL(table_001());
		db.execSQL(table_002());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i("DAOs", "onUpgrade");
	}
	
	public String table_001(){
		StringBuffer ddl = new StringBuffer();
		ddl.append("create table dispositivo (");
			ddl.append("id INTEGER PRIMARY KEY AUTOINCREMENT, ");
			ddl.append("nome VARCHAR(255) NULL, ");
			ddl.append("dataCriacao DATETIME NULL, ");
			ddl.append("ultimaAtualizacao DATETIME NULL, ");
			ddl.append("descricao VARCHAR(255) NULL, ");
			ddl.append("tipo INTEGER NULL ");
			ddl.append(");");
			return ddl.toString();
	}

	public String table_002(){
		StringBuffer ddl = new StringBuffer();			
		ddl.append("create table calibragem (");
			ddl.append("id INTEGER PRIMARY KEY AUTOINCREMENT, ");
			ddl.append("audio INTEGER NULL, ");
			ddl.append("video INTEGER NULL, ");
			ddl.append("descricao VARCHAR(255) NULL, ");
			ddl.append("titulo VARCHAR(255) NULL, ");
			ddl.append("dataCriacao DATETIME NULL, ");
			ddl.append("ultimaAtualizacao DATETIME NULL, ");
			ddl.append("tipo INTEGER NULL, ");
			ddl.append("idDispositivo INTEGER NULL ");
			ddl.append(");");
			return ddl.toString();
	}

}
