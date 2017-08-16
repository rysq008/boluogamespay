package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.GetGuildUserBuild.java
 * @Author lbb
 * @Date 2016年9月16日 上午10:26:44
 * @Company 
 */
public class GetGuildUserBuild extends BaseRequest{

	public GetGuildUserBuild(Context context, String url,String guildId,String nickName) {
		super(context, url);
		params=new Params();
		params.guildId=guildId;
		params.nickName=nickName;
	}
	public Params params;
	
	class Params{
		/* "guildId":"1",
        "nickName":"xx",*/
		public String guildId;
		public String nickName;
		
	}
}
