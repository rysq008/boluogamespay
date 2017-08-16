package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetFocusListBuild;
import com.game.helper.sdk.net.comm.GetFocusListNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.GetFocusListTask.java
 * @Author lbb
 * @Date 2016年9月16日 上午10:16:25
 * @Company 
 */
public class GetFocusListTask  extends BaseBBXTask{
	GetFocusListBuild build;
	public GetFocusListTask(Context context,boolean isShow,String userId,String type,Back back) {
		super(context,isShow);
		this.back=back;
		build=new GetFocusListBuild(context, API_getFocusList_Url, userId, type);
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
		return new GetFocusListNet(context, build.toJson1());
	}
	
}
