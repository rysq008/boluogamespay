package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.getSignListBuild.java
 * @Author lbb
 * @Date 2016年9月16日 上午11:16:11
 * @Company 
 */
public class GetSignListBuild extends BaseRequest{

	public GetSignListBuild(Context context, String url,String guildId,String nowDate) {
		super(context, url);
		params=new Params();
		params.guildId=guildId;
		params.nowDate=nowDate;
	}
	public Params params;
	
	class Params{
		/* "guildId":"1",
        "nowDate":"2016-08-24"*/
		public String guildId;
		public String nowDate;
		
	}
}
