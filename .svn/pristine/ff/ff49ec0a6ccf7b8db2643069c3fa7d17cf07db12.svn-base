package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.AddDZBuild;
import com.game.helper.sdk.net.comm.AddDZNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.addDZTask.java
 * @Author lbb
 * @Date 2016年9月16日 上午11:43:01
 * @Company 
 */
public class AddDZTask extends BaseBBXTask{
	AddDZBuild build;
	public AddDZTask(Context context,String contentId,String opUserId,Back back) {
		super(context);
		this.back=back;
		build=new AddDZBuild(context, API_addDZ_Url, contentId, opUserId);
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
		return new AddDZNet(context, build.toJson1());
	}
	
}