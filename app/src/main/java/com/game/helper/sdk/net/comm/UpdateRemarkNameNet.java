package com.game.helper.sdk.net.comm;

import java.io.InputStream;
import com.game.helper.sdk.model.returns.UpdateRemarkName;
import com.game.helper.sdk.net.base.BaseNetwork;
import com.game.helper.sdk.net.base.GeneralParser;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.net.comm.UpdateRemarkNameNet.java
 * @Author lbb
 * @Date 2016年9月26日 上午9:57:25
 * @Company 
 */
public class UpdateRemarkNameNet extends BaseNetwork{

	public UpdateRemarkNameNet(Context context,  String datas) {
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
		return parser.getData(UpdateRemarkName.class) ;
	}

}
