package com.game.helper.net.task;

import android.content.Context;
import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetAccountBuild;
import com.game.helper.sdk.net.comm.GetAccountNet;

public class GetAccountTask extends BaseBBXTask{
	GetAccountBuild build;
	public GetAccountTask(Context context,String userId,String platId,String gameId,Back back) {
		super(context);
		this.back=back;
		build=new GetAccountBuild(context, API_getAccount_Url, userId, platId,gameId);
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
		return new GetAccountNet(context, build.toJson1());
	}
	
}
