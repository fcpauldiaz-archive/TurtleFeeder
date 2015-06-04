package com.example.turtlefeeder;


import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHandler extends SQLiteOpenHelper {

	// Version de la base de datos
	private static final int DATABASE_VERSION = 4;

	// nombre de la base de datos
	private static final String DATABASE_NAME = "CommandManager";

	// tablas a utilizar
	private static final String TABLE_COMMANDS = "comandosRasPI";
	private static final String TABLE_HOSTS = "RaspiHOST";

	// Nombre de las columnas
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "Nombre";
	private static final String KEY_INSTRUCTION = "Instruccion";
	private static final String KEY_HOST ="Host";
	private static final String KEY_PASS="Pass";
	

	public DataBaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Crear tablas
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_HOST_TABLE = "CREATE TABLE "+ TABLE_HOSTS +"("
				+KEY_ID +" INTEGER PRIMARY KEY," +KEY_HOST + " TEXT,"
				+KEY_PASS+ " TEXT"+")";
		db.execSQL(CREATE_HOST_TABLE);
		String CREATE_COMMAND_TABLE = "CREATE TABLE " + TABLE_COMMANDS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
				+ KEY_INSTRUCTION + " TEXT" + ")";
		db.execSQL(CREATE_COMMAND_TABLE);
		
	}

	// Actualizar base de datos
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMANDS);
		db.execSQL("DROP TABLE IF EXISTS "+  TABLE_HOSTS);

		// Create tables again
		onCreate(db);
	}

	/**
	 * Operaciones de lectura, actualizaciones y eliminiar
	 * 
	 */

	
	
	// agregar nuevo comando
	public void agregarComando(Comando comando) {
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, comando.getNombre()); // nombre del comando
		values.put(KEY_INSTRUCTION, comando.getInstruccion()); //instruccion del comando

		// Insertar columna
		db.insert(TABLE_COMMANDS, null, values);
		db.close(); // cerrar conexion a base de datos
	}
	
	public void agregarHost(Host host){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_HOST, host.getHost()); // nombre del comando
		values.put(KEY_PASS, host.getPassword()); //instruccion del comando

		// Insertar columna
		db.insert(TABLE_HOSTS, null, values);
		db.close(); // cerrar conexion a base de datos
		
	}

	// Obtener un comando simple
	public Comando getComando(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_COMMANDS, new String[] { KEY_ID,
				KEY_NAME, KEY_INSTRUCTION }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Comando comando = new Comando(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1),cursor.getString(2));
		// regresar comando
		return comando;
	}
	
	// Obtener una lista con todos los comandos
	public List<Comando> getAllCommands() {
		List<Comando> listaComandos = new ArrayList<Comando>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_COMMANDS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// recorrer las columnas de la base de datos y agregar a la lista
		if (cursor.moveToFirst()) {
			do {
				Comando comando = new Comando();
				comando.setID(Integer.parseInt(cursor.getString(0)));
				comando.setNombre(cursor.getString(1));
				comando.setInstruccion(cursor.getString(2));
				
				// agregar comando a a la lista
				listaComandos.add(comando);
			} while (cursor.moveToNext());
		}

		// regresar lista de comandos
		return listaComandos;
	}

	public List<Host> getAllHosts(){
		List<Host> listaHost = new ArrayList<Host>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_HOSTS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		if (cursor.moveToFirst()){
			do{
				Host host = new Host();
				host.setID(Integer.parseInt(cursor.getString(0)));
				host.setHost(cursor.getString(1));
				host.setPassword(cursor.getString(2));
				//agregar
				listaHost.add(host);
			}while(cursor.moveToNext());
		}
		
		//regresar lista
		return listaHost;
	}
	
	// Actualizar un comando especifico
	public int updateContact(Comando comando) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, comando.getNombre());
		values.put(KEY_INSTRUCTION, comando.getInstruccion());
		

		// actualizar columna
		return db.update(TABLE_COMMANDS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(comando.getID()) });
	}

	// Eliminar comando especifico
	public void deleteCommand(Comando comando) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_COMMANDS, KEY_ID + " = ?",
				new String[] { String.valueOf(comando.getID()) });
		db.close();
	}
	//eliminar host especifico
	
	public void deleteHost(Host host){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_HOSTS, KEY_ID + " = ?", 
				new String[] { String.valueOf(host.getID()) });
		db.close();
		
	}


	// Get command count
	public int getCommandsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_COMMANDS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		

		// return count
		return cursor.getCount();
	}
	
	public int getHostCount(){
		String countQuery ="SELECT  * FROM " + TABLE_HOSTS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery,null);
		
		
		return cursor.getCount();
	}

}
