package com.example.mylistview.pullListView;

import com.example.mylistview.R;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
@SuppressWarnings("ResourceType")
/**
 * 如果不需要下拉刷新直接在canPullDown中返回false，这里的自动加载和下拉刷新没有冲突，通过增加在尾部的footerview实现自动加载，
 * 所以在使用中不要再动footerview了
 * 更多详解见博客http://blog.csdn.net/zhongkejingwang/article/details/38963177
 * @author chenjing
 * 
 */
public class PullableListView extends ListView implements Pullable
{
	public static final int INIT = 0;
	public static final int LOADING = 1;
	public static final int NO_MORE_DATA = 2;
	private OnLoadListener mOnLoadListener;
	private View mLoadmoreView;
	private RelativeLayout loadingRelative;
	private ImageView mLoadingView,loading_icon1;
	private TextView mStateTextView;
	public int state = INIT;
	private boolean canLoad = true;
	private boolean autoLoad = true;
	private boolean hasMoreData = true;
	private AnimationDrawable mLoadAnim;
	/**【自己】判断是否可以下来，false不管咋样都不可以，true还有根据条件判断是否可以下来*/
	private boolean isPull=true;

	public PullableListView(Context context)
	{
		super(context);
		init(context);
	}

	public PullableListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init(context);
	}

	public PullableListView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context)
	{
		mLoadmoreView = LayoutInflater.from(context).inflate(R.layout.load_more,
				null);
		loadingRelative = (RelativeLayout) mLoadmoreView.findViewById(R.id.loading_Relative);
		mLoadingView = (ImageView) mLoadmoreView.findViewById(R.id.loading_icon);
		loading_icon1= (ImageView) mLoadmoreView.findViewById(R.id.loading_icon1);
		mLoadingView.setBackgroundResource(R.anim.loading_anim);
		mLoadAnim = (AnimationDrawable) mLoadingView.getBackground();
		mStateTextView = (TextView) mLoadmoreView.findViewById(R.id.loadstate_tv);
		mLoadmoreView.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				//点击加载
				if(state != LOADING && hasMoreData){
					load();
				}
			}
		});
		addFooterView(mLoadmoreView, null, false);
	}
	public void hideLoad(){
		removeFooterView(mLoadmoreView);
	}
	public void showLoad(){
		addFooterView(mLoadmoreView, null, false);
	}
	/**【自己】关闭下啦*/
    public void hideRefresh(){
    	this.isPull=false;
    }
	
	public void hideStateTextView(boolean isHide){
		if(isHide){
			mStateTextView.setVisibility(View.GONE);
		}else{
			mStateTextView.setVisibility(View.VISIBLE);
		}
		
		
	}
	public void setMargin(){
		loading_icon1.setVisibility(View.INVISIBLE); 
	}
	public void setBackgroundLoadingRelative(int id){
		loadingRelative.setBackgroundColor(getResources().getColor(id));
	}
	public void setTextColorStateTextView(int id){
		mStateTextView.setTextColor(getResources().getColor(id));
	}
	public void setTextStateTextView(CharSequence  stt){
		mStateTextView.setText(stt);
	}
	/**
	 * 是否开启自动加载
	 * @param enable true启用，false禁用
	 */
	public void enableAutoLoad(boolean enable){
		autoLoad = enable;
	}
	
	/**
	 * 是否显示加载更多
	 * @param v true显示，false不显示
	 */
	public void setLoadmoreVisible(boolean v){
		if(v)
			{
			if (getFooterViewsCount() == 0) {
				addFooterView(mLoadmoreView, null, false);
			}
			}
		else {
			removeFooterView(mLoadmoreView);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		switch (ev.getActionMasked())
		{
		case MotionEvent.ACTION_DOWN:
			// 按下的时候禁止自动加载
			canLoad = false;
			break;
		case MotionEvent.ACTION_UP:
			// 松开手判断是否自动加载
			canLoad = true;
			checkLoad();
			break;
		}
		return super.onTouchEvent(ev);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt)
	{
		super.onScrollChanged(l, t, oldl, oldt);
		// 在滚动中判断是否满足自动加载条件
		checkLoad();
	}

	/**
	 * 判断是否满足自动加载条件
	 */
	private void checkLoad()
	{
		if (reachBottom() && mOnLoadListener != null && state != LOADING
				&& canLoad && autoLoad && hasMoreData)
			load();
	}
	
	public void load(){
		changeState(LOADING);
		mOnLoadListener.onLoad(this);
	}

	private void changeState(int state)
	{
		this.state = state;
		switch (state)
		{
		case INIT:
			mLoadAnim.stop();
			mLoadingView.setVisibility(View.INVISIBLE);
			mStateTextView.setText(R.string.more);
			break;

		case LOADING:
			mLoadingView.setVisibility(View.VISIBLE);
			mLoadAnim.start();
			mStateTextView.setText(R.string.loading);
			break;
			
		case NO_MORE_DATA:
			mLoadAnim.stop();
			mLoadingView.setVisibility(View.INVISIBLE);
			mStateTextView.setText("没有更多的数据了");
			break;
		}
	}

	/**
	 * 完成加载
	 */
	public void finishLoading()
	{
		changeState(INIT);
	}

	@Override
	public boolean canPullDown()
	{   /**【自己】关闭下啦*/
		if(!isPull){
			return false;
		}
		if (getCount() == 0)
		{
			// 没有item的时候也可以下拉刷新
			return true;
		} else if (getFirstVisiblePosition() == 0
				&& getChildAt(0)!=null&&getChildAt(0).getTop() >= 0)
		{
			// 滑到ListView的顶部了
			return true;
		} else
			return false;
	}

	public void setOnLoadListener(OnLoadListener listener)
	{
		this.mOnLoadListener = listener;
	}

	/**
	 * @return footerview可见时返回true，否则返回false
	 */
	private boolean reachBottom()
	{
		if (getCount() == 0)
		{
			return true;
		} else if (getLastVisiblePosition() == (getCount() - 1))
		{
			// 滑到底部，且頂部不是第0个，也就是说item数超过一屏后才能自动加载，否则只能点击加载
			if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null
					&& getChildAt(
							getLastVisiblePosition()
									- getFirstVisiblePosition()).getTop() < getMeasuredHeight() && !canPullDown())
				return true;
		}
		return false;
	}

	public boolean isHasMoreData() {
		return hasMoreData;
	}

	public void setHasMoreData(boolean hasMoreData){
      this.hasMoreData = hasMoreData;
		if (!hasMoreData) {
			changeState(NO_MORE_DATA);
		}else{
			changeState(INIT);
		}
		
	}

	public interface OnLoadListener
	{
		void onLoad(PullableListView pullableListView);
	}

	@Override
	public boolean canPullUp() {
		// TODO Auto-generated method stub
		return false;
	}
}
