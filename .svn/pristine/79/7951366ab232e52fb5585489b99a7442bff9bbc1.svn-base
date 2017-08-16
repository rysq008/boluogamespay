package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.commentDynamicBuild.java
 * @Author lbb
 * @Date 2016年9月16日 下午1:10:19
 * @Company 
 */
public class CommentDynamicBuild extends BaseRequest{

	public CommentDynamicBuild(Context context, String url,String userId,String dynamicId,String content) {
		super(context, url);
		params=new Params();
		params.userId=userId;
		params.dynamicId=dynamicId;
		params.content=content;
	}
	public Params params;
	
	class Params{
		/*  "dynamicId":"542",
        "userId":"10000",
        "content":"xxxxx"*/
		public String userId;
		public String dynamicId;
		public String content;
		
	}
}
