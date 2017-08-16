package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.AddSCBuild;
import com.game.helper.sdk.net.comm.AddSCNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.addSCTask.java
 * @Author lbb
 * @Date 2016年9月16日 上午11:39:59
 * @Company 
 */
public class AddSCTask  extends BaseBBXTask{
	AddSCBuild build;
	public AddSCTask(Context context,String contentId,String opUserId,Back back) {
		super(context);
		build=new AddSCBuild(context, API_addSC_Url, contentId, opUserId);
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
		return new AddSCNet(context, build.toJson1());
	}
	
}
