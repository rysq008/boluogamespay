package com.game.helper.net.task;

import android.content.Context;
import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.ConnectUserBuild;
import com.game.helper.sdk.net.comm.ConnectUserNet;

public class ConnectUserTask  extends BaseBBXTask{
	ConnectUserBuild build;
	public ConnectUserTask(Context context,String refreshDate,String offset,Back back) {
		super(context);
		this.back=back;
		build=new ConnectUserBuild(context, API_connectUser_Url,refreshDate,offset);
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
		return new ConnectUserNet(context, build.toJson1());
	}
	
}