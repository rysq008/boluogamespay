package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.JoinOrExitGuildBuild.java
 * @Author lbb
 * @Date 2016年9月16日 上午10:09:06
 * @Company 
 */
public class JoinOrExitGuildBuild extends BaseRequest{

	public JoinOrExitGuildBuild(Context context, String url,String userId,String guildId,String type) {
		super(context, url);
		params=new Params();
		params.userId=userId;
		params.guildId=guildId;
		params.type=type;
	}
	public Params params;
	
	class Params{
		/*  "guildId":"1",
        "userId":"10000",
        "type":"0" */
		public String userId;
		public String guildId;
		public String type;
	}
}