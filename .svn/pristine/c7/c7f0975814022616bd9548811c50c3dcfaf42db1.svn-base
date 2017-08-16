package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.UpdateRemarkNameBuild;
import com.game.helper.sdk.net.comm.UpdateRemarkNameNet;

import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.UpdateRemarkNameTask.java
 * @Author lbb
 * @Date 2016年9月26日 上午9:57:12
 * @Company 
 */
public class UpdateRemarkNameTask extends BaseBBXTask{
	UpdateRemarkNameBuild build;
	public UpdateRemarkNameTask(Context context,String focusId,String remarkName,Back back) {
		super(context);
		this.back=back;
		build=new UpdateRemarkNameBuild(context, API_updateRemarkName_Url, focusId, remarkName);
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
		return new UpdateRemarkNameNet(context, build.toJson1());
	}
	
}