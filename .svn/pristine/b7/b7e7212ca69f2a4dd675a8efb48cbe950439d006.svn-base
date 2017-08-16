package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.JudgeFocusBuild;
import com.game.helper.sdk.net.comm.JudgeFocusNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.JudgeFocusTask.java
 * @Author lbb
 * @Date 2016年9月27日 下午4:27:53
 * @Company 
 */
public class JudgeFocusTask extends BaseBBXTask{
	JudgeFocusBuild build;
	public JudgeFocusTask(Context context,boolean isShow,String operId,String userId,Back back) {
		super(context,isShow);
		this.back=back;
		build=new JudgeFocusBuild(context, API_judgeFocus_Url, operId, userId);
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
		return new JudgeFocusNet(context, build.toJson1());
	}
	
}