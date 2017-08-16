package com.game.helper.net.task;

import java.util.List;
import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.InsertDynamicBuild;
import com.game.helper.sdk.model.returns.Images;
import com.game.helper.sdk.net.comm.InsertDynamicNet;

import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.insertDynamicTask.java
 * @Author lbb
 * @Date 2016年9月16日 下午1:13:57
 * @Company 
 */
public class InsertDynamicTask  extends BaseBBXTask{
	InsertDynamicBuild build;
	public InsertDynamicTask(Context context,String userId,String guildId,String content,String address, List<Images> imagesList,Back back) {
		super(context);
		this.back=back;
		build=new InsertDynamicBuild(context, API_insertDynamic_Url, userId, guildId,content,address,imagesList);
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
		return new InsertDynamicNet(context, build.toJson1());
	}
	
}
