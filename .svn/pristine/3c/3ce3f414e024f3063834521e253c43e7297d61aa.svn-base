package com.game.helper.net.task;

import android.content.Context;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetPtbRuleBuild;
import com.game.helper.sdk.net.comm.GetPtbRuleNet;

public class GetPtbRuleTask extends BaseBBXTask{
	GetPtbRuleBuild build;
	public GetPtbRuleTask(Context context,boolean isShow,Back back) {
		super(context,isShow);
		this.back=back;
		build=new GetPtbRuleBuild(context, API_getPtbRule_Url);
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
		return new GetPtbRuleNet(context, build.toJson1());
	}
	
}
