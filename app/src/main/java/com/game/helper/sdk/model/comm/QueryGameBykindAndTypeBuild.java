package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.QueryGameBykindAndTypeBuild.java
 * @Author lbb
 * @Date 2016年10月8日 上午10:09:37
 * @Company 
 */
public class QueryGameBykindAndTypeBuild extends BaseRequest{

	public QueryGameBykindAndTypeBuild(Context context, String url,String typeId,String kindId,String orderby,String offset) {
		super(context, url);
		params=new Params();
		params.typeId=typeId;
		params.kindId=kindId;
		params.orderby=orderby;
		params.offset=offset;
	}
	public Params params;
	
	class Params{
		/*   "typeId":"1",
        "kindId":"1",
        "orderby":"field1"
*/
		public String typeId;
		public String kindId;
		public String orderby;
		public String offset;
	}
}
