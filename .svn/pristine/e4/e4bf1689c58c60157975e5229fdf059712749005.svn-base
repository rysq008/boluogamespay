package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetNoticeListBuild;
import com.game.helper.sdk.net.comm.GetNoticeListNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.GetNoticeListTask.java
 * @Author lbb
 * @Date 2016年9月16日 上午11:00:45
 * @Company 
 */
public class GetNoticeListTask  extends BaseBBXTask{
	GetNoticeListBuild build;
	public GetNoticeListTask(Context context,boolean isShow,String guildId,Back back) {
		super(context,isShow);
		this.back=back;
		build=new GetNoticeListBuild(context, API_getNoticeList_Url, guildId);
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
		return new GetNoticeListNet(context, build.toJson1());
	}
	
}
