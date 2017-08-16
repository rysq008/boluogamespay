package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetDynamicByIdBuild;
import com.game.helper.sdk.net.comm.GetDynamicByIdNet;

import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.getDynamicByIdTask.java
 * @Author lbb
 * @Date 2016年9月16日 下午12:33:59
 * @Company 
 */
public class GetDynamicByIdTask  extends BaseBBXTask{
	GetDynamicByIdBuild build;
	public GetDynamicByIdTask(Context context,String dynamicId,Back back) {
		super(context);
		this.back=back;
		build=new GetDynamicByIdBuild(context, API_getDynamicById_Url, dynamicId);
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
		return new GetDynamicByIdNet(context, build.toJson1());
	}
	
}