package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetGoodListBuild;
import com.game.helper.sdk.net.comm.GetGoodListNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.GetGoodListTask.java
 * @Author lbb
 * @Date 2016年9月14日 下午2:22:17
 * @Company 
 */
public class GetGoodListTask extends BaseBBXTask{
	GetGoodListBuild build;
	public GetGoodListTask(Context context,boolean isShow,Back back) {
		super(context,isShow);
		this.back=back;
		build=new GetGoodListBuild(context, API_getGoodList_Url);
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
		return new GetGoodListNet(context, build.toJson1());
	}
	
}