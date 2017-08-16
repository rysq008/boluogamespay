package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.GetHotGameBuild.java
 * @Author lbb
 * @Date 2016年10月19日 下午3:05:23
 * @Company 
 */
public class GetHotGameBuild extends BaseRequest{

	public GetHotGameBuild(Context context, String url,String refreshDate,String offset) {
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
