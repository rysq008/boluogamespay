package com.game.helper.util;


import com.game.helper.R;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

@SuppressWarnings("ResourceType")
/**
 */
public class LoadingDialog extends Dialog {
	
//	private TextView tv;
	private boolean cancelable = true;
	
	private TextView tv;
	public LoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
		init(context,true);
	}
	public LoadingDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
		init(context,true);
	}
	public LoadingDialog(Context context,boolean isLoadCancel) {
		super(context, R.style.Dialog_bocop);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			Window window = getWindow();
			// Translucent status bar
			window.setFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
		init(context,isLoadCancel);
	}
	public LoadingDialog(Context context) {
		super(context, R.style.Dialog_bocop);
		init(context,true);
	}
	private void init(Context context,boolean isLoadCancel) {
		View contentView = View.inflate(context, R.layout.loading_dialog, null);
		tv=(TextView) contentView.findViewById(R.id.tv);
		setContentView(contentView);
		this.setCancelable(isLoadCancel);
		this.setCanceledOnTouchOutside(false);
//		contentView.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if (cancelable) {
//					dismiss();
//				}
//			}
//		});
//		tv = (TextView) findViewById(R.id.tv_loading);
		getWindow().setWindowAnimations(R.anim.alpha_in);
	}

	@Override
	public void show() {
		super.show();
	}
	
	@SuppressLint("NewApi")
	@Override
	public void dismiss() {
		super.dismiss();
	}
	
	public void setTextMsg(String msg){
		tv.setText(""+msg);
	}
	@Override
	public void setCancelable(boolean flag) {
		cancelable = flag;
		super.setCancelable(flag);
	}
	
//	@Override
//	public void setTitle(CharSequence title) {
//		tv.setText(title);
//	}
	
	@Override
	public void setTitle(int titleId) {
		setTitle(getContext().getString(titleId));
	}
}
