package com.example.turtlefeeder;


import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class ConectionFragment extends FragmentActivity {
	 static Context contexto;
	 private List<Host> hosts = null;

	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.conexion);

		
		 
		
		 Button mButton = (Button)findViewById(R.id.button1);
	     Button deleteButton = (Button)findViewById(R.id.button2);
		final EditText mHost = (EditText)findViewById(R.id.editText1);
        final EditText mPass = (EditText)findViewById(R.id.editText3);
        final DataBaseHandler db = new DataBaseHandler(getApplicationContext());
        
		
        
        int cantidad = db.getHostCount();
		if (cantidad!=0){
			hosts =db.getAllHosts();
	        mHost.setText(hosts.get(0).getHost());
	        mPass.setText(hosts.get(0).getPassword());
		}
		
			
        
        contexto = getApplicationContext();
    
        mButton.setOnClickListener(
            new View.OnClickListener()
            {
            	String  password, host;
				
                public void onClick(View view)
                {
                	
                	host = mHost.getText().toString();
                	password = mPass.getText().toString();
                	if (host.equals("")||password.equals(""))
                		Toast.makeText(contexto, "Ha dejado vacío un espacio", Toast.LENGTH_LONG).show();
                	else{
                		db.agregarHost(new Host(host,password));
                		
                		Toast.makeText(contexto, "exito: "+db.getAllHosts().get(0).getHost(), Toast.LENGTH_SHORT).show();
                	}
                	Intent intent = new Intent(contexto, MainActivity.class);
                    startActivity(intent);  
	                	
	                }
	            });
        deleteButton.setOnClickListener(
        		new View.OnClickListener() {
					
        			
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						for (Host h: hosts){
							db.deleteHost(h);
						}
						mHost.setText("");
						mPass.setText("");
						
					}
				}
        		
        		);
		
		
	}

	
	

	 
		}
