package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;

import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.MyWithdrawBuild.java
 * @Author lbb
 * @Date 2016年11月24日 下午4:03:56
 * @Company
 */
public class MyWithdrawBuild extends BaseRequest{

	public MyWithdrawBuild(Context context, String url,String userId) {
		super(context, url);
		params=new Params();
		params.userId=userId;
	}
	public Params params;
	
	class Params{
		
		public String userId;
		
	}
}
