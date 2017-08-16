package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetExchangelistBuild;
import com.game.helper.sdk.net.comm.GetExchangelistNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.GetExchangelistTask.java
 * @Author lbb
 * @Date 2016年9月22日 下午3:53:36
 * @Company 
 */
public class GetExchangelistTask  extends BaseBBXTask{
	GetExchangelistBuild build;
	public GetExchangelistTask(Context context,String userId,Back back) {
		super(context);
		this.back=back;
		build=new GetExchangelistBuild(context, API_getExchangelist_Url, userId);
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
		return new GetExchangelistNet(context, build.toJson1());
	}
	
}