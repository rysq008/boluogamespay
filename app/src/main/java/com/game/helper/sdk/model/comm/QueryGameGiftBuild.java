package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.QueryGameGiftBuild.java
 * @Author lbb
 * @Date 2016年10月8日 上午10:50:39
 * @Company 
 */
public class QueryGameGiftBuild extends BaseRequest{

	public QueryGameGiftBuild(Context context, String url,String offset) {
		super(context, url);
		params=new Params();
		params.offset=offset;
		
	}
	public Params params;
	
	class Params{
		public String offset;
	}
}