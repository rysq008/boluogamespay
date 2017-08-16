package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.QueryGameByModularBuild.java
 * @Author lbb
 * @Date 2016年10月8日 上午10:49:04
 * @Company
 */
public class QueryGameByModularBuild extends BaseRequest{

	public QueryGameByModularBuild(Context context, String url,String modular,String limit,String offset) {
		super(context, url);
		params=new Params();
		params.modular=modular;
		params.limit=limit;
		params.offset=offset;
	}
	public Params params;
	
	class Params{
	
		public String modular;
		public String limit;
		public String offset;

	}
}
