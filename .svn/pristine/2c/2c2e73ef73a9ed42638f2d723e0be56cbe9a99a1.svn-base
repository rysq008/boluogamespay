package com.game.helper.net.task;

import android.content.Context;
import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetTradeListBuild;
import com.game.helper.sdk.net.comm.GetTradeListNet;

public class GetTradeListTask extends BaseBBXTask{
	GetTradeListBuild build;
	public GetTradeListTask(Context context,String userId,String type,String tradeName,Back back) {
		super(context);
		this.back=back;
		build=new GetTradeListBuild(context, API_getTradeList_Url, userId, type,tradeName);
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
		return new GetTradeListNet(context, build.toJson1());
	}
	
}
