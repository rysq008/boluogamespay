package com.game.helper.sdk.net.comm;

import java.io.InputStream;

import android.content.Context;
import com.game.helper.sdk.model.returns.QueryGameByHotWord;
import com.game.helper.sdk.net.base.BaseNetwork;
import com.game.helper.sdk.net.base.GeneralParser;

public class QueryGameByHotWordNet extends BaseNetwork{

	public QueryGameByHotWordNet(Context context,  String datas) {
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
		return parser.getData(QueryGameByHotWord.class) ;
	}

}
