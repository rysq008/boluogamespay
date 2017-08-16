package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetContenteOtherDetailBuild;
import com.game.helper.sdk.net.comm.GetContenteOtherDetailNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.getContenteOtherDetailTask.java
 * @Author lbb
 * @Date 2016年9月16日 上午11:33:35
 * @Company 
 */
public class GetContenteOtherDetailTask  extends BaseBBXTask{
	GetContenteOtherDetailBuild build;
	public GetContenteOtherDetailTask(Context context,String contentId,Back back) {
		super(context,false);
		this.back=back;
		build=new GetContenteOtherDetailBuild(context, API_getContenteOtherDetail_Url, contentId);
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
		return new GetContenteOtherDetailNet(context, build.toJson1());
	}
	
}