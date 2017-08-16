package com.game.helper.net.task;

import android.content.Context;

import java.util.List;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.ChangeIconOrderbyBuild;
import com.game.helper.sdk.model.returns.UserIcons;
import com.game.helper.sdk.net.comm.ChangeIconOrderbyNet;

public class ChangeIconOrderbyTask extends BaseBBXTask{
	ChangeIconOrderbyBuild build;
	public ChangeIconOrderbyTask(Context context,List<UserIcons> userIcons,Back back) {
		super(context,false);
		this.back=back;
		build=new ChangeIconOrderbyBuild(context, API_changeIconOrderby_Url,userIcons);
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
		return new ChangeIconOrderbyNet(context, build.toJson1());
	}
	
}