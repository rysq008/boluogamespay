package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.SaveOrUpdateGuildBuild.java
 * @Author lbb
 * @Date 2016年9月16日 上午10:19:19
 * @Company 
 */
public class SaveOrUpdateGuildBuild extends BaseRequest{

	public SaveOrUpdateGuildBuild(Context context, String url,String userId,String name
			,String icon,String declareContent,String abstractContent,String guildId) {
		super(context, url);
		params=new Params();
		params.userId=userId;
		params.name=name;
		params.icon=icon;
		params.declareContent=declareContent;
		params.abstractContent=abstractContent;
		params.guildId=guildId;
	}
	public Params params;
	
	class Params{
		/*  "icon":"修改图片.jpg",
        "name":"将军之盟3",
        "userId":"1",
        "declareContent":"修改黑玫瑰，我的最爱",
        "abstractContent":"修改全部由大将军组成的联盟"
        "guildId":"1"  */
		public String userId;
		public String name;
		public String icon;
		public String declareContent;
		public String abstractContent;
		public String guildId;
	}
}