package com.game.helper.util;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html.ImageGetter;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * @Description
 * @Path com.game.helper.util.URLImageParser.java
 * @Author lbb
 * @Date 2016年12月1日 下午12:47:25
 * @Company 
 */
public class URLImageParser implements ImageGetter{  
    TextView mTextView;  
  
    public URLImageParser(TextView textView) {  
        this.mTextView = textView;  
    }  
  
    @Override  
    public Drawable getDrawable(String source) {  
        final URLDrawable urlDrawable = new URLDrawable();  
        Log.e("URLImageParser", "Consts.BASE_URL" + source);  
        ImageLoader.getInstance().loadImage( source, new SimpleImageLoadingListener() {  
            @Override  
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {  
                urlDrawable.bitmap = loadedImage;  
                urlDrawable.setBounds(0, 0, loadedImage.getWidth(), loadedImage.getHeight());  
                mTextView.invalidate();  
                mTextView.setText(mTextView.getText()); // 解决图文重叠  
            }  
        });  
        return urlDrawable;  
    }  
    
    public class URLDrawable extends BitmapDrawable{  
        protected Bitmap bitmap;  
      
        @Override  
        public void draw(Canvas canvas) {  
            if (bitmap != null) {  
                canvas.drawBitmap(bitmap, 0, 0, getPaint());  
            }  
        }  
    }  
}  
