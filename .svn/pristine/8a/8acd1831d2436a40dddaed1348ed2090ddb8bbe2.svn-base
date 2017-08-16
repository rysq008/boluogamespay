package com.game.helper.net.task;

import android.content.Context;
import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.CreateOrderBuild;
import com.game.helper.sdk.net.comm.CreateOrderNet;

public class CreateOrderTask extends BaseBBXTask{
	CreateOrderBuild build;
	//retStatus 0：第一次请求。 结果：1成功， -1失败
	public CreateOrderTask(Context context,String userId
			,String money,String ptb,String payWay,String retStatus,String tradeNo,Back back) {
		super(context);
		this.back=back;
		build=new CreateOrderBuild(context, API_createOrder_Url, userId,money, ptb, payWay,retStatus,tradeNo);
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
		return new CreateOrderNet(context, build.toJson1());
	}
	
}