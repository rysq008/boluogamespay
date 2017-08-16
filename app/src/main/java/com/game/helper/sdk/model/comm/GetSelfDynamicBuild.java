package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.GetSelfDynamicBuild.java
 * @Author lbb
 * @Date 2016年9月26日 上午9:39:46
 * @Company 
 */
public class GetSelfDynamicBuild extends BaseRequest{

	public GetSelfDynamicBuild(Context context, String url,String userId,String offset) {
		super(context, url);
		params=new Params();
		params.userId=userId;
		params.offset=offset;
	}
	public Params params;
	
	class Params{
		
		public String userId;
		public String offset;
		
	}
}