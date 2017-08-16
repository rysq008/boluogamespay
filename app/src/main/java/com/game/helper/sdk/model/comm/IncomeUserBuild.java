package com.game.helper.sdk.model.comm;

import android.content.Context;

import com.game.helper.sdk.model.base.BaseRequest;

public class IncomeUserBuild extends BaseRequest{

	public IncomeUserBuild(Context context, String url,String refreshDate,String offset) {
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