package com.game.helper.activity.community;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.activity.home.ImageActivity;
import com.game.helper.adapter.community.DetailsCommentsAdapter;
import com.game.helper.adapter.community.DyImgAdapter;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.CommentDynamicTask;
import com.game.helper.net.task.DeleteDynamicTask;
import com.game.helper.net.task.DzDynamicTask;
import com.game.helper.net.task.GetDynamicByIdTask;
import com.game.helper.sdk.model.returns.CommentDynamic;
import com.game.helper.sdk.model.returns.DzDynamic;
import com.game.helper.sdk.model.returns.GetDynamicById;
import com.game.helper.sdk.model.returns.GetDynamic_1Info.CommentData;
import com.game.helper.sdk.model.returns.GetDynamic_1Info.Dynamic_1Info;
import com.game.helper.sdk.model.returns.GetDynamic_1Info.ImageData;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.SharedPreUtil;
import com.game.helper.util.ToastUtil;
import com.game.helper.view.dialog.MyAlertDailog;
import com.game.helper.view.dialog.MyAlertDailog.onClickLeftListener;
import com.game.helper.view.dialog.MyAlertDailog.onClickRightListener;
import com.game.helper.view.widget.CircleImageView;
import com.game.helper.view.widget.ListViewForScrollView;
import com.game.helper.view.widget.MyScrollviewGridView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 动态详情
 * @Path com.game.helper.activity.community.DynamicDetailsActivity.java
 * @Author lbb
 * @Date 2016年8月26日 上午9:15:11
 * @Company
 */
public class DynamicDetailsActivity extends BaseActivity {
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
    @BindView(R.id.iv_icon)
    CircleImageView iv_icon;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_cleans)
    ImageView tv_cleans;
    @BindView(R.id.tv_msg)
    TextView tv_msg;
    @BindView(R.id.tv_open_unfold)
    ImageView tv_open_unfold;
    @BindView(R.id.dyImg_gridView)
    MyScrollviewGridView dyImg_gridView;
    @BindView(R.id.mLinearImg)
    LinearLayout mLinearImg;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.iv_address1)
    LinearLayout iv_address1;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.iv_zan)
    ImageView iv_zan;
    @BindView(R.id.tv_zan)
    TextView tv_zan;
    @BindView(R.id.tv_comment)
    TextView tv_comment;
    @BindView(R.id.comments_listView)
    ListViewForScrollView comments_listView;
    @BindView(R.id.mScrollView)
    ScrollView mScrollView;
    @BindView(R.id.et_comment)
    EditText et_comment;
    @BindView(R.id.tv_setcomment)
    ImageView tv_setcomment;

    private DetailsCommentsAdapter mDetailsCommentsAdapter;
    private List<CommentData> commentList = new ArrayList<CommentData>();

    private DyImgAdapter mDyImgAdapter;
    private List<ImageData> imageList = new ArrayList<ImageData>();
    ArrayList<String> datas1 = new ArrayList<String>();
    private int isStart = 0;
    private String dynamicId;
    private String dy_userId;
    LoginData user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_community_dynamic_details);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        user = DBManager.getInstance(mContext).getUserMessage();

        topTitle.setText("动态详情");
        topLeftBack.setVisibility(View.VISIBLE);
        mDyImgAdapter = new DyImgAdapter(mContext, imageList);
        dyImg_gridView.setAdapter(mDyImgAdapter);
        dyImg_gridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DynamicDetailsActivity.this, ImageActivity.class);
                intent.putExtra("image_position", position);
                intent.putExtra("image_Total", datas1.size());
                intent.putExtra("images", datas1);
                startActivity(intent);

            }
        });
        mDetailsCommentsAdapter = new DetailsCommentsAdapter(mContext, commentList);
        comments_listView.setAdapter(mDetailsCommentsAdapter);
    }

    public void setDatas(Dynamic_1Info mDynamic_1Info) {
        dy_userId = mDynamic_1Info.userId;
        if (!TextUtils.isEmpty(dy_userId) && user != null && !TextUtils.isEmpty(user.userId) && user.userId.equals(dy_userId)) {
            tv_cleans.setVisibility(View.VISIBLE);
        } else {
            tv_cleans.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(mDynamic_1Info.field1)) {
            Glide.with(BaseApplication.mInstance.context.getApplicationContext())
                    .load("" + mDynamic_1Info.fileAskPath + mDynamic_1Info.field1)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    //.centerCrop()// 长的一边撑满
                    //.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片
                    .error(R.drawable.pic_moren)//加载失败时显示的图片
                    //.crossFade()
                    .into(iv_icon);
        } else {
            iv_icon.setImageDrawable(getResources().getDrawable(R.drawable.pic_moren));
        }

        tv_name.setText(mDynamic_1Info.userName);
        tv_msg.setText(mDynamic_1Info.content);

        if (!TextUtils.isEmpty(mDynamic_1Info.field2)) {
            iv_address1.setVisibility(View.VISIBLE);
            tv_address.setText(mDynamic_1Info.field2);
        } else {
            iv_address1.setVisibility(View.GONE);
            tv_address.setText(mDynamic_1Info.field2);
        }

        tv_time.setText(mDynamic_1Info.publishDateString);
        tv_zan.setText("赞(" + mDynamic_1Info.dzNum + ")");
        tv_comment.setText("全部评论(" + mDynamic_1Info.plNum + ")");
        if (mDynamic_1Info.commentList != null && mDynamic_1Info.commentList.size() >= 0) {
            commentList.clear();
            commentList.addAll(mDynamic_1Info.commentList);
            mDetailsCommentsAdapter.setCommentList(commentList);
        }

        if (mDynamic_1Info.imageList != null && mDynamic_1Info.imageList.size() >= 0) {
            imageList.clear();
            imageList.addAll(mDynamic_1Info.imageList);

            datas1.clear();
            for (ImageData mImageData : imageList) {
                datas1.add("" + mImageData.fileAskPath + mImageData.imageAddress);
            }
            mDyImgAdapter.setData(imageList);
            mLinearImg.setVisibility(View.VISIBLE);
        } else {
            mLinearImg.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData() {
        dynamicId = getIntent().getStringExtra("dynamicId");
        String json = SharedPreUtil.getStringValue(mContext, ACTION_GetDynamicById + dynamicId, "");
        if (!TextUtils.isEmpty(json)) {
            Object mObject = new JsonBuild().getData(GetDynamicById.class, json);
            if (mObject != null && mObject instanceof GetDynamicById) {
                GetDynamicById mData = (GetDynamicById) mObject;
                if (mData != null && mData.data != null) {
                    setDatas(mData.data);
                }
            }
        }
        new GetDynamicByIdTask(mContext, dynamicId, new Back() {

            @Override
            public void success(Object object, String msg) {
                //
                if (object != null && object instanceof GetDynamicById) {
                    GetDynamicById m = (GetDynamicById) object;
                    if (m.data != null) {
                        setDatas(m.data);
                        SharedPreUtil.putStringValue(mContext, ACTION_GetDynamicById + dynamicId, new JsonBuild().setModel(object).getJson1());
                    }
                }
            }

            @Override
            public void fail(String status, String msg, Object object) {
                String json = SharedPreUtil.getStringValue(mContext, ACTION_GetDynamicById + dynamicId, "");
                if (!TextUtils.isEmpty(json)) {
                    Object mObject = new JsonBuild().getData(GetDynamicById.class, json);
                    if (mObject != null && mObject instanceof GetDynamicById) {
                        GetDynamicById mData = (GetDynamicById) mObject;
                        if (mData != null && mData.data != null) {
                            setDatas(mData.data);
                        }
                    }
                }

            }
        }).start();
    }

    @Override
    @OnClick({R.id.top_left_layout, R.id.top_right, R.id.tv_cleans, R.id.iv_icon,
            R.id.tv_open_unfold, R.id.tv_setcomment, R.id.iv_zan, R.id.tv_zan})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            case R.id.iv_icon:
                if (!TextUtils.isEmpty(dy_userId)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("userId", dy_userId);
                    ((BaseActivity) mContext).startActivity(PersonalHomepageActivity.class, bundle);
                }
                break;
            case R.id.tv_cleans:
                //删除
                final MyAlertDailog dialog = new MyAlertDailog(
                        mContext);
                Resources res = mContext.getResources();
                dialog.setTitle("提醒", Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                dialog.setContent1("是否删除发布的动态？"
                        , Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                dialog.setLeftButtonText("取消");
                dialog.setRightButtonText("确定");
                if (dialog != null && !dialog.isShowing()) {
                    dialog.show();
                }
                dialog.setOnClickLeftListener(new onClickLeftListener() {
                    @Override
                    public void onClickLeft() {
                        dialog.dismiss();

                    }
                });
                dialog.setOnClickRightListener(new onClickRightListener() {

                    @Override
                    public void onClickRight() {
                        dialog.dismiss();
                        new DeleteDynamicTask(mContext, dynamicId, new Back() {

                            @Override
                            public void success(Object object, String msg) {
                                ToastUtil.showToast(mContext, "动态已删除");
                                finish1();
                            }

                            @Override
                            public void fail(String status, String msg, Object object) {
                                ToastUtil.showToast(mContext, msg);

                            }
                        }).start();
                    }
                });
                break;
            case R.id.tv_open_unfold:


                break;
            case R.id.tv_zan:
            case R.id.iv_zan:
                if (iv_zan.isEnabled() && tv_zan.isEnabled()) {
                    iv_zan.setEnabled(false);
                    tv_zan.setEnabled(false);
                    new DzDynamicTask(mContext, user.userId, dynamicId, new Back() {

                        @Override
                        public void success(Object object, String msg) {

                            //刷新点赞数
                            if (object != null && object instanceof DzDynamic) {
                                DzDynamic mDzDynamic = (DzDynamic) object;
                                if (mDzDynamic.data != null) {
                                    tv_zan.setText("赞(" + mDzDynamic.data.dzNum + ")");
                                }
                            }
                            iv_zan.setEnabled(true);
                            tv_zan.setEnabled(true);
                            ToastUtil.showToast(mContext, msg);
                        }

                        @Override
                        public void fail(String status, String msg, Object object) {
                            ToastUtil.showToast(mContext, msg);
                            iv_zan.setEnabled(true);
                            tv_zan.setEnabled(true);
                        }
                    }).start();
                }

                break;
            case R.id.tv_setcomment:
                //评论
                if (tv_setcomment.isEnabled()) {
                    tv_setcomment.setEnabled(false);
                    String content = et_comment.getText().toString();
                    if (TextUtils.isEmpty(content)) {
                        ToastUtil.showToast(mContext, "说点什么吧");
                        tv_setcomment.setEnabled(true);
                    } else {
                        new CommentDynamicTask(mContext, user.userId, dynamicId, content, new Back() {

                            @Override
                            public void success(Object object, String msg) {
                                //刷新评论列表和评论总数
                                if (object != null && object instanceof CommentDynamic) {
                                    CommentDynamic mCommentDynamic = (CommentDynamic) object;
                                    if (mCommentDynamic.data != null) {
                                        tv_comment.setText("全部评论(" + mCommentDynamic.data.plNum + ")");
                                        if (mCommentDynamic.data.commentList != null && mCommentDynamic.data.commentList.size() >= 0) {
                                            commentList.clear();
                                            commentList.addAll(mCommentDynamic.data.commentList);
                                            mDetailsCommentsAdapter.setCommentList(commentList);
                                        }
                                    }
                                }
                                new GetDynamicByIdTask(mContext, dynamicId, new Back() {

                                    @Override
                                    public void success(Object object, String msg) {
                                        //
                                        if (object != null && object instanceof GetDynamicById) {
                                            GetDynamicById m = (GetDynamicById) object;
                                            if (m.data != null) {
                                                if (m.data.commentList != null && m.data.commentList.size() >= 0) {
                                                    commentList.clear();
                                                    commentList.addAll(m.data.commentList);
                                                    mDetailsCommentsAdapter.setCommentList(commentList);
                                                }
                                                SharedPreUtil.putStringValue(mContext, ACTION_GetDynamicById + dynamicId, new JsonBuild().setModel(object).getJson1());
                                            }
                                        }
                                    }

                                    @Override
                                    public void fail(String status, String msg, Object object) {
                                        String json = SharedPreUtil.getStringValue(mContext, ACTION_GetDynamicById + dynamicId, "");
                                        if (!TextUtils.isEmpty(json)) {
                                            Object mObject = new JsonBuild().getData(GetDynamicById.class, json);
                                            if (mObject != null && mObject instanceof GetDynamicById) {
                                                GetDynamicById mData = (GetDynamicById) mObject;
                                                if (mData != null && mData.data != null) {
                                                    if (mData.data.commentList != null && mData.data.commentList.size() >= 0) {
                                                        commentList.clear();
                                                        commentList.addAll(mData.data.commentList);
                                                        mDetailsCommentsAdapter.setCommentList(commentList);
                                                    }
                                                }
                                            }
                                        }

                                    }
                                }).start();
                                ToastUtil.showToast(mContext, "评论成功");
                                tv_setcomment.setEnabled(true);
                                et_comment.setText("");
                            }

                            @Override
                            public void fail(String status, String msg, Object object) {
                                tv_setcomment.setEnabled(true);
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
    protected void onResume() {
        // TODO Auto-generated method stub
        MobclickAgent.onPageStart("DynamicDetailsActivity");
        super.onResume();
        if (isStart == 0) {
            isStart = 1;
            mScrollView.post(new Runnable() {
                public void run() {
                    mScrollView.fullScroll(ScrollView.FOCUS_UP);
                }
            });
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
    protected void onPause() {
        MobclickAgent.onPageEnd("DynamicDetailsActivity");
        super.onPause();
    }


}