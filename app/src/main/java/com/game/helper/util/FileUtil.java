package com.game.helper.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.game.helper.BuildConfig;

public class FileUtil {
	private static int FILE_SIZE = 1*1024;
	private static String TAG = "FileUtil";
	public static boolean hasSdcard(){
		String status = Environment.getExternalStorageState();
		if(status.equals(Environment.MEDIA_MOUNTED)){
			return true;
		}
		return false;
	}
	public static boolean createPath(String path){
		File f = new File(path);
		if(!f.exists()){
			Boolean o = f.mkdirs();
			Log.i(TAG, "create dir:"+path+":"+o.toString());
			return o;
		}
		return true;
	}
	public static boolean exists(String file){
		return new File(file).exists();
	}
	public static File saveFile(String file, InputStream inputStream){
		File f = null;
		OutputStream outSm = null;
		try{
			f = new File(file);
			String path = f.getParent();
			if(!createPath(path)){
				Log.e(TAG, "can't create dir:"+path);
				return null;
			}
			if(!f.exists()){
				f.createNewFile();
			}
			outSm = new FileOutputStream(f);
			byte[] buffer = new byte[FILE_SIZE];
			while((inputStream.read(buffer)) != -1){
				outSm.write(buffer);
			}
			outSm.flush();
		}catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}finally{
			try{
				if(outSm != null) outSm.close();
			}catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		Log.v(TAG,"[FileUtil]save file:"+file+":"+Boolean.toString(f.exists()));
		return f;
	}
	public static Drawable getImageDrawable(String file){
		if(!exists(file)) return null;
		try{
			InputStream inp = new FileInputStream(new File(file));
			return BitmapDrawable.createFromStream(inp, "img");
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	public static void apkInstall(File apkFile, Context context){
		Intent intent = new Intent(Intent.ACTION_VIEW);
		//判断是否是AndroidN以及更高的版本
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", apkFile);
			intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
		} else {
			intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		}
		context.startActivity(intent);
	}
}
