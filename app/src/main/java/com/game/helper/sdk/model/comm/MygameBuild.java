package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.MygameBuild.java
 * @Author lbb
 * @Date 2016年10月8日 上午9:53:13
 * @Company 
 */
public class MygameBuild extends BaseRequest{

	public MygameBuild(Context context, String url,String userId) {
		super(context, url);
		params=new Params();
		params.userId=userId;
	}
	public Params params;
	
	class Params{
		
		public String userId;
		
	}
}
