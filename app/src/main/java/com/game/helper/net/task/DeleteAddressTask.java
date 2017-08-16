package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.DeleteAddressBuild;
import com.game.helper.sdk.net.comm.DeleteAddressNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.DeleteAddressTask.java
 * @Author lbb
 * @Date 2016年9月26日 上午10:05:23
 * @Company 
 */
public class DeleteAddressTask extends BaseBBXTask{
	DeleteAddressBuild build;
	public DeleteAddressTask(Context context,String addressId,Back back) {
		super(context);
		this.back=back;
		build=new DeleteAddressBuild(context, API_deleteAddress_Url,addressId);
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
		return new DeleteAddressNet(context, build.toJson1());
	}
	
}