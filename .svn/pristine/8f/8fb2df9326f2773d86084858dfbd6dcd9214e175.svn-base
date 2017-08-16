package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.GetNoNetGameBuild.java
 * @Author lbb
 * @Date 2016年10月19日 下午3:00:56
 * @Company 
 */
public class GetNoNetGameBuild extends BaseRequest{

	public GetNoNetGameBuild(Context context, String url,String refreshDate,String offset) {
		super(context, url);
		params=new Params();
		params.refreshDate=refreshDate;
		params.offset=offset;
	}
	public Params params;
	
	class Params{
		public String offset;
		public String refreshDate;
		
	}
}
