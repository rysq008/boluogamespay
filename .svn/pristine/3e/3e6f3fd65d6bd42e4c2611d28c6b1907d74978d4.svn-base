package com.game.helper.net.task;

import android.content.Context;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.DelGameOrderByNoTaskBuild;
import com.game.helper.sdk.model.comm.GetMyGameOrderBuild;
import com.game.helper.sdk.net.comm.DelGameOrderByNoTastNet;
import com.game.helper.sdk.net.comm.GetMyGameOrderNet;

public class DelGameOrderByNoTask extends BaseBBXTask{
	DelGameOrderByNoTaskBuild build;
	public DelGameOrderByNoTask(Context context, String orderNo, Back back) {
		super(context);
		this.back=back;
		build=new DelGameOrderByNoTaskBuild(context, API_delGameOrderByNo, orderNo);
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
		return new DelGameOrderByNoTastNet(context, build.toJson1());
	}
	
}
