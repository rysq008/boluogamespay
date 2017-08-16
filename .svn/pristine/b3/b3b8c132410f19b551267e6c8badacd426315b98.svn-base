package com.game.helper.sdk.model.comm;

import android.content.Context;
import com.game.helper.sdk.model.base.BaseRequest;

public class ResetPwdBuild  extends BaseRequest{

	public ResetPwdBuild(Context context, String url,String phone
			,String checkCode,String pwd) {
		super(context, url);
		params=new Params();
		params.phone=phone;
		params.checkCode=checkCode;
		params.pwd=pwd;
	}
	public Params params;
	
	class Params{
		/* "phone":"18060478013",
	        "checkCode":"607483",
	        "pwd":"123456"*/
		
		public String phone;
		public String checkCode;
		public String pwd;
		
	}
	
}

