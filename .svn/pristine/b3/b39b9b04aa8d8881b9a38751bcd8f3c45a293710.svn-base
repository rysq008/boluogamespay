package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;

import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.GetCheckCodeBuild.java
 * @Author lbb
 * @Date 2016年9月12日 下午3:03:11
 * @Company 
 */
public class GetCheckCodeBuild extends BaseRequest{

	public GetCheckCodeBuild(Context context,String url,String phone_num) {
		super(context,url);
		params=new Params(phone_num);
	}
	
	public Params params;
	
	class Params{
		public String phone_num;

		public Params(String phone_num) {
			super();
			this.phone_num=phone_num;
		}
		
	}
}

