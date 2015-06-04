package com.example.turtlefeeder;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
public class CommandFragment extends Fragment {
	
	private String password;   //se guada la contraseña
	private String host;       //se guarda un único host
	private int cantidad;      //se obtiene la cantidad de comandos
	private int cantHost;      //se obtiene la cantidad de hosts
	private DataBaseHandler db;//se declara la base de datos  
	private Button commandButton;//boton generado  
	private EditText edit_tiempo;      //se guarda el parámetro de tiempo
	private Button feedButton;  //boton para darle de comer
	private String tiempo;
	
	@Override
	public View onCreateView(LayoutInflater inflater, final ViewGroup container,
			Bundle savedInstanceState) {

		final View rootView = inflater.inflate(R.layout.comandos, container, false);
		
		//BOTÓN PREDEFINIDO
		feedButton = (Button)rootView.findViewById(R.id.feed_button);
		edit_tiempo = ((EditText)rootView.findViewById(R.id.time_input));
		
		db = new DataBaseHandler(rootView.getContext());
		cantidad = db.getCommandsCount();
		cantHost = db.getHostCount();
		//si no existen hosts, no se hace nada
		if (cantHost!=0){
			List<Host> hosts = db.getAllHosts();
			//el host utilizado siempre esta en la primera posicion
			password = hosts.get(0).getPassword();
			host = hosts.get(0).getHost();
			if (cantidad!=0){ //si no existen comando, tampoco se ejecuta el código
				//lista de comandos y hosts
				List<Comando> comandos = db.getAllCommands();
				LinearLayout commandLayout = (LinearLayout)rootView.findViewById(R.id.container);
				for (final Comando c1: comandos)
				{
					Log.d("comandos",c1.getNombre());
					//dependiendo de la cantidad de comandos
					//se crean los botons
					commandButton = new Button(rootView.getContext());
					commandButton.setWidth(150);
					//se le ponen los parametros según la base de datos
					commandButton.setText(c1.getNombre());
					commandButton.setId(c1.getID());
					//a cada uno se le pone su función de acuerdo a la base de datos
					commandButton.setOnClickListener(new View.OnClickListener() {
				                public void onClick(View v) {
			                    
			                	try {
		                
				                	
				                	Toast.makeText(rootView.getContext(), "Usuario: pi" , Toast.LENGTH_SHORT).show();
				                	Toast.makeText(rootView.getContext(), "Contraseña: " + password, Toast.LENGTH_SHORT).show();
				                	Toast.makeText(rootView.getContext(), "Host: " + host, Toast.LENGTH_SHORT).show();
				                	Toast.makeText(rootView.getContext(), "Comando: " + c1.getInstruccion(), Toast.LENGTH_SHORT).show();
				                	
				                	
				                	//AsyncTask que realiza la conexión en segundo plano
				                	new ConexionSSH().execute(password,host,c1.getInstruccion());
			                	
			                		
								} catch (Exception e) {
									Toast.makeText(rootView.getContext(), "Fallo algo", Toast.LENGTH_SHORT).show();
								}
		                     
		                }//cierra método listener
			            });
				commandButton.setOnLongClickListener(new View.OnLongClickListener() {
			
			     //método que elimina el comando seleccionado por un largo tiempo
				@Override
				public boolean onLongClick(View arg0) {
					
						db.deleteCommand(c1);
					
					return false;
				}
		    }); 
			commandLayout.addView(commandButton);
			}//cierra for
			
			 
		}//if cantidad != 0
		
		}else //if cantHost!=0
			Toast.makeText(rootView.getContext(),"Primero debe crear un host", Toast.LENGTH_SHORT).show();
		 
		

		feedButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
		
				
				tiempo=edit_tiempo.getText().toString();
				//envia el request a la pagina web
				if (cantHost!=0)
					new HttpRequest().execute(host,tiempo);
				else 
					Toast.makeText(rootView.getContext(), "no hay hosts", Toast.LENGTH_SHORT).show();
				
				try {
	                
                	
                	Toast.makeText(rootView.getContext(), "Usuario: pi" , Toast.LENGTH_SHORT).show();
                	Toast.makeText(rootView.getContext(), "Contraseña: " + password, Toast.LENGTH_SHORT).show();
                	Toast.makeText(rootView.getContext(), "Host: " + host, Toast.LENGTH_SHORT).show();
                	Toast.makeText(rootView.getContext(), "Comando: " + "pi4j --run Servo "+tiempo, Toast.LENGTH_SHORT).show();
                	
                	if (!tiempo.equals(""))
                		new ConexionSSH().execute(password,host,"pi4j --run Servo "+tiempo);
                	else
                		Toast.makeText(rootView.getContext(), "No ha ingresado el tiempo", Toast.LENGTH_LONG).show();
            		
			} catch (Exception e) {
				Toast.makeText(rootView.getContext(), "Fallo algo", Toast.LENGTH_SHORT).show();
		       
			}
              
				
			}
		});   
		
		
		return rootView;
	}
	
	//clase que se encarga de ejcutar la conexión ssh en segundo plano
	private class ConexionSSH extends AsyncTask<String, Void, Void>{

		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			String password = params[0];
			String host = params[1];
			String command = params[2];
			
			try {
				
				
				EjecutarSSH("pi", password, host, 22, command);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
			
		}
	
    }
	
	//método que realiza la conexión ssh
	public static void EjecutarSSH(String pUser, String pPass, String pHost,
			int pPort, String pComando) throws Exception {
		JSch ssh = new JSch();
		// Instancio el objeto session para la transferencia
		Session session = null;
		// instancio el canal sftp
		ChannelExec channelssh = null;
		//Toast.makeText(contexto, "hola " , Toast.LENGTH_SHORT).show();
		try {
			// Inciciamos el JSch con el usuario, host y puerto
			session = ssh.getSession(pUser, pHost, pPort);
			// Seteamos el password
			session.setPassword(pPass);
			// El SFTP requiere un intercambio de claves
			// con esta propiedad le decimos que acepte la clave
			// sin pedir confirmación
			Properties prop = new Properties();
			prop.put("StrictHostKeyChecking", "no");
			
			
			session.setConfig(prop);
			session.connect();
			
			// Abrimos el canal de sftp y conectamos
			channelssh = (ChannelExec) session.openChannel("exec");
			// seteamos el comando a ejecutar
			channelssh.setCommand(pComando);
			// conectar y ejecutar
			channelssh.connect();
		} catch (Exception e) {
			
			e.printStackTrace();
			
		} finally {
			
			// Cerramos el canal y session
			if (channelssh.isConnected()){
				channelssh.disconnect();
				//Toast.makeText(contexto, "channel " , Toast.LENGTH_SHORT).show();
			}
			if (session.isConnected()){
				session.disconnect();
				//Toast.makeText(contexto, "session " , Toast.LENGTH_SHORT).show();
			}
		}// end try
	}// EjecutarSSH

	//clase que se encarga de mandar un http POST request a la página web
	private class HttpRequest extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... urls) {
        	
        	Log.d("hostname","http://"+urls[0]+":8020");
        	HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://"+urls[0]+":8020");
           
            try {
            	
            	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("tiempo_comida", urls[1]));
                nameValuePairs.add(new BasicNameValuePair("android", "True"));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            	
            	
            	
                HttpResponse response = httpclient.execute(httpPost);
                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    InputStream inputstream = entity.getContent();
                    BufferedReader bufferedreader =
                      new BufferedReader(new InputStreamReader(inputstream));
                    StringBuilder stringbuilder = new StringBuilder();

                    String currentline = null;
                    while ((currentline = bufferedreader.readLine()) != null) {
                        stringbuilder.append(currentline + "\n");
                    }
                    String result = stringbuilder.toString();
                    Log.v("HTTP REQUEST",result);
                    inputstream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        
        }

		
  	}

}
