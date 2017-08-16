package com.game.helper.sdk.model.comm;

import android.content.Context;
import com.game.helper.sdk.model.base.BaseRequest;

public class SaveWithdrawBuild extends BaseRequest{

	public SaveWithdrawBuild(Context context, String url,String userId,String money
			,String txWay,String account,String accountName) {
		super(context, url);
		params=new Params();
		params.userId=userId;
		params.money=money;
		params.txWay=txWay;
		params.account=account;
		params.accountName=accountName;
	}
	public Params params;
	
	class Params{
		/*   "userId":"10000",
         "money":"100",
         "txWay":"alipay",
         "account":"180383892922",
         "accountName":"多多益善"*/
		public String userId;
		public String money;
		public String txWay;
		public String account;
		public String accountName;
	}
}