package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.MygameBuild;
import com.game.helper.sdk.net.comm.MygameNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.MygameTask.java
 * @Author lbb
 * @Date 2016年10月8日 上午9:53:02
 * @Company 
 */
public class MygameTask extends BaseBBXTask{
	MygameBuild build;
	public MygameTask(Context context,boolean isShow,String userId,Back back) {
		super(context,isShow);
		this.back=back;
		build=new MygameBuild(context, API_mygame_Url, userId);
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
		return new MygameNet(context, build.toJson1());
	}
	
}
