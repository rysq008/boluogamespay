package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.getGuildByIdBuild.java
 * @Author lbb
 * @Date 2016年9月16日 上午11:18:53
 * @Company 
 */
public class GetGuildByIdBuild extends BaseRequest{

	public GetGuildByIdBuild(Context context, String url,String guildId) {
		super(context, url);
		params=new Params();
		params.guildId=guildId;
	}
	public Params params;
	
	class Params{
		/*  "guildId":"1"*/
		public String guildId;
	}
}