package cn.rectcircle.bindingsearch.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetWorkStateUtil {

	public static final int NO_NETWORK = -1;
	public static final int NETWORK_WIFI = 1;
	public static final int NETWORK_WAP = 2;
	public static final int NETWORK_NET = 3;


	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	public static boolean isWifiConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mWiFiNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (mWiFiNetworkInfo != null) {
				return mWiFiNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	public static boolean isMobileConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mMobileNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (mMobileNetworkInfo != null) {
				return mMobileNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	//返回值 -1：没有网络  1：WIFI网络2：wap网络3：net网络
	public static int GetNetype(Context context)
	{
		int netType = -1;
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if(networkInfo==null)
		{
			return netType;
		}
		int nType = networkInfo.getType();
		if(nType==ConnectivityManager.TYPE_MOBILE)
		{
			if(networkInfo.getExtraInfo().toLowerCase().equals("cmnet"))
			{
				netType = 3;
			}
			else
			{
				netType = 2;
			}
		}
		else if(nType==ConnectivityManager.TYPE_WIFI)
		{
			netType = 1;
		}
		return netType;
	}

}
