package com.game.helper.net.task;

import android.content.Context;
import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.SavefeedBackBuild;
import com.game.helper.sdk.net.comm.SavefeedBackNet;

public class SavefeedBackTask extends BaseBBXTask{
	SavefeedBackBuild build;
	public SavefeedBackTask(Context context,String userId,String feedbackContent,Back back) {
		super(context);
		this.back=back;
		build=new SavefeedBackBuild(context, API_savefeedBack_Url, userId, feedbackContent);
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
		return new SavefeedBackNet(context, build.toJson1());
	}
	
}
