package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.SaveFocusBuild;
import com.game.helper.sdk.net.comm.SaveFocusNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.SaveFocusTask.java
 * @Author lbb
 * @Date 2016年9月16日 上午10:13:39
 * @Company 
 */
public class SaveFocusTask  extends BaseBBXTask{
	SaveFocusBuild build;
	public SaveFocusTask(Context context,String userId,String operId,Back back) {
		super(context);
		this.back=back;
		build=new SaveFocusBuild(context, API_saveFocus_Url, userId, operId);
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
		return new SaveFocusNet(context, build.toJson1());
	}
	
}
