package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.getDynamicInfoBuild.java
 * @Author lbb
 * @Date 2016年9月16日 下午12:49:46
 * @Company 
 */
public class GetDynamicInfoBuild extends BaseRequest{

	public GetDynamicInfoBuild(Context context, String url,String guildId,String offset) {
		super(context, url);
		params=new Params();
		params.offset=offset;
		params.guildId=guildId;
	}
	public Params params;
	
	class Params{
		/*   "offset":"0",
        "guildId":"1"*/
		public String guildId;
		public String offset;
		
	}
}