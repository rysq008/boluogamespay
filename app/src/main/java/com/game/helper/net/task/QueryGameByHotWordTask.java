package com.game.helper.net.task;

import android.content.Context;
import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.QueryGameByHotWordBuild;
import com.game.helper.sdk.net.comm.QueryGameByHotWordNet;

public class QueryGameByHotWordTask extends BaseBBXTask{
	QueryGameByHotWordBuild build;
	public QueryGameByHotWordTask(Context context,String hotWord,String platId, String offset,Back back) {
		super(context);
		this.back=back;
		build=new QueryGameByHotWordBuild(context, API_queryGameByHotWord_Url, hotWord, platId,offset);
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
		return new QueryGameByHotWordNet(context, build.toJson1());
	}
	
}
