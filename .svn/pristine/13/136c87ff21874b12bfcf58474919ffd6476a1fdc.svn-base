package com.game.helper.sdk.model.comm;

import android.content.Context;
import com.game.helper.sdk.model.base.BaseRequest;

public class CreateOrderBuild extends BaseRequest{

	public CreateOrderBuild(Context context, String url,String userId
			,String money,String ptb,String payWay,String retStatus,String tradeNo) {
		super(context, url);
		params=new Params();
		params.userId=userId;
		params.money=money;
		params.ptb=ptb;
		params.payWay=payWay;
		params.retStatus=retStatus;
		params.tradeNo=tradeNo;
	}
	public Params params;
	
	class Params{
		
		public String userId;
		public String money;
		public String ptb;
		public String payWay;
		public String retStatus;
		public String tradeNo;

	}
}
