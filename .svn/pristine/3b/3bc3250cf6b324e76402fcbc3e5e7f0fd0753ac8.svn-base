package com.game.helper.net.task;

import android.content.Context;
import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetBasicBuild;
import com.game.helper.sdk.net.comm.GetBasicNet;

public class GetBasicTask extends BaseBBXTask{
	GetBasicBuild build;
	public GetBasicTask(Context context,boolean isShow,String code,Back back) {
		super(context,isShow);
		this.back=back;
		build=new GetBasicBuild(context, API_getBasic_Url, code);
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
		return new GetBasicNet(context, build.toJson1());
	}
	
}