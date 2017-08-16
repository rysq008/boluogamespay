package com.game.helper.activity.community;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.activity.ChoosePicActivity;
import com.game.helper.activity.mine.MineDataEditingActivity;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.FileUpDoaldTask;
import com.game.helper.net.task.GetMainPageInfoTask;
import com.game.helper.net.task.SaveOrUpdateGuildTask;
import com.game.helper.pic.AlbumActivity;
import com.game.helper.pic.Bimp;
import com.game.helper.pic.FileUtils;
import com.game.helper.pic.ImageItem;
import com.game.helper.sdk.model.returns.FileUpDoald;
import com.game.helper.sdk.model.returns.GetMainPageInfo;
import com.game.helper.sdk.model.returns.Images;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.ToastUtil;
import com.game.helper.view.img.ImageCompression;
import com.game.helper.view.widget.CircleImageView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RedactSociatyActivity extends BaseActivity {
    public static final int TO_SELECT_PHOTO = 3;
    /*@BindView(R.id.iv_addPhoto) CircleImageView iv_addPhoto;
    @BindView(R.id.et_username) EditText et_username;
	@BindView(R.id.ed_xy) EditText ed_xy;
	@BindView(R.id.ed_jj) EditText ed_jj;*/

    String guildId;
    @BindView(R.id.top_right)
    TextView topRight;
    @BindView(R.id.iv_addPhoto)
    CircleImageView iv_addPhoto;
    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.ed_xy)
    EditText ed_xy;
    @BindView(R.id.ed_jj)
    EditText ed_jj;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;
    private View parentView;
    LoginData user;
    List<Images> imagesList = new ArrayList<Images>();

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                imagesList.add(new Images());
                topRight.setEnabled(true);
                ToastUtil.showToast(mContext, "公会头像上传失败");
            } else if (msg.what == 2) {
                String name = et_username.getText().toString();
                String declareContent = ed_xy.getText().toString();
                String abstractContent = ed_jj.getText().toString();
                new SaveOrUpdateGuildTask(mContext, user.userId, name, imagesList.get(0).srcFilePath, declareContent, abstractContent, "", new Back() {

                    @Override
                    public void success(Object object, String msg) {
                        ToastUtil.showToast(mContext, "公会修改成功");

                        new GetMainPageInfoTask(mContext, user.userId, new Back() {

                            @Override
                            public void success(Object object, String msg) {
                                if (object != null && object instanceof GetMainPageInfo) {
                                    GetMainPageInfo mQueryPtb = (GetMainPageInfo) object;
                                    if (mQueryPtb.data != null) {
                                        user.jsonData = null;
                                        user.guildId = mQueryPtb.data.guildId;
                                        user.jsonData = new JsonBuild().setModel(user).getJsonString1();
                                        DBManager.getInstance(mContext).insert(user);
                                        user = DBManager.getInstance(mContext).getUserMessage();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("guildId", user.guildId);
                                        startActivity(SociatyDetailsActivity.class, bundle);
                                        finishSingleActivityByClass(SociatyBaseActivity.class, null);
                                    }
                                }
                                topRight.setEnabled(true);
                                finish1();
                            }

                            @Override
                            public void fail(String status, String msg, Object object) {
                                topRight.setEnabled(true);
                                finish1();
                            }
                        }).start();
                    }

                    @Override
                    public void fail(String status, String msg, Object object) {
                        ToastUtil.showToast(mContext, "公会头像上传失败  " + msg);
                        topRight.setEnabled(true);
                    }
                }).start();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        parentView = getLayoutInflater().inflate(R.layout.activity_community_redact_sociaty, null);
        setContentView(parentView);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    String fileAskPath;
    String iconThumb;
    String icon, name, declareContent, abstractContent;

    @Override
    protected void initView() {
        setD();
        topTitle.setText("编辑公会信息");
        topLeftBack.setVisibility(View.VISIBLE);

        topRight.setVisibility(View.VISIBLE);
        topRight.setText("保存");
    }

    @Override
    protected void initData() {
        user = DBManager.getInstance(mContext).getUserMessage();

        guildId = getIntent().getStringExtra("guildId");
        fileAskPath = getIntent().getStringExtra("fileAskPath");
        icon = getIntent().getStringExtra("icon");
        iconThumb = getIntent().getStringExtra("iconThumb");
        name = getIntent().getStringExtra("name");
        declareContent = getIntent().getStringExtra("declareContent");
        abstractContent = getIntent().getStringExtra("abstractContent");

        if (!TextUtils.isEmpty(fileAskPath) && !TextUtils.isEmpty(icon)) {
            imagesList.clear();
            Images mImages = new Images();
            mImages.baseAcessPath = fileAskPath;
            mImages.srcFilePath = icon;
            mImages.thumbFilePath = iconThumb;
            mImages.bitmap = null;
            imagesList.add(mImages);
            Glide.with(BaseApplication.mInstance.context.getApplicationContext())
                    .load("" + fileAskPath + icon)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    //.centerCrop()// 长的一边撑满
                    //.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片
                    .error(R.drawable.preview_card_pic_loading)//加载失败时显示的图片
                    //.crossFade()
                    .into(iv_addPhoto);
        }
        if (!TextUtils.isEmpty(name)) {
            et_username.setText(name);
            et_username.setSelection(et_username.getText().length());
        }
        if (!TextUtils.isEmpty(declareContent)) {
            ed_xy.setText(declareContent);
            ed_xy.setSelection(ed_xy.getText().length());
        }
        if (!TextUtils.isEmpty(abstractContent)) {
            ed_jj.setText(abstractContent);
            ed_jj.setSelection(ed_jj.getText().length());
        }

    }

    private PopupWindow pop = null;
    private LinearLayout ll_popup;

    public void setD() {

        pop = new PopupWindow(RedactSociatyActivity.this);

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
                Intent intent = new Intent(RedactSociatyActivity.this,
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
    @OnClick({R.id.top_left_layout, R.id.top_right, R.id.iv_addPhoto})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            case R.id.iv_addPhoto:
                //公会头像
                //startActivityForResult(ChoosePicActivity.class, null,MineDataEditingActivity.TO_SELECT_PHOTO);
                showPop();
                break;
            case R.id.top_right:
                //保存
                if (topRight.isEnabled()) {
                    topRight.setEnabled(false);
                    String name = et_username.getText().toString();
                    String declareContent = ed_xy.getText().toString();
                    String abstractContent = ed_jj.getText().toString();
                    if (TextUtils.isEmpty(name)) {
                        topRight.setEnabled(true);
                        ToastUtil.showToast(mContext, "请输入公会名称");
                    } else if (TextUtils.isEmpty(declareContent)) {
                        topRight.setEnabled(true);
                        ToastUtil.showToast(mContext, "请输入公会宣言");
                    } else if (TextUtils.isEmpty(abstractContent)) {
                        topRight.setEnabled(true);
                        ToastUtil.showToast(mContext, "请输入公会简介");
                    } else if (imagesList.size() == 0) {
                        topRight.setEnabled(true);
                        ToastUtil.showToast(mContext, "请设置您的公会头像");
                    } else if (imagesList.size() > 0 && (imagesList.get(0) == null || TextUtils.isEmpty(imagesList.get(0).srcFilePath))) {
					/*topRight.setEnabled(true);
					ToastUtil.showToast(mContext, "您设置的公会头像未上传成功，请重新设置");*/
                        imagesList.clear();
                        new FileUpDoaldTask(mContext, user.phone, picPath, "1", new Back() {

                            @Override
                            public void success(Object object, String msg) {
                                if (object != null && object instanceof FileUpDoald) {
                                    FileUpDoald mFileUpDoald = (FileUpDoald) object;
                                    if (mFileUpDoald != null && mFileUpDoald.data != null) {
                                        if (!TextUtils.isEmpty(mFileUpDoald.data.srcFilePath)) {
                                            Log.e("lbb", "------FileUpDoaldTask-s-----");
                                            imagesList.add(mFileUpDoald.data);
                                            handler.sendEmptyMessage(2);

                                        } else {
                                            handler.sendEmptyMessage(1);
                                        }
                                    } else {
                                        handler.sendEmptyMessage(1);
                                    }
                                } else {
                                    handler.sendEmptyMessage(1);
                                }


                            }

                            @Override
                            public void fail(String status, String msg, Object object) {
                                handler.sendEmptyMessage(1);
                            }
                        }).start();
                    } else {
                        new SaveOrUpdateGuildTask(mContext, user.userId, name, imagesList.get(0).srcFilePath, declareContent, abstractContent, "", new Back() {

                            @Override
                            public void success(Object object, String msg) {
                                ToastUtil.showToast(mContext, "公会修改成功");


                                new GetMainPageInfoTask(mContext, user.userId, new Back() {

                                    @Override
                                    public void success(Object object, String msg) {
                                        if (object != null && object instanceof GetMainPageInfo) {
                                            GetMainPageInfo mQueryPtb = (GetMainPageInfo) object;
                                            if (mQueryPtb.data != null) {
                                                user.jsonData = null;
                                                user.guildId = mQueryPtb.data.guildId;
                                                user.jsonData = new JsonBuild().setModel(user).getJsonString1();
                                                DBManager.getInstance(mContext).insert(user);
                                                user = DBManager.getInstance(mContext).getUserMessage();
                                                Bundle bundle = new Bundle();
                                                bundle.putString("guildId", user.guildId);
                                                startActivity(SociatyDetailsActivity.class, bundle);
                                                finishSingleActivityByClass(SociatyBaseActivity.class, null);
                                            }
                                        }
                                        topRight.setEnabled(true);
                                        finish1();
                                    }

                                    @Override
                                    public void fail(String status, String msg, Object object) {
                                        topRight.setEnabled(true);
                                        finish1();
                                    }
                                }).start();
                            }

                            @Override
                            public void fail(String status, String msg, Object object) {
                                ToastUtil.showToast(mContext, "修改失败 " + msg);
                                topRight.setEnabled(true);
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish1();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    String picPath = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == MineDataEditingActivity.TO_SELECT_PHOTO) {
            for (int i = 0; i < Bimp.tempSelectBitmap.size(); i++) {
                imagesList.clear();
                picPath = Bimp.tempSelectBitmap.get(i).imagePath;
                new FileUpDoaldTask(mContext, user.phone, picPath, "1", new Back() {

                    @Override
                    public void success(Object object, String msg) {
                        if (object != null && object instanceof FileUpDoald) {
                            FileUpDoald mFileUpDoald = (FileUpDoald) object;
                            if (mFileUpDoald != null && mFileUpDoald.data != null) {
                                if (!TextUtils.isEmpty(mFileUpDoald.data.srcFilePath)) {
                                    Log.e("lbb", "------FileUpDoaldTask-s-----");
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
                iv_addPhoto.setImageBitmap(Bimp.tempSelectBitmap.get(i).getBitmap());
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
                    imagesList.clear();
                    picPath = Bimp.tempSelectBitmap.get(i).imagePath;
                    new FileUpDoaldTask(mContext, user.phone, picPath, "1", new Back() {

                        @Override
                        public void success(Object object, String msg) {
                            if (object != null && object instanceof FileUpDoald) {
                                FileUpDoald mFileUpDoald = (FileUpDoald) object;
                                if (mFileUpDoald != null && mFileUpDoald.data != null) {
                                    if (!TextUtils.isEmpty(mFileUpDoald.data.srcFilePath)) {
                                        Log.e("lbb", "------FileUpDoaldTask-s-----");
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
                            ToastUtil.showToast(mContext, msg);
                        }
                    }).start();
                    iv_addPhoto.setImageBitmap(Bimp.tempSelectBitmap.get(i).getBitmap());
                }

            }

        } else if (resultCode == Activity.RESULT_OK && requestCode == TO_SELECT_PHOTO) {
            picPath = data.getStringExtra(ChoosePicActivity.KEY_PHOTO_PATH);
            imagesList.clear();
            new FileUpDoaldTask(mContext, user.phone, picPath, "1", new Back() {

                @Override
                public void success(Object object, String msg) {
                    if (object != null && object instanceof FileUpDoald) {
                        FileUpDoald mFileUpDoald = (FileUpDoald) object;
                        if (mFileUpDoald != null && mFileUpDoald.data != null) {
                            if (!TextUtils.isEmpty(mFileUpDoald.data.srcFilePath)) {
                                Log.e("lbb", "------FileUpDoaldTask-s-----");
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
                    ToastUtil.showToast(mContext, msg);
                }
            }).start();
            Bitmap smallBitmap = ImageCompression.getSmallBitmap(picPath);
            iv_addPhoto.setImageBitmap(smallBitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private static final int TAKE_PICTURE = 0x000001;

    public void photo() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    public void showPop() {
        BaseApplication.mInstance.num = 1;
        Bimp.cur = 0;
        Bimp.tempSelectBitmap.clear();
        ll_popup.startAnimation(AnimationUtils.loadAnimation(RedactSociatyActivity.this, R.anim.activity_translate_in));
        pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
    }

    @Override
    protected void onPause() {
        MobclickAgent.onPageEnd("RedactSociatyActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("RedactSociatyActivity");
        super.onResume();
    }
}