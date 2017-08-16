package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.QueryGamePlBuild;
import com.game.helper.sdk.net.comm.QueryGamePlNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.QueryGamePlTask.java
 * @Author lbb
 * @Date 2016年10月12日 上午10:27:15
 * @Company 
 */
public class QueryGamePlTask extends BaseBBXTask{
	QueryGamePlBuild build;
	public QueryGamePlTask(Context context,String gameId,Back back) {
		super(context,false);
		this.back=back;
		build=new QueryGamePlBuild(context, API_queryGamePl_Url, gameId);
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
		return new QueryGamePlNet(context, build.toJson1());
	}
	
}
