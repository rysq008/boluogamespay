package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.UpdNickBuild.java
 * @Author lbb
 * @Date 2016年9月14日 上午11:26:48
 * @Company 
 */
public class UpdNickBuild  extends BaseRequest{

	public UpdNickBuild(Context context, String url,String userId,String nickname) {
		super(context, url);
		params=new Params();
		params.userId=userId;
		params.nickname=nickname;
	}
	public Params params;
	
	class Params{
		/*  "userId":"100000",
        "nickname":"小明"*/
		public String userId;
		public String nickname;
		
	}
}