package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.GetGameCutByIdBuild.java
 * @Author lbb
 * @Date 2016年10月13日 上午9:49:49
 * @Company 
 */
public class GetGameCutByIdBuild extends BaseRequest{

	public GetGameCutByIdBuild(Context context, String url,String cutId) {
		super(context, url);
		params=new Params();
		params.cutId=cutId;
	}
	public Params params;
	
	class Params{
		public String cutId;
	}
}