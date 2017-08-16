package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetNoticeByIdBuild;
import com.game.helper.sdk.net.comm.GetNoticeByIdNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.GetNoticeByIdTask.java
 * @Author lbb
 * @Date 2016年9月16日 上午9:40:13
 * @Company 
 */
public class GetNoticeByIdTask  extends BaseBBXTask{
	GetNoticeByIdBuild build;
	public GetNoticeByIdTask(Context context,String noticeId,Back back) {
		super(context,false);
		this.back=back;
		build=new GetNoticeByIdBuild(context, API_getNoticeById_Url, noticeId);
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
		return new GetNoticeByIdNet(context, build.toJson1());
	}
	
}