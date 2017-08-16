package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetGiftListBuild;
import com.game.helper.sdk.net.comm.GetGiftListNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.getGiftListBuild.java
 * @Author lbb
 * @Date 2016年9月16日 上午11:21:45
 * @Company 
 */
public class GetGiftListTask  extends BaseBBXTask{
	GetGiftListBuild build;
	public GetGiftListTask(Context context,boolean isShow ,Back back) {
		super(context,isShow);
		this.back=back;
		build=new GetGiftListBuild(context, API_getGiftList_Url);
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
		return new GetGiftListNet(context, build.toJson1());
	}
	
}
