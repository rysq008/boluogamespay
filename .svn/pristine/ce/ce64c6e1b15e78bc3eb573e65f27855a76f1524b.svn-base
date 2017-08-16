package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.judgeSignBuild.java
 * @Author lbb
 * @Date 2016年9月16日 上午11:11:45
 * @Company 
 */
public class JudgeSignBuild extends BaseRequest{

	public JudgeSignBuild(Context context, String url,String userId,String guildId,String nowDate) {
		super(context, url);
		params=new Params();
		params.userId=userId;
		params.guildId=guildId;
		params.nowDate=nowDate;
	}
	public Params params;
	
	class Params{
		/*  "guildId":"1",
        "userId":"10000",
        "nowDate":"2016-08-24"*/
		public String userId;
		public String guildId;
		public String nowDate;
	}
}
