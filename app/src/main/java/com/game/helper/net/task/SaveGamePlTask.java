package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.SaveGamePlBuild;
import com.game.helper.sdk.net.comm.SaveGamePlNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.SaveGamePlTask.java
 * @Author lbb
 * @Date 2016年10月12日 上午10:16:30
 * @Company 
 */
public class SaveGamePlTask extends BaseBBXTask{
	SaveGamePlBuild build;
	public SaveGamePlTask(Context context,String gameId,String userId,String content,Back back) {
		super(context);
		this.back=back;
		build=new SaveGamePlBuild(context, API_saveGamePl_Url, gameId,userId, content);
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
		return new SaveGamePlNet(context, build.toJson1());
	}
	
}