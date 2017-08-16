package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetGiveListBuild;
import com.game.helper.sdk.net.comm.GetGiveListNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.getGiveListTask.java
 * @Author lbb
 * @Date 2016年9月16日 上午11:24:41
 * @Company 
 */
public class GetGiveListTask  extends BaseBBXTask{
	GetGiveListBuild build;
	public GetGiveListTask(Context context,boolean isShow,String userId,Back back) {
		super(context,isShow);
		this.back=back;
		build=new GetGiveListBuild(context, API_getGiveList_Url, userId);
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
		return new GetGiveListNet(context, build.toJson1());
	}
	
}
