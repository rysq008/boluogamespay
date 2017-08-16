package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.getContenteOtherDetailBuild.java
 * @Author lbb
 * @Date 2016年9月16日 上午11:33:43
 * @Company
 */
public class GetContenteOtherDetailBuild extends BaseRequest{

	public GetContenteOtherDetailBuild(Context context, String url,String contentId) {
		super(context, url);
		params=new Params();
		params.contentId=contentId;
	}
	public Params params;
	
	class Params{
		/* "contentId":"100000"*/
		public String contentId;
		
	}
}
