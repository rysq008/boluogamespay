package com.game.helper.net.task;

import android.content.Context;
import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetPlatformSignBuild;
import com.game.helper.sdk.net.comm.GetPlatformSignNet;

public class GetPlatformSignTask extends BaseBBXTask{
	GetPlatformSignBuild build;
	public GetPlatformSignTask(Context context,String userId,String yearMonth,Back back) {
		super(context);
		this.back=back;
		build=new GetPlatformSignBuild(context, API_getPlatformSign_Url, userId, yearMonth);
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
		return new GetPlatformSignNet(context, build.toJson1());
	}
	
}