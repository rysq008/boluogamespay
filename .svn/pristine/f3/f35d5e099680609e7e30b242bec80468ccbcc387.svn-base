package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.QueryPtbBuild;
import com.game.helper.sdk.net.comm.QueryPtbNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.QueryPtbTask.java
 * @Author lbb
 * @Date 2016年10月12日 上午10:42:12
 * @Company 
 */
public class QueryPtbTask extends BaseBBXTask{
	QueryPtbBuild build;
	public QueryPtbTask(Context context,String userId,Back back) {
		super(context,false);
		this.back=back;
		build=new QueryPtbBuild(context, API_queryPtb_Url, userId);
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
		return new QueryPtbNet(context, build.toJson1());
	}
	
}