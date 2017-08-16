package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.SaveExchangeBuild.java
 * @Author lbb
 * @Date 2016年9月14日 下午3:32:24
 * @Company 
 */
public class SaveExchangeBuild extends BaseRequest{

	public SaveExchangeBuild(Context context, String url,String userId,String goodId,String addressId,String remark) {
		super(context, url);
		params=new Params();
		params.userId=userId;
		params.goodId=goodId;
		params.addressId=addressId;
		params.remark=remark;
	}
	public Params params;
	
	class Params{
		/* "userId":"10001",
           "goodId":"2",
           "addressId":"2"*/
		public String userId;
		public String goodId;
		public String addressId;
		public String remark;
		
	}
}
