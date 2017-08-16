package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetDynamicInfo_1Build;
import com.game.helper.sdk.net.comm.GetDynamicInfo_1Net;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.getDynamicInfoTask.java
 * @Author lbb
 * @Date 2016年9月16日 下午12:17:41
 * @Company 
 */
public class GetDynamicInfo_1Task  extends BaseBBXTask{
	GetDynamicInfo_1Build build;
	public GetDynamicInfo_1Task(Context context,boolean isShow,String userId,String offset,Back back) {
		super(context,isShow);
		this.back=back;
		build=new GetDynamicInfo_1Build(context, API_getDynamicInfo_1_Url, userId, offset);
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
		return new GetDynamicInfo_1Net(context, build.toJson1());
	}
	
}
