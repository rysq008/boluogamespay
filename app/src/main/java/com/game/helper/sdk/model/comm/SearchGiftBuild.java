package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.SearchGiftBuild.java
 * @Author lbb
 * @Date 2016年10月8日 上午10:52:13
 * @Company 
 */
public class SearchGiftBuild extends BaseRequest{

	public SearchGiftBuild(Context context, String url,String keyword) {
		super(context, url);
		params=new Params();
		params.keyword=keyword;
	}
	public Params params;
	
	class Params{
		public String keyword;
		
	}
}
