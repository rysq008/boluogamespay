package com.game.helper.sdk.net.comm;

import java.io.InputStream;

import com.game.helper.sdk.model.returns.GetAddressList;
import com.game.helper.sdk.net.base.BaseNetwork;
import com.game.helper.sdk.net.base.GeneralParser;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.net.comm.SaveOrUpdateAddressNet.java
 * @Author lbb
 * @Date 2016年9月14日 下午3:06:45
 * @Company 
 */
public class SaveOrUpdateAddressNet  extends BaseNetwork{

	public SaveOrUpdateAddressNet(Context context,  String datas) {
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
		return parser.getData(GetAddressList.class) ;
	}

}

