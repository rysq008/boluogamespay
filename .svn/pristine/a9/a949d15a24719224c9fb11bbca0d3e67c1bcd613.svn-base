package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.UpdSexBuild;
import com.game.helper.sdk.net.comm.UpdSexNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.UpdSexTask.java
 * @Author lbb
 * @Date 2016年9月14日 下午2:11:49
 * @Company 
 */
public class UpdSexTask extends BaseBBXTask{
	UpdSexBuild build;
	public UpdSexTask(Context context,String userId,String sex,Back back) {
		super(context,false);
		this.back=back;
		build=new UpdSexBuild(context, API_updSex_Url, userId, sex);
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
		return new UpdSexNet(context, build.toJson1());
	}
	
}