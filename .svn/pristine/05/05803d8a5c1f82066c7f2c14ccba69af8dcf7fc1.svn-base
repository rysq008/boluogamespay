package com.game.helper.sdk.model.comm;

import android.content.Context;

import com.game.helper.sdk.model.base.BaseRequest;

public class QueryGameByHotWordBuild extends BaseRequest{

	public QueryGameByHotWordBuild(Context context, String url,String hotWord,String platId,String offset) {
		super(context, url);
		params=new Params();
		params.hotWord=hotWord;
		params.platId=platId;
		params.offset=offset;
	}
	public Params params;
	
	class Params{
	
		public String hotWord;
		public String offset;
		public String platId;//平台id(用于领取折扣号),非必填
	}
}
