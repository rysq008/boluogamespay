package com.game.helper.interfaces.broadcast;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**	 
 * @Description 
 * @Path		
 * @Author		lbb  
 * @Date		
 * @Company	 	 
 */
public class BaseBroadcast extends BroadcastReceiver {

	Activity activity;

	public BaseBroadcast(Activity activity) {
		this.activity = activity;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if (activity != null && activity.isFinishing())
			return;
	}
}
