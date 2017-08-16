package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.GetExchangelistBuild.java
 * @Author lbb
 * @Date 2016年9月22日 下午3:53:56
 * @Company 
 */
public class GetExchangelistBuild extends BaseRequest{

	public GetExchangelistBuild(Context context, String url,String userId) {
		super(context, url);
		params=new Params();
		params.userId=userId;
	}
	public Params params;
	
	class Params{
		public String userId;
		
	}
}
