package com.example.turtlefeeder;

public class Host {

	private int _id;
	private String _Host;
	private String _password;
	
	public Host(){
	
	}
	public Host(String host, String password){
		this._Host=host;
		this._password=password;
	}
	
	public String getPassword() {
		return _password;
	}
	public void setPassword(String password) {
		this._password = password;
	}
	public String getHost() {
		return _Host;
	}
	public void setHost(String host) {
		_Host = host;
	}

	public int getID() {
		return _id;
	}

	public void setID(int _id) {
		this._id = _id;
	}
	
	
	
}
