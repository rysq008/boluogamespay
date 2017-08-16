package com.game.helper.sdk.model.comm;

import android.content.Context;
import com.game.helper.sdk.model.base.BaseRequest;

public class DownloadFinishBuild extends BaseRequest{

	public DownloadFinishBuild(Context context, String url,String userId,String gameId) {
		super(context, url);
		params=new Params();
		params.userId=userId;
		params.gameId=gameId;
	}
	public Params params;
	
	class Params{
		
		public String userId;
		public String gameId;
		
	}
}