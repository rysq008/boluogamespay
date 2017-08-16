package com.game.helper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.game.helper.interfaces.comm.CommValues;
import com.game.helper.interfaces.comm.HttpComm;
import com.game.helper.view.dialog.MyAlertDailog;
import com.game.helper.view.dialog.MyAlertDailog.onClickLeftListener;
import com.game.helper.view.dialog.MyAlertDailog.onClickRightListener;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
/**
 * @Description
 * @Path 
 * @Author lbb
 * @Date 2016年11月02日 下午1:41:24
 * @Company 
 */

public abstract class BaseActivity extends BbxBaseActivity implements OnClickListener,CommValues, HttpComm {
	public BroadcastReceiver receiver;

	public BaseApplication mApplication;
	public MyAlertDailog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mApplication=(BaseApplication) getApplication();
		mApplication.context=this;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			Window window = getWindow();
			// Translucent status bar
			window.setFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
		super.onCreate(savedInstanceState);
		dialog = new MyAlertDailog(this);
		dialog.setTitle("温馨提醒");
		dialog.setContent1("网络已断开，请重新设置！");
		dialog.setLeftButtonText("取消");
		dialog.setRightButtonText("设置");
		dialog.setOnClickLeftListener(new onClickLeftListener() {
			@Override
			public void onClickLeft() {
				dialog.dismiss();
			}
		});
		dialog.setOnClickRightListener(new onClickRightListener() {

			@Override
			public void onClickRight() {
				dialog.dismiss();
				Intent intent = null;
				//**判断手机系统的版本 即API大于10 就是3.0或以上版本及魅族手机*//*
				if (android.os.Build.VERSION.SDK_INT > 10 && !android.os.Build.MANUFACTURER.equals("Meizu")) {
					intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
				}
				else {
					intent = new Intent();
					ComponentName component = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
					intent.setComponent(component);
					intent.setAction("android.intent.action.VIEW");
				}
				mContext.startActivity(intent);						

			}
		});
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
		JPushInterface.onPause(this);
	}

	@Override
	protected void onResume() {
		//mApplication.context=this;
		super.onResume();
		MobclickAgent.onResume(this);
		JPushInterface.onResume(this);
	}

	@Override
	protected void desotryItems() {
		if (receiver != null)
			unregisterReceiver(receiver);
	}

	@Override
	public void onClick(View v) {

	}

	/** 通过Class跳转界面 **/
	public void startActivity(Class<?> cls) {
		startActivity(cls, null);
	}

	/** 含有Bundle通过Class跳转界面 **/
	public void startActivity(Class<?> cls, Bundle bundle) {
		Intent intent = new Intent();
		intent.setClass(this, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	/** 通过Action跳转界面 **/
	protected void startActivity(String action) {
		startActivity(action, null);
	}

	/** 含有Bundle通过Action跳转界面 **/
	protected void startActivity(String action, Bundle bundle) {
		Intent intent = new Intent();
		intent.setAction(action);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
	/** 含有Bundle带返回结果通过Class跳转界面 **/
	public void startActivityForResult(Class<?> cls,Bundle bundle, int  requestCode) {
		Intent intent = new Intent();
		intent.setClass(this, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivityForResult(intent, requestCode);
	}
	/** 含有Bundle带返回结果通过Class跳转界面 **/
	public void startActivityForResult1(Class<?> cls,Bundle bundle, int  requestCode) {
		Intent intent = new Intent();
		intent.setClass(this, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivityForResult(intent, requestCode);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
	/** 带有右进右出动画**/
	public void finish1() {
		super.finish();
		overridePendingTransition(0, R.anim.push_right_out);
	}
	/** 默认**/
	protected void defaultFinish() {
		super.finish();
		overridePendingTransition(R.anim.dialog_out, 0);
	}
	@Override
	protected void onAppExit() {
		// TODO Auto-generated method stub

	}
	/**
	 * 结束指定的Activity
	 */
	public static void finishSingleActivity(Activity activity,Intent data) {
		if (activity != null) {
			if (activityList.contains(activity)) {
				activityList.remove(activity);
			}
			activity.setResult(activity.RESULT_OK,data);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 结束指定类名的Activity 在遍历一个列表的时�?�不能执行删除操作，
	 * �?有我们先记住要删除的对象，遍历之后才去删除�??
	 */
	public static void finishSingleActivityByClass(Class<?> cls,Intent data) {
		Activity tempActivity = null;
		for (Activity activity : activityList) {
			if (activity.getClass().equals(cls)) {
				tempActivity = activity;
			}
		}
		finishSingleActivity(tempActivity,data);
	} 

	/**
	 * 程序是否在前台运行
	 *
	 * @return
	 */
	public boolean isAppOnForeground() {
		// Returns a list of application processes that are running on the
		// device
		ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
		String packageName = getApplicationContext().getPackageName();

		List<RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();
		if (appProcesses == null)
			return false;

		for (RunningAppProcessInfo appProcess : appProcesses) {
			// The name of the process that this object is associated with.
			if (appProcess.processName.equals(packageName)
					&& appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
				return true;
			}
		}

		return false;
	}

	/**
	 * set no title
	 */
	public static void setNoTitle(Activity intent){
		  /*set it to be no title*/
		intent.requestWindowFeature(Window.FEATURE_NO_TITLE);
       /*set it to be full screen*/
		intent.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
}
