package net.softm.lib;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import net.softm.lib.common.SimpleProgressDialog;

import java.util.Timer;
import java.util.TimerTask;

/**
 * BaseActivity
 * 기본엑티비티 ~
 * @author softm 
 */
//public abstract class BaseActivity extends Activity {
// restart service ondestory 동작.
public abstract class BaseActivity extends AppCompatActivity {
	protected SimpleProgressDialog progressDialog;
	public static final String LOG_TAG = "SOFTM";
	
    protected DefaultApplication mApp;
	public boolean DEBUG = Constant.DEBUG;
	public Var var; // 사용변수
	
	private InputMethodManager imm;
	private static final Boolean bUseImm = true;
	
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		// keyboard
		this.imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//		new ASyncH
		// var
		readVar();		
		super.onCreate(savedInstanceState);
		mApp = (DefaultApplication) getApplicationContext();
//		db = mApp.getDatabase();
	}

	public void startProgressBar() {
		stopProgressBar();
		progressDialog = SimpleProgressDialog.show(this, "", "", true, true, null);
	}

	public void stopProgressBar() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}

	public void alert(int msg) {
		alert(msg, -1, null );
    }

	public void alert(String msg) {
    	alert(msg, null, null );
    }
	public void alert(int msg, int buttonText) {
    	alert(msg, buttonText, null );
    }

	public void alert(int msg, String buttonText) {
    	alert(msg, buttonText, null );
    }

	public void alert(String msg, String buttonText) {
    	alert(msg, buttonText, null );
    }

	public void alert(String msg, int buttonText) {
    	alert(msg, buttonText, null );
    }
	public void alert(int msg, DialogInterface.OnClickListener onClick ){
    	alert(msg, -1, onClick );
    }

	public void alert(String msg, DialogInterface.OnClickListener onClick ){
    	alert(msg, -1, onClick );
    }

	public void alert(int msg, String buttonText, DialogInterface.OnClickListener onClick ){
        String strMsg    = getString(msg);
        alert(strMsg, buttonText, onClick );
    }

	public void alert(String msg, int buttonText, DialogInterface.OnClickListener onClick ){
        String strBtnTxt = buttonText!=-1?getString(buttonText):null;
        alert(msg, strBtnTxt, onClick );
    }

	public void alert(int msg, int buttonText, DialogInterface.OnClickListener onClick ){
        String strMsg    = getString(msg);
        String strBtnTxt = buttonText!=-1?getString(buttonText):null;
        alert(strMsg, strBtnTxt, onClick );
    }

	public void alert(String msg, String btnText, DialogInterface.OnClickListener onClick ) {
		String strBtnTxt = btnText != null?btnText:getString(R.string.alert_confirm_str);
//        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.DialogTheme));
//        alt_bld.setTitle(R.string.alert_str);
		alt_bld.setMessage( "\n" + msg + "\n" );
		alt_bld.setCancelable(false).setPositiveButton(
				strBtnTxt,
				onClick != null ? onClick
						: new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int which) {
					}
				});
		alt_bld.setCancelable(true);
		AlertDialog dialog = alt_bld.show();

		TextView messageText = (TextView)dialog.findViewById(android.R.id.message);
		messageText.setGravity(Gravity.CENTER);

    }

//	protected void confirm(int msg) {
//		confirm(msg, -1, null );
//    }
//
//    protected void confirm(String msg) {
//    	confirm(msg, null, null );
//    }

	public void confirm(int msg, DialogInterface.OnClickListener onClick){
        String strMsg    = getString(msg);
        confirm(strMsg, null,onClick, null,null);
    }

	public void confirm(String msg, DialogInterface.OnClickListener onClick){
        confirm(msg, null,onClick, null,null );
    }

	public void confirm(int msg, DialogInterface.OnClickListener onClick,DialogInterface.OnClickListener onCancel ){
        String strMsg    = getString(msg);
        confirm(strMsg, null,onClick, null,onCancel );
    }

	public void confirm(String msg, DialogInterface.OnClickListener onClick,DialogInterface.OnClickListener onCancel ){
        confirm(msg, null,onClick, null,onCancel );
    }

	public void confirm(int msg
			, int btnText
			, DialogInterface.OnClickListener onClick
			, int btnCancelText
			, DialogInterface.OnClickListener onCancel
    ) {
		confirm(getString(msg), btnText!=-1?getString(btnText):null,onClick, btnCancelText!=-1?getString(btnCancelText):null,onCancel,null,null );
    }

	public void confirm(String msg
			, int btnText
			, DialogInterface.OnClickListener onClick
			, int btnCancelText
			, DialogInterface.OnClickListener onCancel
    ) {
		confirm(msg, btnText!=-1?getString(btnText):null,onClick, btnCancelText!=-1?getString(btnCancelText):null,onCancel,null,null );
    }

	public void confirm(String msg
			, String btnText
			, DialogInterface.OnClickListener onClick
			, String btnCancelText
			, DialogInterface.OnClickListener onCancel
    ) {
		confirm(msg, btnText,onClick, btnCancelText,onCancel,null,null );
    }

	public void confirm(int msg
			, int btnText
			, DialogInterface.OnClickListener onClick
			, int btnCancelText
			, DialogInterface.OnClickListener onCancel
            , int btnNegativeText
            , DialogInterface.OnClickListener onNegative
            ) {
		confirm(getString(msg), btnText!=-1?getString(btnText):null,onClick
				   , btnCancelText!=-1?getString(btnCancelText):null, onCancel
				   , btnNegativeText!=-1?getString(btnNegativeText):null,onNegative
				);
	}

	public void confirm(String msg
			, String btnText
			, DialogInterface.OnClickListener onClick
			, String btnCancelText
			, DialogInterface.OnClickListener onCancel
            , String btnNegativeText
            , DialogInterface.OnClickListener onNegative
            ) {
        String strBtnTxt = btnText != null?btnText:getString(R.string.alert_confirm_str);
        String strBtnCancelText = btnCancelText != null?btnCancelText:getString(R.string.alert_cancel_str);
        String strBtnNegativeText = btnNegativeText != null?btnNegativeText:getString(R.string.alert_negative_str);
//        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.DialogTheme));
//        alt_bld.setTitle(R.string.alert_str);
        alt_bld.setMessage( "\n" + msg + "\n" );
        alt_bld.setCancelable(true);
        alt_bld.setPositiveButton(
                strBtnTxt,
                onClick != null ? onClick
                        : new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                    int which) {
                            }
                        });
        	alt_bld.setNeutralButton(
        			strBtnCancelText,
        			onCancel != null ? onCancel
        					: new DialogInterface.OnClickListener() {
        				public void onClick(DialogInterface dialog,
        						int which) {
        				}
        			});
        if ( onNegative != null ) {
	        alt_bld.setNegativeButton(
	        		strBtnNegativeText,
	        		onNegative != null ? onNegative
	                        : new DialogInterface.OnClickListener() {
	                            public void onClick(DialogInterface dialog,
	                                    int which) {
	                            }
	                        });
        }

        AlertDialog dialog = alt_bld.show();

		TextView messageText = (TextView)dialog.findViewById(android.R.id.message);
		messageText.setGravity(Gravity.CENTER);

	}



	// confirm dialog
	// for Reflection
	String confirmMethodNameOk;
	String confirmMethodNameCancel;
	Object confirmObject;
//	public void confirm(String message, String methodNameOk ) {
//		confirm( message, methodNameOk, "" );
//	}
	public void confirm(String message, String methodNameOk ) {
		confirmMethodNameOk = methodNameOk;
		confirmObject = this;

		alert(message,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if( confirmMethodNameOk != null && confirmMethodNameOk.length() > 0 ) {
							try {
								confirmObject.getClass().getMethod(confirmMethodNameOk).invoke(confirmObject);
							} catch (Exception e) {
								e.printStackTrace();
								Util.e( confirmMethodNameOk + " call error!");
							}
						}
					}
				});
		beep();

	}

	public void confirm(String message, String methodNameOk, String methodNameCancel) {
		confirmMethodNameOk = methodNameOk;
		confirmMethodNameCancel = methodNameCancel;
		confirmObject = this;

		confirm(message,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if( confirmMethodNameOk != null && confirmMethodNameOk.length() > 0 ) {
							try {
								confirmObject.getClass().getMethod(confirmMethodNameOk).invoke(confirmObject);
							} catch (Exception e) {
								e.printStackTrace();
								Util.e( confirmMethodNameOk + " call error!");
							}
						}
					}
				},
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if( confirmMethodNameCancel != null && confirmMethodNameCancel.length() > 0 ) {
							try {
								confirmObject.getClass().getMethod(confirmMethodNameCancel).invoke(confirmObject);
							} catch (Exception e) {
								e.printStackTrace();
								Util.e( confirmMethodNameCancel + " call error!");
							}
						}
					}
				});
		beep();
	}

	public void confirmNoCancelable(String message, String methodNameOk ) {
		confirmMethodNameOk = methodNameOk;
		confirmObject = this;

		AlertDialog.Builder alt_bld = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.DialogTheme));
		alt_bld.setMessage( "\n" + message + "\n" );
		alt_bld.setPositiveButton(
				"확인",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if( confirmMethodNameOk != null && confirmMethodNameOk.length() > 0 ) {
							try {
								confirmObject.getClass().getMethod(confirmMethodNameOk).invoke(confirmObject);
							} catch (Exception e) {
								e.printStackTrace();
								log( confirmMethodNameOk + " call error!");
							}
						}
					}
				});
		alt_bld.setCancelable(false);
		AlertDialog dialog = alt_bld.show();

		TextView messageText = (TextView)dialog.findViewById(android.R.id.message);
		messageText.setGravity(Gravity.CENTER);

		beep();
	}

    public boolean isCameraAvailable() {
        PackageManager pm = getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }
    protected void setText(int id, int v) {
    	setText(id,""+v);
    }
    protected void setText(int id, String v) {
		View vw = (View) findViewById(id);    	
    	setText(vw,v);
    }
    
    protected void setText(View vw, int v) {
    	setText(vw,""+v);
    }
	protected void setText(View vw, String v) {
		if ( vw instanceof TextView ) {
			TextView tv = (TextView) vw;
			tv.setText(v);	
		} else if ( vw instanceof SpinnerCd ) {
			SpinnerCd sp = (SpinnerCd) vw;
			sp.setValue(v);
		}
    }
    
    /**
     * View Element 현재값 반환.
     * @param id
     */
    protected String getValue(int id) {
		View vw = (View) findViewById(id);
		return getValue(vw);
    }
    
    /**
     * View Element 현재값 반환.
     * @param vw
     */
    protected String getValue(View vw) {
		String rtn = "";
		if ( vw instanceof TextView ) {
			TextView tv = (TextView) vw;
			rtn = tv.getText().toString();	
		} else if ( vw instanceof SpinnerCd ) {
			SpinnerCd sp = (SpinnerCd) vw;
			rtn = sp.getValue();	
		}
		return rtn;
    }    
    
    /**
     * 포커스 이동 & keyboard 표시 여부 설정
	 */
    public void setFocus(int id) {
		setFocus(id,true);
    }
    /**
     * 포커스 이동 & keyboard 표시 여부 설정
	 */
    public void setFocus(int id,boolean keyboard) {
		View v = (View) findViewById(id);
		setFocus(v,keyboard);
    }
    /**
     * 포커스 이동 & keyboard 표시 여부 설정
     * @param v
     */
    public void setFocus(final View v,boolean keyboard) {
		v.requestFocus();
		if ( keyboard ) {
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
			v.postDelayed(new Runnable() {
	            @Override
	            public void run() {
	                InputMethodManager keyboard = (InputMethodManager)
	                getSystemService(Context.INPUT_METHOD_SERVICE);
	                keyboard.showSoftInput(v, 0);
	            }
			 }, 500);
		}
    }
    
    /**
     * 포커스 이동
     * @param v
     */
    public void setFocus(final View v) {
    	setFocus(v,true);
    }
    
    /**
     * 보이게 안보이게
	 */
    protected void setVisibility(int id,int visibility) {
		View v = (View) findViewById(id);
		setVisibility(v, visibility);
    }
    /**
     * 보이게 안보이게
	 */
    protected void setVisibility(int id) {
    	setVisibility(id,View.VISIBLE);
    }
    
    protected void setVisibility(View v) {
		v.setVisibility(View.VISIBLE);
    }
    
    protected void setVisibility(View v,int visibility) {
    	v.setVisibility(visibility);
    }
    
    protected void setInVisibility(int id) {
		setVisibility(id,View.INVISIBLE);		
    }
    protected void setInVisibility(View vw) {
    	setVisibility(vw,View.INVISIBLE);		
    }
    
    protected void setInVisibility(int id,int visibility) {
    	visibility = View.INVISIBLE == visibility || View.GONE == visibility ? visibility: View.INVISIBLE;  
		setVisibility(id,visibility);		
    }
    
    protected void setInVisibility(View vw,int visibility) {
    	visibility = View.INVISIBLE == visibility || View.GONE == visibility ? visibility: View.INVISIBLE;  
    	setVisibility(vw,visibility);		
    }
    
    /**
     * 활성화 비활성화
     * @param id
     * @param enable
     */
    protected void setEnabled(int id,boolean enable) {
		View v = (View) findViewById(id);
		setEnabled(v,enable);
    }
    
    /**
     * 활성화 비활성화
     * @param v
     */
    protected void setEnabled(View v,boolean enable) {
    	v.setEnabled(enable);
    	v.setClickable(enable);
    	v.setFocusable(enable);
    }

    /**
     * 비활성화
     * @param id
     */
    protected void setDisabled(int id) {
    	setEnabled(id, Boolean.FALSE);
    }
    
    /**
     * 비활성화
	 */
    protected void setDisabled(View v) {
    	setEnabled(v, Boolean.FALSE);
    }
    
    /**
     * 활성화 비활성화
	 */
    protected void setEnabled(int id) {
    	setEnabled(id,Boolean.TRUE);
    }
    
    /**
     * 활성화
     * @param v
     */
    protected void setEnabled(View v) {
		setEnabled(v);
    }    
    /**
     * 체크 체크해제
     * @param id
     * @param enable
     */
    protected void setChecked(int id,boolean enable) {
    	CompoundButton v = (CompoundButton) findViewById(id);
    	v.setChecked(enable);
    }
    
    /**
     * 체크 체크해제
     * @param id
	 */
    protected void setChecked(int id) {
    	setChecked(id,Boolean.TRUE);
    }

    /**
     * view class 반환.
     * @param id
     */
    protected View getView(int id) {
		View v = (View) findViewById(id);
		return v;
    }
    
    /**
     * activity 실행.
     * @param cls
     */
    protected void runActivity( Class<?> cls ) {
		Intent intent = new Intent( this, cls );
		startActivity( intent );
	}
    
    /**
     * 설치여부확인
     * @param packageName
     * @return
     */
	public boolean isInstalledApplication(String packageName) {
		PackageManager pm = getPackageManager();

		try {
			pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
    
	public void showMenu() {
		View anchor = (View) findViewById( R.id.btn_top1 );
		showMenu(anchor, R.menu.main);
	}
	// showmenu
	public void showMenu(View anchor, int menuRes) {
		//Context wrapper = new ContextThemeWrapper(getBaseContext(), R.style.PopupMenu);
		PopupMenu popup = new PopupMenu(this, anchor);
	    popup.setOnMenuItemClickListener((OnMenuItemClickListener) this);
	    popup.inflate(menuRes);
	    popup.show();
	}
	
	// intent
	public void goMap( String address ) {
		Uri uri = Uri.parse("geo:0,0?q=" + address );
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		//intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
		startActivity(intent);
	}
	
	public void goDial( String number ) {
		Uri uri = Uri.parse("tel:"+number);
		
		//Intent intent = new Intent(Intent.ACTION_CALL, urinumber);
    	Intent intent = new Intent(Intent.ACTION_DIAL, uri);
    	startActivity(intent);        		
	}
	
	public void beep() {
		float sound_volume;
		int sound_max_volume;
		float volume;
		
		AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
		sound_volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_DTMF);
		sound_max_volume = (int) audioManager.getStreamMaxVolume(AudioManager.STREAM_DTMF);
		
		volume = sound_volume / sound_max_volume * 100 ; 
		
//		Log.d("EWIRE","V:" + sound_volume);
//		Log.d("EWIRE","M:" + sound_max_volume);
//		Log.d("EWIRE","C:" + volume);
		
		// sometimes : AudioFinger could not create track
		ToneGenerator toneGenerator = new ToneGenerator(AudioManager.STREAM_SYSTEM, (int)volume);
		toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP);		
	}
	
	// toast
	public void toast( int msg) {
        String strMsg    = getString(msg);		
		toast(strMsg);
	}
	// toast
	public void toast( String value ) {
		Toast.makeText(this, value, Toast.LENGTH_LONG).show();
	}
	
    protected BaseActivity setError(EditText et, int msgid) {
    	setError(et,getString(msgid));
    	return this;
    }
    protected BaseActivity setError(EditText et, String errorMsg) {
    	et.setError(errorMsg);
    	return this;
    }
    protected BaseActivity setError(int id, int msgid) {
    	setError(id,getString(msgid));
    	return this;
    }
    
   	protected BaseActivity setError(int id, String errorMsg) {
    	View vw = (View) findViewById(id);
    	if ( vw instanceof EditText ) {
    		EditText et = (EditText) findViewById(id);
    		setError(et,errorMsg);
    	}
    	return this;    	
    }

	public void hideKeyboard() {
		try {
			imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
		} catch (Exception e) {
			View view = new View(this.getApplicationContext());
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
			e.printStackTrace();
		}
	}
	public void hideKeyboard(View view) {
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);		
	}
	// keyboard
	public void showKeyboard() {
		if( bUseImm )
		{
			TimerTask myTask = new TimerTask(){
				public void run(){
					if( imm != null )
						try {
							imm.showSoftInput(getCurrentFocus(), InputMethodManager.SHOW_FORCED);
						} catch (Exception e) {
							e.printStackTrace();
						}
				}	
			};
	
			Timer timer = new Timer();
			timer.schedule(myTask, 500);
		}
	}

	public String getString( TextView editText ) {
		return getValue(editText);
	}
	
	// log
	public void log( String value ) {
		Util.d( value );
	}
	
	public void saveVar() {
		AppContext.putValue("VAR", var);
	}
	
	public void readVar() {
		this.var = AppContext.getValue("VAR");
	}
}

