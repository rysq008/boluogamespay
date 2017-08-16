package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.QueryFirstOneBuild.java
 * @Author lbb
 * @Date 2016年10月8日 上午10:40:03
 * @Company
 */
public class QueryFirstOneBuild extends BaseRequest{

	public QueryFirstOneBuild(Context context, String url) {
		super(context, url);
		params=new Params();
		
	}
	public Params params;
	
	class Params{
		
	}
}
