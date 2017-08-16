package com.example.mylistview.swipemenu;


import com.example.mylistview.R;
import com.example.mylistview.pullListView.PullableListView;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.ScrollerCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * 
 * @author baoyz
 * @date 2014-8-23
 * 
 */
public class SwipeMenuLayout extends RelativeLayout {

	private static final int CONTENT_VIEW_ID = 1;
	private static final int MENU_VIEW_ID = 2;

	private static final int STATE_CLOSE = 0;
	private static final int STATE_OPEN = 1;

	private View mContentView;
	private SwipeMenuView mMenuView;
	private int mDownX;
	private int state = STATE_CLOSE;
	private GestureDetectorCompat mGestureDetector;
	private OnGestureListener mGestureListener;
	private boolean isFling;
	private int MIN_FLING = dp2px(15);
	private int MAX_VELOCITYX = -dp2px(500);
	private ScrollerCompat mOpenScroller;
	private ScrollerCompat mCloseScroller;
	private int mBaseX;
	private int position;
	private Interpolator mCloseInterpolator;
	private Interpolator mOpenInterpolator;

	public SwipeMenuLayout(View contentView, SwipeMenuView menuView) {
		this(contentView, menuView, null, null);
	}

	public SwipeMenuLayout(View contentView, SwipeMenuView menuView,
			Interpolator closeInterpolator, Interpolator openInterpolator) {
		super(contentView.getContext());
		mCloseInterpolator = closeInterpolator;
		mOpenInterpolator = openInterpolator;
		mContentView = contentView;
		mMenuView = menuView;
		mMenuView.setLayout(this);
		init();
	}

	// private SwipeMenuLayout(Context context, AttributeSet attrs, int
	// defStyle) {
	// super(context, attrs, defStyle);
	// }

	private SwipeMenuLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private SwipeMenuLayout(Context context) {
		super(context);
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
		mMenuView.setPosition(position);
	}

	private void init() {
		setLayoutParams(new PullableListView.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		mGestureListener = new SimpleOnGestureListener() {
			@Override
			public boolean onDown(MotionEvent e) {
				isFling = false;
				return true;
			}

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				// TODO
				if ((e1.getX() - e2.getX()) > MIN_FLING
						&& velocityX < MAX_VELOCITYX) {
					isFling = true;
				}
				// Log.i("byz", MAX_VELOCITYX + ", velocityX = " + velocityX);
				return super.onFling(e1, e2, velocityX, velocityY);
			}
		};
		mGestureDetector = new GestureDetectorCompat(getContext(),
				mGestureListener);

		// mScroller = ScrollerCompat.create(getContext(), new
		// BounceInterpolator());
		if (mCloseInterpolator != null) {
			mCloseScroller = ScrollerCompat.create(getContext(),
					mCloseInterpolator);
		} else {
			mCloseScroller = ScrollerCompat.create(getContext());
		}
		if (mOpenInterpolator != null) {
			mOpenScroller = ScrollerCompat.create(getContext(),
					mOpenInterpolator);
		} else {
			mOpenScroller = ScrollerCompat.create(getContext());
		}

		LayoutParams contentParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mContentView.setLayoutParams(contentParams);
		
		if (mContentView.getId() < 1) {
			mContentView.setId(CONTENT_VIEW_ID);
		}

		mMenuView.setId(MENU_VIEW_ID);
		mMenuView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		addView(mMenuView);
		addView(mContentView);
		
		 if (mContentView.getBackground() == null) {
			 mContentView.setBackgroundDrawable(getResources().getDrawable(R.drawable.list_item));//先设置默认颜色免得没设置则为
		 }

		// in android 2.x, MenuView height is MATCH_PARENT is not work.
		// getViewTreeObserver().addOnGlobalLayoutListener(
		// new OnGlobalLayoutListener() {
		// @Override
		// public void onGlobalLayout() {
		// setMenuHeight(mContentView.getHeight());
		// // getViewTreeObserver()
		// // .removeGlobalOnLayoutListener(this);
		// }
		// });

	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
	}

	public boolean onSwipe(MotionEvent event,boolean isSwipe) {
		if(!isSwipe){
			return true;
		}else{
			try {
				mGestureDetector.onTouchEvent(event);
			} catch (Exception e) {
				e.printStackTrace();
			}
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mDownX = (int) event.getX();
				isFling = false;
				break;
			case MotionEvent.ACTION_MOVE:
				// Log.i("byz", "downX = " + mDownX + ", moveX = " + event.getX());
				int dis = (int) (mDownX - event.getX());
				if (state == STATE_OPEN) {
					dis += mMenuView.getWidth();
				}
				swipe(dis,false);
				break;
			case MotionEvent.ACTION_UP:
				if (isFling || (mDownX - event.getX()) > (mMenuView.getWidth() / 2)) {
					// open
					smoothOpenMenu();
				} else {
					// close
					smoothCloseMenu();
					return false;
				}
				break;
			}
			return true;
		}
		
	}
	public boolean isOpen() {
		return state == STATE_OPEN;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

	private void swipe(int dis,boolean computeScroll) {
		int dos=dis;//菜单的滚动距离
		if (dos > mMenuView.getWidth()) {
			dos=mMenuView.getWidth();
		 }
		if (dis > mMenuView.getWidth()&&computeScroll==false) {
			dis = mMenuView.getWidth()+((dis-mMenuView.getWidth())/3);
		}
		if (dis < 0) {
			dis = 0;
			dos=0;
		}
		mContentView.layout(-dis, mContentView.getTop(),
				mContentView.getWidth() - dis, getMeasuredHeight());
//		mMenuView.layout(mContentView.getWidth() - dos, mMenuView.getTop(),
//				mContentView.getWidth() + mMenuView.getWidth() - dos,
//				mMenuView.getBottom());
	}

	@Override
	public void computeScroll() {
		if (state == STATE_OPEN) {
			if (mOpenScroller.computeScrollOffset()) {
				swipe(mOpenScroller.getCurrX(),true);
				postInvalidate();
			}
		} else {
			if (mCloseScroller.computeScrollOffset()) {
				swipe(mBaseX - mCloseScroller.getCurrX(),true);
				postInvalidate();
			}
		}
	}

	public void smoothCloseMenu() {
		if(mOnMenuSwitchStateListener!=null)mOnMenuSwitchStateListener.OnMenuSwitchState(false);
		state = STATE_CLOSE;
		mBaseX = -mContentView.getLeft();
		mCloseScroller.startScroll(0, 0, mBaseX, 0, 700);
		postInvalidate();
	}

	public void smoothOpenMenu() {
		if(mOnMenuSwitchStateListener!=null)mOnMenuSwitchStateListener.OnMenuSwitchState(true);
		state = STATE_OPEN;	
		if((Math.abs(mContentView.getLeft())-mMenuView.getWidth())>0){//已经完全打开，超过菜单需要反弹回去
			mOpenScroller.startScroll(-mContentView.getLeft(), 0,
					-(Math.abs(mContentView.getLeft())-mMenuView.getWidth()), 0, 1000);
		}
		else{
			mOpenScroller.startScroll(-mContentView.getLeft(), 0,
					mMenuView.getWidth()-(Math.abs(mContentView.getLeft())), 0, 1000);
		}
		postInvalidate();
	}

	public void closeMenu() {
		if (mCloseScroller.computeScrollOffset()) {
			mCloseScroller.abortAnimation();
		}
		if (state == STATE_OPEN) {
			state = STATE_CLOSE;
			swipe(0,false);
		}
	}

	public void openMenu() {
		if (state == STATE_CLOSE) {
			state = STATE_OPEN;
			swipe(mMenuView.getWidth(),false);
		}
	}

	public View getContentView() {
		return mContentView;
	}

	public SwipeMenuView getMenuView() {
		return mMenuView;
	}

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getContext().getResources().getDisplayMetrics());
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mMenuView.measure(MeasureSpec.makeMeasureSpec(0,
				MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(
				getMeasuredHeight(), MeasureSpec.EXACTLY));
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		mContentView.layout(0, 0, getMeasuredWidth(),
				mContentView.getMeasuredHeight());
		mMenuView.layout(getMeasuredWidth()- mMenuView.getMeasuredWidth(), 0,
				getMeasuredWidth() ,
				mContentView.getMeasuredHeight());
		// setMenuHeight(mContentView.getMeasuredHeight());
		// bringChildToFront(mContentView);
	}

	public void setMenuHeight(int measuredHeight) {
		Log.i("byz", "pos = " + position + ", height = " + measuredHeight);
		LayoutParams params = (LayoutParams) mMenuView.getLayoutParams();
		if (params.height != measuredHeight) {
			params.height = measuredHeight;
			mMenuView.setLayoutParams(mMenuView.getLayoutParams());
		}
	}
	

	/**
	 * 开关状态监听
	 */
	public interface OnMenuSwitchStateListener {
		public void  OnMenuSwitchState(boolean state);
	}

	/**
	 * 开关状态监听
	 */
	public static void setOnMenuSwitchStateListener(
			OnMenuSwitchStateListener hOnMenuSwitchStateListener) {
		mOnMenuSwitchStateListener = hOnMenuSwitchStateListener;
	}
	public  static OnMenuSwitchStateListener mOnMenuSwitchStateListener;
}
