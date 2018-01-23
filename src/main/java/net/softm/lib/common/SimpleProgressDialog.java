package net.softm.lib.common;

import net.softm.lib.R;
import android.app.Dialog;
import android.content.Context;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;

/**
 * SimpleProgressDialog
 * @author softm 
 */
public class SimpleProgressDialog extends Dialog {

	public static SimpleProgressDialog show(Context context, CharSequence title,
			CharSequence message) {
		return show(context, title, message, false);
	}

	public static SimpleProgressDialog show(Context context, CharSequence title,
			CharSequence message, boolean indeterminate) {
		return show(context, title, message, indeterminate, false, null);
	}

	public static SimpleProgressDialog show(Context context, CharSequence title,
			CharSequence message, boolean indeterminate, boolean cancelable) {
		return show(context, title, message, indeterminate, cancelable, null);
	}

	public static SimpleProgressDialog show(Context context, CharSequence title,
			CharSequence message, boolean indeterminate, boolean cancelable,
			OnCancelListener cancelListener) {
		SimpleProgressDialog dialog = new SimpleProgressDialog(context);
		dialog.setTitle(title);
		dialog.setCancelable(cancelable);
		dialog.setOnCancelListener(cancelListener);
		/* The next line will add the ProgressBar to the dialog. */
		
		ProgressBar mProgressBar = new ProgressBar(context);
		mProgressBar.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		mProgressBar.setMax(3);
//		mProgressBar.setScrollBarStyle(android.R.style.Widget_ProgressBar_Small);
//		mProgressBar.setScrollBarStyle(android.R.style.Widget_ProgressBar_Horizontal);
//		uiScreen.addTopRightBox(mProgressBar);
		//ProgressBar mProgressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleInverse)
		
//		dialog.addContentView(mProgressBar, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
// modify.		
//		TextView tv = new TextView(context);
//		tv.setText(R.string.app_name);
//		dialog.addContentView(tv, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		
		dialog.addContentView(new ProgressBar(context, null, android.R.attr.progressBarStyleInverse), new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));		
		dialog.show();
		return dialog;
	}

	public SimpleProgressDialog(Context context) {
		super(context, R.style.NewDialog);
	}
}