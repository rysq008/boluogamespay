package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetGuildGameListBuild;
import com.game.helper.sdk.net.comm.GetGuildGameListNet;

import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.GetGuildGameListTask.java
 * @Author lbb
 * @Date 2016年9月22日 下午5:35:45
 * @Company 
 */
public class GetGuildGameListTask extends BaseBBXTask{
	GetGuildGameListBuild build;
	public GetGuildGameListTask(Context context,String guildId,Back back) {
		super(context,false);
		this.back=back;
		build=new GetGuildGameListBuild(context, API_getGuildGameList_Url, guildId);
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
		return new GetGuildGameListNet(context, build.toJson1());
	}
	
}