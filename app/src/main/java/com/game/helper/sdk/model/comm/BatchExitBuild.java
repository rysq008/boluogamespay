package com.game.helper.sdk.model.comm;

import java.util.List;
import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.batchExitBuild.java
 * @Author lbb
 * @Date 2016年9月16日 上午11:05:19
 * @Company 
 */
public class BatchExitBuild extends BaseRequest{

	public BatchExitBuild(Context context, String url,List<String> userList) {
		super(context, url);
		params=new Params();
		params.userList=userList;
	}
	public Params params;
	
	class Params{
		/*  "userList":[10000,10001]*/
		public List<String> userList;
	}
}
