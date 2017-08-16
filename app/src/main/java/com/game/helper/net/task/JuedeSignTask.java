package com.game.helper.net.task;

import android.content.Context;
import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.JuedeSignBuild;
import com.game.helper.sdk.net.comm.JuedeSignNet;

public class JuedeSignTask extends BaseBBXTask{
	JuedeSignBuild build;
	public JuedeSignTask(Context context,String userId,Back back) {
		super(context,false);
		this.back=back;
		build=new JuedeSignBuild(context, API_juedeSign_Url, userId);
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
		return new JuedeSignNet(context, build.toJson1());
	}
	
}
