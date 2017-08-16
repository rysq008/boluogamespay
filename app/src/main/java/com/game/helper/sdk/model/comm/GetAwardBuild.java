package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.GetAwardBuild.java
 * @Author lbb
 * @Date 2016年9月14日 下午3:45:19
 * @Company
 */
public class GetAwardBuild extends BaseRequest{

	public GetAwardBuild(Context context, String url,String userId,String type) {
		super(context, url);
		params=new Params();
		params.userId=userId;
		params.type=type;
	}
	public Params params;
	
	class Params{
		/*  "userId":"10000",
        "type":"1"*/
		public String userId;
		public String type;
		
	}
}