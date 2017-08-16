package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.ListGiftBuild;
import com.game.helper.sdk.net.comm.ListGiftNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.ListGiftTask.java
 * @Author lbb
 * @Date 2016年10月8日 上午9:44:56
 * @Company 
 */
public class ListGiftTask  extends BaseBBXTask{
	ListGiftBuild build;
	public ListGiftTask(Context context,boolean isShow,String userId,String gameId,Back back) {
		super(context,isShow);
		this.back=back;
		build=new ListGiftBuild(context, API_listGift_Url, userId, gameId);
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
		return new ListGiftNet(context, build.toJson1());
	}
	
}
