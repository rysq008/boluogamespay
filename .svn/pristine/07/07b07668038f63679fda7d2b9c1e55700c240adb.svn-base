package com.game.helper.view.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.CrollListTask;
import com.game.helper.sdk.model.returns.CrollList;
import com.game.helper.sdk.model.returns.CrollList.Croll;

/**
 * Created by lixueyong on 16/2/19.
 */
public class BarrageView extends RelativeLayout {
	private Context mContext;
	private BarrageHandler mHandler = new BarrageHandler();
	private Random random = new Random(System.currentTimeMillis());
	private static  long BARRAGE_GAP_MIN_DURATION = 3000;//两个弹幕的最小间隔时间
	//private static  long BARRAGE_GAP_MAX_DURATION = 2000;//两个弹幕的最大间隔时间
	//  private int maxSpeed = 14000;//速度，ms
	private int minSpeed = 8000;//速度，ms
	//private int maxSize = 30;//文字大小，dp
	//private int minSize = 15;//文字大小，dp
	public static boolean isStop=true;

	//private int totalHeight = 0;
	private int lineHeight = 0;//每一行弹幕的高度
	private int totalLine = 1;//弹幕的行数
	/*private String[] itemText = {"是否需长长长长长长长长要帮忙", "what are you 弄啥长长长长来", "哈长长长长长***", "是否需长长长长长长长长要长长长帮忙",
			"我不会长长长长长长长长长长长轻易的狗带", "嘿长长长长长长长长长长长长长长嘿", "这是我长见过的最长长长长长长长长的评论","4414124", "9094124", "2124", "2342455", "0789", "5290",
			"27337", "057325345", "3745736222"};*/
	private List<Croll> itemText = new ArrayList<Croll>();
	//private int textCount;
	private List<BarrageItem> itemList = new ArrayList<BarrageItem>();
	BarrageItem lastItem;
	public BarrageView(Context context) {
		this(context, null);
	}

	public BarrageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BarrageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mContext = context;
		init();
	}

	private void init() {
		isCcStop=false;
		new BBSThread().start();
		num=1;
		//textCount = itemText.size();
		isStop=true;
		//int duration = (int) ((BARRAGE_GAP_MAX_DURATION - BARRAGE_GAP_MIN_DURATION) * Math.random());
		//mHandler.sendEmptyMessageDelayed(0, 1000);
	}

	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		super.onWindowFocusChanged(hasWindowFocus);
		//totalHeight = getMeasuredHeight();
		lineHeight = getLineHeight();
		totalLine = 1;
		Log.e("lbb", "-----------onWindowFocusChanged----------"+hasWindowFocus);
	}
	private void generateItem() {
		
		BarrageItem item = new BarrageItem();
		String tx="";
		if(itemText.size()>0){
			postion=postion%itemText.size();
			tx = itemText.get(postion).rollContent;	
		}
		
		if(!TextUtils.isEmpty(tx)){
			if(tx.length()<=10){
				BARRAGE_GAP_MIN_DURATION=6000;
			}else if(tx.length()<=15){
				BARRAGE_GAP_MIN_DURATION=7000;
			}else if(tx.length()<=20){
				BARRAGE_GAP_MIN_DURATION=8000;
			}else if(tx.length()<=25){
				BARRAGE_GAP_MIN_DURATION=8100;
			}else if(tx.length()<=30){
				BARRAGE_GAP_MIN_DURATION=8200;
			}else if(tx.length()<=35){
				BARRAGE_GAP_MIN_DURATION=8300;
			}else if(tx.length()<=40){
				BARRAGE_GAP_MIN_DURATION=8400;
			}else{
				BARRAGE_GAP_MIN_DURATION=8500;
			}
		}else{
			BARRAGE_GAP_MIN_DURATION=1000;
		}

		//int sz = (int) (minSize + (maxSize - minSize) * Math.random());
		item.textView = new TextView(mContext);
		item.textView.setText(tx);
		item.textView.setSingleLine(true);
		item.textView.setTextSize(12);
		int clo=Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
		//1356465 7685374 10982095 1960865
		Log.e("lbb", "-----generateItem--clo--------"+clo);
		//int s=13838555;
		item.textView.setTextColor(clo);//5030263
		item.textMeasuredWidth = (int) getTextWidth(item, tx, 12);
		item.moveSpeed = minSpeed;
		if (totalLine == 0) {
			//totalHeight = getMeasuredHeight();
			lineHeight = getLineHeight();
			//totalLine = totalHeight / lineHeight;
		}
		if(totalLine == 0) {
			totalLine = 1;
		}
		item.verticalPos =totalLine * lineHeight;
		//        itemList.add(item);
		showBarrageItem(item);
	}

	private void showBarrageItem(final BarrageItem item) {

		int leftMargin = this.getRight() - this.getLeft() - this.getPaddingLeft();

		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		params.topMargin = item.verticalPos;
		/*if(lastItem!=null){
			lastItem.textView.clearAnimation();
			BarrageView.this.removeView(lastItem.textView);
		}*/
		//lastItem=item;
		this.addView(item.textView, params);
		Animation anim = generateTranslateAnim(item, leftMargin/num);
		if(num==2){
			num=1;
		}
		anim.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				item.textView.clearAnimation();
				BarrageView.this.removeView(item.textView);
				itemList.remove(item);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});
		item.textView.startAnimation(anim);
		itemList.add(item);
	}

	private TranslateAnimation generateTranslateAnim(BarrageItem item, int leftMargin) {
		TranslateAnimation anim = new TranslateAnimation(leftMargin, -item.textMeasuredWidth, 0, 0);
		anim.setDuration(item.moveSpeed);
		anim.setInterpolator(new LinearInterpolator());
		anim.setFillAfter(false);//指动画结束是画面停留在此动画的最后一帧。
		return anim;
	}

	/**
	 * 计算TextView中字符串的长度
	 *
	 * @param text 要计算的字符串
	 * @param Size 字体大小
	 * @return TextView中字符串的长度
	 */
	public float getTextWidth(BarrageItem item, String text, float Size) {
		Rect bounds = new Rect();
		TextPaint paint;
		paint = item.textView.getPaint();
		paint.getTextBounds(text, 0, text.length(), bounds);
		return bounds.width();
	}

	/**
	 * 获得每一行弹幕的最大高度
	 *
	 * @return
	 */
	private int getLineHeight() {
		BarrageItem item = new BarrageItem();
		
		String tx = "每一行弹幕的最大高度";
		item.textView = new TextView(mContext);
		item.textView.setText(tx);
		item.textView.setTextSize(12);

		Rect bounds = new Rect();
		TextPaint paint;
		paint = item.textView.getPaint();
		paint.getTextBounds(tx, 0, tx.length(), bounds);
		return bounds.height();
	}
    int postion=0;
	class BarrageHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what == 1){
				new CrollListTask(mContext, new Back() {
					
					@Override
					public void success(Object object, String msg) {
						if(object!=null && object instanceof  CrollList){
							 CrollList mCrollList=(CrollList) object;
							 if(mCrollList.data!=null){
								 itemText.clear();
								 itemText.addAll(mCrollList.data);
								 //textCount = itemText.size();
							 }
						}
						
					}
					
					@Override
					public void fail(String status, String msg, Object object) {
						
						
					}
				}).start();
			}else if(msg.what == 0){
				try {
					
				
				if(!isStop){
					generateItem();
					postion++;
					//每个弹幕产生的间隔时间随机
					//int duration = (int) ((BARRAGE_GAP_MAX_DURATION - BARRAGE_GAP_MIN_DURATION) * Math.random());
					this.sendEmptyMessageDelayed(0,BARRAGE_GAP_MIN_DURATION);
				}/*else{
					//BarrageView.this.removeAllViews();
					this.sendEmptyMessageDelayed(0,100);
				}*/
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if(msg.what == 2){
				
			}
			

		}
	}
	int num=1;
	public void stop(){
		try {
			for(BarrageItem mBarrageItem:itemList){
				mBarrageItem.textView.clearAnimation();
			}
			this.removeAllViews();
			isStop=true;
			//BARRAGE_GAP_MIN_DURATION = 100;
			mHandler.removeMessages(0);
		} catch (Exception e) {
			 e.printStackTrace();
		}

	}
	public void reInit(List<Croll> data){
		 itemText.clear();
		 itemText.addAll(data);
		
		 if(itemText.size()>0){
			 postion=(int) (Math.random() * itemText.size());
		 }
	}
	public void reStart(){
		try {
			 
			 
			for(BarrageItem mBarrageItem:itemList){
				mBarrageItem.textView.clearAnimation();
			}
			num=2;
			this.removeAllViews();
			
			//BARRAGE_GAP_MIN_DURATION = 6*1000;
			isStop=false;
			mHandler.sendEmptyMessageDelayed(0,100);
		} catch (Exception e) {
			 e.printStackTrace();
		}
	}
	public static boolean isCcStop=true;
	public static int TIME = 5*60*1000;
	public  class BBSThread extends Thread{

		@Override
		public synchronized void run() {
			// TODO Auto-generated method stub
			try {
				while (!isCcStop) {
					mHandler.sendEmptyMessage(1);
					Thread.sleep(TIME);

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}	
}
