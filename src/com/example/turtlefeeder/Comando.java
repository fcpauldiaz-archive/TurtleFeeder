package com.example.turtlefeeder;
/**
 * Universidad del Valle de Guatemala
 * Pablo Diaz
 * Adolfo Morales
 * Clase que modela las características de un comando de la raspberry pi
 */


public class Comando {

	private int _id;
	private String _nombre;
	private String _instruccion;
	
	//constructor vacio
	public Comando(){
		
	}
	//constructor de los atributos
	public Comando(String name,String instruccion){
		this.setNombre(name);
		this.setInstruccion(instruccion);
	}
	//constructor de los atributos
	public Comando(int id, String name,String instruccion){
		this.setID(id);
		this.setNombre(name);
		this.setInstruccion(instruccion);
	}

	//siguen métodos accesores y mutadores de los atributos
	public int getID() {
		return _id;
	}

	public void setID(int _id) {
		this._id = _id;
	}

	public String getNombre() {
		return _nombre;
	}

	public void setNombre(String _name) {
		this._nombre = _name;
	}

	public String getInstruccion() {
		return _instruccion;
	}

	public void setInstruccion(String _instruccion) {
		this._instruccion = _instruccion;
	}
	
	
}
