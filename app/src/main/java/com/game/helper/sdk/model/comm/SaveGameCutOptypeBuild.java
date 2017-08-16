package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.SaveGameCutOptypeBuild.java
 * @Author lbb
 * @Date 2016年10月12日 上午9:32:02
 * @Company 
 */
public class SaveGameCutOptypeBuild extends BaseRequest{

	public SaveGameCutOptypeBuild(Context context, String url,String userId,String cutId,String opType,String content) {
		super(context, url);
		params=new Params();
		params.userId=userId;
		params.cutId=cutId;
		params.opType=opType;
		params.content=content;
	}
	public Params params;
	
	class Params{
		/* "userId":"10000",
        "cutId":"1",
        "opType":"pl",
        "content":"评论1"
*/
		public String userId;
		public String cutId;
		public String opType;
		public String content;
	}
}