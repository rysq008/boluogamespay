package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.QueryGameCutBuild.java
 * @Author lbb
 * @Date 2016年10月12日 上午9:42:03
 * @Company 
 */
public class QueryGameCutBuild extends BaseRequest{

	public QueryGameCutBuild(Context context, String url,String gameId) {
		super(context, url);
		params=new Params();
		params.gameId=gameId;
	}
	public Params params;
	
	class Params{
		
		public String gameId;
		
	}
}
