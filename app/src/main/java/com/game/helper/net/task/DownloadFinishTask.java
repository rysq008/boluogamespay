package com.game.helper.net.task;

import android.content.Context;
import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.DownloadFinishBuild;
import com.game.helper.sdk.net.comm.DownloadFinishNet;

public class DownloadFinishTask extends BaseBBXTask{
	DownloadFinishBuild build;
	public DownloadFinishTask(Context context,String userId,String gameId,Back back) {
		super(context,false);
		this.back=back;
		build=new DownloadFinishBuild(context, API_downloadFinish_Url, userId, gameId);
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
		return new DownloadFinishNet(context, build.toJson1());
	}
	
}
