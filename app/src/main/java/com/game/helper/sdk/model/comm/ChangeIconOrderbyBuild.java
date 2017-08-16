package com.game.helper.sdk.model.comm;

import android.content.Context;

import java.util.List;
import com.game.helper.sdk.model.base.BaseRequest;
import com.game.helper.sdk.model.returns.UserIcons;

public class ChangeIconOrderbyBuild  extends BaseRequest{

	public ChangeIconOrderbyBuild(Context context, String url,List<UserIcons> userIcons) {
		super(context, url);
		params=new Params();
		params.userIcons=userIcons;
	}
	public Params params;
	
	class Params{
		
		public List<UserIcons> userIcons;
		
	}
}
