package com.game.helper.net.task;

import android.content.Context;
import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.ResetPwdBuild;
import com.game.helper.sdk.net.comm.ResetPwdNet;

public class ResetPwdTask extends BaseBBXTask{
	ResetPwdBuild build;
	public ResetPwdTask(Context context,String phone,String checkCode,String pwd,Back back) {
		super(context);
		this.back=back;
		build=new ResetPwdBuild(context, API_resetPwd_Url, phone, checkCode, pwd);
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
		return new ResetPwdNet(context, build.toJson1());
	}
	

}