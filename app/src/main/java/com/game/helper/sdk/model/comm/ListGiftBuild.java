package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;

import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.ListGiftBuild.java
 * @Author lbb
 * @Date 2016年10月8日 上午9:44:08
 * @Company 
 */
public class ListGiftBuild extends BaseRequest{

	public ListGiftBuild(Context context, String url,String userId,String gameId) {
		super(context, url);
		params=new Params();
		params.userId=userId;
		params.gameId=gameId;
	}
	public Params params;
	
	class Params{
		/*  "gameId":"1",
        "userId":"10000"
*/
		public String gameId;
		public String userId;
		
	}
}