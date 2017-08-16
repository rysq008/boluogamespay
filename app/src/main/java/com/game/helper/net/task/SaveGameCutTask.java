package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.SaveGameCutBuild;
import com.game.helper.sdk.net.comm.SaveGameCutNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.SaveGameCutTask.java
 * @Author lbb
 * @Date 2016年10月12日 上午9:17:01
 * @Company 
 */
public class SaveGameCutTask extends BaseBBXTask{
	SaveGameCutBuild build;
	public SaveGameCutTask(Context context,String userId,String gameId
			,String title,String content,String img1,String img2,String img3,String img4,String img5,Back back) {
		super(context);
		this.back=back;
		build=new SaveGameCutBuild(context, API_saveGameCut_Url, userId, gameId, title, content, img1, img2, img3, img4, img5);
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
		return new SaveGameCutNet(context, build.toJson1());
	}
	
}
