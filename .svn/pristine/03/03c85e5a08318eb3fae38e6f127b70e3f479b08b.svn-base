package com.game.helper.sdk.net.comm;

import java.io.InputStream;
import com.game.helper.sdk.model.returns.SaveGameCutOptype;
import com.game.helper.sdk.net.base.BaseNetwork;
import com.game.helper.sdk.net.base.GeneralParser;

import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.net.comm.SaveGameCutOptypeNet.java
 * @Author lbb
 * @Date 2016年10月12日 上午9:32:27
 * @Company 
 */
public class SaveGameCutOptypeNet extends BaseNetwork{

	public SaveGameCutOptypeNet(Context context,  String datas) {
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
		return parser.getData(SaveGameCutOptype.class) ;
	}

}
