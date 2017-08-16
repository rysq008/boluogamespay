package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.UpdPhoneBuild;
import com.game.helper.sdk.net.comm.UpdPhoneNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.UpdPhoneTask.java
 * @Author lbb
 * @Date 2016年9月14日 下午2:17:14
 * @Company
 */
public class UpdPhoneTask extends BaseBBXTask{
	UpdPhoneBuild build;
	public UpdPhoneTask(Context context,String userId,String phone,String checkCode,Back back) {
		super(context);
		this.back=back;
		build=new UpdPhoneBuild(context, API_updPhone_Url, userId, phone, checkCode);
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
		return new UpdPhoneNet(context, build.toJson1());
	}
	
}