package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetGiftBuild;
import com.game.helper.sdk.net.comm.GetGiftNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.GetGiftTask.java
 * @Author lbb
 * @Date 2016年10月8日 上午10:52:58
 * @Company 
 */
public class GetGiftTask extends BaseBBXTask{
	GetGiftBuild build;
	public GetGiftTask(Context context,String userId,String gameId,String giftId,Back back) {
		super(context);
		this.back=back;
		build=new GetGiftBuild(context, API_getGift_Url, userId, gameId,giftId);
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
		return new GetGiftNet(context, build.toJson1());
	}
	
}
