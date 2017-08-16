package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.SaveFocusBuild.java
 * @Author lbb
 * @Date 2016年9月16日 上午10:13:50
 * @Company 
 */
public class SaveFocusBuild extends BaseRequest{

	public SaveFocusBuild(Context context, String url,String userId,String operId) {
		super(context, url);
		params=new Params();
		params.userId=userId;
		params.operId=operId;
	}
	public Params params;
	
	class Params{
		/*  "userId":"10000",
         "operId":"10001"*/
		public String userId;
		public String operId;
		
	}
}
