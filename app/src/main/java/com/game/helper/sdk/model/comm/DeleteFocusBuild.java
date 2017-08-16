package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.DeleteFocusBuild.java
 * @Author lbb
 * @Date 2016年9月16日 上午10:00:57
 * @Company 
 */
public class DeleteFocusBuild extends BaseRequest{

	public DeleteFocusBuild(Context context, String url,String focusId) {
		super(context, url);
		params=new Params();
		params.focusId=focusId;
	}
	public Params params;
	
	class Params{
		/* "focusId":"100000"*/
		public String focusId;
		
	}
}