package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.DzDynamicBuild;
import com.game.helper.sdk.net.comm.DzDynamicNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.dzDynamicTask.java
 * @Author lbb
 * @Date 2016年9月16日 下午12:59:55
 * @Company 
 */
public class DzDynamicTask  extends BaseBBXTask{
	DzDynamicBuild build;
	public DzDynamicTask(Context context,String userId,String dynamicId,Back back) {
		super(context);
		this.back=back;
		build=new DzDynamicBuild(context, API_dzDynamic_Url, dynamicId, userId);
	}
	@Override
	public void onSuccess(Object object, String msg) {
		// TODO Auto-generated method stub
		if(back!=null){
			back.success(object,msg);
		}
	}

	@Override
	public void onFailed(String status, String msg, Object result) {
		// TODO Auto-generated method stub
		super.onFailed(status, msg, result);
       if(back!=null){
    	   back.fail(status, msg, result);
		}
	}

	@Override
	protected Object doInBackground(Object... params) {
		// TODO Auto-generated method stub
		return new DzDynamicNet(context, build.toJson1());
	}
	
}
