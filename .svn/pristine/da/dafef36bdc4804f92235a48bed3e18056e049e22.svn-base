package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.SaveGamePlBuild.java
 * @Author lbb
 * @Date 2016年10月12日 上午10:16:58
 * @Company 
 */
public class SaveGamePlBuild extends BaseRequest{

	public SaveGamePlBuild(Context context, String url,String gameId,String userId,String content) {
		super(context, url);
		params=new Params();
		params.gameId=gameId;
		params.userId=userId;
		params.content=content;
	}
	public Params params;

	class Params{
		/*"gameId":"1",
          "userId":"1",
          "content":"评论1"
		 */
		public String gameId;
		public String userId;
		public String content;
	}
}
