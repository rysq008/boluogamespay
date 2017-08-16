package com.game.helper.activity.mine;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.db.manager.DBManager;
import com.game.helper.draggridview.DragAdapter;
import com.game.helper.draggridview.DragAdapter.DeleteListener;
import com.game.helper.draggridview.DragGridView;
import com.game.helper.draggridview.DragGridView.OnChanageListener;
import com.game.helper.model.mine.PersonaImg;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.AddUserIconTask;
import com.game.helper.net.task.FileUpDoaldTask;
import com.game.helper.net.task.QueryUserIconTask;
import com.game.helper.pic.AlbumActivity;
import com.game.helper.pic.Bimp;
import com.game.helper.pic.FileUtils;
import com.game.helper.pic.ImageItem;
import com.game.helper.sdk.model.returns.AddUserIcon;
import com.game.helper.sdk.model.returns.FileUpDoald;
import com.game.helper.sdk.model.returns.Images;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.model.returns.QueryUserIcon;
import com.game.helper.util.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 个人头像/图片
 * @Path com.game.helper.activity.mine.MineDataEditingActivity.java
 * @Author lbb
 * @Date 2016年8月18日 下午5:29:28
 * @Company
 */
public class MineDataEditingPicActivity extends BaseActivity implements DeleteListener {

    public static final int TO_SELECT_PHOTO = 3;

    @BindView(R.id.iv_icon)
    ImageView iv_icon;
    @BindView(R.id.photo_gridview)
    DragGridView photo_gridview;//个人照片
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_left_layout)
    LinearLayout topLeftLayout;
    @BindView(R.id.top_right)
    TextView topRight;
    @BindView(R.id.top_iv_right)
    ImageView topIvRight;
    @BindView(R.id.tv_set)
    TextView tvSet;
    @BindView(R.id.isDownload1)
    View isDownload1;
    @BindView(R.id.top_liner_right)
    RelativeLayout topLinerRight;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.mRelativeLayout)
    RelativeLayout mRelativeLayout;
    private View parentView;
    DragAdapter mDragAdapter;
    private ArrayList<PersonaImg> mList = new ArrayList<PersonaImg>();
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int REQUEST_CAPTURE = 2;
    private static final int REQUEST_PICTURE = 5;
    private static final int REVERSAL_LEFT = 3;
    private static final int REVERSAL_UP = 4;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {

            } else if (msg.what == 2) {

                final Images data = (Images) msg.obj;
                new AddUserIconTask(mContext, user.userId, data.srcFilePath,
                        data.thumbFilePath, new Back() {

                    @Override
                    public void success(Object object, String msg) {
                        if (object != null && object instanceof AddUserIcon) {
                            AddUserIcon mAddUserIcon = (AddUserIcon) object;
                            if (mAddUserIcon.data != null) {
                                try {
                                    mAddUserIcon.data.smallBitmap = data.bitmap;
                                    mList.add(mAddUserIcon.data);
                                    mDragAdapter.setList(mList);
                                } catch (Exception e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }

                    }

                    @Override
                    public void fail(String status, String msg, Object object) {
                        ToastUtil.showToast(mContext, "图片上传失败 " + msg);

                    }
                }).start();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        parentView = getLayoutInflater().inflate(R.layout.activity_mine_dataeditingpic, null);
        setContentView(parentView);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setD();
        topTitle.setText("照片墙");
        topLeftBack.setVisibility(View.VISIBLE);
        topRight.setText("保存");
        topRight.setVisibility(View.GONE);
        user = DBManager.getInstance(mContext).getUserMessage();

        iv_icon.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mList.size() < 8) {
                    /*startActivityForResult(ChoosePicActivity.class, null,
							MineDataEditingActivity.TO_SELECT_PHOTO);*/
                    showPop();
                }
            }
        });
        mList.clear();
        photo_gridview.setNumColumns(3);
        mDragAdapter = new DragAdapter(this, mList);
        mDragAdapter.setmDeleteListener(this);
        photo_gridview.setAdapter(mDragAdapter);
        photo_gridview.setOnChangeListener(new OnChanageListener() {

            @Override
            public void onChange(int from, int to) {
            }
        });
    }

    @Override
    protected void initData() {
        new QueryUserIconTask(mContext, true, user.userId, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof QueryUserIcon) {
                    QueryUserIcon mQueryUserIcon = (QueryUserIcon) object;
                    if (mQueryUserIcon.data != null) {
                        try {
                            mList.clear();
                            mList.addAll(mQueryUserIcon.data);
                            mDragAdapter.setList(mList);
                            Collections.sort(mList);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            }

            @Override
            public void fail(String status, String msg, Object object) {
                // TODO Auto-generated method stub

            }
        }).start();

    }

    private PopupWindow pop = null;
    private LinearLayout ll_popup;

    public void setD() {

        pop = new PopupWindow(MineDataEditingPicActivity.this);

        View view = getLayoutInflater().inflate(R.layout.item_popupwindows, null);

        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);

        pop.setWidth(LayoutParams.MATCH_PARENT);
        pop.setHeight(LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);

        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        Button bt1 = (Button) view
                .findViewById(R.id.item_popupwindows_camera);
        Button bt2 = (Button) view
                .findViewById(R.id.item_popupwindows_Photo);
        Button bt3 = (Button) view
                .findViewById(R.id.item_popupwindows_cancel);
        parent.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                photo();
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MineDataEditingPicActivity.this,
                        AlbumActivity.class);
                startActivityForResult(intent, MineDataEditingActivity.TO_SELECT_PHOTO);
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt3.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
    }
    @Override
    @OnClick({R.id.top_left_layout})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;

            default:
                super.onClick(v);
                break;
        }
    }

    //String picPaths ;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (resultCode == Activity.RESULT_OK && requestCode == MineDataEditingActivity.TO_SELECT_PHOTO) {
                LoginData user = DBManager.getInstance(mContext).getUserMessage();
                for (int i = 0; i < Bimp.tempSelectBitmap.size(); i++) {
                    final int a = i;
                    new FileUpDoaldTask(mContext, user.phone, Bimp.tempSelectBitmap.get(a).imagePath, "1", new Back() {

                        @Override
                        public void success(Object object, String msg) {
                            if (object != null && object instanceof FileUpDoald) {
                                final FileUpDoald mFileUpDoald = (FileUpDoald) object;
                                if (mFileUpDoald != null && mFileUpDoald.data != null) {
                                    if (!TextUtils.isEmpty(mFileUpDoald.data.srcFilePath)) {
                                        Log.e("lbb", "------FileUpDoaldTask-----32-");
                                        //imagesList.add(mFileUpDoald.data);
                                        mFileUpDoald.data.bitmap = Bimp.tempSelectBitmap.get(a).getBitmap();
                                        Message ms = new Message();
                                        ms.what = 2;
                                        ms.obj = mFileUpDoald.data;
                                        handler.sendMessage(ms);
                                    } else {
                                        ToastUtil.showToast(mContext, "图片上传失败");
                                    }
                                } else {
                                    ToastUtil.showToast(mContext, "图片上传失败");
                                }
                            } else {
                                ToastUtil.showToast(mContext, "图片上传失败");
                            }


                        }

                        @Override
                        public void fail(String status, String msg, Object object) {
                            ToastUtil.showToast(mContext, "图片上传失败 " + msg);

                        }
                    }).start();
                }
            } else if (resultCode == Activity.RESULT_OK && requestCode == TAKE_PICTURE) {
                if ((Bimp.cur + Bimp.tempSelectBitmap.size()) < BaseApplication.mInstance.num) {
                    String fileName = String.valueOf(System.currentTimeMillis());
                    Bitmap bm = (Bitmap) data.getExtras().get("data");
                    FileUtils.saveBitmap(bm, fileName);

                    ImageItem takePhoto = new ImageItem();
                    takePhoto.setBitmap(bm);
                    takePhoto.setImagePath(FileUtils.SDPATH + "/" + fileName + ".JPEG");
                    Bimp.tempSelectBitmap.add(takePhoto);
                    LoginData user = DBManager.getInstance(mContext).getUserMessage();
                    for (int i = 0; i < Bimp.tempSelectBitmap.size(); i++) {
                        final int a = i;
                        new FileUpDoaldTask(mContext, user.phone, Bimp.tempSelectBitmap.get(a).imagePath, "1", new Back() {

                            @Override
                            public void success(Object object, String msg) {
                                if (object != null && object instanceof FileUpDoald) {
                                    final FileUpDoald mFileUpDoald = (FileUpDoald) object;
                                    if (mFileUpDoald != null && mFileUpDoald.data != null) {
                                        if (!TextUtils.isEmpty(mFileUpDoald.data.srcFilePath)) {
                                            Log.e("lbb", "------FileUpDoaldTask-----32-");
                                            //imagesList.add(mFileUpDoald.data);
                                            mFileUpDoald.data.bitmap = Bimp.tempSelectBitmap.get(a).getBitmap();
                                            Message ms = new Message();
                                            ms.what = 2;
                                            ms.obj = mFileUpDoald.data;
                                            handler.sendMessage(ms);
                                        } else {
                                            ToastUtil.showToast(mContext, "图片上传失败");
                                        }
                                    } else {
                                        ToastUtil.showToast(mContext, "图片上传失败");
                                    }
                                } else {
                                    ToastUtil.showToast(mContext, "图片上传失败");
                                }


                            }

                            @Override
                            public void fail(String status, String msg, Object object) {
                                ToastUtil.showToast(mContext, "图片上传失败 " + msg);

                            }
                        }).start();
                    }
                }
            } else if (resultCode == Activity.RESULT_OK && requestCode == TO_SELECT_PHOTO) {/*
			picPaths = data.getStringExtra(ChoosePicActivity.KEY_PHOTO_PATH);
			final LoginData user=DBManager.getInstance(mContext).getUserMessage();
			new FileUpDoaldTask(mContext,user.phone, picPaths,"1",new Back() {

				@Override
				public void success(Object object, String msg) {
					if(object!=null && object instanceof FileUpDoald){
						final FileUpDoald mFileUpDoald=(FileUpDoald)object;
						if(mFileUpDoald!=null&& mFileUpDoald.data!=null){
							if(!TextUtils.isEmpty(mFileUpDoald.data.srcFilePath)){
								Log.e("lbb", "------FileUpDoaldTask-----32-");
								//imagesList.add(mFileUpDoald.data);
								Message ms=new Message();
								ms.what=2;
								ms.obj=mFileUpDoald.data;
								handler.sendMessage(ms);
							}else{
								ToastUtil.showToast(mContext, "图片上传失败");
							}
						}else{
							ToastUtil.showToast(mContext, "图片上传失败");
						}
					}else{
						ToastUtil.showToast(mContext, "图片上传失败");
					}


				}

				@Override
				public void fail(String status, String msg, Object object) {
					ToastUtil.showToast(mContext, "图片上传失败 "+msg);

				}
			}).start();

			 */
            }
            super.onActivityResult(requestCode, resultCode, data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish1();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void delete(final int pos) {
        Log.e("lbb", "---------delete---pos-" + pos);
        return;


    }

    LoginData user;

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("MineDataEditingPicActivity");
        super.onResume();

    }

    private static final int TAKE_PICTURE = 0x000001;

    public void photo() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    public void showPop() {
        BaseApplication.mInstance.num = 8;
        Bimp.cur = mList.size();
        Bimp.tempSelectBitmap.clear();
        ll_popup.startAnimation(AnimationUtils.loadAnimation(MineDataEditingPicActivity.this, R.anim.activity_translate_in));
        pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
    }

    @Override
    protected void onPause() {
        MobclickAgent.onPageEnd("MineDataEditingPicActivity");
        super.onPause();
    }

}
