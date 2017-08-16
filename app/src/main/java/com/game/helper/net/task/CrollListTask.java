package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.CrollListBuild;
import com.game.helper.sdk.net.comm.CrollListNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.CrollListTask.java
 * @Author lbb
 * @Date 2016年12月19日 上午10:54:26
 * @Company 
 */
public class CrollListTask extends BaseBBXTask{
	CrollListBuild build;
	public CrollListTask(Context context,Back back) {
		super(context,false);
		this.back=back;
		build=new CrollListBuild(context, API_crollList_Url);
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
		return new CrollListNet(context, build.toJson1());
	}
	
}
