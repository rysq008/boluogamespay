package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.UpdSignBuild.java
 * @Author lbb
 * @Date 2016年9月14日 下午12:13:39
 * @Company 
 */
public class UpdSignBuild extends BaseRequest{

	public UpdSignBuild(Context context, String url,String userId,String sign) {
		super(context, url);
		params=new Params();
		params.userId=userId;
		params.sign=sign;
	}
	public Params params;
	
	class Params{
		/* "userId":"100000",
        "sign":"大帅比"*/
		public String userId;
		public String sign;
		
	}
}