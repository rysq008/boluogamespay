package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.VersionBuild.java
 * @Author lbb
 * @Date 2016年11月29日 下午4:00:54
 * @Company 
 */
public class VersionBuild extends BaseRequest{

	public VersionBuild(Context context, String url,String channel,String version) {
		super(context, url);
		params=new Params();
		params.channel=channel;
		params.version=version;
	}
	public Params params;
	
	class Params{
		
	//	public int platform;
		public String channel;
		public String version;
		
	}
}