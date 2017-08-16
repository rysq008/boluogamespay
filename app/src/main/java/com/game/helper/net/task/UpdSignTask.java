package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.UpdSignBuild;
import com.game.helper.sdk.net.comm.UpdSignNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.UpdSignTask.java
 * @Author lbb
 * @Date 2016年9月14日 下午12:12:52
 * @Company 
 */
public class UpdSignTask extends BaseBBXTask{
	UpdSignBuild build;
	public UpdSignTask(Context context,String userId,String sign,Back back) {
		super(context);
		this.back=back;
		build=new UpdSignBuild(context, API_updSign_Url, userId, sign);
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
		return new UpdSignNet(context, build.toJson1());
	}
	
}