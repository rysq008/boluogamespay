package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;

import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.DeleteAddressBuild.java
 * @Author lbb
 * @Date 2016年9月26日 上午10:05:12
 * @Company 
 */
public class DeleteAddressBuild  extends BaseRequest{

	public DeleteAddressBuild(Context context, String url,String addressId) {
		super(context, url);
		params=new Params();
		params.addressId=addressId;
	}
	public Params params;
	
	class Params{
		public String addressId;
		
	}
}
