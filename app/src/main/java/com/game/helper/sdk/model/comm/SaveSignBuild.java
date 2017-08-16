package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.saveSignBuild.java
 * @Author lbb
 * @Date 2016年9月16日 上午11:08:37
 * @Company 
 */
public class SaveSignBuild extends BaseRequest{

	public SaveSignBuild(Context context, String url,String userId,String guildId) {
		super(context, url);
		params=new Params();
		params.userId=userId;
		params.guildId=guildId;
	}
	public Params params;
	
	class Params{
		/*  "guildId":"1",
        "userId":"10000"*/
		public String userId;
		public String guildId;
		
	}
}