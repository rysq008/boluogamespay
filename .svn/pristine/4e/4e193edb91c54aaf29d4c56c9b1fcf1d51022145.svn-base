package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetGameByIdBuild;
import com.game.helper.sdk.net.comm.GetGameByIdNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.GetGameByIdTask.java
 * @Author lbb
 * @Date 2016年10月13日 上午9:42:20
 * @Company 
 */
public class GetGameByIdTask extends BaseBBXTask{
	GetGameByIdBuild build;
	public GetGameByIdTask(Context context,String gameId,Back back) {
		super(context);
		this.back=back;
		build=new GetGameByIdBuild(context, API_getGameById_Url, gameId);
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
		return new GetGameByIdNet(context, build.toJson1());
	}
	
}
