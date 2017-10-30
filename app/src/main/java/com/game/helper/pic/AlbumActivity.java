package com.game.helper.pic;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 这个是进入相册显示所有图片的界面
 *
 * @author king
 * @version 2014年10月18日  下午11:47:15
 * @QQ:595163260
 */
public class AlbumActivity extends BaseActivity {
    private static final int CODE_FOR_WRITE_PERMISSION = 0x1000;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;
    //显示手机里的所有图片的列表控件
    private GridView gridView;
    //当手机里没有图片时，提示用户没有图片的控件
    private TextView tv;
    //gridView的adapter
    private AlbumGridViewAdapter gridImageAdapter;
    //完成按钮
    private Button okButton;
    // 返回按钮
    //private Button back;
    // 取消按钮
    private Button cancel;
    private Intent intent;
    // 预览按钮
//	private Button preview;
    private Context mContext;
    private ArrayList<ImageItem> dataList;
    private AlbumHelper helper;
    public static List<ImageBucket> contentList;
    public static Bitmap bitmap;

    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.plugin_camera_album);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        mContext = this;
        //注册一个广播，这个广播主要是用于在GalleryActivity进行预览时，防止当所有图片都删除完后，再回到该页面时被取消选中的图片仍处于选中状态
        IntentFilter filter = new IntentFilter("data.broadcast.action");
        registerReceiver(broadcastReceiver, filter);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.plugin_camera_no_pictures);
        initss();
        initListener();
        //这个函数主要用来控制预览和完成按钮的状态
        isShowOkBt();
    }

    @Override
    protected void initView() {
        topTitle.setText("选择图片");
        topLeftBack.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {

    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            //mContext.unregisterReceiver(this);
            // TODO Auto-generated method stub
            gridImageAdapter.notifyDataSetChanged();
        }
    };

	/*// 预览按钮的监听
    private class PreviewListener implements OnClickListener {
		public void onClick(View v) {
			if (Bimp.tempSelectBitmap.size() > 0) {
				intent.putExtra("position", "1");
				intent.setClass(AlbumActivity.this, GalleryActivity.class);
				startActivity(intent);
			}
		}

	}*/

    @Override
    @OnClick({R.id.top_left_layout})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish();
                break;
            default:
                super.onClick(v);
                break;
        }
    }

    // 完成按钮的监听
    private class AlbumSendListener implements OnClickListener {
        public void onClick(View v) {
            //overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
            //intent.setClass(mContext, MainActivity.class);
            //startActivity(intent);
            setResult(Activity.RESULT_OK);
            finish();
        }

    }

    // 返回按钮监听
    /*private class BackListener implements OnClickListener {
		public void onClick(View v) {
			intent.setClass(AlbumActivity.this, ImageFile.class);
			startActivity(intent);
		}
	}*/

    // 取消按钮的监听
    private class CancelListener implements OnClickListener {
        public void onClick(View v) {
            Bimp.tempSelectBitmap.clear();
            finish();
        }
    }


    // 初始化，给一些对象赋值
    private void initss() {
        helper = AlbumHelper.getHelper();
        helper.init(getApplicationContext());

        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    CODE_FOR_WRITE_PERMISSION);
            ToastUtil.showToast(this,"打开相册访问权限！！");
            return;
        }
        contentList = helper.getImagesBucketList(false);
        dataList = new ArrayList<ImageItem>();
        for (int i = 0; i < contentList.size(); i++) {
            dataList.addAll(contentList.get(i).imageList);
        }

        //back = (Button) findViewById(R.id.back);
        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new CancelListener());
        //back.setOnClickListener(new BackListener());
        //preview = (Button) findViewById(R.id.preview);
        //preview.setOnClickListener(new PreviewListener());
        intent = getIntent();
        Bundle bundle = intent.getExtras();
        gridView = (GridView) findViewById(R.id.myGrid);
        gridImageAdapter = new AlbumGridViewAdapter(this, dataList,
                Bimp.tempSelectBitmap);
        gridView.setAdapter(gridImageAdapter);
        tv = (TextView) findViewById(R.id.myText);
        gridView.setEmptyView(tv);
        okButton = (Button) findViewById(R.id.ok_button);
        okButton.setText("完成(" + (Bimp.cur + Bimp.tempSelectBitmap.size())
                + "/" + BaseApplication.mInstance.num + ")");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        if (requestCode == CODE_FOR_WRITE_PERMISSION) {
//            if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                //用户同意使用write
//                startGetImageThread();
//            } else {
//                //用户不同意，自行处理即可
//                finish();
//            }
//        }
        if (requestCode == CODE_FOR_WRITE_PERMISSION){
            if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    &&grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //用户同意使用write
//                startGetImageThread();
                initss();
            }else{
                //用户不同意，向用户展示该权限作用
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    AlertDialog dialog = new AlertDialog.Builder(this)
                            .setMessage("该相册需要赋予访问存储的权限，不开启将无法正常工作！")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }).create();
                    dialog.show();
                    return;
                }
                finish();
            }
        }
    }

    private void initListener() {

        gridImageAdapter
                .setOnItemClickListener(new AlbumGridViewAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(final ToggleButton toggleButton,
                                            int position, boolean isChecked, Button chooseBt) {
                        if ((Bimp.cur + Bimp.tempSelectBitmap.size()) >= BaseApplication.mInstance.num) {
                            toggleButton.setChecked(false);
                            chooseBt.setVisibility(View.GONE);
                            if (!removeOneData(dataList.get(position))) {
                                Toast.makeText(AlbumActivity.this, R.string.only_choose_num,
                                        200).show();
                            }
                            return;
                        }
                        if (isChecked) {
                            chooseBt.setVisibility(View.VISIBLE);
                            Bimp.tempSelectBitmap.add(dataList.get(position));
                            okButton.setText("完成(" + (Bimp.cur + Bimp.tempSelectBitmap.size())
                                    + "/" + BaseApplication.mInstance.num + ")");
                        } else {
                            Bimp.tempSelectBitmap.remove(dataList.get(position));
                            chooseBt.setVisibility(View.GONE);
                            okButton.setText("完成(" + (Bimp.cur + Bimp.tempSelectBitmap.size()) + "/" + BaseApplication.mInstance.num + ")");
                        }
                        isShowOkBt();
                    }
                });

        okButton.setOnClickListener(new AlbumSendListener());

    }

    private boolean removeOneData(ImageItem imageItem) {
        if (Bimp.tempSelectBitmap.contains(imageItem)) {
            Bimp.tempSelectBitmap.remove(imageItem);
            okButton.setText("完成(" + (Bimp.cur + Bimp.tempSelectBitmap.size()) + "/" + BaseApplication.mInstance.num + ")");
            return true;
        }
        return false;
    }

    public void isShowOkBt() {
        okButton.setText("完成(" + (Bimp.cur + Bimp.tempSelectBitmap.size()) + "/" + BaseApplication.mInstance.num + ")");
        //	preview.setPressed(true);
        okButton.setPressed(true);
        //preview.setClickable(true);
        okButton.setClickable(true);
        //okButton.setTextColor(Color.WHITE);
        //preview.setTextColor(Color.WHITE);
		/*if (Bimp.tempSelectBitmap.size() > 0) {
			
		} else {
			okButton.setText("完成(" + Bimp.cur+Bimp.tempSelectBitmap.size() + "/"+BaseApplication.mInstance.num+")");
					//preview.setPressed(false);
			//preview.setClickable(false);
			okButton.setPressed(false);
			okButton.setClickable(false);
			//okButton.setTextColor(Color.parseColor("#E1E0DE"));
			//preview.setTextColor(Color.parseColor("#E1E0DE"));
		}*/
    }

    /*public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            intent.setClass(AlbumActivity.this, ImageFile.class);
            startActivity(intent);
        }
        return false;

    }*/
    @Override
    protected void onRestart() {
        isShowOkBt();
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

}
