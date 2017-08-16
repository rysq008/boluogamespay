package com.game.helper.sdk.model.comm;

import android.content.Context;
import com.game.helper.sdk.model.base.BaseRequest;

public class ConnectUserBuild  extends BaseRequest{

	public ConnectUserBuild(Context context, String url,String refreshDate,String offset) {
		super(context, url);
		params=new Params();
		params.refreshDate=refreshDate;
		params.offset=offset;
	}
	public Params params;
	
	class Params{
		 /*"refreshDate": "20161126231506",
	        "offset":"0"*/
		String refreshDate;
		String offset;
	}
}
