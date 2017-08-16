package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetCheckCodeBuild;
import com.game.helper.sdk.net.comm.GetCheckCodeNet;

import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.GetCheckCodeTask.java
 * @Author lbb
 * @Date 2016年9月12日 下午3:30:03
 * @Company 
 */
public class GetCheckCodeTask extends BaseBBXTask{
	GetCheckCodeBuild build;
	public GetCheckCodeTask(Context context,String phone,Back back) {
		super(context);
		build=new GetCheckCodeBuild(context,API_V_CODE_Url,phone);
		this.back=back;
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
		return new GetCheckCodeNet(context, build.toJson1());
	}

}
