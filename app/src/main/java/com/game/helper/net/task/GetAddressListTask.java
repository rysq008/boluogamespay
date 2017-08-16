package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetAddressListBuild;
import com.game.helper.sdk.net.comm.GetAddressListNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.GetAddressListTask.java
 * @Author lbb
 * @Date 2016年9月14日 下午2:27:06
 * @Company 
 */
public class GetAddressListTask extends BaseBBXTask{
	GetAddressListBuild build;
	public GetAddressListTask(Context context,boolean isShow,String userId,Back back) {
		super(context,isShow);
		this.back=back;
		build=new GetAddressListBuild(context, API_getAddressList_Url, userId);
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
		return new GetAddressListNet(context, build.toJson1());
	}
	
}
