package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.deleteDynamicBuild.java
 * @Author lbb
 * @Date 2016年9月16日 下午1:03:37
 * @Company 
 */
public class DeleteDynamicBuild extends BaseRequest{

	public DeleteDynamicBuild(Context context, String url,String dynamicId) {
		super(context, url);
		params=new Params();
		params.dynamicId=dynamicId;
	}
	public Params params;
	
	class Params{
		/* "dynamicId":"100000"*/
		public String dynamicId;
	}
	
}