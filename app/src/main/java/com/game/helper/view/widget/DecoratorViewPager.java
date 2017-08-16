package com.game.helper.view.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class DecoratorViewPager  extends ViewPager {
	private ViewGroup parent;  
	// 滑动距离及坐标
    private float xDistance, yDistance, xLast, yLast;
	public DecoratorViewPager(Context context) {  
		super(context);  
		
	}  

	public DecoratorViewPager(Context context, AttributeSet attrs) {  
		super(context, attrs);  
		
	}  

	public void setNestedpParent(ViewGroup parent) {  
		this.parent = parent;  
	}  

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
                          return false;   //表示向下传递事件
                  }
          }

          return super.onInterceptTouchEvent(ev);
		
	}  

	
	
}
