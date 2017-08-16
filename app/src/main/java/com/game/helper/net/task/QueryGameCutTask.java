package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.QueryGameCutBuild;
import com.game.helper.sdk.net.comm.QueryGameCutNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.QueryGameCutTask.java
 * @Author lbb
 * @Date 2016年10月12日 上午9:41:54
 * @Company 
 */
public class QueryGameCutTask extends BaseBBXTask{
	QueryGameCutBuild build;
	public QueryGameCutTask(Context context,String gameId,Back back) {
		super(context,false);
		this.back=back;
		build=new QueryGameCutBuild(context, API_queryGameCut_Url, gameId);
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
		return new QueryGameCutNet(context, build.toJson1());
	}
	
}
