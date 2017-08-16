package com.game.helper.sdk.model.comm;

import android.content.Context;
import com.game.helper.sdk.model.base.BaseRequest;

public class GetAccountBuild extends BaseRequest{

	public GetAccountBuild(Context context, String url,String userId,String platId,String gameId) {
		super(context, url);
		params=new Params();
		params.userId=userId;
		params.platId=platId;
		params.gameId=gameId;
	}
	public Params params;
	
	class Params{
		
		public String userId;
		public String platId;
		public String gameId;
	}
}
