package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.SaveGiveBuild;
import com.game.helper.sdk.net.comm.SaveGiveNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.SaveGiveTask.java
 * @Author lbb
 * @Date 2016年9月16日 上午10:04:16
 * @Company
 */
public class SaveGiveTask  extends BaseBBXTask{
	SaveGiveBuild build;
	public SaveGiveTask(Context context,String userId,String giftId,String operId,String count,Back back) {
		super(context);
		this.back=back;
		build=new SaveGiveBuild(context, API_saveGive_Url, userId, giftId, operId, count);
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
		return new SaveGiveNet(context, build.toJson1());
	}
	
}