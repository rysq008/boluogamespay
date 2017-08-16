package com.game.helper.sdk.model.comm;

import android.content.Context;

import com.game.helper.sdk.model.base.BaseRequest;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.QueryGameBykindAndTypeBuild.java
 * @Author lbb
 * @Date 2016年10月8日 上午10:09:37
 * @Company 
 */
public class QueryGameByPlatidBuild extends BaseRequest{

	public QueryGameByPlatidBuild(Context context, String url, String platid,String offset) {
		super(context, url);
		params=new Params();
		params.platId=platid;
		params.offset=offset;
	}
	public Params params;
	
	class Params{
		public String platId;
		public String offset;
	}
}
