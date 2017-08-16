package com.game.helper.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

public class APNManager {

	public static final Uri PREFERRED_APN_URI;

	private String mApn; // 接入点名称

	private int mPort; // 端口号

	private String mProxy; // 代理服务器

	private boolean mUseWap; // 是否正在使用WAP

	static {
		PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn"); // 取得当前设置的APN
	}

	public APNManager(Context context) {
		try {
			checkNetworkType(context);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (SystemUtil.isMobileActive(context)) { // 手机网络
				String proxyHost = android.net.Proxy.getDefaultHost();
				if (proxyHost == null) { // net方式
					mUseWap = false;
				} else {// wap方式
					mUseWap = true;
					mProxy = proxyHost;
					mPort = android.net.Proxy.getDefaultPort();
				}
			} else { // wifi网络
				mUseWap = false;
			}
			
		}
	}

	/**
	 * 获得当前设置的APN相关参数
	 * 
	 * @param context
	 */
	private void checkApn(Context context) {
		ContentResolver contentResolver = context.getContentResolver();
		Uri uri = PREFERRED_APN_URI;
		String[] apnInfo = new String[3];
		apnInfo[0] = "apn";
		apnInfo[1] = "proxy";
		apnInfo[2] = "port";

		Cursor cursor = contentResolver.query(uri, apnInfo, null, null, null);
		if (cursor != null) {
			while (cursor.moveToFirst()) {
				this.mApn = cursor.getString(cursor.getColumnIndex("apn"));
				this.mProxy = cursor.getString(cursor.getColumnIndex("proxy"));
				this.mPort = cursor.getInt(cursor.getColumnIndex("port"));

				// 代理为空
//				if ((this.mProxy == null) || (this.mProxy.length() <= 0)) {
					String apn = this.mApn.toUpperCase();
					// 中国移动WAP设置：APN：CMWAP；代理：10.0.0.172；端口：80
					// 中国联通WAP设置：APN：UNIWAP；代理：10.0.0.172；端口：80
					// 中国联通WAP设置（3G）：APN：3GWAP；代理：10.0.0.172；端口：80
					if ((apn.equals("CMWAP")) || (apn.equals("UNIWAP"))
							|| (apn.equals("3GWAP"))) {
						this.mUseWap = true;
						this.mProxy = "10.0.0.172";
						this.mPort = 80;
					// 中国电信WAP设置：APN(或者接入点名称)：CTWAP；代理：10.0.0.200；端口：80
					}else if (apn.equals("CTWAP")) {
						this.mUseWap = true;
						this.mProxy = "10.0.0.200";
						this.mPort = 80;
					}else{
						this.mPort = 80;
						this.mUseWap = false;
					}
//				}
				break;
			}
		}else{
			this.mUseWap = false;
		}
		cursor.close();
	}

	/**
	 * 检测当前使用的网络类型是WIFI还是WAP
	 * 
	 * @param context
	 */
	private void checkNetworkType(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		if(connectivityManager != null){
			NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isAvailable()) {
				if (!"wifi".equals(networkInfo.getTypeName().toLowerCase())) {
					checkApn(context);
					return;
				}
			}
			this.mUseWap = false;
		}
	}

	/**
	 * 判断当前网络连接状态
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		NetworkInfo networkInfo = ((ConnectivityManager) context
				.getApplicationContext().getSystemService(
						Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
		if (networkInfo != null) {
			return networkInfo.isConnectedOrConnecting();
		}
		return false;
	}

	public String getApn() {
		return this.mApn;
	}

	public String getProxy() {
		return this.mProxy;
	}

	public int getProxyPort() {
		return this.mPort;
	}

	public boolean isWapNetwork() {
		return this.mUseWap;
	}
}