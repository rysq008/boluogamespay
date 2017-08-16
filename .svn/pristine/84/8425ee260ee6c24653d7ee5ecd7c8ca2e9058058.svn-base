package com.game.helper.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ScrollView;
/**
 * 监听ScrollView的滚动位置
 */

public class CustomScrollView extends ScrollView {
	private onScrollChangeListener listener;
	private boolean canScroll;

	private GestureDetector mGestureDetector;
	private float xDistance, yDistance, xLast, yLast;

	public void setOnScrollChangeListener(onScrollChangeListener listener) {
		this.listener = listener;
	}

	public interface onScrollChangeListener {
		void onScrollChanged(int y);
	}

	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if(listener!=null){
			
			listener.onScrollChanged(t);
		}
	}

	public CustomScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 也就是决定是否允许Touch事件继续向下（子控件）传递，一但返回True（代表事件在当前的viewGroup中会被处理），
	 * 则向下传递之路被截断（所有子控件将没有机会参与Touch事件），
	 * 同时把事件传递给当前的控件的onTouchEvent()处理；返回false，则把事件交给子控件的onInterceptTouchEvent()
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			xDistance = yDistance = 0f;
			xLast = ev.getX();
			yLast = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			final float curX = ev.getX();
			final float curY = ev.getY();

			xDistance += Math.abs(curX - xLast);
			yDistance += Math.abs(curY - yLast);
			xLast = curX;
			yLast = curY;

			if (xDistance > yDistance) {
				return false;
			}
		}

		return super.onInterceptTouchEvent(ev);
	}
} 