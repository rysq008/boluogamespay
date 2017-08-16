package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.QueryThemeGameBuild.java
 * @Author lbb
 * @Date 2016年10月8日 上午10:47:53
 * @Company 
 */
public class QueryThemeGameBuild extends BaseRequest{

	public QueryThemeGameBuild(Context context, String url,String themeId) {
		super(context, url);
		params=new Params();
		params.themeId=themeId;
	}
	public Params params;
	
	class Params{
		
		public String themeId;
		
	}
}