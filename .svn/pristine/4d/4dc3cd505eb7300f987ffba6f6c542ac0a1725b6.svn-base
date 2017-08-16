package com.game.helper.util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.game.helper.sdk.Config;

import android.util.Log;


public class TestFileUpload {

	
	private static String uploadFile(String upUrl, String filePath) {
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
		try {
			URL url = new URL(upUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			con.setRequestMethod("POST");
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");
			con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			DataOutputStream ds = new DataOutputStream(con.getOutputStream());
			ds.writeBytes(twoHyphens + boundary + end);
			ds.writeBytes("Content-Disposition: form-data; " + "name=\"file\";filename=\""
					+ fileName + "\"" + end);
			ds.writeBytes(end);
			InputStream fStream = new FileInputStream(new File(filePath));
			int bufferSize = 1024;
			byte[] buffer = new byte[bufferSize];
			int length = -1;
			while ((length = fStream.read(buffer)) != -1) {
				ds.write(buffer, 0, length);
			}
			ds.writeBytes(end);
			ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
			fStream.close();
			ds.flush();
			InputStream is = con.getInputStream();
			int ch;
			StringBuffer b = new StringBuffer();
			while ((ch = is.read()) != -1) {
				b.append((char) ch);
			}
			ds.close();
			return b.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static String upLoad(String  userPhone, String filePath,String needThumb) throws UnsupportedEncodingException {
//		String resp = uploadFile("http://localhost:3030/vma-trusttee/uploadFile/uploadAudio.do?username=18060478013&needThumb=1","F:\\KuGou\\闽南语歌曲 - 送行.mp3");
//		String resp = uploadFile("http://localhost:3030/vma-trusttee/uploadFile/uploadAudio.do?username=18060478013","D:\\psb.MP3");
		String resp = uploadFile(Config.getInstance().IP+"/helper/uploadFile/uploadPicture.do?phone_num="+userPhone+"&needThumb="+needThumb,filePath);
		resp =new String(resp.getBytes("ISO-8859-1"),"UTF-8");
		Log.e("lbb", "------uploadFile-----:"+resp);
		//System.out.println();
		return resp;
	}

}
