package com.game.helper.sdk.model.comm;

import android.content.Context;
import com.game.helper.sdk.model.base.BaseRequest;

public class GetTradeListBuild extends BaseRequest{

	public GetTradeListBuild(Context context, String url,String userId,String type,String tradeName) {
		super(context, url);
		params=new Params();
		params.userId=userId;
		params.type=type;
		params.tradeName=tradeName;
	}
	public Params params;
	
	class Params{
		
		public String userId;
		public String type;
		public String tradeName;
	}
}