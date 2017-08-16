package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetGuildUserBuild;
import com.game.helper.sdk.net.comm.GetGuildUserNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.GetGuildUserTask.java
 * @Author lbb
 * @Date 2016年9月16日 上午10:26:21
 * @Company 
 */
public class GetGuildUserTask  extends BaseBBXTask{
	GetGuildUserBuild build;
	public GetGuildUserTask(Context context,String guildId,String nickName,Back back) {
		super(context);
		this.back=back;
		build=new GetGuildUserBuild(context, API_getGuildUser_Url, guildId, nickName);
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
		return new GetGuildUserNet(context, build.toJson1());
	}
	
}