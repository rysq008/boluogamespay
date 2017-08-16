package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.SaveNoticeBuild.java
 * @Author lbb
 * @Date 2016年9月16日 上午10:42:14
 * @Company 
 */
public class SaveNoticeBuild extends BaseRequest{

	public SaveNoticeBuild(Context context, String url,String guildId,String noticeName,String content) {
		super(context, url);
		params=new Params();
		params.guildId=guildId;
		params.noticeName=noticeName;
		params.content=content;
	}
	public Params params;
	
	class Params{
		/*   "guildId":"1",
         "noticeName":"小学生开始放假了，请注意",
         "content":"小学生一放假，送人头，脑残时间频繁发生"*/
		public String guildId;
		public String noticeName;
		public String content;
	}
}
