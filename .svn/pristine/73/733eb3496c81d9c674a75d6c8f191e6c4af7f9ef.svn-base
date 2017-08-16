package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.QueryGameCutPlBuild;
import com.game.helper.sdk.net.comm.QueryGameCutPlNet;

import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.QueryGameCutPlTask.java
 * @Author lbb
 * @Date 2016年10月12日 上午10:01:08
 * @Company 
 */
public class QueryGameCutPlTask extends BaseBBXTask{
	QueryGameCutPlBuild build;
	public QueryGameCutPlTask(Context context,String cutId,Back back) {
		super(context,false);
		this.back=back;
		build=new QueryGameCutPlBuild(context, API_queryGameCutPl_Url, cutId);
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
		return new QueryGameCutPlNet(context, build.toJson1());
	}
	
}