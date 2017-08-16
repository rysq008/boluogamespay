package com.game.helper.sdk.model.comm;

import android.content.Context;
import com.game.helper.sdk.model.base.BaseRequest;

public class QueryGameImgBuild extends BaseRequest{

	public QueryGameImgBuild(Context context, String url,String gameId) {
		super(context, url);
		params=new Params();
		params.gameId=gameId;
	}
	public Params params;
	
	class Params{
	
		public String gameId;
		
	}
}
