package com.game.helper.net.task;

import java.util.List;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.BatchExitBuild;
import com.game.helper.sdk.net.comm.BatchExitNet;

import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.batchExitTask.java
 * @Author lbb
 * @Date 2016年9月16日 上午11:05:08
 * @Company 
 */
public class BatchExitTask  extends BaseBBXTask{
	BatchExitBuild build;
	public BatchExitTask(Context context,List<String> userList,Back back) {
		super(context);
		this.back=back;
		build=new BatchExitBuild(context, API_batchExit_Url, userList);
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
		return new BatchExitNet(context, build.toJson1());
	}
	
}