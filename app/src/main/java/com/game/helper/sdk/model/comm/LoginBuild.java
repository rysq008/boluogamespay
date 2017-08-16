package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;

import android.content.Context;

public class LoginBuild extends BaseRequest{

	public LoginBuild(Context context, String url,String accountOrPhone
			,String pwd) {
		super(context, url);
		params=new Params();
		params.accountOrPhone=accountOrPhone;
		params.pwd=pwd;
	}
	public Params params;
	
	class Params{
		/*  "accountOrPhone":"18060478013",
        "pwd":"123456"*/
		
		public String accountOrPhone;
		public String pwd;
		
	}
	
}
