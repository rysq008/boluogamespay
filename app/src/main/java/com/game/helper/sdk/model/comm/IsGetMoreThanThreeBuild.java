package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.IsGetMoreThanThreeBuild.java
 * @Author lbb
 * @Date 2016年11月9日 上午9:53:53
 * @Company 
 */
public class IsGetMoreThanThreeBuild extends BaseRequest{

	public IsGetMoreThanThreeBuild(Context context, String url,String userId) {
		super(context, url);
		params=new Params();
		params.userId=userId;
	}
	public Params params;
	
	class Params{
		
		public String userId;
		
	}
}