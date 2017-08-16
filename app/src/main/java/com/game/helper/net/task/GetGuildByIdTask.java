package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetGuildByIdBuild;
import com.game.helper.sdk.net.comm.GetGuildByIdNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.getGuildByIdTask.java
 * @Author lbb
 * @Date 2016年9月16日 上午11:18:45
 * @Company 
 */
public class GetGuildByIdTask  extends BaseBBXTask{
	GetGuildByIdBuild build;
	public GetGuildByIdTask(Context context,String guildId,Back back) {
		super(context);
		this.back=back;
		build=new GetGuildByIdBuild(context, API_getGuildById_Url, guildId);
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
		return new GetGuildByIdNet(context, build.toJson1());
	}
	
}