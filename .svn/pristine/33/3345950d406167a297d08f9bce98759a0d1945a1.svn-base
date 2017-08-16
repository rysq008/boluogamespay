package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.UpdPwdBuild;
import com.game.helper.sdk.net.comm.UpdPwdNet;

import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.net.comm.UpdPwdTask.java
 * @Author lbb
 * @Date 2016年9月14日 上午11:15:26
 * @Company
 */
public class UpdPwdTask extends BaseBBXTask{
	UpdPwdBuild build;
	public UpdPwdTask(Context context,String userId,String pwd,Back back) {
		super(context);
		this.back=back;
		build=new UpdPwdBuild(context, API_updPwd_Url, userId, pwd);
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
		return new UpdPwdNet(context, build.toJson1());
	}
	
}
