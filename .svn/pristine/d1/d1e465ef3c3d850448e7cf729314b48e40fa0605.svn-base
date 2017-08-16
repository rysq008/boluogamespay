package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.UpdSexBuild.java
 * @Author lbb
 * @Date 2016年9月14日 下午2:12:10
 * @Company 
 */
public class UpdSexBuild extends BaseRequest{

	public UpdSexBuild(Context context, String url,String userId,String sex) {
		super(context, url);
		params=new Params();
		params.userId=userId;
		params.sex=sex;
	}
	public Params params;
	
	class Params{
		/* "userId":"100000",
        "sex":"男"*/
		public String userId;
		public String sex;
		
	}
}