package com.game.helper.services.ui;



import com.game.helper.R;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 */
public class MyUpdateDailog extends Dialog {
	public LinearLayout layout_dailog;
	private boolean cancelable = true;
    private Context context;
    private boolean isFinish=false;
    private  MyProgressBar mMyProgressBar;
	public MyUpdateDailog(Context context) {
		super(context, R.style.Dialog_bocop);
		this.context=context;
		init();
	}

	private void init() {
		View contentView = View.inflate(getContext(), R.layout.update_progress,
				null);
		setContentView(contentView);
		mMyProgressBar = (MyProgressBar) findViewById(R.id.pb_update_progress);
		layout_dailog = (LinearLayout) findViewById(R.id.layout_dailog);
		layout_dailog.setVisibility(View.GONE);
		this.setCancelable(false);
		contentView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (cancelable) {
					dismiss();
				}
			}
		});
	}

	@Override
	public void show() {
		super.show();
		Animation a = AnimationUtils.loadAnimation(context, R.anim.dialog_in);
		layout_dailog.startAnimation(a);
		layout_dailog.setVisibility(View.VISIBLE);
		isFinish=false;
	}

	@SuppressLint("NewApi")
	@Override
	public void dismiss() {
		if(isFinish)
		super.dismiss();
		Animation a = AnimationUtils.loadAnimation(context, R.anim.dialog_out);
		layout_dailog.startAnimation(a);
		a.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation anim) {}
			
			@Override
			public void onAnimationRepeat(Animation anim) {}	
			@Override
			public void onAnimationEnd(Animation anim) {
				isFinish=true;
				dismiss();
			}
		});

	}

	@Override
	public void setCancelable(boolean flag) {
		cancelable = flag;
		super.setCancelable(flag);
	}
	public MyProgressBar getProgressBar(){
		return mMyProgressBar;
	}
}
