package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.SaveNoticeBuild;
import com.game.helper.sdk.net.comm.SaveNoticeNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.SaveNoticeTask.java
 * @Author lbb
 * @Date 2016年9月16日 上午10:36:39
 * @Company 
 */
public class SaveNoticeTask  extends BaseBBXTask{
	SaveNoticeBuild build;
	public SaveNoticeTask(Context context,String guildId,String noticeName,String content,Back back) {
		super(context);
		this.back=back;
		build=new SaveNoticeBuild(context, API_saveNotice_Url, guildId,  noticeName, content);
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
		return new SaveNoticeNet(context, build.toJson1());
	}
	
}
