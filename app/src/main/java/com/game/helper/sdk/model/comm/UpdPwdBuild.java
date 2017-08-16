package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.UpdPwdBuild.java
 * @Author lbb
 * @Date 2016年9月14日 上午11:12:54
 * @Company 
 */
public class UpdPwdBuild  extends BaseRequest{

	public UpdPwdBuild(Context context, String url,String userId,String pwd) {
		super(context, url);
		params=new Params();
		params.userId=userId;
		params.pwd=pwd;
	}
	public Params params;
	
	class Params{
		/* "userId":"100000",
        "pwd":"123456"*/
		public String userId;
		public String pwd;
		
	}
}

