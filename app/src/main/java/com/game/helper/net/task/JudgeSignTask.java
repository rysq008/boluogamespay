package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.JudgeSignBuild;
import com.game.helper.sdk.net.comm.JudgeSignNet;

import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.judgeSignTask.java
 * @Author lbb
 * @Date 2016年9月16日 上午11:11:35
 * @Company 
 */
public class JudgeSignTask  extends BaseBBXTask{
	JudgeSignBuild build;
	public JudgeSignTask(Context context,boolean isShow,String userId,String guildId,String nowDate,Back back) {
		super(context,isShow);
		this.back=back;
		build=new JudgeSignBuild(context, API_judgeSign_Url, userId, guildId,nowDate);
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
		return new JudgeSignNet(context, build.toJson1());
	}
	
}