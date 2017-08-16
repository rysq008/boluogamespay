package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.GetAdListBuild.java
 * @Author lbb
 * @Date 2016年9月12日 下午7:43:05
 * @Company 
 */
public class GetAdListBuild extends BaseRequest{

	public GetAdListBuild(Context context,String url,String type,String userId) {
		super(context,url);
		params=new Params(type,userId);
	}
	
	public Params params;
	
	class Params{
		public String type;
		public String userId;

		public Params(String type,String userId) {
			super();
			this.type=type;
			this.userId=userId;
		}
		
	}
}