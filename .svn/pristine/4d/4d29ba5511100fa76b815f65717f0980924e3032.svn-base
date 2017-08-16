package com.game.helper.sdk.net.base;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.game.helper.util.APNManager;
import com.game.helper.util.TimeUtil;

import android.content.Context;
import android.util.Log;

public abstract class BaseNetwork implements HttpComm {

	public String url = BASE_URL;
	protected BaseParser parser;
	protected boolean isShowErr;
	protected Context context;
	int CODE=200;
	abstract public Object getData();

	public BaseNetwork(Context context, boolean isShowErr) {
		this.context = context;
		this.isShowErr = isShowErr;
	}

	/**
	 * 拼接数组型参数
	 * 
	 * @param str
	 * @return
	 */
	protected String getParamsFromString(String[] str) {

		String params = "";
		if (str != null && str.length > 0)
			for (int i = 0; i < str.length; i++) {
				params += str[i];
				if (i != str.length - 1)
					params += ",";
			}

		return params;
	}

	public List<NameValuePair> buildPost(Map<String, Object> datas) {
		List<NameValuePair> data = new ArrayList<NameValuePair>();
		if (datas != null) {
			for (String key : datas.keySet()) {
				if (datas.get(key) != null && !(datas.get(key) + "").isEmpty()) {  //android level>8,此处才可以，不然会有提示错误。
					data.add(new BasicNameValuePair(key, datas.get(key) + ""));
				}
			}
		}
		return data;
	}

	/**
	 * 返回接口通用ResStatus
	 * 
	 * @return
	 */
	public boolean getSuccess() {
		if (parser != null)
			return parser.getSuccess();
		return false;
	}

	public String getJson() {
		if (parser != null) {
			return parser.data;
		}
		return "";
	}

	public String getCode() {
		if (parser != null)
			return parser.getCode();
		return (""+CODE);
	}
	/**
	 * 银行卡接口调用
	 *  //"state":"ok/fail"
	 * @return
	 *//*
	public String getState() {
		if (parser != null)
			return parser.getState();
		return "";
	}
	*//**
	 * 银行卡接口调用
	 * 
	 * @return result
	 *//*
	public String getResult() {
		if (parser != null)
			return parser.getResult();
		return "";
	}*/
	/**
	 * 返回接口通用信息ResInfo
	 * 
	 * @return
	 */
	public String getMsg() {
		if (parser != null)
			return parser.getMessage();
		return "";
	}
	public String getString(){
		if (parser != null)
			return parser.getString();
		return "";
	}

	/**
	 * 通过GET方式从服务器取得数据
	 * 
	 * @param urlpath
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	protected InputStream getWebContent() {
		try {
			URL url = new URL(this.url);
			Log.e("lbb","url--->" + this.url);
			HttpURLConnection conn = null;
			conn = checkNetType(url, conn, context);
			conn.setRequestProperty(AUTH_KEY, AUTH_SECRET);
			conn.setRequestProperty(ACCEPT_ENCODING, GZIP);
			conn.setRequestMethod(METHOD_GET);
			conn.setConnectTimeout(HTTP_TIMEOUT);
			Log.e("lbb","url->" + url + "--code->"
					+ conn.getResponseCode());
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				if (GZIP.equalsIgnoreCase(conn.getContentEncoding())) {
					return new GZIPInputStream(conn.getInputStream());
				} else {
					return conn.getInputStream();
				}
			} else {
				if (isShowErr)
					showErr();
			}
		} catch (Exception e) {
			if (isShowErr)
				showErr();
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 通过GET方式从服务器取得数据
	 * 
	 * @param urlpath
	 * @param encoding
	 * @return InputStream
	 * @throws Exception
	 */
	protected InputStream getWebWithoutAuth(String encoding) {
		try {
			URL url = new URL(this.url);
			Log.e("lbb","url--->" + this.url);
			HttpURLConnection conn = null;
			conn = checkNetType(url, conn, context);
			conn.setRequestMethod(METHOD_GET);
			conn.setRequestProperty(ACCEPT_ENCODING, GZIP);
			conn.setConnectTimeout(HTTP_TIMEOUT);
			// 设置 HttpURLConnection的字符编码
			conn.setRequestProperty(ACCEPT_CHARSET, encoding);
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				if (GZIP.equalsIgnoreCase(conn.getContentEncoding())) {
					return new GZIPInputStream(conn.getInputStream());
				} else {
					return conn.getInputStream();
				}
			}
			Log.e("lbb","url-" + conn.getResponseCode());
		} catch (Exception e) {
			if (isShowErr)
				showErr();
			e.printStackTrace();
		}
		return null;
	}

	private void showErr() {
		// try {
		// ((ActivityContent) context).runOnUiThread(new Runnable() {
		// @Override
		// public void run() {
		// if (SystemUtil.getNetworkStatus(context)) {
		//
		// }else{
		// ToastUtil.showToast(context, "读取失败");
		// }
		// }
		// });
		// } catch (ClassCastException s) {
		// s.printStackTrace();
		// }
	}

	/**
	 * 通过POST方式从服务器获取数据 有返�?
	 * 
	 * @param params
	 *            封装好的请求参数
	 * @param urlPath
	 *            地址
	 * @return
	 */
	protected InputStream getContentWithPOST(String data) {
		// 建立POST连接
		InputStream is = null;
		try {
			Log.e("lbb","[time-start:]->["+TimeUtil.getTime()+"]");
			Log.e("lbb","[data:]->["+data+"]");
			
			StringEntity HE=new StringEntity(data,  HTTP.UTF_8);
			HttpPost hp = new HttpPost(url);
			// 设置发送内容
			hp.setEntity(HE);
		    BasicHttpParams httpParams = new BasicHttpParams();  
	        HttpConnectionParams.setConnectionTimeout(httpParams, CONNECTION_TIMEOUT);  
	        HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);  
	        DefaultHttpClient hc = new DefaultHttpClient(httpParams);  
//			HttpClient hc = new DefaultHttpClient();
	        hc.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(0, false)); //不加此处，竖版的超时不会重复发送请求数据，横版的会发送4次，设置之后就只发送一次了
			// 请求超时
//			hc.getParams().setParameter(
//					CoreConnectionPNames.CONNECTION_TIMEOUT,
//					CONNECTION_TIMEOUT);
//			// 读取超时
////			hc.getParams().setParameter(
////					CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);
//			hc.getParams().setParameter(
//					CoreConnectionPNames.SO_TIMEOUT, 5000);
			//hc.getParams().set
			// 使用客户端发请求对象，返回响应对象
			HttpResponse hr = hc.execute(hp);
			int code = hr.getStatusLine().getStatusCode();
			CODE=code;
			Log.e("lbb","[responseCode:]->["+code+"]");
			if(code != HttpStatus.SC_OK){
				// 中断请求
				hp.abort();
				return null;
			}
			/*LogUtils.addLog("[responseCode:]->[" + code+"]");*/
			// 获取响应内容
			HttpEntity he = hr.getEntity();
			is = he.getContent();
			//String strResult = EntityUtils.toString(he);
			//Log.e("lbb","[back:]->[" +   (strResult.toString()) +"]");
			// 转化为流						
			return is;
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("lbb","[try-catch:]->"+e.getMessage()+e.getLocalizedMessage()+"]");
		}finally{
			Log.e("lbb","[url:]->["+url+"]");
			Log.e("lbb",("[time-end:]->["+TimeUtil.getTime())+"]");
			
		}
		return null;
	} 

	/**
	 * 根据网络类型建立连接
	 * 
	 * @param url
	 * @param conn
	 * @return
	 * @throws IOException
	 */
	private HttpURLConnection checkNetType(URL url, HttpURLConnection conn,
			Context context) throws IOException {
		APNManager apnManager = new APNManager(context);
		if (apnManager.isWapNetwork()) {
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(apnManager.getProxy(), apnManager.getProxyPort()));
			conn = (HttpURLConnection) url.openConnection(proxy);
		} else {
			conn = (HttpURLConnection) url.openConnection();
		}
		return conn;
	}
}
