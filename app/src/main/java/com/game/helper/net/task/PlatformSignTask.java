package com.game.helper.net.task;

import android.content.Context;
import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.PlatformSignBuild;
import com.game.helper.sdk.net.comm.PlatformSignNet;

public class PlatformSignTask extends BaseBBXTask{
	PlatformSignBuild build;
	public PlatformSignTask(Context context,String userId,Back back) {
		super(context);
		this.back=back;
		build=new PlatformSignBuild(context, API_platformSign_Url, userId);
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
		return new PlatformSignNet(context, build.toJson1());
	}
	
}
