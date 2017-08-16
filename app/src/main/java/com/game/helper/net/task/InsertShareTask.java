package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.InsertShareBuild;
import com.game.helper.sdk.net.comm.InsertShareNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.InsertShareTask.java
 * @Author lbb
 * @Date 2016年12月5日 上午10:54:19
 * @Company 
 */
public class InsertShareTask extends BaseBBXTask{
	InsertShareBuild build;
	public InsertShareTask(Context context,String type,String userId,Back back) {
		super(context,false);
		this.back=back;
		build=new InsertShareBuild(context, API_insertShare_Url, type, userId);
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
		return new InsertShareNet(context, build.toJson1());
	}
	
}
