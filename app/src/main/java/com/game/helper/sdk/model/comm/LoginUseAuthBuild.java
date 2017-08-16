package com.game.helper.sdk.model.comm;

import android.content.Context;

import com.game.helper.sdk.model.base.BaseRequest;

public class LoginUseAuthBuild extends BaseRequest{

	public LoginUseAuthBuild(Context context, String url, String phone, String checkCode) {
		super(context, url);
		params=new Params();
		params.phone=phone;
		params.checkCode=checkCode;
	}
	public Params params;
	
	class Params{

		public String phone;
		public String checkCode;
		
	}
	
}
