package com.game.helper.activity.home;

import org.xml.sax.XMLReader;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetBasicTask;
import com.game.helper.sdk.Config;
import com.game.helper.sdk.model.returns.GetBasic;
import com.game.helper.util.HtmlImageGetter;
import com.game.helper.util.URLImageParser;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import com.umeng.analytics.MobclickAgent;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.Html.ImageGetter;
import android.text.Html.TagHandler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 充值说明的详情
 * @Path com.game.helper.activity.home.RechargeExplainDetailsActivity.java
 * @Author lbb
 * @Date 2016年8月24日 上午10:57:04
 * @Company 
 */
public class RechargeExplainDetailsActivity extends BaseActivity{
	@BindView(R.id.tv_title) TextView tv_titles;
	@BindView(R.id.tv_content) TextView tv_content;
	@BindView(R.id.iv_mImageView1) ImageView iv_mImageView1;
	@BindView(R.id.topLeftBack)
	ImageView topLeftBack;
	@BindView(R.id.top_title)
	TextView topTitle;
	public @BindView(R.id.webview) WebView webview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_home_recharge_explain_details);
		ButterKnife.bind(this);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initView() {
		WebSettings webSetting = webview.getSettings();
		webSetting.setAllowFileAccess(true);
		webSetting.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webSetting.setSupportZoom(true);
		webSetting.setBuiltInZoomControls(true);
		webSetting.setUseWideViewPort(true);
		webSetting.setSupportMultipleWindows(false);
		//webSetting.setLoadWithOverviewMode(true);
		webSetting.setAppCacheEnabled(true);
		//webSetting.setDatabaseEnabled(true);
		webSetting.setDomStorageEnabled(true);
		webSetting.setJavaScriptEnabled(true);
		webSetting.setGeolocationEnabled(true);
		webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
		webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
		webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
		webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
				.getPath());
		// webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
		webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
		//webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
		// webSetting.setPreFectch(true);

		webview.setInitialScale(25);
		
		topLeftBack.setVisibility(View.VISIBLE);
	}

	@Override
	protected void initData() {
		int type=getIntent().getIntExtra("KEY_NAMETYPE", 0);
		if(type==0){
			topTitle.setText("新账号首充说明");
			new GetBasicTask(mContext,true,  "free", new Back() {

				@Override
				public void success(Object object, String msg) {
					if(object!=null && object instanceof GetBasic){
						GetBasic mGetBasic=(GetBasic) object;
						if(mGetBasic.data!=null){
							tv_titles.setText(mGetBasic.data.basicName);
							
							//默认图片，无图片或没加载完显示此图片
							Drawable defaultDrawable = getResources().getDrawable(R.drawable.preview_card_pic_loading);
							//调用
							//Spanned sp = Html.fromHtml(mGetBasic.data.context, new URLImageParser(tv_content), null);
							
							//tv_content.setText(sp);
							webview.loadUrl(Config.getInstance().IP+"/helper/app/toWapPage?type=basic&id="+4);
							
							/*tv_content.setText(""+Html.fromHtml(mGetBasic.data.context, new ImageGetter() {
								
								@Override
								public Drawable getDrawable(String source) {
									if(!TextUtils.isEmpty(source)){
										Glide.with(BaseApplication.mInstance.context.getApplicationContext())
										.load(""+source)
										.diskCacheStrategy(DiskCacheStrategy.SOURCE)
										//.centerCrop()// 长的一边撑满
										//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片  
										.error(R.drawable.picture_defeated)//加载失败时显示的图片
										//.crossFade()
										.into(iv_mImageView1);
									}
									return null;
								}
							}, new TagHandler() {
								
								@Override
								public void handleTag(boolean arg0, String arg1, Editable arg2,
										XMLReader arg3) {
									// TODO Auto-generated method stub
									
								}
							}));*/
						}
					}

				}

				@Override
				public void fail(String status, String msg, Object object) {

				}
			}).start();
		}else if(type==1){
			topTitle.setText("折扣号续充说明");
			new GetBasicTask(mContext,true,  "discount", new Back() {

				@Override
				public void success(Object object, String msg) {
					if(object!=null && object instanceof GetBasic){
						GetBasic mGetBasic=(GetBasic) object;
						if(mGetBasic.data!=null){
							tv_titles.setText(mGetBasic.data.basicName);
							//默认图片，无图片或没加载完显示此图片
							Drawable defaultDrawable = getResources().getDrawable(R.drawable.preview_card_pic_loading);
							//调用
							//Spanned sp = Html.fromHtml(mGetBasic.data.context, new URLImageParser(tv_content), null);
							
							//tv_content.setText(sp);
							webview.loadUrl(Config.getInstance().IP+"/helper/app/toWapPage?type=basic&id="+5);
							
							/*tv_content.setText(""+Html.fromHtml(mGetBasic.data.context, new ImageGetter() {
								
								@Override
								public Drawable getDrawable(String source) {
									if(!TextUtils.isEmpty(source)){
										Glide.with(BaseApplication.mInstance.context.getApplicationContext())
										.load(""+source)
										.diskCacheStrategy(DiskCacheStrategy.SOURCE)
										//.centerCrop()// 长的一边撑满
										//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片  
										.error(R.drawable.picture_defeated)//加载失败时显示的图片
										//.crossFade()
										.into(iv_mImageView1);
									}
									return null;
								}
							}, new TagHandler() {
								
								@Override
								public void handleTag(boolean arg0, String arg1, Editable arg2,
										XMLReader arg3) {
									// TODO Auto-generated method stub
									
								}
							}));*/
						}
					}

				}

				@Override
				public void fail(String status, String msg, Object object) {

				}
			}).start();
		}else if(type==2){
			topTitle.setText("金币充值说明");
			new GetBasicTask(mContext,true,  "recharge", new Back() {

				@Override
				public void success(Object object, String msg) {
					if(object!=null && object instanceof GetBasic){
						GetBasic mGetBasic=(GetBasic) object;
						if(mGetBasic.data!=null){
							tv_titles.setText(mGetBasic.data.basicName);
							//默认图片，无图片或没加载完显示此图片
							Drawable defaultDrawable = getResources().getDrawable(R.drawable.preview_card_pic_loading);
							//调用
							//Spanned sp = Html.fromHtml(mGetBasic.data.context, new URLImageParser(tv_content), null);
							
							//tv_content.setText(sp);
							webview.loadUrl(Config.getInstance().IP+"/helper/app/toWapPage?type=basic&id="+3);
							
							/*tv_content.setText(""+Html.fromHtml(mGetBasic.data.context, new ImageGetter() {
								
								@Override
								public Drawable getDrawable(String source) {
									if(!TextUtils.isEmpty(source)){
										Glide.with(BaseApplication.mInstance.context.getApplicationContext())
										.load(""+source)
										.diskCacheStrategy(DiskCacheStrategy.SOURCE)
										//.centerCrop()// 长的一边撑满
										//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片  
										.error(R.drawable.picture_defeated)//加载失败时显示的图片
										//.crossFade()
										.into(iv_mImageView1);
									}
									return null;
								}
							}, new TagHandler() {
								
								@Override
								public void handleTag(boolean arg0, String arg1, Editable arg2,
										XMLReader arg3) {
									// TODO Auto-generated method stub
									
								}
							}));*/
						}
					}

				}

				@Override
				public void fail(String status, String msg, Object object) {

				}
			}).start();
		}



	}

	@Override
	@OnClick({R.id.top_left_layout})
	public void onClick(View v) {
		switch ( v.getId()) {
		case R.id.top_left_layout:
			finish1();
			break;
		default:
			super.onClick(v);
			break;
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			finish1();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	protected void onPause() {
		MobclickAgent.onPageEnd("RechargeExplainDetailsActivity");
		super.onPause();
	}

	@Override
	protected void onResume() {
		MobclickAgent.onPageStart("RechargeExplainDetailsActivity");
		super.onResume();
	}
}