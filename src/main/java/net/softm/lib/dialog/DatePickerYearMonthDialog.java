package net.softm.lib.dialog;

import java.lang.reflect.Field;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.DatePicker;
/**
 * DatePickerYearMonthDialog
 * @author softm 
 */
public class DatePickerYearMonthDialog extends DatePickerDialog {
	@Override
	public void setCancelable(boolean flag) {
		super.setCancelable(flag);
	}

	public DatePickerYearMonthDialog(Context context, final OnDateSetListener callBack,
			int year, int monthOfYear, int dayOfMonth) {
 
		super(context, callBack, year, monthOfYear, dayOfMonth);
		try {

			Field[] f = DatePickerDialog.class.getDeclaredFields();
			for (Field dateField : f) {
				if (dateField.getName().equals("mDatePicker")) {
					dateField.setAccessible(true);
					final DatePicker datePicker = (DatePicker) dateField.get(this);
					Field datePickerFields[] = dateField.getType().getDeclaredFields();

					for (Field datePickerField : datePickerFields) {
//						Util.i("dateField.getName(): " + datePickerField.getName());
						if ("mDayPicker".equals(datePickerField.getName())
								|| "mDaySpinner".equals(datePickerField.getName())
								|| "mCalendarView".equals(datePickerField.getName())
								) {
							datePickerField.setAccessible(true);
							Object dayPicker = new Object();
							dayPicker = datePickerField.get(datePicker);
//							((View) dayPicker).setVisibility(View.GONE);
						}
					}
					
				}
				this.setCancelable(true);
			      this.setButton(DialogInterface.BUTTON_POSITIVE, getContext().getText(android.R.string.ok),
		          new DialogInterface.OnClickListener() {
		              @Override
		              public void onClick(DialogInterface dialog, int which) {
						DatePicker datePicker = (DatePicker) DatePickerYearMonthDialog.this.getDatePicker();
		                callBack.onDateSet(datePicker, datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
		              }
		          });				
			      this.setButton(DialogInterface.BUTTON_NEGATIVE, getContext().getText(android.R.string.cancel),
			    		  new DialogInterface.OnClickListener() {
			    	  @Override
			    	  public void onClick(DialogInterface dialog, int which) {
			    		  if (which == DialogInterface.BUTTON_NEGATIVE) {			    		  
								DatePicker datePicker = (DatePicker) DatePickerYearMonthDialog.this.getDatePicker();
					    		callBack.onDateSet(datePicker, -1, -1, -1);
			    		  }
			    	  }
			      });
			}
			setTitle(year + "년 " + (monthOfYear+1) + "월" + (dayOfMonth) + "일");
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDateChanged(DatePicker view, int year, int month, int day) {
		super.onDateChanged(view, year, month, day);
		setTitle(year + "년 " + (month + 1) + "월");
	}
}