package com.game.helper.sdk.net.comm;

import java.io.InputStream;

import com.game.helper.sdk.model.returns.AddPL;
import com.game.helper.sdk.net.base.BaseNetwork;
import com.game.helper.sdk.net.base.GeneralParser;

import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.net.comm.addPLNet.java
 * @Author lbb
 * @Date 2016年9月16日 上午11:30:57
 * @Company 
 */
public class AddPLNet extends BaseNetwork{

	public AddPLNet(Context context,  String datas) {
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
		return parser.getData(AddPL.class) ;
	}

}

