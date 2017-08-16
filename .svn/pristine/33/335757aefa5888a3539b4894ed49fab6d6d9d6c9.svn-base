package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetContactListBuild;
import com.game.helper.sdk.net.comm.GetContactListNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.GetContactListTask.java
 * @Author lbb
 * @Date 2016年9月22日 下午6:14:39
 * @Company 
 */
public class GetContactListTask  extends BaseBBXTask{
	GetContactListBuild build;
	public GetContactListTask(Context context,Back back) {
		super(context,false);
		this.back=back;
		build=new GetContactListBuild(context, API_getContactList_Url);
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
		return new GetContactListNet(context, build.toJson1());
	}
	
}
