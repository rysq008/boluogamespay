package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.InsertShareBuild.java
 * @Author lbb
 * @Date 2016年12月5日 上午10:54:06
 * @Company 
 */
public class InsertShareBuild extends BaseRequest{

	public InsertShareBuild(Context context, String url,String type,String userId) {
		super(context, url);
		params=new Params();
		params.type=type;
		params.userId=userId;
	}
	public Params params;
	
	class Params{
	
		public String type;
		public String userId;
		
	}
}
