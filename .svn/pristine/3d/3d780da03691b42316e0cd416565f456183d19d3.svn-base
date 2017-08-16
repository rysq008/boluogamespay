package com.game.helper.net.task;

import android.content.Context;
import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.StarUserBuild;
import com.game.helper.sdk.net.comm.StarUserNet;

public class StarUserTask extends BaseBBXTask{
	StarUserBuild build;
	public StarUserTask(Context context,String refreshDate,String offset,Back back) {
		super(context);
		this.back=back;
		build=new StarUserBuild(context, API_starUser_Url,refreshDate,offset);
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
		return new StarUserNet(context, build.toJson1());
	}
	
}
