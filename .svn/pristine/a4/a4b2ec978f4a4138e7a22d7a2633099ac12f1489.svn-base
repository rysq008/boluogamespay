package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.AddPLBuild;
import com.game.helper.sdk.net.comm.AddPLNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.addPLTask.java
 * @Author lbb
 * @Date 2016年9月16日 上午11:30:31
 * @Company 
 */
public class AddPLTask  extends BaseBBXTask{
	AddPLBuild build;
	public AddPLTask(Context context,String contentId,String opUserId,String plContent,Back back) {
		super(context);
		this.back=back;
		build=new AddPLBuild(context, API_addPL_Url, contentId, opUserId,plContent);
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
		return new AddPLNet(context, build.toJson1());
	}
	
}
