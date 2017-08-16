package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.CgametypelistBuild;
import com.game.helper.sdk.net.comm.CgametypelistNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.CgametypelistTask.java
 * @Author lbb
 * @Date 2016年10月8日 上午10:49:29
 * @Company 
 */
public class CgametypelistTask extends BaseBBXTask{
	CgametypelistBuild build;
	public CgametypelistTask(Context context,Back back) {
		super(context,false);
		this.back=back;
		build=new CgametypelistBuild(context, API_cgametypelist_Url);
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
		return new CgametypelistNet(context, build.toJson1());
	}
	
}