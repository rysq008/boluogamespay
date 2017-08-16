package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.DeleteDynamicBuild;
import com.game.helper.sdk.net.comm.DeleteDynamicNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.deleteDynamicTask.java
 * @Author lbb
 * @Date 2016年9月16日 下午1:03:27
 * @Company 
 */
public class DeleteDynamicTask  extends BaseBBXTask{
	DeleteDynamicBuild build;
	public DeleteDynamicTask(Context context,String dynamicId,Back back) {
		super(context);
		this.back=back;
		build=new DeleteDynamicBuild(context, API_deleteDynamic_Url, dynamicId);
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
		return new DeleteDynamicNet(context, build.toJson1());
	}
	
}