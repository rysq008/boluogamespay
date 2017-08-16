
package com.game.helper.receiver;

import java.util.List;

import com.game.helper.interfaces.comm.CommValues;
import com.game.helper.util.ToastUtil;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.BroadcastReceiver;
import android.content.Context;

/**
 * @Description
 * @Path 
 * @Author lbb
 * @Date 
 * @Company 
 */
public abstract class BaseRevevier extends BroadcastReceiver implements CommValues{

	public boolean isAppRunning = false;

	public boolean isAppRun(Context context) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> list = am.getRunningTasks(100);
		for (RunningTaskInfo info : list) {
			if (info.topActivity.getPackageName().equals( context.getPackageName()) && info.baseActivity.getPackageName().equals( context.getPackageName())) {
				isAppRunning = true;
				break;
			}
		}
		
		ToastUtil.showToast(context, "is run" + isAppRunning);
		return false;
	}
}
