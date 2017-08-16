package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.QueryThemeGameBuild;
import com.game.helper.sdk.net.comm.QueryThemeGameNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.QueryThemeGameTask.java
 * @Author lbb
 * @Date 2016年10月8日 上午10:47:43
 * @Company
 */
public class QueryThemeGameTask extends BaseBBXTask{
	QueryThemeGameBuild build;
	public QueryThemeGameTask(Context context,String themeId,Back back) {
		super(context);
		this.back=back;
		build=new QueryThemeGameBuild(context, API_queryThemeGame_Url, themeId);
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
		return new QueryThemeGameNet(context, build.toJson1());
	}
	
}
