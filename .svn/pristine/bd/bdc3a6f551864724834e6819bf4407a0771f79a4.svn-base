package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.QueryGameBykindAndTypeBuild;
import com.game.helper.sdk.net.comm.QueryGameBykindAndTypeNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.QueryGameBykindAndTypeTask.java
 * @Author lbb
 * @Date 2016年10月8日 上午10:09:20
 * @Company 
 */
public class QueryGameBykindAndTypeTask  extends BaseBBXTask{
	QueryGameBykindAndTypeBuild build;
	public QueryGameBykindAndTypeTask(Context context,String typeId,String kindId,String orderby,String offset,Back back) {
		super(context,false);
		this.back=back;
		build=new QueryGameBykindAndTypeBuild(context, API_queryGameBykindAndType_Url, typeId, kindId,orderby,offset);
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
		return new QueryGameBykindAndTypeNet(context, build.toJson1());
	}
	
}