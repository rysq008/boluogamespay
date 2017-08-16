package com.game.helper.net.task;

import android.content.Context;
import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetQuestionlistBuild;
import com.game.helper.sdk.net.comm.GetQuestionlistNet;

public class GetQuestionlistTask extends BaseBBXTask{
	GetQuestionlistBuild build;
	public GetQuestionlistTask(Context context,Back back) {
		super(context);
		this.back=back;
		build=new GetQuestionlistBuild(context, API_getQuestionlist_Url);
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
		return new GetQuestionlistNet(context, build.toJson1());
	}
	
}
