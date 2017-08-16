package com.game.helper.sdk.net.comm;

import java.io.InputStream;

import com.game.helper.sdk.model.returns.IsAccountAllowSc;
import com.game.helper.sdk.net.base.BaseNetwork;
import com.game.helper.sdk.net.base.GeneralParser;

import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.net.comm.IsAccountAllowScNet.java
 * @Author lbb
 * @Date 2016年11月13日 下午3:45:13
 * @Company 
 */
public class IsAccountAllowScNet extends BaseNetwork{

	public IsAccountAllowScNet(Context context,  String datas) {
		super(context, false);
		url =BASE_URL;
		InputStream inputStream = getContentWithPOST(datas);
		if (inputStream != null) {
			parser = new GeneralParser(context, inputStream);
		}
	}

	@Override
	public Object getData() {
		if(parser==null){
			return null;
		}
		return parser.getData(IsAccountAllowSc.class) ;
	}

}