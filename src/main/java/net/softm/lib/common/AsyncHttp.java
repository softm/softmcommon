package net.softm.lib.common;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


import net.softm.lib.BaseActivity;
import net.softm.lib.Constant;
import net.softm.lib.R;
import net.softm.lib.Util;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * AsyncHttp
 * @param <R>
 * @author softm
 */
public abstract class AsyncHttp<R>
{
	private static final String TAG = AsyncHttp.class.getSimpleName();

	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}
	
	  @SuppressWarnings("unused")
	  private R returnValue;	 
	  @SuppressWarnings("unchecked")
	  public Class<R> getReturnClassType () {
//		  <R>.class
		  Type[] parameterizedTypes = ReflectionUtil.getParameterizedTypes(this);
		  Class<R> clazz = null;
			try {
				clazz = (Class<R>)ReflectionUtil.getClass(parameterizedTypes[0]);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			R parameter = null;
			try {
				parameter = (R) clazz.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			Util.d("[" + TAG + "] init DTO name : " + parameter.getClass().getName());
		  return (Class<R>) parameter.getClass();
//		  return ((Class<R>) returnValue.getClass());
//		  return (Class<R>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

//		  return ((Class<R>) returnValue.getClass());
//		  Type[] t = ((ParameterizedType) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getActualTypeArguments();
//		  return (Class<R>) t[0];
//		  return (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];		  
	  }
	  
	private Context context;
	private Object object = null; // for param	
	public AsyncHttp( Context context ) {
		this(context,AsyncHttpType.JSONELEMENT);
	}
	public AsyncHttp( Context context, AsyncHttpType a ) {
		this.context = context;
	}

	public AsyncHttp( Context context, String message ) {
		this.context = context;
	}

	public AsyncHttp( Context context, String message, Boolean statusbar ) {
		this.context = context;
	}

	public AsyncHttp( Context context, String message, Boolean statusbar, Object obj ) {
		this.context = context;
		this.object = obj;
	}
	
	public R send(AsyncHttpParam data,boolean async) {
		R rtn = null;		

		int httpCode=-1;
		String errMsg = null;
		try {
			String str="";
//			OkHttpClient client = new OkHttpClient();
			WebkitCookieManagerProxy proxy = new WebkitCookieManagerProxy();

			OkHttpClient client = new OkHttpClient.Builder().cookieJar(proxy)
					.connectTimeout(Constant.DEFAULT_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
					.writeTimeout(Constant.DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS)
					.readTimeout(Constant.DEFAULT_READ_TIMEOUT, TimeUnit.MILLISECONDS)
					.build();
			Util.i("[" + TAG + "] send request url [\"" + data.getSerivce()+ "\"] : " + data.getUrl());
//		new Request.Builder().url(data.getUrl())
//		.post(data.getFormBody()).build();

//			Request request = new Request.Builder().url(data.getUrl())
//					.post(data.getFormBody()).build();

			Request request = new Request.Builder().url(data.getUrl())
					.get().build();

			Response response;

			response = client.newCall(request).execute();
//			saveCookie(conn);

			httpCode = response.code();
				str = response.body().string();

			Util.i("[" + TAG + "] retrun string data [\"" + data.getSerivce()+ "\"] : " + str);
			JsonElement result = new JsonParser().parse(str).getAsJsonObject();
			Gson gson = new Gson();
			rtn = (R)gson.fromJson(result, getReturnClassType());
		} catch (SocketTimeoutException se) {
			httpCode = Constant.HTTP_CODE_NETWORK;
			errMsg = se.toString();
		} catch (Exception e) {
			errMsg = e.toString();
		} finally {
			if ( errMsg != null ) {
				try {
					rtn = (R) getReturnClassType ().newInstance();
					Method method = rtn.getClass().getMethod("setMsg", String.class);
					method.invoke(rtn,errMsg);// "Not connected"
					Method method2 = rtn.getClass().getMethod("setMsgCode", String.class);
					// httpCode가 없을경우(-1) ::> 404처리
					method2.invoke(rtn,httpCode!=-1?String.valueOf(httpCode):Constant.HTTP_CODE_NOT_FOUND);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		return rtn;
	}
	
	public void sync(AsyncHttpParam data) {
		if (android.os.Build.VERSION.SDK_INT > 9) {
			   StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			   StrictMode.setThreadPolicy(policy);
			}
		if( mThreadRunning == true )
			return;
		R rtn;		
		rtn = (R) send(data,false);
		complete(rtn);
		callback(rtn);
	}
	public R syncAs(AsyncHttpParam data) {
		if (android.os.Build.VERSION.SDK_INT > 9) {
		   StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		   StrictMode.setThreadPolicy(policy);
		}
		if( mThreadRunning == true )
			return null;
		return send(data,false);		
	}
	
	public void execute(AsyncHttpParam data) {
		if( !Connectivity.isConnected( this.context ) ) {
//	    	BaseActivity activity = (BaseActivity)context;
//			activity.toast(net.softm.lib.R.string.msg_network_not_connected);
			Toast.makeText(context, context.getString(net.softm.lib.R.string.msg_network_not_connected), Toast.LENGTH_SHORT).show();
			R rtn;
			try {
				rtn = (R) getReturnClassType ().newInstance();
				Method method = rtn.getClass().getMethod("setMsg", String.class);
				method.invoke(rtn,context.getString(net.softm.lib.R.string.msg_network_not_connected));// "Not connected"
				Method method2 = rtn.getClass().getMethod("setMsgCode", String.class);
				method2.invoke(rtn,String.valueOf(Constant.HTTP_CODE_NETWORK));
				complete((R)rtn);
//				activity.alert(net.softm.lib.R.string.msg_network_not_connected);
//				activity.stopProgressBar();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}
		if( mThreadRunning == true )
			return;
//		if (getReturnClassType ().equals(ArrayList.class)) {
			new AsyncHttpTask<R>().execute(data);
//		}
	}
	
	// require
	public abstract void complete(R result);
	
	public abstract void callback(R result);
	// thread
	boolean mThreadRunning = false;
	
	class AsyncHttpTask<RR> extends AsyncTask<AsyncHttpParam, Integer, R> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		@Override
    	protected R doInBackground(AsyncHttpParam... data) {
			try {
				return send(data[0],true);
			} catch (Exception e) {
				e.printStackTrace();
			}
    		return null;
    	}
    	
    	@Override
    	protected void onProgressUpdate(Integer... progress) {
//    		progressBar.setProgress(progress[0]);
    	}
    	
    	protected void onPostExecute(R result) {
			mThreadRunning = true;
//			hideDialog();
//			BaseActivity activity = (BaseActivity)context;
			if ( result == null ) { // TODO SOFTM : CHECK
				R rtn;
				try {
					Toast.makeText(context, context.getString(net.softm.lib.R.string.msg_network_sockettimeout), Toast.LENGTH_SHORT).show();
//					activity.toast(net.softm.lib.R.string.msg_network_sockettimeout);
					rtn = (R) getReturnClassType ().newInstance();
					Method method1 = rtn.getClass().getMethod("setMsgCode", String.class);
					method1.invoke(rtn,Constant.HTTP_CODE_SOCKETTIMEOUT);
					Method method2 = rtn.getClass().getMethod("setMsg", String.class);
					method2.invoke(rtn,context.getString(net.softm.lib.R.string.msg_network_sockettimeout)); // "소켓타임아웃에러.."
//					complete((R) rtn);
//					activity.alert(net.softm.lib.R.string.msg_network_sockettimeout);
//					context.stopProgressBar();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} else {
				R rtn = (R) result;
				try {
					Method method1 = rtn.getClass().getMethod("getMsgCode");
					Method method2 = rtn.getClass().getMethod("getMsg");
					String msgCode = (String)method1.invoke(rtn);
					String msg     = (String)method2.invoke(rtn);
					if ( String.valueOf(Constant.HTTP_CODE_SUCCESS).equals(msgCode)){
						complete(rtn);
						callback(rtn);
//					} else {
//						activity.alert(msg);						
					} else {
						if (String.valueOf(Constant.HTTP_CODE_NOT_FOUND).equals(msgCode)) {
//							activity.toast(net.softm.lib.R.string.msg_server_error_file_not_found);
//							Toast.makeText(context, context.getString(net.softm.lib.R.string.msg_server_error_file_not_found), Toast.LENGTH_SHORT).show();
							complete(rtn);
							callback(rtn);
						} else {
							complete(rtn);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
//				activity.stopProgressBar();
				
			}
			super.onPostExecute(result);    		
    	}
    	
    	@Override
		protected void onCancelled() {
			super.onCancelled();
		}
    }

	/**
	 * 쿠키값을 ConnectionManager에 저장한다.
	 * @param conn URLConnection 인스턴스
	 */
	public static void saveCookie(URLConnection conn) {
		String cookie = conn.getHeaderField("Set-Cookie");
		WLog.d(TAG, "saveCookie, cookie=" + cookie);
		if (cookie != null) {
			String url = conn.getURL().toString();
			CookieManager cookieManger = CookieManager.getInstance();
			cookieManger.setCookie(url, cookie);

			// permanent 영역에 쿠기를 즉시 동기화 한다.
			if (Build.VERSION.SDK_INT < 21) {
				CookieSyncManager.getInstance().sync();
			} else {
				cookieManger.flush();
			}
		}
	}
}