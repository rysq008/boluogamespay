package com.game.helper.sdk.net.comm;

import android.content.Context;

import com.game.helper.sdk.model.returns.Cgamekindlist;
import com.game.helper.sdk.model.returns.GameFromList;
import com.game.helper.sdk.net.base.BaseNetwork;
import com.game.helper.sdk.net.base.GeneralParser;

import java.io.InputStream;

/**
 * @Description
 * @Path com.game.helper.sdk.net.comm.CgamekindlistNet.java
 * @Author lbb
 * @Date 2016年10月8日 上午10:51:15
 * @Company 
 */
public class GameFromNet extends BaseNetwork{

	public GameFromNet(Context context, String datas) {
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
		return parser.getData(GameFromList.class) ;
	}

}