package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.DeleteFocusBuild;
import com.game.helper.sdk.net.comm.DeleteFocusNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.DeleteFocusTask.java
 * @Author lbb
 * @Date 2016年9月16日 上午10:00:48
 * @Company 
 */
public class DeleteFocusTask extends BaseBBXTask{
	DeleteFocusBuild build;
	public DeleteFocusTask(Context context,String focusId,Back back) {
		super(context);
		this.back=back;
		build=new DeleteFocusBuild(context, API_deleteFocus_Url, focusId);
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
		return new DeleteFocusNet(context, build.toJson1());
	}
	
}