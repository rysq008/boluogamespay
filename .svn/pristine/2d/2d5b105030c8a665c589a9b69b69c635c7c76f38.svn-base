package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetSignListBuild;
import com.game.helper.sdk.net.comm.GetSignListNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.getSignListTask.java
 * @Author lbb
 * @Date 2016年9月16日 上午11:16:00
 * @Company 
 */
public class GetSignListTask  extends BaseBBXTask{
	 GetSignListBuild build;
	public GetSignListTask(Context context,boolean isShow,String guildId,String nowDate,Back back) {
		super(context,isShow);
		this.back=back;
		build=new GetSignListBuild(context, API_getSignList_Url, guildId, nowDate);
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
		return new GetSignListNet(context, build.toJson1());
	}
	
}
