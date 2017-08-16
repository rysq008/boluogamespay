package com.game.helper.sdk.model.comm;

import android.content.Context;

import com.game.helper.sdk.model.base.BaseRequest;

public class RegisterBuild extends BaseRequest{

	public RegisterBuild(Context context, String url,String phone
			,String checkCode,String pwd,String inviteCode,String channelId,String channelName,String deviceName) {
		super(context, url);
		params=new Params();
		params.phone=phone;
		params.checkCode=checkCode;
		params.pwd=pwd;
		params.inviteCode=inviteCode;
		params.channelId=channelId;
		params.channelName=channelName;
		params.deviceName=deviceName;
	}
	public Params params;
	
	class Params{
		/* "phone":"18060478013",
	        "checkCode":"607483",
	        "pwd":"123456"*/
		
		public String phone;
		public String checkCode;
		public String pwd;
		public String inviteCode;
		public String channelId;
		public String channelName;
		public String deviceName;
	}
	
}
