package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.JoinOrExitGuildBuild;
import com.game.helper.sdk.net.comm.JoinOrExitGuildNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.JoinOrExitGuildTask.java
 * @Author lbb
 * @Date 2016年9月16日 上午10:08:52
 * @Company 
 */
public class JoinOrExitGuildTask  extends BaseBBXTask{
	JoinOrExitGuildBuild build;
	public JoinOrExitGuildTask(Context context,String userId,String guildId,String type,Back back) {
		super(context);
		this.back=back;
		build=new JoinOrExitGuildBuild(context, API_joinOrExitGuild_Url, userId,guildId, type);
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
		return new JoinOrExitGuildNet(context, build.toJson1());
	}
	
}