package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.SaveSignBuild;
import com.game.helper.sdk.net.comm.SaveSignNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.saveSignTask.java
 * @Author lbb
 * @Date 2016年9月16日 上午11:08:29
 * @Company 
 */
public class SaveSignTask  extends BaseBBXTask{
	SaveSignBuild build;
	public SaveSignTask(Context context,String userId,String guildId,Back back) {
		super(context);
		this.back=back;
		build=new SaveSignBuild(context, API_saveSign_Url, userId, guildId);
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
		return new SaveSignNet(context, build.toJson1());
	}
	
}
