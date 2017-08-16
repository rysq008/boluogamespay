package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.MyWithdrawBuild;
import com.game.helper.sdk.net.comm.MyWithdrawNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.MyWithdrawTask.java
 * @Author lbb
 * @Date 2016年11月24日 下午4:03:45
 * @Company 
 */
public class MyWithdrawTask  extends BaseBBXTask{
	MyWithdrawBuild build;
	public MyWithdrawTask(Context context,String userId,Back back) {
		super(context);
		this.back=back;
		build=new MyWithdrawBuild(context, API_myWithdraw_Url, userId);
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
		return new MyWithdrawNet(context, build.toJson1());
	}
	
}