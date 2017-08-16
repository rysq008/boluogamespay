package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.AddMyZkhBuild.java
 * @Author lbb
 * @Date 2016年11月21日 上午9:27:45
 * @Company 
 */
public class AddMyZkhBuild extends BaseRequest{

	public AddMyZkhBuild(Context context, String url,String userId,String platId,String gameAccount) {
		super(context, url);
		params=new Params();
		params.userId=userId;
		params.platId=platId;
		params.gameAccount=gameAccount;
		//params.gamePwd=gamePwd;
	}
	public Params params;
	
	class Params{
		
		public String userId;
		public String platId;
		public String gameAccount;
	//	public String gamePwd;
	}
}