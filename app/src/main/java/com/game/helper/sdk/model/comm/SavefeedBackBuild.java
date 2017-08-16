package com.game.helper.sdk.model.comm;

import android.content.Context;

import com.game.helper.sdk.model.base.BaseRequest;

public class SavefeedBackBuild extends BaseRequest{

	public SavefeedBackBuild(Context context, String url,String userId,String feedbackContent) {
		super(context, url);
		params=new Params();
		params.userId=userId;
		params.feedbackContent=feedbackContent;
	}
	public Params params;
	
	class Params{
		
		public String userId;
		public String feedbackContent;
		
	}
}