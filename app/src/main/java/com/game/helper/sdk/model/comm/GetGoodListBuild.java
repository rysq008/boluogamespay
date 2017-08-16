package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.GetGoodListBuild.java
 * @Author lbb
 * @Date 2016年9月14日 下午2:22:25
 * @Company 
 */
public class GetGoodListBuild extends BaseRequest{

	public GetGoodListBuild(Context context, String url) {
		super(context, url);
		params=new Params();
	}
	public Params params;
	
	class Params{
		
	}
}
