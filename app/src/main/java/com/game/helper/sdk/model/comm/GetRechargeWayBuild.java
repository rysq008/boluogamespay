package com.game.helper.sdk.model.comm;

import android.content.Context;

import com.game.helper.sdk.model.base.BaseRequest;

public class GetRechargeWayBuild extends BaseRequest{

	public GetRechargeWayBuild(Context context, String url,String gameId, String gameAccount,String userId) {
		super(context, url);
		params=new Params();
		params.gameId=gameId;
		params.gameAccount=gameAccount;
		params.userId=userId;
	}
	public Params params;
	
	class Params{
		
		public String gameId;
		public String gameAccount;
		public String userId;

	}
}
