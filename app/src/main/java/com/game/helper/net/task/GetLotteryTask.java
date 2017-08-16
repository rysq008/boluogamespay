package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetLotteryBuild;
import com.game.helper.sdk.net.comm.GetLotteryNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.GetLotteryTask.java
 * @Author lbb
 * @Date 2016年9月14日 下午3:38:03
 * @Company 
 */
public class GetLotteryTask extends BaseBBXTask{
	GetLotteryBuild build;
	public GetLotteryTask(Context context,String msg,String userId,String goodId,Back back) {
		super(context,msg,true);
		this.back=back;
		build=new GetLotteryBuild(context, API_getLottery_Url, userId, goodId);
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
		return new GetLotteryNet(context, build.toJson1());
	}
	
}