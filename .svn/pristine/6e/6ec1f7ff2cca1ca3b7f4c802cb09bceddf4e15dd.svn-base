package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetDynamicInfoBuild;
import com.game.helper.sdk.net.comm.GetDynamicInfoNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.getDynamicInfo.java
 * @Author lbb
 * @Date 2016年9月16日 下午12:49:28
 * @Company 
 */
public class GetDynamicInfoTask  extends BaseBBXTask{
	GetDynamicInfoBuild build;
	public GetDynamicInfoTask(Context context,boolean isShow,String guildId,String offset,Back back) {
		super(context,isShow);
		this.back=back;
		build=new GetDynamicInfoBuild(context, API_getDynamicInfo_Url, guildId, offset);
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
		return new GetDynamicInfoNet(context, build.toJson1());
	}
	
}