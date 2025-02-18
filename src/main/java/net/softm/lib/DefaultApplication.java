package net.softm.lib;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Handler;
import android.util.Log;

import java.util.HashMap;
import java.util.List;

/**
 * DefaultApplication
 * @author softm 
 */
public abstract class DefaultApplication extends Application {
	public List <String> loginInfo  ;
	public static final String TAG = "SOFTM";
	public static SQLiteDatabase db = null;
	public static DefaultApplication mInstance = null;
	public static SQLiteDatabase mSqliteDatabase = null;
	public Handler mHandler = new Handler();

	HashMap<String, Object> mStorage = new HashMap<String, Object>();
	
	/** 작업처리id : return proc_gid	in xml */
	private String jobId = null;
	public String getJobId() {
		return jobId;
	}
	
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	@Override
	public void onCreate() {
		super.onCreate();
        
        Log.i(TAG, "Application:Create ###############################");
		mInstance = this;
		// 전역변수 생성 초기화
		Var var = new Var();
		mStorage.put("VAR", var);        
	}

	public void onTerminate() {
		super.onTerminate();
        try {
        	if ( db != null ) {
        		db.close();
        	}
        } catch (SQLiteException se ) {
            Log.e(getClass().getSimpleName(), "error >> db.close();");
        } finally {
        	db = null; 
        }    		
	}

	//자동로그인 플래그 수집 
	public List <String> getLoginInfo() {
		return loginInfo ;
	}
	
	// 자동로그인해제
	public void setLoginInfo(String id, String passwd, String pointUrl, String openUrl, String key ) {
		loginInfo.add(0,id) ;
		loginInfo.add(1,passwd) ;
		loginInfo.add(2,pointUrl) ;
		loginInfo.add(3,openUrl) ;
		loginInfo.add(4,key) ; 
	}
	
	public void saveObject(String key,Object value) {
		mStorage.put(key, value);		
	}
	
	public Object loadObject(String key) {
		Object value = null;	
		value = mStorage.get(key);
		return value;
	}
	
	public Object removeObject(String key) {
		return mStorage.remove(key);
	}	
}
