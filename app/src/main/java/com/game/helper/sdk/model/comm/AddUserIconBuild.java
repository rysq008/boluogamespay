package com.game.helper.sdk.model.comm;

import android.content.Context;
import com.game.helper.sdk.model.base.BaseRequest;

public class AddUserIconBuild extends BaseRequest{

	public AddUserIconBuild(Context context, String url,String userId
			,String icon,String iconThumb) {
		super(context, url);
		params=new Params();
		params.userId=userId;
		params.icon=icon;
		params.iconThumb=iconThumb;
	}
	public Params params;
	
	class Params{
		
		public String userId;
		public String icon;
		public String iconThumb;
	}
}
