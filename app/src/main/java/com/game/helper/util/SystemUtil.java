package com.game.helper.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.PowerManager;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

/**
 * @Description 手机系统相关工具类
 * @CreateDate 2016-9-24下午3:40:00
 * @CreateBy lbb
 */
public class SystemUtil {

	private static PackageManager packageManager;

	private static TelephonyManager telephonyManager;

	public static final int NETWORK_TYPE_WIFI = 2;
	public static final int NETWORK_TYPE_WAP = 1;
	public static final int NETWORK_TYPE_NET = 0;

	/**
	 * 根据包名获取PackageInfo
	 * 
	 * @param context
	 * @param packageName 包名
	 * @return PackageInfo
	 */
	public static PackageInfo getPackageInfo(Context context, String packageName) {
		try {
			packageManager = context.getPackageManager();
			List<PackageInfo> installedAppInfo = packageManager.getInstalledPackages(PackageManager.GET_PROVIDERS);

			for (PackageInfo packageInfo : installedAppInfo) {

				String name = packageInfo.packageName;
				if (name != null && name.equals(packageName)) {
					return packageInfo;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 获取版本号
	 * 
	 * @param context
	 * @return 版本号
	 */
	public static String getAppVersionName(Context context) {
		try {
			PackageInfo info = null;
			try {
				if(context==null){
					return "1.0.0";
				}
				if (packageManager == null)
					packageManager = context.getPackageManager();
				if(packageManager!=null){
					if(context.getPackageName()!=null){
						info = packageManager.getPackageInfo(context.getPackageName(), 0);
					}
				}
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
			if (info != null) {
				return info.versionName;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		}
		
		return "1.0.0";
	}

	/**
	 * 获取当前应用程序的versionCode
	 * **/
	public static int getAppVersionCode(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			int versionCode = packInfo.versionCode;
			return versionCode;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		}
		return 0;
	}

	/**
	 * 获取手机mac地址
	 * 
	 * @param context
	 * @return mac地址
	 */
	public static String getMac(Context context) {
		try {

			if (telephonyManager == null) {
				telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			}
			return telephonyManager.getDeviceId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	/**
	 * 设备mac地址
	 * 
	 * @param context
	 * @return mac地址
	 */
	public static String getMac() {
		String macSerial = null;
		String str = "";
		try {
			Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address");
			InputStreamReader ir = new InputStreamReader(pp.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);

			for (; null != str;) {
				str = input.readLine();
				if (str != null) {
					macSerial = str.trim();// 去空格
					break;
				}
			}
		} catch (IOException ex) {
			// 赋予默认值
			ex.printStackTrace();
		}
		return macSerial;
	}
	/**
	 * 设备mac地址
	 * @param context
	 * @return mac地址
	 */
	public static String getLocalMacAddress() {  
		String mac = "";
		try{  
			String path="sys/class/net/eth0/address";  
			FileInputStream fis_name = new FileInputStream(path);  
//			byte[] buffer_name = new byte[1024*8];
			byte[] buffer_name = new byte[1024*50];
			int byteCount_name = fis_name.read(buffer_name);
			if(byteCount_name>0)  
			{  
				mac = new String(buffer_name, 0, byteCount_name, "utf-8");  
			}  

			if(mac.length()==0||mac==null){  
				path="sys/class/net/eth0/wlan0";  
				FileInputStream fis = new FileInputStream(path);  
//				byte[] buffer = new byte[1024*8];
				byte[] buffer = new byte[1024*50];
				int byteCount = fis.read(buffer);
				if(byteCount>0)  
				{  
					mac = new String(buffer, 0, byteCount, "utf-8");  
				}  
			}  

			if(mac.length()==0||mac==null){  
				return "";  
			}  
		}catch(Exception io){  
			io.printStackTrace();
		}  
		return mac.trim();  
	}   
	/**
	 * 获取手机品牌
	 * 
	 * @return 手机品牌
	 */
	public static String getBrand() {
		try {

			return android.os.Build.BRAND.replace(' ', '_');
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取手机型号
	 * 
	 * @return 手机型号
	 */
	public static String getModel() {
		return android.os.Build.MODEL.replace(' ', '_');
	}

	/**
	 * 获取系统版本号
	 * 
	 * @return 系统版本号
	 */
	public static String getSystemVersionInfo() {
		return android.os.Build.VERSION.RELEASE;
	}

	/**
	 * 获取运营商（移动 电信 联通 其它）
	 * 
	 * @param context
	 * @return 运营商
	 */
	public static String getSimCardInfo(Context context) {
		if (telephonyManager == null) {
			telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		}
		String idInfo = telephonyManager.getSimOperator();
		StringBuffer otherInfo = new StringBuffer();
		try {
			if (idInfo.substring(0, 3).equals("460")) {
				otherInfo.append("中国");
				if (idInfo.substring(3, idInfo.length()).equals("00")
						|| idInfo.substring(3, idInfo.length()).equals("02"))
					otherInfo.append("移动");
				else if (idInfo.substring(3, idInfo.length()).equals("01"))
					otherInfo.append("联通");
				else if (idInfo.substring(3, idInfo.length()).equals("03"))
					otherInfo.append("电信");
				else
					otherInfo.append("原始代码：" + idInfo);
			} else {
				otherInfo.append("原始代码：" + idInfo);
			}
		} catch (Exception e) {
			// TODO: handle exception
			otherInfo.append("Unknow");
		}

		return otherInfo.toString();
	}

	/**
	 * 根据colorId返回颜色值
	 * 
	 * @param context
	 * @param colorId
	 * @return 颜色值
	 */
	public static ColorStateList getColorFromXml(Context context, int colorId) {
		Resources resource = context.getResources();
		ColorStateList csl = (ColorStateList) resource.getColorStateList(colorId);
		return csl;
	}

	/**
	 * 手机震动（须在AndroidManifest.xml配置权限）
	 * 
	 * @param activity 调用页面
	 * @param milliseconds 震动时长
	 */
	public static void Vibrate(final Activity activity, long milliseconds) {

		Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
		vib.vibrate(milliseconds);
	}

	/**
	 * 获取SDCard路径
	 * 
	 * @return SDCard路径
	 */
	public static String getSDCardPath() {
		// 判断SdCard是否存在并且是可用的
		if (ExistSDCard()) {
			return Environment.getExternalStorageDirectory().toString();
		} else {
			return null;
		}
	}

	/**
	 * 获取当前网络类型
	 * 
	 * @param context
	 * @return 0-手机网络net方式 1-手机网络wap方式 2-wifi网络
	 * @throws IOException
	 */
	public static int checkNetType(Context context) {
		if (SystemUtil.isMobileActive(context)) { // 手机网络
			String proxyHost = android.net.Proxy.getDefaultHost();

			if (proxyHost == null) { // net方式
				return NETWORK_TYPE_NET;
			} else {// wap方式
				return NETWORK_TYPE_WAP;
			}
		} else { // wifi网络
			return NETWORK_TYPE_WIFI;
		}
	}

	/**
	 * 判断是否为手机网络
	 * 
	 * @param ctx context
	 * @return true-手机网络 false-非手机网络
	 */
	public static boolean isMobileActive(Context ctx) {
		try {
			ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mobileNet = cm.getActiveNetworkInfo();
			if (mobileNet != null && mobileNet.getType() == ConnectivityManager.TYPE_MOBILE) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * 判断网络连接是否可用
	 * 
	 * @param ctx context
	 * @return 
	 */
	public static boolean isNetworkAvailable(Context context) {   
		ConnectivityManager cm = (ConnectivityManager) context   
				.getSystemService(Context.CONNECTIVITY_SERVICE);   
		if (cm == null) {   
		} else {
			//如果仅仅是用来判断网络连接
			//则可以使用 cm.getActiveNetworkInfo().isAvailable();  
			NetworkInfo[] info = cm.getAllNetworkInfo();   
			if (info != null) {   
				for (int i = 0; i < info.length; i++) {   
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {   
						return true;   
					}
				}
			}
		}
		return false;  
	}
	/**
	 * 判断是否联网
	 * 
	 * @param context
	 * @return true-已联网 false-未联网
	 */
	public static boolean getNetworkStatus(Context context) {
		try {
			ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = cm.getActiveNetworkInfo();    
			if (info != null && info.isConnected()) {     
				if (info.getState() == NetworkInfo.State.CONNECTED) {     
					return true;     
				}     
			} 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			//ToastUtil.showToast(context, "网络异常");
			return false;
		}
		//ToastUtil.showToast(context, "网络异常");
		return false;
	}

	private static DisplayMetrics GetDisplayMetrics(Activity activity) {
		DisplayMetrics displayMetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		displayMetrics = activity.getResources().getDisplayMetrics();
		return displayMetrics;
	}

	/**
	 * 获取屏幕宽度,返回值为像素
	 * 
	 * @param displayMetrics2
	 * @return
	 */
	public static int getWidth(Activity activity) {
		int width;
		width = GetDisplayMetrics(activity).widthPixels;
		return width;
	}

	/**
	 * 获取屏幕scale值
	 * 
	 * @param context
	 * @return
	 */
	public static float getScale(Activity activity) {
		return GetDisplayMetrics(activity).density;
	}

	/**
	 * 获取屏幕宽度,返回值为像素
	 * 
	 * @param displayMetrics2
	 * @return
	 */
	public static int getHeight(Activity activity) {
		// TODO Auto-generated method stub
		int height;
		height = GetDisplayMetrics(activity).heightPixels;
		return height;
	}

	/**
	 * 判断网路异常
	 * 
	 * @param context
	 * @param isToast 是否显示网络异常提示
	 * @return ture 有网络 false 无网络
	 */
	public static boolean hasNetWorkToast(Context context, boolean isToast) {
		if (SystemUtil.getNetworkStatus(context)) {
			return true;
		} else {
			if (isToast) {
				ToastUtil.showToast(context, "网络异常");
			}
			return false;
		}
	}

	/**
	 * 判断服务是否运行.
	 * 
	 * @param context
	 * @param className 判断的服务名字
	 * @return true -- 在运行 false -- 不在运行
	 */
	public static boolean isServiceRunning(Context mContext, String className) {
		boolean isRunning = false;
		ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(30);
		if (!(serviceList.size() > 0)) {
			return false;
		}
		for (int i = 0; i < serviceList.size(); i++) {
			if (serviceList.get(i).service.getClassName().equals(className) == true) {
				isRunning = true;
				break;
			}
		}
		return isRunning;
	}
	public static boolean isActRunning(Context mContext, String className) {
		boolean isRunning = false;
		try {
			ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
			List<ActivityManager.RunningTaskInfo> serviceList = activityManager.getRunningTasks(30);
			if (!(serviceList.size() > 0)) {
				return false;
			}
			for (int i = 0; i < serviceList.size(); i++) {
				if (serviceList.get(i).topActivity.getClassName().equals(className) == true) {
					isRunning = true;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return isRunning;
	}
	/**
	 * 
	 * @param IntentFlag
	 * @param valueId
	 * @param objectActivity
	 */
	public static void startActivity(Context context, String IntentFlag, int valueId, Class<?> objectActivity) {
		Intent intent = new Intent(context, objectActivity);
		intent.putExtra(IntentFlag, valueId);
		context.startActivity(intent);
	}

	/**
	 * 进入详情界面前判断是否为空
	 * 
	 * @param context
	 * @param value
	 * @return ture 网络正常 输入正常
	 */
	public static boolean InputCheck(Context context, String value) {
		if (SystemUtil.hasNetWorkToast(context, true)) {
			if (StringUtil.isNotNullString(value)) {
				return true;
			} else {
				ToastUtil.showToast(context, "输入正确的查询内容");
				return false;
			}
		} else {
			ToastUtil.showToast(context, "网络异常");
			return false;
		}
	}

	public static boolean ExistSDCard() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else
			return false;
	}

	public static void unlock(Context context){
		try {
			KeyguardManager km= (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);  
			// 获取KeyguardLock对象
			KeyguardLock kl = km.newKeyguardLock("unlock");
			// 解锁
			kl.disableKeyguard();
			// 获取WakeLock对象
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void bright(Context context){
		try {
			PowerManager pm = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
			PowerManager.WakeLock wl = pm.newWakeLock(
					PowerManager.ACQUIRE_CAUSES_WAKEUP
					| PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
			// 点亮屏幕
			wl.acquire();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取清单文件张Meta-Data的Value值
	 */
	public static String getMetaValue(Context context, String metaKey) {

		if (context == null || metaKey == null) {
			return null;
		}

		try {
			ApplicationInfo aiApplicationInfo = context.getPackageManager().getApplicationInfo(
					context.getPackageName(), PackageManager.GET_META_DATA);

			if (null != aiApplicationInfo) {
				if (null != aiApplicationInfo.metaData) {
					return aiApplicationInfo.metaData.getString(metaKey);
				}
			}

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	// 获取程序自身包名
	public static String getAppPackageName(Context context) {
		try {
			String pkName = context.getPackageName();
			return pkName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取手机操作系统版本
	 * @return
	 * @author SHANHY
	 * @date   2015年12月4日
	 */
	public static int getSDKVersionNumber() {  
		int sdkVersion;  
		try {  
			sdkVersion = Integer.valueOf(android.os.Build.VERSION.SDK);  
		} catch (NumberFormatException e) {  
			e.printStackTrace();
			sdkVersion = 0;  
		}  
		return sdkVersion;  
	}  

}
