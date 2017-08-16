package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.AddMyZkhBuild;
import com.game.helper.sdk.net.comm.AddMyZkhNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.AddMyZkhTask.java
 * @Author lbb
 * @Date 2016年11月21日 上午9:27:16
 * @Company 
 */
public class AddMyZkhTask extends BaseBBXTask{
	AddMyZkhBuild build;
	public AddMyZkhTask(Context context,String userId,String platId,String gameAccount,Back back) {
		super(context);
		this.back=back;
		build=new AddMyZkhBuild(context, API_addMyZkh_Url, userId, platId, gameAccount);
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
		return new AddMyZkhNet(context, build.toJson1());
	}
	
}
