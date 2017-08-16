package com.game.helper.sdk.model.comm;

import android.content.Context;
import com.game.helper.sdk.model.base.BaseRequest;

public class StarUserBuild extends BaseRequest{

	public StarUserBuild(Context context, String url,String refreshDate,String offset) {
		super(context, url);
		params=new Params();
		params.refreshDate=refreshDate;
		params.offset=offset;
	}
	public Params params;
	
	class Params{
		String refreshDate;
		String offset;
		
	}
}
