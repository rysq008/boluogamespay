package com.game.helper.view.widget;

import java.util.Iterator;
import java.util.Map;

import org.apache.http.util.EncodingUtils;

import com.game.helper.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * @Description
 * @Path com.game.helper.view.widget.CommonWebView.java
 * @Author lbb
 * @Date 2016年8月30日 下午5:25:36
 * @Company 
 */
public class CommonWebView extends WebView{
	
	  private static final String TAG = "CommonWebView";
	  private String typeGlobal;//和JS互动
	  public Context mContext;
	  private Map<String, String> map;
	  public static WebViewStatusListener statusListener;
	  private ProgressBar progressbar;
	  public static abstract interface WebViewStatusListener
	  {
	    public abstract void onDown();
	    
	    public abstract void onPageFinished();
	    
	    public abstract void onPageStarted();
	    
	    public abstract void onProgressChanged(int paramInt);
	    
	    public abstract void onReceivedTitle(String paramString);
	    
	    public abstract void onRecevieError();
	    
	    public abstract boolean onShouldOverrideUrlLoading(String paramString);
	  }
	  
	  

	@SuppressWarnings("deprecation")
	  public CommonWebView(Context context)
	  {
	    super(context);
	    this.mContext = context;
	    progressbar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
	    progressbar.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 5, 0, 0));
	    progressbar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.webview_progressbar));
	    addView(progressbar);
	    init();
	  }
	  
	  @SuppressWarnings("deprecation")
	  public CommonWebView(Context context, AttributeSet paramAttributeSet)
	  {
	    super(context, paramAttributeSet);
	    this.mContext = context;
	    progressbar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
	    progressbar.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 3, 0, 0));
	    progressbar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.webview_progressbar));
	    addView(progressbar);
	   
	    init();
	  }
	  
	  @SuppressLint({"NewApi", "SetJavaScriptEnabled"})
	  private void init()
	  {
	    setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);//设置滚动条隐藏 View.SCROLLBARS_OUTSIDE_OVERLAY
	    WebSettings webSettings = getSettings();//返回一WebSettings对象，用来控制WebView的属性设置
	    /*便页面支持缩放*/
	   // webSettings.setSupportZoom(true); //支持缩放
	    webSettings.setJavaScriptEnabled(true); //启用JS脚本
	  //  webSettings.setBuiltInZoomControls(true); //启用内置缩放装置
	    webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
	    webSettings.setCacheMode(WebSettings.LOAD_NORMAL);//关闭Webview中缓存,解决缓存问题
	   // webSettings.setDefaultTextEncodingName("UTF-8");//设置默认字符编码方式
	    webSettings.setSavePassword(false);       //设置是否存储密码
	    webSettings.setSaveFormData(false);
	    /*打开页面时， 自适应屏幕*/
	   // webSettings.setUseWideViewPort(true);//将图片调整到适合webview的大小,设置此属性，可任意比例缩放
	   // webSettings.setLoadWithOverviewMode(true);//缩放至屏幕的大小
	    if (Build.VERSION.SDK_INT > 11) {
	     // webSettings.setDisplayZoomControls(false);//隐藏webview缩放按钮
	  	  Log.e(TAG,"Build.VERSION.SDK_INT: "+Build.VERSION.SDK_INT);
	    }
	    if (Build.VERSION.SDK_INT > 7)
	    {
	    	Log.e(TAG,"Build.VERSION.SDK_INT: "+Build.VERSION.SDK_INT);
	      /*webSettings.setDomStorageEnabled(true);//使用LocalStorage则必须打开
	      应用可以有缓存 
	      webSettings.setAppCacheEnabled(true); 
	      webSettings.setAppCacheMaxSize(8388608L);// 设置应用缓存的最大尺寸
	      webSettings.setAppCachePath(mContext.
	    		  getApplicationContext().getCacheDir().getAbsolutePath());// 设置应用缓存的路径
	*/    
	    //  webSettings.setAllowFileAccess(true);//设置是允许访问文件数据
	     /*应用可以有数据库*/
	     /*mWebSettings.setDatabaseEnabled(true);  
	      String dbPath = mContext.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
	      mWebSettings.setDatabasePath(dbPath);*/
	    }
	    
	    /*
	     * WebViewClient可以辅助WebView处理各种通知、请求等事件。
	     * WebViewClient提供了一些方法
	     * */
	    setWebViewClient(new WebViewClient() {
	    	
	        @Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
			}

			//当点击链接时,希望覆盖而不是打开新窗口
	        @Override
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {
	            view.loadUrl(url);  //加载新的url
	            return true;    //返回true,代表事件已处理,事件流到此终止
	        }
	        
	    });
	   //点击后退按钮,让WebView后退一页(也可以覆写Activity的onKeyDown方法)
	    setOnKeyListener(new OnKeyListener() {
	        @Override
	        public boolean onKey(View v, int keyCode, KeyEvent event) {
	            if (event.getAction() == KeyEvent.ACTION_DOWN) {
	                if (keyCode == KeyEvent.KEYCODE_BACK && canGoBack()) {
	                    goBack();   //后退
	                    return true;    //已处理
	                }
	            }
	            return false;
	        }
	    });
	    /*
	     *实现文件下载的功能 
	     */
	    setDownloadListener(new DownloadListener() {
	        @Override
	        public void onDownloadStart(String url, String userAgent, String contentDisposition, 
	        		String mimetype, long contentLength) {
	            /*if (url != null && url.startsWith("http://"))*/    //?
	              mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
	        }
	    });
	    /*
	     * 为WebView指定一个WebChromeClient对象。
	     * WebChromeClient专门用来辅助WebView处理JavaScript的对话框、网站title、网站图标、加载进度等。
	     */
	    setWebChromeClient(new WebChromeClient() {
	        //当WebView进度改变时更新窗口进度
	        @Override
	        public void onProgressChanged(WebView view, int newProgress) {
	            //Activity的进度范围在0到10000之间,所以这里要乘以100
	            /*((Activity)mContext).setProgress(newProgress * 100);*/
	        	 if (newProgress == 100) {
	                 progressbar.setVisibility(GONE);
	             } else {
	                 if(progressbar.getVisibility() == GONE){
	                     progressbar.setVisibility(VISIBLE);
	                 }
	                 progressbar.setProgress(newProgress);
	             }
	             super.onProgressChanged(view, newProgress);
	        }
	        /** 
	         * 处理JavaScript Alert事件 
	         */  
			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					JsResult result) {
				
				//Toast.makeText(mContext, "WebView",Toast.LENGTH_SHORT).show();
				return true;
			}
	    });
	  //<a onclick="window.smarttouch.clickOnAndroid("type")">Test</a>
	    
	  }
	  /*@SuppressWarnings("deprecation")
	  @Override
	  protected void onScrollChanged(int l, int t, int oldl, int oldt) {
	      LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
	      lp.x = l;
	      lp.y = t;
	      progressbar.setLayoutParams(lp);
	      super.onScrollChanged(l, t, oldl, oldt);
	  }*/
	 
	  public ProgressBar getProgressbar() {
			return progressbar;
	  }

	  public void setProgressbar(ProgressBar progressbar) {
			this.progressbar = progressbar;
	  }
	  
	  
	  
	  @SuppressWarnings({ "rawtypes", "unused" })
	  public void get(String paramString, Map<String, String> paramMap)
	  {
	    this.map = paramMap;
	    Iterator localIterator;
	    if (paramMap != null) {
	      localIterator = paramMap.entrySet().iterator();
	    }
	    for (;;)
	    {
	      if (true)
	      {
	    	  Log.e(TAG, "CommonWebView-加载入口页 GET = " + paramString);
	         // loadUrl( "http://m.10010.com/");
	        return;
	      }
	      Map.Entry localEntry = (Map.Entry)localIterator.next();
	      if (!paramString.contains((CharSequence)localEntry.getKey())) {
	        if (paramString.contains("?")) {
	          paramString = paramString + "&" + (String)localEntry.getKey() + "=" + (String)localEntry.getValue();
	        } else {
	          paramString = paramString + "?" + (String)localEntry.getKey() + "=" + (String)localEntry.getValue();
	        }
	      }
	    }
	  }
	  
	  public void loadURL(String url)
	  {
	    loadUrl(url);
	  }
	  
	  @SuppressWarnings({ "unused", "rawtypes" })
	  public void post(String url, Map<String, String> mMap)
	  {
	    this.map = mMap;
	    String str = "";
	    Iterator localIterator;
	    if (mMap != null) {
	      localIterator = mMap.entrySet().iterator();
	    }
	    for (;;)
	    {
	      if (true)
	      {
	        if (!TextUtils.isEmpty(str)) {
	          str = str.substring(0, -1 + str.length());
	        }
	        postUrl(url, EncodingUtils.getBytes(str, "BASE64"));
	        return;
	      }
	      Map.Entry localEntry = (Map.Entry)localIterator.next();
	      str = str + (String)localEntry.getKey() + "=" + (String)localEntry.getValue() + "&";
	    }
	  }
	  
	  @SuppressWarnings("static-access")
	  public void setStatusListener(WebViewStatusListener paramWebViewStatusListener)
	  {
	    this.statusListener = paramWebViewStatusListener;
	  }
	}

