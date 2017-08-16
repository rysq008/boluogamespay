package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.SaveOrUpdateAddressBuild;
import com.game.helper.sdk.net.comm.SaveOrUpdateAddressNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.SaveOrUpdateAddressTask.java
 * @Author lbb
 * @Date 2016年9月14日 下午3:05:53
 * @Company 
 */
public class SaveOrUpdateAddressTask extends BaseBBXTask{
	SaveOrUpdateAddressBuild build;
	public SaveOrUpdateAddressTask(Context context,String userId,String realName
			,String phone,String cityName,String areaName,String address,String isDefault,String addressId,Back back) {
		super(context);
		this.back=back;
		build=new SaveOrUpdateAddressBuild(context, API_saveOrUpdateAddress_Url, userId, realName,
				phone, cityName, areaName, address, isDefault, addressId);
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
		return new SaveOrUpdateAddressNet(context, build.toJson1());
	}
	
}