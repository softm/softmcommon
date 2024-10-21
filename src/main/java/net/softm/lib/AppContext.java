package net.softm.lib;

import android.content.Context;
import android.content.SharedPreferences;
/**
 * AppContext
 * @author softm 
 */
public class AppContext {
	public static final String PREF_KEY = "SOFTM";
	
	public static Context getContext() {
		return DefaultApplication.mInstance;
	}
	
	/* AppContext Storage: Data Transfer  */
	public static void putValue(Class<?> cls,Object value) {
		String key = cls.getName();
		DefaultApplication.mInstance.saveObject(key, value);
	}
	
	public static<T> T getValue(Class<?> cls) {
		String key = cls.getName();
		T t = (T)DefaultApplication.mInstance.loadObject(key);	 
		return t;
	}

	//TO store key-values
	public static void putValue(String key,Object value) {
		DefaultApplication.mInstance.saveObject(key, value);
	}
	
	public static<T> T getValue(String key) {
		T t = (T)DefaultApplication.mInstance.loadObject(key);	 
		return t;
	}
	
	public static<T> T removeValue(Class<?> cls) {
		String key = cls.getName();
		T t = (T)DefaultApplication.mInstance.removeObject(key);	 
		return t;
	}

	public static<T> T remove(String key) {
		T t = (T)DefaultApplication.mInstance.removeObject(key);	 
		return t;
	}
	
	//Shared Preference 
	public static SharedPreferences getAppPref() {
		SharedPreferences pref = getContext().getSharedPreferences(PREF_KEY, 0);
		return pref;
	}

	//To redirect  to UI Thread
	public static void post(Runnable r) {
		DefaultApplication.mInstance.mHandler.post(r);
	}
	
	public static void postDelayed(Runnable r,int millis) {
		DefaultApplication.mInstance.mHandler.postDelayed(r,millis);
	}
	
//	// UI Conversion
//	public static int dp2px(float dp) {
//		return (int)(WApplication.mInstance.mDesity * dp);
//	}
//	
//	public static int getScreenWidth() {
//		return WApplication.mInstance.mDisplay.getWidth();
//	}
}
