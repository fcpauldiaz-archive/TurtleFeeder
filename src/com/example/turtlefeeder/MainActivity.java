package com.example.turtlefeeder;



import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;




public class MainActivity  extends FragmentActivity implements
ActionBar.TabListener {
	
	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	private String[] tabs = {"Comandos","Calendario"};
	
	
	
   
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
       
        //parametro enviado para que cierto código solo se ejecute una vez
       iniciarAplicacion(0);
    
        
    }
    
    public void iniciarAplicacion(int inicio){
		// Initilization
    	
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
				
		viewPager.setAdapter(mAdapter);
		actionBar.show();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);	
		// Adding Tabs
		if (inicio==0){
			for (String tab_name : tabs) {
				actionBar.addTab(actionBar.newTab().setText(tab_name)
						.setTabListener(this));
			}
    	}

		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id){
        //action bar button de editar conexión
        case R.id.edit_bar:
        	Intent intent = new Intent(getApplicationContext(),ConectionFragment.class);
        	startActivity(intent);
        	break;
        //action bar button de actualizar app
        case R.id.refresh_bar:
        	iniciarAplicacion(1); 
        	break;
        //action bar button para agregar comando
        case R.id.command_bar:
        	
        	// get prompts.xml view
        	
            LayoutInflater layoutInflater = LayoutInflater.from(this);

            View promptView = layoutInflater.inflate(R.layout.dialog, null);
            
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            
 
            // Set an EditText view to get user input 
            final EditText input = (EditText)promptView.findViewById(R.id.nombre_comando);
            final EditText input2 = (EditText)promptView.findViewById(R.id.comando);
            alert.setView(promptView);
 
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
	             //You will get as string input data in this variable.
	             // here we convert the input to a string and show in a toast.
	             Toast.makeText(getApplicationContext(),input.getText().toString(),Toast.LENGTH_LONG).show();                
	             Toast.makeText(getApplicationContext(), input2.getText().toString(), Toast.LENGTH_SHORT).show();
	            
	             DataBaseHandler db = new DataBaseHandler(getApplicationContext());
	             
	             db.agregarComando(new Comando(input.getText().toString(),input2.getText().toString()));
	             List<Comando> comandos = db.getAllCommands();
	             for (Comando c1: comandos){
	            	 Log.d("comandos",c1.getNombre());
	             }
	            
	             
            
            } // End of onClick(DialogInterface dialog, int whichButton)
        }); //End of alert.setPositiveButton
            alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
                  dialog.cancel();
              }
        }); //End of alert.setNegativeButton
            AlertDialog alertDialog = alert.create();
            alertDialog.show();

        	break;
        	
        //action bar button sobre 
        case R.id.about_bar:
        	
        	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
    				this);
     
    			// set title
    			alertDialogBuilder.setTitle("Domestic Feeder");
     
    			// set dialog message
    			alertDialogBuilder
    				.setMessage("By Pablo Diaz and Adolfo Morales \n diaz13203@uvg.edu.gt mor13014@uvg.edu.gt")
    				.setCancelable(false)
    				
    				.setNegativeButton("Ok",new DialogInterface.OnClickListener() {
    					public void onClick(DialogInterface dialog,int id) {
    						// if this button is clicked, just close
    						// the dialog box and do nothing
    						dialog.cancel();
    					}
    				});
     
    				// create alert dialog
    				AlertDialog alertDialog2 = alertDialogBuilder.create();
     
    				// show it
    				alertDialog2.show();
    			
        	break;
        
        }
        return super.onOptionsItemSelected(item);
    }

    
   
    

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onTabSelected(Tab tab, FragmentTransaction arg1) {
		// on tab selected
		// show respected fragment view
		viewPager.setCurrentItem(tab.getPosition());
		
	}


	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
	
	
	  	
	 
	
 
}
