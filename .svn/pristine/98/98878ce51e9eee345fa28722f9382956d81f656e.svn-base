package com.game.helper.net.task;

import android.content.Context;
import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.BrowseBuild;
import com.game.helper.sdk.net.comm.BrowseNet;

public class BrowseTask extends BaseBBXTask{
	BrowseBuild build;
	public BrowseTask(Context context,String userId,String gameId,Back back) {
		super(context,false);
		this.back=back;
		build=new BrowseBuild(context, API_browse_Url, userId, gameId);
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
		return new BrowseNet(context, build.toJson1());
	}
	
}
