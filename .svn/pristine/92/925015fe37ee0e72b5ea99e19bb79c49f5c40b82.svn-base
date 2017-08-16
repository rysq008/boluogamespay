package com.game.helper.sdk.model.comm;

import android.content.Context;
import com.game.helper.sdk.model.base.BaseRequest;

public class CreateGameOrderBuild extends BaseRequest{

	public CreateGameOrderBuild(Context context, String url,String userId,
			String gameId, String gameAccount,String payWay,String orderType
			, String ptb,String money/*,String card*/,String realPay,String saveMoney,String retStatus,String tradeNo) {
		super(context, url);
		params=new Params();
		params.userId=userId;
		params.gameId=gameId;
		params.gameAccount=gameAccount;
		params.payWay=payWay;
		params.orderType=orderType;
		params.ptb=ptb;
		params.money=money;
		//params.card=card;
		params.realPay=realPay;
		params.saveMoney=saveMoney;
		params.retStatus=retStatus;
		params.tradeNo=tradeNo;
	}
	public Params params;
	
	class Params{
		
		public String userId;
		public String gameId;
		public String gameAccount;
		public String payWay;
		public String orderType;
		public String ptb;
		public String money;
		//public String card;
		public String realPay;
		public String saveMoney;
		public String retStatus;
		public String tradeNo;
	}
}