package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.QueryGameByModularBuild;
import com.game.helper.sdk.net.comm.QueryGameByModularNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.QueryGameByModularTask.java
 * @Author lbb
 * @Date 2016年10月8日 上午10:48:40
 * @Company 
 */
public class QueryGameByModularTask extends BaseBBXTask{
	QueryGameByModularBuild build;
	public QueryGameByModularTask(Context context,String modular,String limit,String offset,Back back) {
		super(context,false);
		this.back=back;
		build=new QueryGameByModularBuild(context, API_queryGameByModular_Url, modular, limit,offset);
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
		return new QueryGameByModularNet(context, build.toJson1());
	}
	
}
