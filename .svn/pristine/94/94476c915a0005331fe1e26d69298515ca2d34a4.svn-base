package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.SaveOrUpdateGuildBuild;
import com.game.helper.sdk.net.comm.SaveOrUpdateGuildNet;

import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.SaveOrUpdateGuildTask.java
 * @Author lbb
 * @Date 2016年9月16日 上午10:19:10
 * @Company 
 */
public class SaveOrUpdateGuildTask  extends BaseBBXTask{
	SaveOrUpdateGuildBuild build;
	public SaveOrUpdateGuildTask(Context context,String userId,String name,String icon,String declareContent,String abstractContent,String guildId,Back back) {
		super(context);
		this.back=back;
		build=new SaveOrUpdateGuildBuild(context, API_saveOrUpdateGuild_Url, userId, name, icon, declareContent, abstractContent,guildId);
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
		return new SaveOrUpdateGuildNet(context, build.toJson1());
	}
	
}