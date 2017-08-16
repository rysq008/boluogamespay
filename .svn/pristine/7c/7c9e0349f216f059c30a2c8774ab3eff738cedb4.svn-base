package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.getInfoListBuild.java
 * @Author lbb
 * @Date 2016年9月16日 上午11:27:15
 * @Company 
 */
public class GetInfoListBuild extends BaseRequest{

	public GetInfoListBuild(Context context, String url,String typeId,String offset,String refreshDate) {
		super(context, url);
		params=new Params();
		params.typeId=typeId;
		params.offset=offset;
		params.refreshDate=refreshDate;
	}
	public Params params;
	
	class Params{
		/*  "typeId":"1",
        "offset":"0",
        "refreshDate":"20160826104241""*/
		public String typeId;
		public String offset;
		public String refreshDate;
	}
}