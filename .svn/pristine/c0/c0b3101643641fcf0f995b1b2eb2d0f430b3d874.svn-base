package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetGameCutByIdBuild;
import com.game.helper.sdk.net.comm.GetGameCutByIdNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.GetGameCutByIdTask.java
 * @Author lbb
 * @Date 2016年10月13日 上午9:49:39
 * @Company 
 */
public class GetGameCutByIdTask extends BaseBBXTask{
	GetGameCutByIdBuild build;
	public GetGameCutByIdTask(Context context,boolean isShow,String cutId,Back back) {
		super(context,isShow);
		this.back=back;
		build=new GetGameCutByIdBuild(context, API_getGameCutById_Url, cutId);
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
		return new GetGameCutByIdNet(context, build.toJson1());
	}
	
}
