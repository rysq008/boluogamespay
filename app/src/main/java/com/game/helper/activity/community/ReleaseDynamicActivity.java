package com.game.helper.activity.community;

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
import android.widget.CheckBox;
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
import com.game.helper.adapter.mine.DragDropGridAdapter;
import com.game.helper.adapter.mine.DragDropGridAdapter.DeleteListener;
import com.game.helper.db.manager.DBManager;
import com.game.helper.model.mine.PersonaImg;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.FileUpDoaldTask;
import com.game.helper.net.task.InsertDynamicTask;
import com.game.helper.pic.AlbumActivity;
import com.game.helper.pic.Bimp;
import com.game.helper.pic.FileUtils;
import com.game.helper.pic.ImageItem;
import com.game.helper.sdk.model.returns.FileUpDoald;
import com.game.helper.sdk.model.returns.Images;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.util.SharedPreUtil;
import com.game.helper.util.ToastUtil;
import com.game.helper.view.img.ImageCompression;
import com.game.helper.view.pdgrid.PagedDragDropGrid;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 发布动态
 * @Path com.game.helper.activity.community.ReleaseDynamicActivity.java
 * @Author lbb
 * @Date 2016年8月25日 下午5:47:50
 * @Company
 */
public class ReleaseDynamicActivity extends BaseActivity implements DeleteListener {

    public static final int TO_SELECT_PHOTO = 3;
    DragDropGridAdapter mDragDropGridAdapter;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_right)
    TextView topRight;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.et_context)
    EditText et_context;
    @BindView(R.id.cb_clause)
    CheckBox cb_clause;
    @BindView(R.id.rv_addressName)
    TextView rv_addressName;
    @BindView(R.id.photo_gridview)
    PagedDragDropGrid photo_gridview;
    private ArrayList<PersonaImg> mList = new ArrayList<PersonaImg>();
    private ArrayList<PersonaImg> mList1 = new ArrayList<PersonaImg>();

	/*@BindView(R.id.et_context) EditText et_context;
    @BindView(R.id.cb_clause)  CheckBox cb_clause;
	@BindView(R.id.rv_addressName)  TextView rv_addressName;
	@BindView(R.id.photo_gridview) PagedDragDropGrid photo_gridview;//个人照片 */

    //boolean isChecked;
    String guildId;
    LoginData user;
    List<Images> imagesList = new ArrayList<Images>();
    private View parentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setContentView(R.layout.activity_community_release_dynamic);
        parentView = getLayoutInflater().inflate(R.layout.activity_community_release_dynamic, null);
        setContentView(parentView);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setD();
        cb_clause.setChecked(false);
        topTitle.setText("发布动态");
        topLeftBack.setVisibility(View.VISIBLE);
        topRight.setText("确定");
        topRight.setVisibility(View.VISIBLE);
        String add = SharedPreUtil.getStringValue(mContext, SHA_LAST_ADDR, "");
        rv_addressName.setText(add);

        guildId = getIntent().getStringExtra("guildId");
        user = DBManager.getInstance(mContext).getUserMessage();
    }

    int i = 0;

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("ReleaseDynamicActivity");
        super.onResume();
		/*if(i==0){
			i=1;
		}else{
			user=DBManager.getInstance(mContext).getUserMessage();
		}*/
    }

    @Override
    protected void initData() {
        mList1.clear();
        PersonaImg mPersonaImg = new PersonaImg();
        mPersonaImg.id = mList1.size();
        //mPersonaImg.index=mList1.size();
        mList1.add(mPersonaImg);
        mDragDropGridAdapter = new DragDropGridAdapter(this, photo_gridview, mList1, 4, 3, 9);
        mDragDropGridAdapter.setmDeleteListener(this);
        photo_gridview.setAdapter(mDragDropGridAdapter);
    }

    private PopupWindow pop = null;
    private LinearLayout ll_popup;

    public void setD() {

        pop = new PopupWindow(ReleaseDynamicActivity.this);

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
                Intent intent = new Intent(ReleaseDynamicActivity.this,
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
                //确定
                if (topRight.isEnabled()) {
                    topRight.setEnabled(false);
                    String content = et_context.getText().toString();
                    if (TextUtils.isEmpty(content)) {
                        ToastUtil.showToast(mContext, "请输入动态内容");
                        topRight.setEnabled(true);
                    } else {
                        new InsertDynamicTask(mContext, user.userId, guildId, content, cb_clause.isChecked() ? rv_addressName.getText().toString() : "", imagesList, new Back() {

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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish1();
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
                                    Log.e("lbb", "------FileUpDoaldTask------");
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
                //mPersonaImg.index=mList.size();
                mList1.addAll(mList);

                mPersonaImg = new PersonaImg();
                mPersonaImg.id = mList1.size();
                //mPersonaImg.index=mList1.size();
                mList1.add(mPersonaImg);

                mDragDropGridAdapter.setList(mList1, mList);
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
                                        Log.e("lbb", "------FileUpDoaldTask------");
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
                    //mPersonaImg.index=mList.size();
                    mList1.addAll(mList);

                    mPersonaImg = new PersonaImg();
                    mPersonaImg.id = mList1.size();
                    //mPersonaImg.index=mList1.size();
                    mList1.add(mPersonaImg);

                    mDragDropGridAdapter.setList(mList1, mList);
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
        } else if (resultCode == Activity.RESULT_OK && requestCode == TO_SELECT_PHOTO) {
            String picPath = data.getStringExtra(ChoosePicActivity.KEY_PHOTO_PATH);
            mList1.clear();
            new FileUpDoaldTask(mContext, user.phone, picPath, "1", new Back() {

                @Override
                public void success(Object object, String msg) {
                    if (object != null && object instanceof FileUpDoald) {
                        FileUpDoald mFileUpDoald = (FileUpDoald) object;
                        if (mFileUpDoald != null && mFileUpDoald.data != null) {
                            if (!TextUtils.isEmpty(mFileUpDoald.data.srcFilePath)) {
                                Log.e("lbb", "------FileUpDoaldTask------");
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
            //mPersonaImg.index=mList.size();
            mList1.addAll(mList);

            mPersonaImg = new PersonaImg();
            mPersonaImg.id = mList1.size();
            //mPersonaImg.index=mList1.size();
            mList1.add(mPersonaImg);

            mDragDropGridAdapter.setList(mList1, mList);
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

    private static final int TAKE_PICTURE = 0x000001;

    public void photo() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    public void showPop() {
        BaseApplication.mInstance.num = 9;
        Bimp.cur = imagesList.size();
        Bimp.tempSelectBitmap.clear();
        ll_popup.startAnimation(AnimationUtils.loadAnimation(ReleaseDynamicActivity.this, R.anim.activity_translate_in));
        pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void showpop() {
        showPop();

    }

    @Override
    protected void onPause() {
        MobclickAgent.onPageEnd("ReleaseDynamicActivity");
        super.onPause();
    }


}
