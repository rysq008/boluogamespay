package com.game.helper.sdk.model.comm;

import android.content.Context;
import com.game.helper.sdk.model.base.BaseRequest;

public class QueryMyGiftBuild extends BaseRequest{

	public QueryMyGiftBuild(Context context, String url,String userId) {
		super(context, url);
		params=new Params();
		params.userId=userId;
	}
	public Params params;
	
	class Params{
		
		public String userId;
		
	}
}
