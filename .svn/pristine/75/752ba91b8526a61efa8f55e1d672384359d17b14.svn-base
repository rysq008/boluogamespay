package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.QueryGameGiftBuild;
import com.game.helper.sdk.net.comm.QueryGameGiftNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.QueryGameGiftTask.java
 * @Author lbb
 * @Date 2016年10月8日 上午10:50:21
 * @Company 
 */
public class QueryGameGiftTask extends BaseBBXTask{
	QueryGameGiftBuild build;
	public QueryGameGiftTask(Context context,String offset,Back back) {
		super(context);
		this.back=back;
		build=new QueryGameGiftBuild(context, API_queryGameGift_Url,offset);
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
		return new QueryGameGiftNet(context, build.toJson1());
	}
	
}