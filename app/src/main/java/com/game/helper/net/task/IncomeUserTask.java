package com.game.helper.net.task;

import android.content.Context;
import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.IncomeUserBuild;
import com.game.helper.sdk.net.comm.IncomeUserNet;

public class IncomeUserTask  extends BaseBBXTask{
	IncomeUserBuild build;
	public IncomeUserTask(Context context,String refreshDate,String offset,Back back) {
		super(context);
		this.back=back;
		build=new IncomeUserBuild(context, API_incomeUser_Url,refreshDate,offset);
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
		return new IncomeUserNet(context, build.toJson1());
	}
	
}
