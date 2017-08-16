package com.game.helper.net.task;

import android.content.Context;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.SaveWithdrawBuild;
import com.game.helper.sdk.net.comm.SaveWithdrawNet;

public class SaveWithdrawTask extends BaseBBXTask{
	SaveWithdrawBuild build;
	public SaveWithdrawTask(Context context,String userId,String money
			,String txWay,String account,String accountName,Back back) {
		super(context);
		this.back=back;
		build=new SaveWithdrawBuild(context, API_saveWithdraw_Url, userId, money
				, txWay, account, account);
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
		return new SaveWithdrawNet(context, build.toJson1());
	}
	
}