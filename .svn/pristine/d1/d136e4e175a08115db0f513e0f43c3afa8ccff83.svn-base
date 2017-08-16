package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetRestTimeBuild;
import com.game.helper.sdk.net.comm.GetRestTimeNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.getRestTimeTask.java
 * @Author lbb
 * @Date 2016年9月16日 下午12:23:32
 * @Company 
 */
public class GetRestTimeTask  extends BaseBBXTask{
	GetRestTimeBuild build;
	public GetRestTimeTask(Context context,String userId,Back back) {
		super(context,false);
		this.back=back;
		build=new GetRestTimeBuild(context, API_getRestTime_Url, userId);
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
		return new GetRestTimeNet(context, build.toJson1());
	}
	
}
