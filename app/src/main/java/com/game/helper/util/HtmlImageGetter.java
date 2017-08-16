package com.game.helper.util;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.Html.ImageGetter;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

/**
 * @Description
 * @Path com.game.helper.util.HtmlImageGetter.java
 * @Author lbb
 * @Date 2016年11月16日 下午2:19:11
 * @Company 
 */
public class HtmlImageGetter implements ImageGetter{
	  private TextView _htmlText;
	  private String _imgPath;
	  private Drawable _defaultDrawable;
	  private String TAG = "HtmlImageGetter";
	  public HtmlImageGetter(TextView htmlText, String imgPath, Drawable defaultDrawable){
	    _htmlText = htmlText;
	    _imgPath = imgPath;
	    _defaultDrawable = defaultDrawable;
	  }
	  @Override
	  public Drawable getDrawable(String imgUrl) {
		URLDrawable urlDrawable = new URLDrawable(_defaultDrawable);
		Log.e("lbb", "--------imgUrl----"+imgUrl);
		if(!TextUtils.isEmpty(imgUrl)&&imgUrl.indexOf("file://")!=-1){
			return urlDrawable;
		}
	    String imgKey = String.valueOf(imgUrl.hashCode());
	    String path = Environment.getExternalStorageDirectory() + _imgPath;
	    FileUtil.createPath(path);
	    String[] ss = imgUrl.split("\\.");
	    String imgX = ss[ss.length-1];
	    imgKey = path+"/" + imgKey+"."+imgX;
	    if(FileUtil.exists(imgKey)){
	      Drawable drawable = FileUtil.getImageDrawable(imgKey);
	      if(drawable != null){
	    	  Bitmap bm = BitmapFactory.decodeFile(imgKey);
	          if (bm != null) {
	        	  Log.e("lbb", bm.getWidth()+"--------bm------"+bm.getHeight());
	        	  drawable.setBounds(0, 0, bm.getWidth(),bm.getHeight());
	          }
	        return drawable;
	      }else{
	        Log.v(TAG,"load img:"+imgKey+":null");
	      }
	    }
	  
	    new AsyncThread(urlDrawable).execute(imgKey, imgUrl);
	    return urlDrawable;
	  }
	  private class AsyncThread extends AsyncTask<String, Integer, Drawable> {
	    private String imgKey;
	    private URLDrawable _drawable;
	    public AsyncThread(URLDrawable drawable){
	      _drawable = drawable;
	    }
	    @Override
	    protected Drawable doInBackground(String... strings) {
	      imgKey = strings[0];
	      InputStream inps = NetWork.getInputStream(strings[1]);
	      if(inps == null) return _drawable;
	      FileUtil.saveFile(imgKey, inps);
	      Drawable drawable = Drawable.createFromPath(imgKey);
	      Bitmap bm = BitmapFactory.decodeFile(imgKey);
          if (bm != null) {
        	  drawable.setBounds(0, 0, bm.getWidth(),bm.getHeight());
          }
	      return drawable;
	    }
	    public void onProgressUpdate(Integer... value) {
	    }
	    @Override
	    protected void onPostExecute(Drawable result) {
	      _drawable.setDrawable(result);
	      _htmlText.setText(_htmlText.getText());
	    }
	  }
	  public class URLDrawable extends BitmapDrawable {
	    private Drawable drawable;
	    public URLDrawable(Drawable defaultDraw){
	      setDrawable(defaultDraw);
	    }
	    private void setDrawable(Drawable ndrawable){
	      drawable = ndrawable;
	      drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable
	          .getIntrinsicHeight());
	      setBounds(0, 0, drawable.getIntrinsicWidth(), drawable
	          .getIntrinsicHeight());
	    }
	    @Override
	    public void draw(Canvas canvas) {
	      drawable.draw(canvas);
	    }
	  }
	}

