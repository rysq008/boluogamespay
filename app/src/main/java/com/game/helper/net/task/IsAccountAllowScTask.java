package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.IsAccountAllowScBuild;
import com.game.helper.sdk.net.comm.IsAccountAllowScNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.IsAccountAllowScTask.java
 * @Author lbb
 * @Date 2016年11月13日 下午3:44:36
 * @Company
 */
public class IsAccountAllowScTask extends BaseBBXTask{
	IsAccountAllowScBuild build;
	public IsAccountAllowScTask(Context context,String gameAccount,Back back) {
		super(context,false);
		this.back=back;
		build=new IsAccountAllowScBuild(context, API_isAccountAllowSc_Url, gameAccount);
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
		return new IsAccountAllowScNet(context, build.toJson1());
	}
	
}