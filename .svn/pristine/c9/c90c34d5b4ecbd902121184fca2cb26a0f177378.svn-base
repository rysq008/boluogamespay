package com.game.helper.activity.home;

import java.util.ArrayList;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.view.smooth.SmoothImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import butterknife.BindView;

public class SpaceImageDetailActivity extends BaseActivity {
	@BindView(R.id.mSmoothImageView) SmoothImageView mSmoothImageView;
	private ArrayList<String> mDatas;
	private int mPosition;
	private int mLocationX;
	private int mLocationY;
	private int mWidth;
	private int mHeight;
	//SmoothImageView imageView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_space_image_detail);
		super.onCreate(savedInstanceState);

		mDatas = (ArrayList<String>) getIntent().getSerializableExtra("images");
		mPosition = getIntent().getIntExtra("position", 0);
		mLocationX = getIntent().getIntExtra("locationX", 0);
		mLocationY = getIntent().getIntExtra("locationY", 0);
		mWidth = getIntent().getIntExtra("width", 0);
		mHeight = getIntent().getIntExtra("height", 0);

		//imageView = new SmoothImageView(this);
		//mSmoothImageView.setOriginalInfo(mWidth, mHeight, mLocationX, mLocationY);
		//mSmoothImageView.transformIn();
		//mSmoothImageView.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
		mSmoothImageView.setScaleType(ScaleType.FIT_XY);
		
		ImageLoader.getInstance().displayImage(mDatas.get(mPosition), mSmoothImageView);
//		imageView.setImageResource(R.drawable.temp);
		// ScaleAnimation scaleAnimation = new ScaleAnimation(0.5f, 1.0f, 0.5f,
		// 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
		// 0.5f);
		// scaleAnimation.setDuration(300);
		// scaleAnimation.setInterpolator(new AccelerateInterpolator());
		// imageView.startAnimation(scaleAnimation);

	}

	@Override
	public void onBackPressed() {
		mSmoothImageView.setOnTransformListener(new SmoothImageView.TransformListener() {
			@Override
			public void onTransformComplete(int mode) {
				if (mode == 2) {
					finish();
				}
			}
		});
		mSmoothImageView.transformOut();
	}

	@Override
	protected void onPause() {
		MobclickAgent.onPageEnd("SpaceImageDetailActivity");
		super.onPause();
		if (isFinishing()) {
			overridePendingTransition(0, 0);
		}
	}
	
	@Override
	protected void onResume() {
		MobclickAgent.onPageStart("SpaceImageDetailActivity");
		super.onResume();
	}
}
