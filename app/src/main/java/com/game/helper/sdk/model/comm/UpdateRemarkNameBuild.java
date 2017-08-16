package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.UpdateRemarkNameBuild.java
 * @Author lbb
 * @Date 2016年9月26日 上午9:57:35
 * @Company 
 */
public class UpdateRemarkNameBuild extends BaseRequest{

	public UpdateRemarkNameBuild(Context context, String url,String focusId,String remarkName) {
		super(context, url);
		params=new Params();
		params.focusId=focusId;
		params.remarkName=remarkName;
	}
	public Params params;
	
	class Params{
		public String focusId;
		public String remarkName;
		
	}
}
