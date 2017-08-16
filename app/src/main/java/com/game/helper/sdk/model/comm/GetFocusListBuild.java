package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.GetFocusListBuild.java
 * @Author lbb
 * @Date 2016年9月16日 上午10:16:36
 * @Company 
 */
public class GetFocusListBuild extends BaseRequest{

	public GetFocusListBuild(Context context, String url,String userId,String type) {
		super(context, url);
		params=new Params();
		params.userId=userId;
		params.type=type;
	}
	public Params params;
	
	class Params{
		/* "userId":"10000",
         "type":"1"*/
		public String userId;
		public String type;
		
	}
}
