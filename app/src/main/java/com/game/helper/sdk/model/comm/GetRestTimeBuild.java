package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.getRestTimeBuild.java
 * @Author lbb
 * @Date 2016年9月16日 下午12:28:19
 * @Company 
 */
public class GetRestTimeBuild extends BaseRequest{

	public GetRestTimeBuild(Context context, String url,String userId) {
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
