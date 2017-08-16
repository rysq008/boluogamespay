package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetAwardBuild;
import com.game.helper.sdk.net.comm.GetAwardNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.GetAwardTask.java
 * @Author lbb
 * @Date 2016年9月14日 下午3:45:10
 * @Company 
 */
public class GetAwardTask  extends BaseBBXTask{
	GetAwardBuild build;
	public GetAwardTask(Context context,String userId,String type,Back back) {
		super(context);
		this.back=back;
		build=new GetAwardBuild(context, API_getAward_Url, userId, type);
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
		return new GetAwardNet(context, build.toJson1());
	}
	
}