package com.game.helper.sdk.net.comm;

import java.io.InputStream;

import com.game.helper.sdk.model.returns.QueryGameBykindAndType;
import com.game.helper.sdk.net.base.BaseNetwork;
import com.game.helper.sdk.net.base.GeneralParser;

import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.net.comm.QueryGameBykindAndTypeNet.java
 * @Author lbb
 * @Date 2016年10月8日 上午10:09:48
 * @Company 
 */
public class QueryGameBykindAndTypeNet extends BaseNetwork{

	public QueryGameBykindAndTypeNet(Context context,  String datas) {
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
		return parser.getData(QueryGameBykindAndType.class) ;
	}

}