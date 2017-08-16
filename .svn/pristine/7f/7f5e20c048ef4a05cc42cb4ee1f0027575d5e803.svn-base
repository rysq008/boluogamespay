
package com.game.helper.receiver;

import com.game.helper.interfaces.comm.CommValues;
import com.game.helper.util.SystemUtil;
import com.game.helper.util.TimeUtil;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @Description
 * @Path 
 * @Author 
 * @Date 
 * @Company 
 */
public class NetChangeReceiver extends BaseRevevier implements CommValues {


	public boolean isLoading = false;
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if ( ACTION_NET.equals(action)) {
			if (SystemUtil.getNetworkStatus(context)) {
				//LogUtils.debug(TimeUtil.getTime()+"has netword");
				if(!isLoading){
					/*Login user=ForSDk.getUser(context);
					if(!TextUtils.isEmpty(user.access_token)&&!TextUtils.isEmpty(user.uid)){
						new UpdataTokenTask(context).start();
						Log.e("UpdataTokenTasklbb",TimeUtil.getTime()+"new UpdataTokenTask(context).start()");					
					}*/
				}else{
					Log.e("UpdataTokenTasklbb",TimeUtil.getTime()+"UpdataTokenTask is loading");	
				}
				//ToastUtil.showToast(context, "网络已恢复");
			}else{
				/*ForSDk.unlogin(context);
				LogUtils.debug(TimeUtil.getTime()+"no netword");*/
				//ToastUtil.showToast(context, "网络不给力，请重新连接");
			}
		}
	}
	
}
