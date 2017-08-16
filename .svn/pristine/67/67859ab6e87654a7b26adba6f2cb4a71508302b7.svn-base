package com.game.helper.net.task;

import android.content.Context;
import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.AllowTxMoneyBuild;
import com.game.helper.sdk.net.comm.AllowTxMoneyNet;

public class AllowTxMoneyTask extends BaseBBXTask{
	AllowTxMoneyBuild build;
	public AllowTxMoneyTask(Context context,String userId,Back back) {
		super(context);
		this.back=back;
		build=new AllowTxMoneyBuild(context, API_allowTxMoney_Url,userId);
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
		return new AllowTxMoneyNet(context, build.toJson1());
	}
	
}