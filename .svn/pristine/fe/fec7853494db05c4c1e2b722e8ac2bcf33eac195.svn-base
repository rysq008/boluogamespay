package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.IsGetMoreThanThreeBuild;
import com.game.helper.sdk.net.comm.IsGetMoreThanThreeNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.IsGetMoreThanThreeTask.java
 * @Author lbb
 * @Date 2016年11月9日 上午9:53:37
 * @Company 
 */
public class IsGetMoreThanThreeTask extends BaseBBXTask{
	IsGetMoreThanThreeBuild build;
	public IsGetMoreThanThreeTask(Context context,String userId,Back back) {
		super(context);
		this.back=back;
		build=new IsGetMoreThanThreeBuild(context, API_isGetMoreThanThree_Url, userId);
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
		return new IsGetMoreThanThreeNet(context, build.toJson1());
	}
	
}