package com.game.helper.sdk.net.base;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.game.helper.sdk.model.base.Result;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public abstract class BaseParser implements HttpComm {

	abstract protected Object parseData();

	public String data;
	private Result generalData;
	protected Context context;
	private boolean isShowErr;

	public BaseParser(String data) {
		this.data = data;
		generalData = getInfoAndStatus();
	}

	public BaseParser(Context context, InputStream is) {
		String str_data=StreamToString(is);
		data = str_data;
		/*if (data != null&&!IsJson(data)) {
			data = SDK.decrypt(data);
		}*/
		Log.e("lbb","返回结果"+data);
		if(data==null||data.equals("")){
			Log.e("lbb","错误结果："+str_data);
		}
		
		//Log.e("lbb",data);
		this.context = context;
		// this.isShowErr = isShowErr;
		generalData = getInfoAndStatus();
	}

	private String StreamToString(InputStream inputStream) {
		String result;
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream, HTTP.UTF_8));
			while ((result = reader.readLine()) != null) {
				sb.append(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return sb.toString();
	}

	public Object parseAndPrintData() {
		// int isOk = generalData.code;
		// final String info = generalData.msg;
		// // 如果调用接口失败 返回失败信息
		// if (isShowErr) {
		// if (!(isOk==1)) {
		// if (info != null && !TextUtils.isEmpty(info))
		// ((ActivityContent) context).runOnUiThread(new Runnable() {
		// @Override
		// public void run() {
		// ToastUtil.showToast(context, info);
		// }
		// });
		// }
		// }
		return parseData();
	}

	private Result getInfoAndStatus() {

		Result result = new Result();
		try {
			Type listType = new TypeToken<Result>() {
			}.getType();
			if (data.startsWith("{")) {
			} else {
				data = data.substring(data.indexOf("{"));
			}
			if (!TextUtils.isEmpty(data)) {
				Gson gson = new Gson();
				JSONObject jsonObject = new JSONObject(data);
//				result = gson.fromJson(jsonObject.toString(), listType);
//				String data = ;
				result = gson.fromJson(String.valueOf(jsonObject), listType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean getSuccess() {
		return getCode() .equals((""+ API_CODE_SUCCESS));
	}

	public String getCode() {
		if (generalData != null&&!TextUtils.isEmpty(generalData.code)){
			return generalData.code;
		}
			
		return (""+TIMEOUT_CODE);
	}
	/*public String getState() {
		String state = "";
		if (generalData != null) {
			state = generalData.state;
			if (TextUtils.isEmpty(state)) {
				state ="";
			}
		}
		return state;

	}*/
	public String getMessage() {
		String msg = "";
		if (generalData != null) {
			msg = generalData.msg;

			if (TextUtils.isEmpty(msg)) {
				msg = ("error!!!");
			}
		}
		return msg;

	}
	/*public String getResult() {
		String result = "";
		if (generalData != null) {
			result = generalData.result;

			if (TextUtils.isEmpty(result)) {
				result = ("error!!!");
			}
		}
		return result;

	}*/
	public String getString() {
		String msg = "";
		if (generalData != null) {
			return data;
		}
		return msg;

	}
	public Object getDatas(Type type) {
		try {
			JSONArray json = new JSONArray(data);
			return getData(json.toString(), type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Object getData(Type type) {
		try {
			JSONObject json = new JSONObject(data);
			return getData(json.toString(), type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Object getData(String json, Type type) {
		if (json != null) {
			try{
				Gson gson = new Gson();
//				Object tempData= gson.fromJson(json, type);
////				AccountInfoBuild buildData = new AccountInfoBuild(context); 
//				String dataString = tempData.toString();
				return gson.fromJson(json, type);
			}
			catch(Exception e) {
				Log.e("111111", e.toString());
				return null;
			}

		}
		return null;
	}
	/**
	 * 判断该String类型是否是Json数据
	 */
	public static boolean IsJson(String str){
		boolean isjson=false;
		try {
			JSONObject jsonObject = new JSONObject(str);
			isjson=true;
		} catch (Exception e) {
			isjson=false;
			e.printStackTrace();
		}
		return isjson;
		
	}
}
