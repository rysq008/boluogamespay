package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.addDZBuild.java
 * @Author lbb
 * @Date 2016年9月16日 上午11:43:12
 * @Company 
 */
public class AddDZBuild extends BaseRequest{

	public AddDZBuild(Context context, String url,String contentId,String opUserId) {
		super(context, url);
		params=new Params();
		params.contentId=contentId;
		params.opUserId=opUserId;
	}
	public Params params;
	
	class Params{
		/* "contentId":"201",
        "opUserId":"10000"
*/
		public String contentId;
		public String opUserId;
		
	}
}