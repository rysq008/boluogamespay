package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.GetGiftBuild.java
 * @Author lbb
 * @Date 2016年10月8日 上午10:53:16
 * @Company 
 */
public class GetGiftBuild extends BaseRequest{

	public GetGiftBuild(Context context, String url,String userId,String gameId,String giftId) {
		super(context, url);
		params=new Params();
		params.userId=userId;
		params.gameId=gameId;
		params.giftId=giftId;
	}
	public Params params;
	
	class Params{
		/*  "gameId":"1",
        "userId":"10000"
*/
		public String gameId;
		public String userId;
		public String giftId;
	}
}
