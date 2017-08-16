package com.game.helper.net.task;

import android.content.Context;
import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.CreateGameOrderBuild;
import com.game.helper.sdk.net.comm.CreateGameOrderNet;

public class CreateGameOrderTask extends BaseBBXTask{
	CreateGameOrderBuild build;
	public CreateGameOrderTask(Context context,String userId,
			String gameId, String gameAccount,String payWay,String orderType
			, String ptb,String money/*,String card*/,String realPay,String saveMoney,String retStatus,String tradeNo,Back back) {
		super(context);
		this.back=back;
		build=new CreateGameOrderBuild(context, API_createGameOrder_Url, userId, gameId, gameAccount, payWay
				, orderType, ptb, money/*, card*/, realPay, saveMoney,retStatus,tradeNo);
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
		return new CreateGameOrderNet(context, build.toJson1());
	}
	
}