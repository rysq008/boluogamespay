package com.game.helper.sdk.net.comm;

import java.io.InputStream;

import com.game.helper.sdk.model.returns.IsGetMoreThanThree;
import com.game.helper.sdk.net.base.BaseNetwork;
import com.game.helper.sdk.net.base.GeneralParser;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.net.comm.IsGetMoreThanThreeNet.java
 * @Author lbb
 * @Date 2016年11月9日 上午9:54:11
 * @Company 
 */
public class IsGetMoreThanThreeNet extends BaseNetwork{

	public IsGetMoreThanThreeNet(Context context,  String datas) {
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
		return parser.getData(IsGetMoreThanThree.class) ;
	}

}