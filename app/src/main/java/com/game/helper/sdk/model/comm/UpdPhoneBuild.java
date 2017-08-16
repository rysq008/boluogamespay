package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.UpdPhoneBuild.java
 * @Author lbb
 * @Date 2016年9月14日 下午2:17:52
 * @Company 
 */
public class UpdPhoneBuild extends BaseRequest{

	public UpdPhoneBuild(Context context, String url,String userId,String phone,String checkCode) {
		super(context, url);
		params=new Params();
		params.userId=userId;
		params.phone=phone;
		params.checkCode=checkCode;
	}
	public Params params;
	
	class Params{
		/*  "userId":"100000",
        "phone":"18060478013",
        "checkCode":"111111"*/
		public String userId;
		public String phone;
		public String checkCode;
	}
}