package com.game.helper.net.task;

import android.content.Context;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetBasicBuild;
import com.game.helper.sdk.model.comm.GetRechargeWayBuild;
import com.game.helper.sdk.net.comm.GetBasicNet;
import com.game.helper.sdk.net.comm.GetRechargeWayNet;

public class GetRechargeWayTask extends BaseBBXTask{
	GetRechargeWayBuild build;
	public GetRechargeWayTask(Context context, String gameId, String gameAccount, String userId,Back back) {
		super(context);
		this.back=back;
		build=new GetRechargeWayBuild(context, API_findGameDisCount, gameId,gameAccount,userId);
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
		return new GetRechargeWayNet(context, build.toJson1());
	}
	
}