package net.softm.lib.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;

/**
 * Connectivity
 * @author softm
 */
public class Connectivity {
	
	public static boolean isConnected_(Context context) {

		ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wimax = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIMAX);
		try {
			if (mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)!=null && mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting())
				return true;
			else if(mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)!=null &&  mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting()){
				return true;
			}else if(wimax!=null && wimax.isConnectedOrConnecting()){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			WLog.printException(e);
			return false;
		}
	}
	public static boolean isConnected(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivityManager != null) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				// Android Marshmallow (API 23) 이상에서 사용
				android.net.Network network = connectivityManager.getActiveNetwork();
				NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
				return capabilities != null && (
						capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
								capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
								capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
				);
			} else {
				// Android Lollipop (API 21) 이하에서 사용
				android.net.NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
				return activeNetwork != null && activeNetwork.isConnected();
			}
		}
		return false;
	}

	public static boolean isMobileDataConnected(Context context) {
		ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		try {
			if (mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			WLog.printException(e);
			return false;
		}
	}

	public static boolean isSimSupport(Context context)
	{
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);  //gets the current TelephonyManager
		return !(tm.getSimState() == TelephonyManager.SIM_STATE_ABSENT);
	}



	private boolean haveNetworkConnection(Context ctx) {
		boolean haveConnectedWifi = false;
		boolean haveConnectedMobile = false;

		ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		for (NetworkInfo ni : netInfo) {
			if (ni.getTypeName().equalsIgnoreCase("WIFI"))
				if (ni.isConnected())
					haveConnectedWifi = true;
			if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
				if (ni.isConnected())
					haveConnectedMobile = true;
		}
		return haveConnectedWifi || haveConnectedMobile;
	}


}
