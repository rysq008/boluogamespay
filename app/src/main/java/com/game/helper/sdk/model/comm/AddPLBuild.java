package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.addPLBuild.java
 * @Author lbb
 * @Date 2016年9月16日 上午11:30:41
 * @Company 
 */
public class AddPLBuild extends BaseRequest{

	public AddPLBuild(Context context, String url,String contentId,String opUserId,String plContent) {
		super(context, url);
		params=new Params();
		params.contentId=contentId;
		params.opUserId=opUserId;
		params.plContent=plContent;
	}
	public Params params;
	
	class Params{
		/*  "contentId":"201",
        "opUserId":"10000",
        "plContent":"xxxxxx"*/
		public String contentId;
		public String opUserId;
		public String plContent;
	}
}
