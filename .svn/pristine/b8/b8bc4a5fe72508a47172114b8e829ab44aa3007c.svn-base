package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.UpdNickBuild;
import com.game.helper.sdk.net.comm.UpdNickNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.UpdNickTask.java
 * @Author lbb
 * @Date 2016年9月14日 上午11:27:00
 * @Company 
 */
public class UpdNickTask  extends BaseBBXTask{
	UpdNickBuild build;
	public UpdNickTask(Context context,String userId,String nickname,Back back) {
		super(context);
		this.back=back;
		build=new UpdNickBuild(context, API_updNick_Url, userId, nickname);
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
		return new UpdNickNet(context, build.toJson1());
	}
	
}