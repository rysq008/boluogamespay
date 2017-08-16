package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetInfoActBuild;
import com.game.helper.sdk.net.comm.GetInfoActNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.GetInfoActTask.java
 * @Author lbb
 * @Date 2016年10月8日 上午11:02:58
 * @Company 
 */
public class GetInfoActTask extends BaseBBXTask{
	GetInfoActBuild build;
	public GetInfoActTask(Context context,Back back) {
		super(context,false);
		this.back=back;
		build=new GetInfoActBuild(context, API_getInfoAct_Url);
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
		return new GetInfoActNet(context, build.toJson1());
	}
	
}