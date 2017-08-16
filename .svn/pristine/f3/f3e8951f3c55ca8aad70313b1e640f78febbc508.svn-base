package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.GetLottery.java
 * @Author lbb
 * @Date 2016年9月14日 下午3:38:08
 * @Company 
 */
public class GetLotteryBuild extends BaseRequest{

	public GetLotteryBuild(Context context, String url,String userId,String goodId) {
		super(context, url);
		params=new Params();
		params.userId=userId;
		params.goodId=goodId;
	}
	public Params params;
	
	class Params{
		/* "userId":"100000""*/
		public String userId;
		public String goodId;
	}
}
