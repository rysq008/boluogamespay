package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetNoNetGameBuild;
import com.game.helper.sdk.net.comm.GetNoNetGameNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.GetNoNetGameTask.java
 * @Author lbb
 * @Date 2016年10月19日 下午3:01:08
 * @Company 
 */
public class GetNoNetGameTask extends BaseBBXTask{
	GetNoNetGameBuild build;
	public GetNoNetGameTask(Context context,String refreshDate,String offset,Back back) {
		super(context);
		this.back=back;
		build=new GetNoNetGameBuild(context, API_getNoNetGame_Url,refreshDate,offset);
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
		return new GetNoNetGameNet(context, build.toJson1());
	}
	
}
