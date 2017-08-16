package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.getDynamicInfoBuild.java
 * @Author lbb
 * @Date 2016年9月16日 下午12:17:55
 * @Company 
 */
public class GetDynamicInfo_1Build extends BaseRequest{

	public GetDynamicInfo_1Build(Context context, String url,String userId,String offset) {
		super(context, url);
		params=new Params();
		params.userId=userId;
		params.offset=offset;
	}
	public Params params;
	
	class Params{
		/*   "offset":"0",
        "guildId":"10000"*/
		public String userId;
		public String offset;
		
	}
}
