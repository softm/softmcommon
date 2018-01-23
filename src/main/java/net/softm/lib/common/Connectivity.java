package net.softm.lib.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Connectivity
 * @author softm
 */
public class Connectivity {
	
	public static boolean isConnected(Context context) {

		ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		try {
			if (mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting())
				return true;
			else if(mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting()){
				return true;
			}else if(mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIMAX).isConnectedOrConnecting()){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			WLog.printException(e);
			return false;
		}
	}

}
