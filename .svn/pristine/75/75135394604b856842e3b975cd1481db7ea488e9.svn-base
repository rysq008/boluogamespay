package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.SaveExchangeBuild;
import com.game.helper.sdk.net.comm.SaveExchangeNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.SaveExchangeTask.java
 * @Author lbb
 * @Date 2016年9月14日 下午3:32:15
 * @Company 
 */
public class SaveExchangeTask extends BaseBBXTask{
	SaveExchangeBuild build;
	public SaveExchangeTask(Context context,String msg,String userId,String goodId,String addressId,String remark,Back back) {
		super(context,msg,true);
		this.back=back;
		build=new SaveExchangeBuild(context, API_saveExchange_Url, userId, goodId, addressId,remark);
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
		return new SaveExchangeNet(context, build.toJson1());
	}
	
}