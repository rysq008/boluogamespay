package com.game.helper.net.task;

import android.content.Context;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.QueryGameByPlatidBuild;
import com.game.helper.sdk.model.comm.QueryGameBykindAndTypeBuild;
import com.game.helper.sdk.net.comm.QueryGameBykindAndTypeNet;

/**
 * @Description
 * @Path com.game.helper.net.task.QueryGameBykindAndTypeTask.java
 * @Author lbb
 * @Date 2016年10月8日 上午10:09:20
 * @Company 
 */
public class QueryGameByPlatid extends BaseBBXTask{
	QueryGameByPlatidBuild build;
	public QueryGameByPlatid(Context context,String platid,String offset, Back back) {
		super(context);
		this.back=back;
		build=new QueryGameByPlatidBuild(context, API_queryGameByPlatId, platid,offset);
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
		return new QueryGameBykindAndTypeNet(context, build.toJson1());
	}
	
}