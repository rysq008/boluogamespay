package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetInfoListBuild;
import com.game.helper.sdk.net.comm.GetInfoListNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.getInfoListTask.java
 * @Author lbb
 * @Date 2016年9月16日 上午11:27:05
 * @Company 
 */
public class GetInfoListTask  extends BaseBBXTask{
	GetInfoListBuild build;
	public GetInfoListTask(Context context,boolean isShow,String typeId,String offset,String refreshDate,Back back) {
		super(context,isShow);
		this.back=back;
		build=new GetInfoListBuild(context, API_getInfoList_Url, typeId, offset, refreshDate);
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
		return new GetInfoListNet(context, build.toJson1());
	}
	
}
