package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.QueryMyZkhBuild;
import com.game.helper.sdk.net.comm.QueryMyZkhNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.QueryMyZkhTask.java
 * @Author lbb
 * @Date 2016年11月21日 上午9:34:42
 * @Company
 */
public class QueryMyZkhTask extends BaseBBXTask{
	QueryMyZkhBuild build;
	public QueryMyZkhTask(Context context,String userId,Back back) {
		super(context);
		this.back=back;
		build=new QueryMyZkhBuild(context, API_queryMyZkh_Url, userId);
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
		return new QueryMyZkhNet(context, build.toJson1());
	}
	
}