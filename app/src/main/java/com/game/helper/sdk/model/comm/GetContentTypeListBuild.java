package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.GetContentTypeListBuild.java
 * @Author lbb
 * @Date 2016年9月22日 下午5:55:33
 * @Company 
 */
public class GetContentTypeListBuild extends BaseRequest{

	public GetContentTypeListBuild(Context context, String url) {
		super(context, url);
		params=new Params();
		
	}
	public Params params;
	
	class Params{
		
	}
}
