package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.CrollListBuild.java
 * @Author lbb
 * @Date 2016年12月19日 上午10:53:35
 * @Company 
 */
public class CrollListBuild extends BaseRequest{

	public CrollListBuild(Context context, String url) {
		super(context, url);
		params=new Params();
	}
	public Params params;
	
	class Params{
		
	}
}
