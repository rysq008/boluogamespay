package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.JudgeFocusBuild.java
 * @Author lbb
 * @Date 2016年9月27日 下午4:27:08
 * @Company 
 */
public class JudgeFocusBuild extends BaseRequest{

	public JudgeFocusBuild(Context context, String url,String operId,String userId) {
		super(context, url);
		params=new Params();
		params.operId=operId;
		params.userId=userId;
	}
	public Params params;
	
	class Params{
		
		public String userId;
		public String operId;
		
	}
}