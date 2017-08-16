package com.game.helper.view.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
/**
 * @Description  取代ListView的LinearLayout，使之能够成功嵌套在ScrollView中
 * @Path com.game.helper.view.widget.LinearLayoutForListView.java
 * @Author lbb
 * @Date 2016年10月28日 下午4:25:22
 * @Company 
 */	

public class LinearLayoutForListView extends LinearLayout {
    private BaseAdapter adapter;
    private OnClickListener onClickListener = null;
    /**
     * 绑定布局
     */
    public void bindLinearLayout() {
        int count = adapter.getCount();
        this.removeAllViews();
        for (int i = 0; i < count; i++) {
            View v = adapter.getView(i, null, null);
            v.setOnClickListener(this.onClickListener);
            addView(v, i);
        }
       Log.v("countTAG", "" + count);
    }
    @SuppressLint("NewApi")
	public LinearLayoutForListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}
	public LinearLayoutForListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}
	public LinearLayoutForListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public LinearLayoutForListView(Context context) {
        super(context);
       }
 	}