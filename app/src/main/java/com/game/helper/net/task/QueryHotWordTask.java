package com.game.helper.net.task;

import android.content.Context;
import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.QueryHotWordBuild;
import com.game.helper.sdk.net.comm.QueryHotWordNet;

public class QueryHotWordTask extends BaseBBXTask{
	QueryHotWordBuild build;
	public QueryHotWordTask(Context context,Back back) {
		super(context,false);
		this.back=back;
		build=new QueryHotWordBuild(context, API_queryHotWord_Url);
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
		return new QueryHotWordNet(context, build.toJson1());
	}
	
}
