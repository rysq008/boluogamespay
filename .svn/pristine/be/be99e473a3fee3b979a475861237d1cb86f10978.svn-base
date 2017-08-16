package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.QueryThemeBuild;
import com.game.helper.sdk.net.comm.QueryThemeNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.QueryThemeTask.java
 * @Author lbb
 * @Date 2016年10月8日 上午10:24:36
 * @Company
 */
public class QueryThemeTask extends BaseBBXTask{
	QueryThemeBuild build;
	public QueryThemeTask(Context context,Back back) {
		super(context);
		this.back=back;
		build=new QueryThemeBuild(context, API_queryTheme_Url);
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
		return new QueryThemeNet(context, build.toJson1());
	}
	
}