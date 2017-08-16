package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.SaveGameCutOptypeBuild;
import com.game.helper.sdk.net.comm.SaveGameCutOptypeNet;

import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.SaveGameCutOptypeTask.java
 * @Author lbb
 * @Date 2016年10月12日 上午9:31:44
 * @Company 
 */
public class SaveGameCutOptypeTask extends BaseBBXTask{
	SaveGameCutOptypeBuild build;
	public SaveGameCutOptypeTask(Context context,String userId,String cutId,String opType,String content,Back back) {
		super(context,false);
		this.back=back;
		build=new SaveGameCutOptypeBuild(context, API_saveGameCutOptype_Url, userId, cutId, opType, content);
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
		return new SaveGameCutOptypeNet(context, build.toJson1());
	}
	
}