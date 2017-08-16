package com.game.helper.sdk.net.comm;

import android.content.Context;

import com.game.helper.sdk.model.returns.GetBasic;
import com.game.helper.sdk.model.returns.GetRechargeModel;
import com.game.helper.sdk.net.base.BaseNetwork;
import com.game.helper.sdk.net.base.GeneralParser;

import java.io.InputStream;

public class GetRechargeWayNet extends BaseNetwork{

	public GetRechargeWayNet(Context context, String datas) {
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
		return parser.getData(GetRechargeModel.class) ;
	}

}

