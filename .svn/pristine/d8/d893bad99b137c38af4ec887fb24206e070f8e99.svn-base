package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.DzGamePlBuild;
import com.game.helper.sdk.net.comm.DzGamePlNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.DzGamePlTask.java
 * @Author lbb
 * @Date 2016年10月12日 上午10:39:29
 * @Company 
 */
public class DzGamePlTask extends BaseBBXTask{
	DzGamePlBuild build;
	public DzGamePlTask(Context context,String userId,String plId,Back back) {
		super(context,false);
		this.back=back;
		build=new DzGamePlBuild(context, API_dzGamePl_Url, userId, plId);
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
		return new DzGamePlNet(context, build.toJson1());
	}
	
}
