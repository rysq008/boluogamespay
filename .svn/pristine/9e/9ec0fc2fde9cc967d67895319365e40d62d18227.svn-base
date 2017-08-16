package com.game.helper.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.interfaces.listener.OnGetPhotoListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;
/**
 * @Description
 * @Path com.game.helper.activity.ChoosePicActivity.java
 * @Author lbb
 * @Date 2016年9月1日 上午11:16:33
 * @Company 
 */
public class ChoosePicActivity extends BaseActivity{
	/*
	 * 调取相册和照相 
	 */
	private static final int TAKE_PICTURE = 0;// 拍照的请求码 
	private static final int CHOOSE_PICTURE = 1;// 相册的请求码
	private static final int CROP_PICTURE = 3;//裁剪的请求码
	private RelativeLayout dialogLayout;
	private RelativeLayout takePhotoBtn, pickPhotoBtn;
	private static final int SCALE = 5;// 照片缩小比例
	private Intent lastIntent;
	private static Uri imageUri = null;
	/** 获取到的图片路径 */
	private String picPath;
	/**
	 * 从Intent获取图片路径的KEY
	 */
	public static final String KEY_PHOTO_PATH = "photo_path";  
	/**
	 * 从Intent获取图片btmap
	 */
	public static final String KEY_PHOTO_BTMAP = "photo_btmap";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.choose_pic_layout);
		super.onCreate(savedInstanceState);
		initViews();
	}

	/**
	 * 初始化加载View
	 */
	private void initViews() {
		dialogLayout = (RelativeLayout) findViewById(R.id.dialog_layout);
		dialogLayout.setOnClickListener(this);
		takePhotoBtn = (RelativeLayout) findViewById(R.id.rl_take_photo);
		takePhotoBtn.setOnClickListener(this);
		pickPhotoBtn = (RelativeLayout) findViewById(R.id.rl_pick_photo);
		pickPhotoBtn.setOnClickListener(this);

		lastIntent = new Intent();  
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.rl_pick_photo) {
			doPickPhotoFromGallery();// 从相册中去获取
		} else if (v.getId() == R.id.rl_take_photo) {  
			String status = Environment
					.getExternalStorageState();
			if (status
					.equals(Environment.MEDIA_MOUNTED)) {// 判断是否有SD卡
				doTakePhoto();// 用户点击了从照相机获取
			}
		} else {// 取消
			finish();
		}

	}

	/**
	 * 用来标识请求照相功能的请求码
	 */
	protected static final int CAMERA_WITH_DATA = 3023;

	/**
	 * 用来标识请求gallery的请求码
	 */
	protected static final int PHOTO_PICKED_WITH_DATA = 3021;

	/**
	 * 拍照的照片存储位置
	 */
	public static final File PHOTO_DIR = new File(
			Environment.getExternalStorageDirectory() + "/DCIM/Camera");

	/**
	 * 照相机拍照得到的图片
	 */
	protected File mCurrentPhotoFile;
	/**
	 * 当前加载的图片地址
	 */
	protected Uri curPhotoPath;
	/**
	 * 图片获取监听
	 */
	private OnGetPhotoListener l;

	// 请求Gallery程序
	protected void doPickPhotoFromGallery() {
		try {
			// Launch picker to choose photo for selected contact
			final Intent intent = getPhotoPickIntent();
			startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
		} catch (ActivityNotFoundException e) {
		}    
	}

	/**
	 * 拍照获取图片
	 * 
	 */
	protected void doTakePhoto() {
		try {
			// Launch camera to take photo for selected contact
			PHOTO_DIR.mkdirs();// 创建照片的存储目录
			mCurrentPhotoFile = new File(PHOTO_DIR, getPhotoFileName());// 给新照的照片文件命名
			final Intent intent = getTakePickIntent(mCurrentPhotoFile);
			startActivityForResult(intent, CAMERA_WITH_DATA);
		} catch (ActivityNotFoundException e) {
		}
	}

	/**
	 * 用当前时间给取得的图片命名
	 * 
	 */
	public static String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return "IMG_yyyyMMdd_HHmmss"+ ".jpg";
	}
	/**
	 * 用当前时间给取得的图片命名
	 * 
	 */
	/*private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}*/

	public Intent getTakePickIntent(File f) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
		return intent;
	}

	// 封装请求Gallery的intent
	public Intent getPhotoPickIntent() {
		
		
		Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		intent.addCategory(Intent.CATEGORY_OPENABLE); 
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);  
		intent.putExtra("crop", "true");
		//intent.putExtra("aspectX", 2);
	//	intent.putExtra("aspectY", 2);
		intent.putExtra("outputX", 800);
		intent.putExtra("outputY", 800);
		intent.putExtra("return-data", false);
		intent.putExtra(
				"output",
				curPhotoPath = Uri.fromFile(new File(PHOTO_DIR,
						getPhotoFileName())));
		return intent;
	}

	protected void doCropPhoto(File f) {  
		try {
			// 启动gallery去剪辑这个照片
			final Intent intent = getCropImageIntent(Uri.fromFile(f));
			startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
		} catch (Exception e) {
			Log.e("", "");
		}
	}

	/**
	 * Constructs an intent for image cropping. 调用图片剪辑程序
	 */
	public Intent getCropImageIntent(Uri photoUri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(photoUri, "image/*");
		intent.putExtra("crop", "true");
		//intent.putExtra("aspectX", 1);
		//intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 800);
		intent.putExtra("outputY", 800);
		intent.putExtra("return-data", false);
		intent.putExtra(
				"output",
				curPhotoPath = Uri.fromFile(new File(PHOTO_DIR,
						getPhotoFileName())));
		return intent;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK)
			return;
		switch (requestCode) {
		case PHOTO_PICKED_WITH_DATA: // 调用Gallery返回的
			String path = curPhotoPath.getPath();
			if (TextUtils.isEmpty(path)) {
				finish();
				return;  
			}
			lastIntent.putExtra(KEY_PHOTO_PATH, path);
			setResult(Activity.RESULT_OK, lastIntent);  
			finish();

			break;
		case CAMERA_WITH_DATA: {// 照相机程序返回的,再次调用图片剪辑程序去修剪图片
			doCropPhoto(mCurrentPhotoFile);
			break;
		}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void setOnGetPhotoListener(OnGetPhotoListener l) {
		this.l = l;
	}
}
