
package com.game.helper.sdk.model.base;

import com.game.helper.sdk.Config;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.SystemUtil;
import com.game.helper.util.TimeUtil;

import android.content.Context;

/**

 */
public class BaseRequest {
	/*{
	    "system": {
	        "time": "",
	        "client_type": "android",
	        "sign": "",
	        "version": "1.0.0"
	    },
	    "params": {
	        "phone_num":"18060478013"
	    },
	    "method": "/tcheckcode/getCheckCode"
	}*/
	//public String access_token;
/*	public String userId;
	public String app_version;
	public String device_type_phone;
    public String app_type;*/
	public	System system;
	public String method;
    class System{
    	public String time;
    	public String client_type;
    	public String sign="";
    	public String version;
    	public String device_type_phone;
		public System(Context context) {
			super();
			version=SystemUtil.getAppVersionName(context);
	    	device_type_phone=SystemUtil.getBrand();
	    	client_type=Config.APP_STYPE;
	    	time=TimeUtil.getCurTime(TimeUtil.TIME_FORMAT_FULL);
		}
    	
    }
   /* public BaseRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
*/
	public BaseRequest(Context context,String url) {
		system=new System(context);
		method=url;
	}

	/*public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getApp_Version() {
		return app_version;
	}

	public void setApp_Version(String app_version) {
		this.app_version = app_version;
	}
	public String getDevice_type_phone() {
		return device_type_phone;
	}

	public void setDevice_type_phone(String device_type_phone) {
		this.device_type_phone = device_type_phone;
	}
	public String getApp_type() {
		return app_type;
	}

	public void setApp_type(String app_type) {
		this.app_type = app_type;
	}*/
	/*public String toJsonStr() {
		try {
			return new JsonBuild().setModel(this).getJsonString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public String toJson() {
		try {
			return new JsonBuild().setModel(this).getJson();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}*/
	public String toJson1() {
		try {
			return new JsonBuild().setModel(this).getJson1();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
