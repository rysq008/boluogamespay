package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetQuestionBuild;
import com.game.helper.sdk.net.comm.GetQuestionNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.GetQuestionTask.java
 * @Author lbb
 * @Date 2016年10月21日 下午4:42:12
 * @Company 
 */
public class GetQuestionTask extends BaseBBXTask{
	GetQuestionBuild build;
	public GetQuestionTask(Context context,String questionId,Back back) {
		super(context);
		this.back=back;
		build=new GetQuestionBuild(context, API_getQuestion_Url, questionId);
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
		return new GetQuestionNet(context, build.toJson1());
	}
	
}
