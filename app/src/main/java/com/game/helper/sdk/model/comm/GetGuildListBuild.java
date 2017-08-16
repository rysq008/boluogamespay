package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.GetGuildListBuild.java
 * @Author lbb
 * @Date 2016年9月16日 上午10:23:42
 * @Company 
 */
public class GetGuildListBuild extends BaseRequest{

	public GetGuildListBuild(Context context, String url,String keyWord) {
		super(context, url);
		params=new Params();
		params.keyWord=keyWord;
	}
	public Params params;
	
	class Params{
		/* "keyWord":"将军"  */
		public String keyWord;
		
	}
}
