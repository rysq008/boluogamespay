package com.game.helper.sdk.model.comm;

import android.content.Context;
import com.game.helper.sdk.model.base.BaseRequest;

public class RemoveMyGiftBuild extends BaseRequest{

	public RemoveMyGiftBuild(Context context, String url,String id) {
		super(context, url);
		params=new Params();
		params.id=id;
	}
	public Params params;
	
	class Params{
		
		public String id;
		
	}
}