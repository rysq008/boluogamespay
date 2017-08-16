package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.SaveGiveBuild.java
 * @Author lbb
 * @Date 2016年9月16日 上午10:04:29
 * @Company 
 */
public class SaveGiveBuild extends BaseRequest{

	public SaveGiveBuild(Context context, String url,String userId,String giftId,String operId,String count) {
		super(context, url);
		params=new Params();
		params.userId=userId;
		params.giftId=giftId;
		params.operId=operId;
		params.count=count;
	}
	public Params params;
	
	class Params{
		/*  "userId":"10000",
         "giftId":"1",
         "operId":"10001",
         "count":"1"*/
		public String userId;
		public String giftId;
		public String operId;
		public String count;
	}
}
