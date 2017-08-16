package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetHotGuildBuild;
import com.game.helper.sdk.net.comm.GetHotGuildNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.GetHotGuildTask.java
 * @Author lbb
 * @Date 2016年9月22日 下午6:07:15
 * @Company 
 */
public class GetHotGuildTask  extends BaseBBXTask{
	GetHotGuildBuild build;
	public GetHotGuildTask(Context context,Back back) {
		super(context);
		this.back=back;
		build=new GetHotGuildBuild(context, API_getHotGuild_Url);
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
		return new GetHotGuildNet(context, build.toJson1());
	}
	
}