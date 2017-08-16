package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetContentTypeListBuild;
import com.game.helper.sdk.net.comm.GetContentTypeListNet;

import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.GetContentTypeListTask.java
 * @Author lbb
 * @Date 2016年9月22日 下午5:55:21
 * @Company 
 */
public class GetContentTypeListTask  extends BaseBBXTask{
	GetContentTypeListBuild build;
	public GetContentTypeListTask(Context context,Back back) {
		super(context,false);
		this.back=back;
		build=new GetContentTypeListBuild(context, API_getContentTypeList_Url);
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
		return new GetContentTypeListNet(context, build.toJson1());
	}
	
}
