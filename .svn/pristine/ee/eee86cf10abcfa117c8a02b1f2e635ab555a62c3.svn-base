package com.game.helper.activity.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.activity.ChoosePicActivity;
import com.game.helper.activity.mine.MineDataEditingActivity;
import com.game.helper.adapter.home.PhotoStrategyAdapter;
import com.game.helper.adapter.home.PhotoStrategyAdapter.DeleteListener1;
import com.game.helper.db.manager.DBManager;
import com.game.helper.model.mine.PersonaImg;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.FileUpDoaldTask;
import com.game.helper.net.task.SaveGameCutTask;
import com.game.helper.pic.AlbumActivity;
import com.game.helper.pic.Bimp;
import com.game.helper.pic.FileUtils;
import com.game.helper.pic.ImageItem;
import com.game.helper.sdk.model.returns.FileUpDoald;
import com.game.helper.sdk.model.returns.Images;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.util.ToastUtil;
import com.game.helper.view.img.ImageCompression;
import com.game.helper.view.widget.MyScrollviewGridView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 发布攻略
 * @Path com.game.helper.activity.home.ReleaseStrategyActivity.java
 * @Author lbb
 * @Date 2016年8月24日 下午7:32:39
 * @Company
 */
public class ReleaseStrategyActivity extends BaseActivity implements DeleteListener1 {
    @BindView(R.id.et_title)
    EditText et_title;
    @BindView(R.id.et_context)
    EditText et_context;
    @BindView(R.id.photo_gridview)
    MyScrollviewGridView photo_gridview;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_right)
    TextView topRight;
    @BindView(R.id.top_title)
    TextView topTitle;
    private ArrayList<PersonaImg> mList = new ArrayList<PersonaImg>();
    private ArrayList<PersonaImg> mList1 = new ArrayList<PersonaImg>();
    PhotoStrategyAdapter mPhotoStrategyAdapter;
    @BindView(R.id.msg100)
    TextView msg100;

    String gameId;
    LoginData user;
    List<Images> imagesList = new ArrayList<Images>();
    private View parentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        parentView = getLayoutInflater().inflate(R.layout.activity_home_release_strategy, null);
        setContentView(parentView);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        msg100.setText("提示：为确保每个玩家发布的攻略的质量，您发布的攻略将要提交审核，审核通过将获得" + BaseApplication.mInstance.publishNum + "金币作为奖励，感谢您的贡献！");
        setD();
        topTitle.setText("发布攻略");
        topLeftBack.setVisibility(View.VISIBLE);
        topRight.setVisibility(View.VISIBLE);
        topRight.setText("提交");
        gameId = getIntent().getStringExtra("gameId");
        user = DBManager.getInstance(mContext).getUserMessage();
    }

    @Override
    protected void initData() {
        mList1.clear();
        PersonaImg mPersonaImg = new PersonaImg();
        mPersonaImg.id = mList1.size();
        //mPersonaImg.index=mList1.size();
        mList1.add(mPersonaImg);
        mPhotoStrategyAdapter = new PhotoStrategyAdapter(this, mList1, 5);
        mPhotoStrategyAdapter.setmDeleteListener(this);
        photo_gridview.setAdapter(mPhotoStrategyAdapter);
    }

    private PopupWindow pop = null;
    private LinearLayout ll_popup;

    public void setD() {

        pop = new PopupWindow(ReleaseStrategyActivity.this);

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
                Intent intent = new Intent(ReleaseStrategyActivity.this,
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
    @OnClick({R.id.top_left_layout, R.id.top_right})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            case R.id.top_right:
                //提交
                if (topRight.isEnabled()) {
                    topRight.setEnabled(false);
                    String title = et_title.getText().toString();
                    String context = et_context.getText().toString();
                    if (TextUtils.isEmpty(title)) {
                        topRight.setEnabled(true);
                        ToastUtil.showToast(mContext, "请输入您的攻略标题");
                    } else if (TextUtils.isEmpty(context)) {
                        topRight.setEnabled(true);
                        ToastUtil.showToast(mContext, "请输入您的攻略内容");
                    } else {
                        String img1 = null, img2 = null, img3 = null, img4 = null, img5 = null;
                        for (int i = 0; i < imagesList.size(); i++) {
                            if (i == 0) {
                                img1 = imagesList.get(i).srcFilePath;
                            } else if (i == 1) {
                                img2 = imagesList.get(i).srcFilePath;
                            } else if (i == 2) {
                                img3 = imagesList.get(i).srcFilePath;
                            } else if (i == 3) {
                                img4 = imagesList.get(i).srcFilePath;
                            } else if (i == 4) {
                                img5 = imagesList.get(i).srcFilePath;
                            }
                        }
                        new SaveGameCutTask(mContext, user.userId, gameId, title, context,
                                img1, img2, img3, img4, img5, new Back() {

                            @Override
                            public void success(Object object, String msg) {
                                ToastUtil.showToast(mContext, "发布成功");
                                topRight.setEnabled(true);
                                finish1();
                            }

                            @Override
                            public void fail(String status, String msg, Object object) {
                                topRight.setEnabled(true);
                                ToastUtil.showToast(mContext, msg);
                            }
                        }).start();
                    }

                }
                break;
            default:
                super.onClick(v);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == MineDataEditingActivity.TO_SELECT_PHOTO) {
            for (int i = 0; i < Bimp.tempSelectBitmap.size(); i++) {
                mList1.clear();
                new FileUpDoaldTask(mContext, user.phone, Bimp.tempSelectBitmap.get(i).imagePath, "1", new Back() {

                    @Override
                    public void success(Object object, String msg) {
                        if (object != null && object instanceof FileUpDoald) {
                            FileUpDoald mFileUpDoald = (FileUpDoald) object;
                            if (mFileUpDoald != null && mFileUpDoald.data != null) {
                                if (!TextUtils.isEmpty(mFileUpDoald.data.srcFilePath)) {
                                    Log.e("lbb", "------FileUpDoaldTask---ReleaseStrategyActivity---");
                                    imagesList.add(mFileUpDoald.data);
                                } else {
                                    imagesList.add(new Images());
                                }
                            } else {
                                imagesList.add(new Images());
                            }
                        } else {
                            imagesList.add(new Images());
                        }

                    }

                    @Override
                    public void fail(String status, String msg, Object object) {
                        imagesList.add(new Images());

                    }
                }).start();
                Bitmap smallBitmap = Bimp.tempSelectBitmap.get(i).getBitmap();
                PersonaImg mPersonaImg = new PersonaImg();
                mPersonaImg.smallBitmap = smallBitmap;
                mList.add(mPersonaImg);
                mPersonaImg.id = mList.size();
                //	mPersonaImg.index=mList.size();
                mList1.addAll(mList);

                mPersonaImg = new PersonaImg();
                mPersonaImg.id = mList1.size();
                //	mPersonaImg.index=mList1.size();
                mList1.add(mPersonaImg);

                mPhotoStrategyAdapter.setList(mList1, mList);
                try {
                    Collections.sort(mList1);

					/*Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    Uri uri =  Uri.fromFile(new File(ChoosePicActivity.PHOTO_DIR,ChoosePicActivity.getPhotoFileName()));
					intent.setData(uri);
	                sendBroadcast(intent); */
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
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
                for (int i = 0; i < Bimp.tempSelectBitmap.size(); i++) {
                    mList1.clear();
                    new FileUpDoaldTask(mContext, user.phone, Bimp.tempSelectBitmap.get(i).imagePath, "1", new Back() {

                        @Override
                        public void success(Object object, String msg) {
                            if (object != null && object instanceof FileUpDoald) {
                                FileUpDoald mFileUpDoald = (FileUpDoald) object;
                                if (mFileUpDoald != null && mFileUpDoald.data != null) {
                                    if (!TextUtils.isEmpty(mFileUpDoald.data.srcFilePath)) {
                                        Log.e("lbb", "------FileUpDoaldTask---ReleaseStrategyActivity---");
                                        imagesList.add(mFileUpDoald.data);
                                    } else {
                                        imagesList.add(new Images());
                                    }
                                } else {
                                    imagesList.add(new Images());
                                }
                            } else {
                                imagesList.add(new Images());
                            }

                        }

                        @Override
                        public void fail(String status, String msg, Object object) {
                            imagesList.add(new Images());

                        }
                    }).start();
                    Bitmap smallBitmap = Bimp.tempSelectBitmap.get(i).getBitmap();
                    PersonaImg mPersonaImg = new PersonaImg();
                    mPersonaImg.smallBitmap = smallBitmap;
                    mList.add(mPersonaImg);
                    mPersonaImg.id = mList.size();
                    //	mPersonaImg.index=mList.size();
                    mList1.addAll(mList);

                    mPersonaImg = new PersonaImg();
                    mPersonaImg.id = mList1.size();
                    //	mPersonaImg.index=mList1.size();
                    mList1.add(mPersonaImg);

                    mPhotoStrategyAdapter.setList(mList1, mList);
                    try {
                        Collections.sort(mList1);

						/*Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
						Uri uri =  Uri.fromFile(new File(ChoosePicActivity.PHOTO_DIR,ChoosePicActivity.getPhotoFileName()));
						intent.setData(uri);
		                sendBroadcast(intent); */
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

        } else if (resultCode == Activity.RESULT_OK && requestCode == MineDataEditingActivity.TO_SELECT_PHOTO) {
            String picPath = data.getStringExtra(ChoosePicActivity.KEY_PHOTO_PATH);
            mList1.clear();
            new FileUpDoaldTask(mContext, user.phone, picPath, "1", new Back() {

                @Override
                public void success(Object object, String msg) {
                    if (object != null && object instanceof FileUpDoald) {
                        FileUpDoald mFileUpDoald = (FileUpDoald) object;
                        if (mFileUpDoald != null && mFileUpDoald.data != null) {
                            if (!TextUtils.isEmpty(mFileUpDoald.data.srcFilePath)) {
                                Log.e("lbb", "------FileUpDoaldTask---ReleaseStrategyActivity---");
                                imagesList.add(mFileUpDoald.data);
                            } else {
                                imagesList.add(new Images());
                            }
                        } else {
                            imagesList.add(new Images());
                        }
                    } else {
                        imagesList.add(new Images());
                    }


                }

                @Override
                public void fail(String status, String msg, Object object) {
                    imagesList.add(new Images());

                }
            }).start();
            Bitmap smallBitmap = ImageCompression.getSmallBitmap(picPath);
            PersonaImg mPersonaImg = new PersonaImg();
            mPersonaImg.smallBitmap = smallBitmap;
            mList.add(mPersonaImg);
            mPersonaImg.id = mList.size();
            //	mPersonaImg.index=mList.size();
            mList1.addAll(mList);

            mPersonaImg = new PersonaImg();
            mPersonaImg.id = mList1.size();
            //	mPersonaImg.index=mList1.size();
            mList1.add(mPersonaImg);

            mPhotoStrategyAdapter.setList(mList1, mList);
            try {
                Collections.sort(mList1);

				/*Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
				Uri uri =  Uri.fromFile(new File(ChoosePicActivity.PHOTO_DIR,ChoosePicActivity.getPhotoFileName()));
				intent.setData(uri);
                sendBroadcast(intent); */
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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

    /*protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
        case TAKE_PICTURE:
            if (Bimp.tempSelectBitmap.size() < 9 && resultCode == RESULT_OK) {

                String fileName = String.valueOf(System.currentTimeMillis());
                Bitmap bm = (Bitmap) data.getExtras().get("data");
                FileUtils.saveBitmap(bm, fileName);

                ImageItem takePhoto = new ImageItem();
                takePhoto.setBitmap(bm);
                Bimp.tempSelectBitmap.add(takePhoto);
            }
            break;
        }
    }*/
    private static final int TAKE_PICTURE = 0x000001;

    public void photo() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    public void showPop() {
        BaseApplication.mInstance.num = 5;
        Bimp.cur = imagesList.size();
        Bimp.tempSelectBitmap.clear();
        ll_popup.startAnimation(AnimationUtils.loadAnimation(ReleaseStrategyActivity.this, R.anim.activity_translate_in));
        pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void delete(int pos) {
        if (pos >= mList.size()) {
            return;
        }
        mList.remove(pos);

        if (pos >= imagesList.size()) {
            return;
        }
        imagesList.remove(pos);

    }

    @Override
    public void showpop() {
        showPop();

    }

    @Override
    protected void onPause() {
        MobclickAgent.onPageEnd("ReleaseStrategyActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("ReleaseStrategyActivity");
        super.onResume();
    }

}
