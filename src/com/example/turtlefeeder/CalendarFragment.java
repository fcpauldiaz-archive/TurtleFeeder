package com.example.turtlefeeder;
import java.util.Calendar;




import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class CalendarFragment extends Fragment {
	

	// Widget GUI
	Button btnCalendar, btnTimePicker;
	
	
	Toast msgDia, msgHora;
	// Variable for storing current date and time
	private int mYear, mMonth, mDay, mHour, mMinute;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View rootView = inflater.inflate(R.layout.calendar, container, false);
		
		
		btnCalendar.setOnClickListener(
				 new View.OnClickListener(){

					@Override
					public void onClick(View arg0) {
						// Process to get Current Date
						final Calendar c = Calendar.getInstance();
						mYear = c.get(Calendar.YEAR);
						mMonth = c.get(Calendar.MONTH);
						mDay = c.get(Calendar.DAY_OF_MONTH);

						// Launch Date Picker Dialog
						DatePickerDialog dpd = new DatePickerDialog(rootView.getContext(),
								new DatePickerDialog.OnDateSetListener() {

									@Override
									public void onDateSet(DatePicker view, int year,
											int monthOfYear, int dayOfMonth) {
										// Display Selected date in textbox
										Toast.makeText(rootView.getContext(), dayOfMonth + "-"
												+ (monthOfYear + 1) + "-" + year, Toast.LENGTH_SHORT).show();
										
										//txtDate.setText(dayOfMonth + "-"+ (monthOfYear + 1) + "-" + year);
										Log.d("date", dayOfMonth + "-"+ (monthOfYear + 1) + "-" + year);
									}
								}, mYear, mMonth, mDay);
						dpd.show();
					}
					 
					 
					 
					 
				 }
				
				
				
				
				);
		btnTimePicker.setOnClickListener(
				
				new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// Process to get Current Time
						final Calendar c = Calendar.getInstance();
						mHour = c.get(Calendar.HOUR_OF_DAY);
						mMinute = c.get(Calendar.MINUTE);

						// Launch Time Picker Dialog
						TimePickerDialog tpd = new TimePickerDialog(rootView.getContext(),
								new TimePickerDialog.OnTimeSetListener() {

									@Override
									public void onTimeSet(TimePicker view, int hourOfDay,
											int minute) {
										// Display Selected time in textbox
										Toast.makeText(rootView.getContext(), hourOfDay + ":" + minute, Toast.LENGTH_SHORT).show();
										//txtTime.setText(hourOfDay + ":" + minute);
										Log.d("time", hourOfDay + ":" + minute);
									}
								}, mHour, mMinute, false);
						tpd.show();
						
					}
				}
				
				
				
				);
		
		
		return rootView;
	}
	
	


}