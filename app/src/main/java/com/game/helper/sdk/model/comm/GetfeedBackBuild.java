package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.GetfeedBackBuild.java
 * @Author lbb
 * @Date 2016年11月24日 下午3:54:59
 * @Company 
 */
public class GetfeedBackBuild extends BaseRequest{

	public GetfeedBackBuild(Context context, String url,String userId) {
		super(context, url);
		params=new Params();
		params.userId=userId;
	}
	public Params params;
	
	class Params{
		
		public String userId;
		
	}
}