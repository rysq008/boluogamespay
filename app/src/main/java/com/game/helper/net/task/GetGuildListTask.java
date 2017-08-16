package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetGuildListBuild;
import com.game.helper.sdk.net.comm.GetGuildListNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.GetGuildListTask.java
 * @Author lbb
 * @Date 2016年9月16日 上午10:23:32
 * @Company 
 */
public class GetGuildListTask  extends BaseBBXTask{
	GetGuildListBuild build;
	public GetGuildListTask(Context context,boolean isShow,String keyWord,Back back) {
		super(context,isShow);
		this.back=back;
		build=new GetGuildListBuild(context, API_getGuildList_Url, keyWord);
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
		return new GetGuildListNet(context, build.toJson1());
	}
	
}
