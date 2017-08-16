package com.game.helper.net.task;

import android.content.Context;
import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.AddUserIconBuild;
import com.game.helper.sdk.net.comm.AddUserIconNet;

public class AddUserIconTask extends BaseBBXTask{
	AddUserIconBuild build;
	public AddUserIconTask(Context context,String userId,String icon,
			  String iconThumb,Back back) {
		super(context);
		this.back=back;
		build=new AddUserIconBuild(context, API_addUserIcon_Url, userId, icon, iconThumb);
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
		return new AddUserIconNet(context, build.toJson1());
	}
	
}
