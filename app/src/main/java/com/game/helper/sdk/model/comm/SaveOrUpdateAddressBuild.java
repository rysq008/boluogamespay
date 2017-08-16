package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.SaveOrUpdateAddressBuild.java
 * @Author lbb
 * @Date 2016年9月14日 下午3:06:08
 * @Company
 */
public class SaveOrUpdateAddressBuild extends BaseRequest{

	public SaveOrUpdateAddressBuild(Context context, String url,String userId,String realName
			,String phone,String cityName,String areaName,String address,String isDefault,String addressId) {
		super(context, url);
		params=new Params();
		params.userId=userId;
		params.realName=realName;
		params.phone=phone;
		params.cityName=cityName;
		params.areaName=areaName;
		params.address=address;
		params.isDefault=isDefault;
		params.addressId=addressId;
	}
	public Params params;
	
	class Params{
		/*  
		 *  "userId":"10001",
        "realName":"xxxx",
        "phone":"13806944856",
        "cityName":"福建省",
        "areaName":"漳州市",
        "address":"群裕小区31栋",
         "isDefault":"Y"
		 * 
		 * 
		 * "userId":"10001",
        "realName":"xxxx",
        "phone":"13806944856",
        "cityName":"福建省",
        "areaName":"漳州市",
        "address":"群裕小区31栋",
        "isDefault":"Y",
        "addressId":"2"*/
		public String userId;
		public String realName;
		public String phone;
		public String cityName;
		public String areaName;
		public String address;
		public String isDefault;
		public String addressId;
	}
}
