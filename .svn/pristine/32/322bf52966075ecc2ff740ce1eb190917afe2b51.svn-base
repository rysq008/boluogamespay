
package com.game.helper.sdk.net.base;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.provider.ContactsContract.Contacts.Data;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * @Description
 
 */
public class JsonBuild {

	public Object object;
	public JSONObject jsonObject;
	
	public boolean islocEncrypt=false;//是否本地加密

	public JsonBuild setModel(Object object) {
		this.object = object;		
		return this;
	}
	
	/**设置实体*//*
	public JSONObject getJsonObject() {
		if (object != null) {
			try {
				return changeJson(object,true);
//				return changeJson(object);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}*/
	/** 是否本地加密 */
	public JsonBuild islocEncrypt(boolean islocEncrypt) {
		this.islocEncrypt = islocEncrypt;
		return this;
	}
	
	/*public String getJson() {
		return getJsonStr(true);
	}*/
//	public String getJsonZero() {
//		return getJsonStr(false);
//	}
	
	public String getJson1() {
		try {
			return getJsonStr1(true);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "";
		
	}
	public String getJson2() {
		return getJsonStr1(false);
	}
	
	
	
	public String getJsonStr1(boolean isCode){
		JSONObject jsonObject;
		try {
			 Gson gson=new Gson();
             // String obj=gson.toJson(object);
			 //jsonObject = new JSONObject(object.toString());
			 String json =gson.toJson(object);
			if (json != null){
				//String json = jsonObject.toString();
				Log.e("xxx","提交的json:"+json);
				if(json != null) {
					/*if(isCode){
						return islocEncrypt?SDK.aesEncrypt(json):SDK.encrypt(json);
					}else{
						return json;
					}*/
					return json;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	/*public String getJsonStr(boolean isCode){
		JSONObject jsonObject = getJsonObject();
		if (jsonObject != null){
			String json = jsonObject.toString();
			Log.e("xxx","提交的json:"+json);
			if(json != null){
				if(isCode){
					return islocEncrypt?SDK.aesEncrypt(json):SDK.encrypt(json);
				}else{
					return json;
				}
				return json;
			}
		}
		return "";
	}*/
	public String getJsonString1(){
		return getJsonStr1(false);
	}
	/*public String getJsonString(){
		return getJsonStr(false);
	}*/
	
	public Object getData(Type type,String json) {
		if (json != null) {
			Gson gson = new Gson();
			return gson.fromJson(json, type);
		}
		return null;
	}
	/**
	 * 
	* @Title:       changeJson
	* @param        @param object:出入的数据
	* @param        @param flag:true表示第一次传入，flase:表示同一串数据中调用第二次或者是多次
	* @param        @return      
	* @return       JSONObject    返回类型
	* @throws
	* @author       lulinhua
	* @date         2016-3-21 上午10:49:22
	* @Description: TODO ：加入flag主要是解决传输过程中每个类继承了基类后都讲基类的参数加入其中
	 */
	/*public JSONObject changeJson(Object object,boolean flag)     {
		JSONObject jb = null;
		
		try {
			jb = new JSONObject();
			Class c = object.getClass(); // 获取类的类型类
			Field[] fields = c.getFields(); // 获取属性集合
			Field.setAccessible(fields, true); // 在类外面要想获取私有属性的值必须设置
			Object[] name = new Object[fields.length]; // 存储变量名
			Object[] value = new Object[fields.length]; // 存储变量值
			Object[] type = new Object[fields.length]; // 存储变量类型
			int k=0;  
			for (int i = 0; i < fields.length; i++) { // 设置数组的值
				if(flag) {
					name[k] = fields[i].getName();
					value[k] = fields[i].get(object);
					type[k] = fields[i].getType();
					k++;
				} else {
					String nameStr =  fields[i].getName();
					if(nameStr.equals("app_version")
							||nameStr.equals("device_type_phone")||nameStr.equals("app_type")) {
						//如果有改基类，则此处也需要对应的修改
						continue;
					} else {
						name[k] = fields[i].getName();
						value[k] = fields[i].get(object);
						type[k] = fields[i].getType();
						k++;
					}
				}
			}

			for (int i = 0; i < k; i++) {
				String s1 = type[i].toString(); // 变量类型
				String v1 = value[i] + ""; // 变量值
				String n1 = name[i].toString(); // 变量名
				if (s1.contains("java.lang.String")) {
					if (!v1.equals("null")) {
						jb.put(n1, v1); // 给jsonobject设置key-value
					}
				} else if (s1.contains("long")) {
					jb.put(n1, Long.parseLong(v1));
				} else if (s1.equals("int")) {
					jb.put(n1, Integer.parseInt(v1));
				}else if(s1.equals("double")){
					jb.put(n1, Double.parseDouble(v1));
				}else if(s1.equals("float")){
					jb.put(n1, Float.parseFloat(v1));
				}
				else if (s1.equals("double")) {
					jb.put(n1, Double.parseDouble(v1));
				} 
				else if(s1.contains("java.util.List")) {
					List<Object> v1_list_value =(List<Object>) value[i]; // 变量值
					JSONArray ja =new JSONArray();
					for(int j=0;j<v1_list_value.size();j++) {	
						JSONObject jb2 =new JSONObject(changeJson(v1_list_value.get(j),false).toString());
						ja.put(jb2);	
					}
					jb.accumulate(n1, ja);
				}  else if(s1.contains("JSONObject")) {
					JSONObject data = (JSONObject) value[i];		
					// jb.putAll(data);
					if(null !=data &&  data.isNull("line_ids")==false) {  //此处后续需要优化，如果有多个需要每个都判断。
						Object temp = data.get("line_ids");
						jb.put(n1, temp);
					}					
				
				}
				else {//如果是自定义类型的话，如用户提交信息多了个Info的自定义类型，则传递变量值在循环一次changeJson的
					jb.put(n1,changeJson(value[i],false));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jb;
	}
	
	*//**
	 * 
	* @Title:       changeJson
	* @param        @param object:出入的数据
	* @param        @return      
	* @return       JSONObject    返回类型
	* @throws
	* @author       lulinhua
	* @date         2016-3-21 上午10:49:22
	* @Description: TODO ：加入flag主要是解决传输过程中每个类继承了基类后都讲基类的参数加入其中
	 *//*
	public JSONObject changeJson(Object object)     {
		JSONObject jb = null;
		
		try {
			jb = new JSONObject();
			Class c = object.getClass(); // 获取类的类型类
			Field[] fields = c.getFields(); // 获取属性集合
			Field.setAccessible(fields, true); // 在类外面要想获取私有属性的值必须设置
			Object[] name = new Object[fields.length]; // 存储变量名
			Object[] value = new Object[fields.length]; // 存储变量值
			Object[] type = new Object[fields.length]; // 存储变量类型 
			for (int i = 0; i < fields.length; i++) { // 设置数组的值
				name[i] = fields[i].getName();
				value[i] = fields[i].get(object);
				type[i] = fields[i].getType();
			}

			for (int i = 0; i < name.length; i++) {
				String s1 = type[i].toString(); // 变量类型
				String v1 = value[i] + ""; // 变量值
				String n1 = name[i].toString(); // 变量名
				if (s1.contains("java.lang.String")) {
					if (!v1.equals("null")) {
						jb.put(n1, v1); // 给jsonobject设置key-value
					}
				} else if (s1.contains("long")) {
					jb.put(n1, Long.parseLong(v1));
				} else if (s1.equals("int")) {
					jb.put(n1, Integer.parseInt(v1));
				}else if(s1.equals("double")){
					jb.put(n1, Double.parseDouble(v1));
				}else if(s1.equals("float")){
					jb.put(n1, Float.parseFloat(v1));
				}
				else if (s1.equals("double")) {
					jb.put(n1, Double.parseDouble(v1));
				}
				else if(s1.contains("java.util.List")) {
					List<Object> v1_list_value =(List<Object>) value[i]; // 变量值
					JSONArray ja =new JSONArray();
					for(int j=0;j<v1_list_value.size();j++) {	
						JSONObject jb2 =new JSONObject(changeJson(v1_list_value.get(j)).toString());
						ja.put(jb2);
						
					}
					jb.accumulate(n1, ja);
				}
				else {//如果是自定义类型的话，如用户提交信息多了个Info的自定义类型，则传递变量值在循环一次changeJson的
					jb.put(n1,changeJson(value[i]));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jb;
	}*/

}
