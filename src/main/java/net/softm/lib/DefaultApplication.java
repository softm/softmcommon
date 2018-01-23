package net.softm.lib;

import android.app.Activity;
import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * DefaultApplication
 * @author softm 
 */
public abstract class DefaultApplication extends Application {
	public List <String> loginInfo  ;
	public static final String TAG = "CITIS";	
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
        // register to be informed of activities starting up
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(Activity activity, 
                                          Bundle savedInstanceState) {

                // new activity created; force its orientation to portrait
//                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                
                try {
                	setJobId(activity.getApplicationContext().getResources().getString(R.string.proc_gid));
                	Constant.WORK_DIR     = Constant.SD_DIR + File.separator+ getJobId();
//                	Constant.TMP_DIR      = Constant.SD_DIR + File.separator+ getJobId() + File.separator+ "tmp";
//                	Constant.PIC_DIR      = Constant.SD_DIR + File.separator+ getJobId() + File.separator+ "pic";
                	Util.createWorkDir();
                    Log.i(Constant.LOG_TAG,"onActivityCreated - JOB_ID : " + getJobId());
                } catch (SQLiteException se ) {
                    Log.e(getClass().getSimpleName(), "error >> DefaultApplication;");
                } finally {
                }                
            }

			@Override
			public void onActivityStarted(Activity activity) {
				
			}

			@Override
			public void onActivityResumed(Activity activity) {
				
			}

			@Override
			public void onActivityPaused(Activity activity) {
				
			}

			@Override
			public void onActivityStopped(Activity activity) {
				
			}

			@Override
			public void onActivitySaveInstanceState(Activity activity,
					Bundle outState) {
				
			}

			@Override
			public void onActivityDestroyed(Activity activity) {
//                try {
//                	if ( db != null ) {
//                		db.close();
//                	}
//                } catch (SQLiteException se ) {
//                    Log.e(getClass().getSimpleName(), "error >> db.close();");
//                } finally {
//                	db = null; 
//                    dbHelper = null;                	
//                }                
			}
        });		
        
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
