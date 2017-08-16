package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.GetNoticeByIdBuild.java
 * @Author lbb
 * @Date 2016年9月16日 上午9:40:46
 * @Company 
 */
public class GetNoticeByIdBuild extends BaseRequest{

	public GetNoticeByIdBuild(Context context, String url,String noticeId) {
		super(context, url);
		params=new Params();
		params.noticeId=noticeId;
	}
	public Params params;
	
	class Params{
		/* "noticeId":"100000"*/
		public String noticeId;
		
	}
}