package com.game.helper.receiver;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.activity.LoginActivity;
import com.game.helper.activity.MainActivity;
import com.game.helper.activity.WelcomeActivity;
import com.game.helper.activity.mine.MineSystemMsgActivity;
import com.game.helper.db.DBComm;
import com.game.helper.db.manager.DBManager;
import com.game.helper.installPackage.MonitorSysReceiver;
import com.game.helper.interfaces.comm.CommValues;
import com.game.helper.model.PushModel;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.util.SystemUtil;

import java.util.Iterator;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver implements DBComm,CommValues{
	private static final String TAG = "JPush";

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		Log.d(TAG, "[MyReceiver] onReceive - " + intent.getExtras().toString() + ", extras: "+ printBundle1(context,bundle) );//

		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
			String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
			Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
			//send the Registration Id to your server...

		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
			Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
			//processCustomMessage(context, bundle);
			PushModel mPushModel=printBundle(context,bundle);
			LoginData user=DBManager.getInstance(context).getUserMessage();
			mPushModel.userId=user.userId;
			mPushModel.content=bundle.getString(JPushInterface.EXTRA_MESSAGE);
			DBManager.getInstance(context).insert(mPushModel);
			context.sendBroadcast(new Intent(ACTION_GET_INFO));
		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
			Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
			int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
			Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
			PushModel mPushModel=printBundle(context,bundle);
			LoginData user=DBManager.getInstance(context).getUserMessage();
			mPushModel.userId=user.userId;
			DBManager.getInstance(context).insert(mPushModel);
			context.sendBroadcast(new Intent(ACTION_GET_INFO));

		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
			Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
			Log.d(TAG, "[MyReceiver] 用户点击打开了通知 - " + intent.getExtras().toString() + ", extras: "+ printBundle1(context,bundle) );//
			PushModel mPushModel=printBundle(context,bundle);
			int currIndex=0;
			if(mPushModel!=null&&!TextUtils.isEmpty(mPushModel.tradetype)){
				if(mPushModel.tradetype.equals("6")||mPushModel.tradetype.equals("7")||mPushModel.tradetype.equals("8")){
					//充值
					currIndex=2;
				}else{
					currIndex=1;
				}
			}
			if(bundle==null){
				bundle=new Bundle();
			}
			bundle.putInt("currIndex", currIndex);
			try {

				if(isAppAlive(context, context.getPackageName())){
					if(SystemUtil.isActRunning(BaseApplication.mInstance.context, LoginActivity.class.getName())){

					}else if(SystemUtil.isActRunning(BaseApplication.mInstance.context, WelcomeActivity.class.getName())){

					}else{
						/*if(!SystemUtil.isActRunning(BaseApplication.mInstance.context, MainActivity.class.getName())){
							//濡傛灉瀛樻椿鐨勮瘽锛屽氨鐩存帴鍚姩DetailActivity锛屼絾瑕佽�冭檻涓�绉嶆儏鍐碉紝灏辨槸app鐨勮繘绋嬭櫧鐒朵粛鐒跺湪
							//浣員ask鏍堝凡缁忕┖浜嗭紝姣斿鐢ㄦ埛鐐瑰嚮Back閿��鍑哄簲鐢紝浣嗚繘绋嬭繕娌℃湁琚郴缁熷洖鏀讹紝濡傛灉鐩存帴鍚姩
							//DetailActivity,鍐嶆寜Back閿氨涓嶄細杩斿洖MainActivity浜嗐�傛墍浠ュ湪鍚姩
							//DetailActivity鍓嶏紝瑕佸厛鍚姩MainActivity銆�
							Log.i("NotificationReceiver", "the app process is alive");
							Intent mainIntent = new Intent(context, MainActivity.class);
							//灏哅ainAtivity鐨刲aunchMode璁剧疆鎴怱ingleTask, 鎴栬�呭湪涓嬮潰flag涓姞涓奍ntent.FLAG_CLEAR_TOP,
							//濡傛灉Task鏍堜腑鏈塎ainActivity鐨勫疄渚嬶紝灏变細鎶婂畠绉诲埌鏍堥《锛屾妸鍦ㄥ畠涔嬩笂鐨凙ctivity閮芥竻鐞嗗嚭鏍堬紝
							//濡傛灉Task鏍堜笉瀛樺湪MainActivity瀹炰緥锛屽垯鍦ㄦ爤椤跺垱寤�
							mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
							mainIntent.setAction(Intent.ACTION_MAIN);
							mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP); 

							Intent detailIntent = new Intent(context, MineSystemMsgActivity.class);
							Intent[] intents = {mainIntent, detailIntent};
							//context.startActivities(intents);
							context.getApplicationContext().startActivities(intents); 
						}else{

						}
						 */
						if(!SystemUtil.isActRunning(BaseApplication.mInstance.context, MineSystemMsgActivity.class.getName())){
							//打开自定义的Activity
							Intent i = new Intent(context, MineSystemMsgActivity.class);
							i.putExtras(bundle);
							//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
							((BaseActivity)BaseApplication.mInstance.context).startActivity(i);
							//((BaseActivity)BaseApplication.mInstance.context).startActivity(MineSystemMsgActivity.class);
						}
					}
				}else {
					if(MonitorSysReceiver.checkApkExist(context,"com.game.helper" , null)){
						if(context!=null){
							if( context.getApplicationContext()!=null){
								PackageManager packageManager = context.getApplicationContext().getPackageManager();  
								if(packageManager!=null){
									Intent resultIntent=new Intent();   
									resultIntent =packageManager.getLaunchIntentForPackage("com.game.helper");   
									if(resultIntent!=null){  
										context.startActivity(resultIntent); 
									}  
								}
							}
						}
						
					}
					
					/*Intent resultIntent = new Intent(context, WelcomeActivity.class);  
					resultIntent.addCategory(Intent.CATEGORY_LAUNCHER);
					resultIntent.setAction(Intent.ACTION_MAIN);
					resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);  
					context.startActivity(resultIntent);*/
				}

				/*if(SystemUtil.isActRunning(BaseApplication.mInstance.context, LoginActivity.class.getName())){

				}else  if(SystemUtil.isActRunning(BaseApplication.mInstance.context, WelcomeActivity.class.getName())){

				}else {
					if(!SystemUtil.isActRunning(BaseApplication.mInstance.context, MainActivity.class.getName())){

					}
				}*/



			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
			Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
			//在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

		} else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
			boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
			Log.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
		} else {
			Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
		}
	}

	// 打印所有的 intent extra 数据
	private static PushModel printBundle(Context context,Bundle bundle) {
		PushModel push =new PushModel();

		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				push.pushId=""+bundle.getInt(key);
				sb.append("\nkey:" + key + ",EXTRA_NOTIFICATION_ID value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", EXTRA_CONNECTION_CHANGE value:" + bundle.getBoolean(key));
			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
					Log.i(TAG, "This message has no Extra data");
					continue;
				}
				try {
					JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
					Iterator<String> it =  json.keys();

					while (it.hasNext()) {
						String myKey = it.next().toString();

						if(!TextUtils.isEmpty(myKey)&&myKey.equals("content")){
							push.content=json.optString(myKey);
						}else if(!TextUtils.isEmpty(myKey)&&myKey.equals("id")){
							push.id=json.optString(myKey);
						}else if(!TextUtils.isEmpty(myKey)&&myKey.equals("tradedate")){
							push.tradedate=json.optString(myKey);
						}else if(!TextUtils.isEmpty(myKey)&&myKey.equals("tradetype")){
							push.tradetype=json.optString(myKey);
						}/*else if(!TextUtils.isEmpty(myKey)&&myKey.equals("userId")){
							push.userId=json.optString(myKey);
						}*/
						sb.append("\nkey:" + key + ", EXTRA_EXTRA value: [" +
								myKey + " - " +json.optString(myKey) + "]");
					}
				} catch (JSONException e) {
					Log.e(TAG, "Get message extra JSON error!");
				}

			}else if (key.equals(JPushInterface.EXTRA_ALERT)) {
				push.content= bundle.getString(key);
			}else if (key.equals(JPushInterface.EXTRA_MSG_ID)) {
				push.pushId=""+bundle.getString(key);
			}else if (key.equals(JPushInterface.EXTRA_NOTIFICATION_TITLE)) {
				push.title=""+bundle.getString(key);
			}else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}

		return push;
	}
	// 打印所有的 intent extra 数据
	private static String printBundle1(Context context,Bundle bundle) {
		PushModel push =new PushModel();

		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				push.pushId=""+bundle.getInt(key);
				sb.append("\nkey:" + key + ",EXTRA_NOTIFICATION_ID value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){

				sb.append("\nkey:" + key + ", EXTRA_CONNECTION_CHANGE value:" + bundle.getBoolean(key));
			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
					Log.i(TAG, "This message has no Extra data");
					continue;
				}

				try {
					JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
					Iterator<String> it =  json.keys();

					while (it.hasNext()) {
						String myKey = it.next().toString();

						if(!TextUtils.isEmpty(myKey)&&myKey.equals("content")){
							push.content=json.optString(myKey);
						}else if(!TextUtils.isEmpty(myKey)&&myKey.equals("id")){
							push.id=json.optString(myKey);
						}else if(!TextUtils.isEmpty(myKey)&&myKey.equals("tradedate")){
							push.tradedate=json.optString(myKey);
						}else if(!TextUtils.isEmpty(myKey)&&myKey.equals("tradetype")){
							push.tradetype=json.optString(myKey);
						}/*else if(!TextUtils.isEmpty(myKey)&&myKey.equals("userId")){
								push.userId=json.optString(myKey);
							}*/
						sb.append("\nkey:" + key + ", EXTRA_EXTRA value: [" +
								myKey + " - " +json.optString(myKey) + "]");
					}
				} catch (JSONException e) {

					Log.e(TAG, "Get message extra JSON error!");
				}

			}else if (key.equals(JPushInterface.EXTRA_ALERT)) {
				push.content= bundle.getString(key);
			}else if (key.equals(JPushInterface.EXTRA_MSG_ID)) {
				push.pushId=""+bundle.getString(key);
			}else if (key.equals(JPushInterface.EXTRA_NOTIFICATION_TITLE)) {
				push.title=""+bundle.getString(key);
			}else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}

		return sb.toString();
	}
	//send msg to MainActivity
	private void processCustomMessage(Context context, Bundle bundle) {
		/*if (MainActivity.isForeground) {

		}*/
		String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
		String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
		/*Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
		msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
		if (!TextUtils.isEmpty(extras)) {
			try {
				JSONObject extraJson = new JSONObject(extras);
				if (null != extraJson && extraJson.length() > 0) {
					msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
				}
			} catch (JSONException e) {

			}

		}
		context.sendBroadcast(msgIntent);*/
	}
	/**
	 * 
	 * @param context ontext
	 * @param packageName
	 * @return boolean
	 */
	public static boolean isAppAlive(Context context, String packageName){
		ActivityManager activityManager =(ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> processInfos= activityManager.getRunningAppProcesses();
		for(int i = 0; i < processInfos.size(); i++){
			if(processInfos.get(i).processName.equals(packageName)){
				Log.i("NotificationLaunch",
						String.format("the %s is running, isAppAlive return true", packageName));
				return true;
			}
		}
		Log.i("NotificationLaunch",
				String.format("the %s is not running, isAppAlive return false", packageName));
		return false;
	}
}
