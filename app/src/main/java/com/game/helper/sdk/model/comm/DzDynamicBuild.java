package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.dzDynamicBuild.java
 * @Author lbb
 * @Date 2016年9月16日 下午1:00:04
 * @Company 
 */
public class DzDynamicBuild extends BaseRequest{

	public DzDynamicBuild(Context context, String url,String dynamicId,String userId) {
		super(context, url);
		params=new Params();
		params.dynamicId=dynamicId;
		params.userId=userId;
	}
	public Params params;
	
	class Params{
		/*  "dynamicId":"542",
        "userId":"10000"*/
		public String dynamicId;
		public String userId;
		
	}
}