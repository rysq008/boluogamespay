package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;

import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.getGiveListBuild.java
 * @Author lbb
 * @Date 2016年9月16日 上午11:24:50
 * @Company 
 */
public class GetGiveListBuild extends BaseRequest{

	public GetGiveListBuild(Context context, String url,String userId) {
		super(context, url);
		params=new Params();
		params.userId=userId;
	}
	public Params params;
	
	class Params{
		/* "userId":"100000"*/
		public String userId;
		
	}
}
