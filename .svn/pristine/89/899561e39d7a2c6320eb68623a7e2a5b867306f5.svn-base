package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetSelfDynamicBuild;
import com.game.helper.sdk.net.comm.GetSelfDynamicNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.GetSelfDynamicTask.java
 * @Author lbb
 * @Date 2016年9月26日 上午9:38:53
 * @Company 
 */
public class GetSelfDynamicTask  extends BaseBBXTask{
	GetSelfDynamicBuild build;
	public GetSelfDynamicTask(Context context,String userId,String offset,Back back) {
		super(context);
		this.back=back;
		build=new GetSelfDynamicBuild(context, API_getSelfDynamic_Url, userId, offset);
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
		return new GetSelfDynamicNet(context, build.toJson1());
	}
	
}