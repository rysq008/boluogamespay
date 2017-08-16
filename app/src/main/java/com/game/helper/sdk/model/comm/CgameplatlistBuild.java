package com.game.helper.sdk.model.comm;

import android.content.Context;
import com.game.helper.sdk.model.base.BaseRequest;

public class CgameplatlistBuild extends BaseRequest{

	public CgameplatlistBuild(Context context, String url) {
		super(context, url);
		params=new Params();
		
	}
	public Params params;
	
	class Params{
		
		
	}
}
