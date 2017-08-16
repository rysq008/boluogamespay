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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.activity.home.GameDetailActivity;
import com.game.helper.activity.home.ImageActivity;
import com.game.helper.adapter.community.PersonaImgAdapter;
import com.game.helper.adapter.community.TAPlayingAdapter;
import com.game.helper.db.manager.DBManager;
import com.game.helper.download.bean.AppContent;
import com.game.helper.model.mine.PersonaImg;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.DeleteFocusTask;
import com.game.helper.net.task.GetMainPageInfoTask;
import com.game.helper.net.task.JudgeFocusTask;
import com.game.helper.net.task.MygameTask;
import com.game.helper.net.task.QueryUserIconTask;
import com.game.helper.net.task.SaveFocusTask;
import com.game.helper.net.task.UpdateRemarkNameTask;
import com.game.helper.sdk.model.returns.GetMainPageInfo;
import com.game.helper.sdk.model.returns.JudgeFocus;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.model.returns.Mygame;
import com.game.helper.sdk.model.returns.QueryUserIcon;
import com.game.helper.util.ToastUtil;
import com.game.helper.view.dialog.MyAlertDailog;
import com.game.helper.view.dialog.MyAlertDailog.onClickSingleListener;
import com.game.helper.view.dialog.MyAlertDailogEd;
import com.game.helper.view.dialog.MyAlertDailogEd.onClickLeftListener;
import com.game.helper.view.dialog.MyAlertDailogEd.onClickRightListener;
import com.game.helper.view.widget.CircleImageView;
import com.game.helper.view.widget.MyScrollviewGridView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 个人主页
 * @Path com.game.helper.activity.community.PersonalHomepageActivity.java
 * @Author lbb
 * @Date 2016年8月26日 上午11:03:13
 * @Company
 */
public class PersonalHomepageActivity extends BaseActivity {
    PersonaImgAdapter mPersonaImgAdapter;
    TAPlayingAdapter mTAPlayingAdapter;
    boolean isOpenEveryonePlaying = false;//是否是全部展开状态
    public List<AppContent> gameList = new ArrayList<AppContent>();
    String userId;
    String nickName, focusId;

    ArrayList<String> datas1 = new ArrayList<String>();
    @BindView(R.id.iv_icon)
    CircleImageView iv_icon;
    @BindView(R.id.tv_item)
    TextView tv_item;
    @BindView(R.id.weddingsNum)
    TextView weddingsNum;
    @BindView(R.id.collect_Num)
    TextView collect_Num;
    @BindView(R.id.gift_Num)
    TextView gift_Num;
    @BindView(R.id.photo_gridview)
    MyScrollviewGridView photo_gridview;
    @BindView(R.id.games_gridview)
    MyScrollviewGridView games_gridview;
    @BindView(R.id.iv_follows)
    ImageView iv_follows;
    @BindView(R.id.tv_follows)
    TextView tv_follows;
    @BindView(R.id.tv_follow)
    LinearLayout tv_follow;
    @BindView(R.id.personal_back)
    ImageView personalBack;
    @BindView(R.id.personal_setname)
    TextView personalSetname;
    @BindView(R.id.personal_vip)
    ImageView personalVip;
    private ArrayList<PersonaImg> mList = new ArrayList<PersonaImg>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_community_personal_homepage);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        /*if(){
			//判断备注名是否为空
		}*/

        //topLeftBack.setVisibility(View.VISIBLE);

		/*topRight.setVisibility(View.VISIBLE);
		topRight.setText("修改备注");
        */
        mPersonaImgAdapter = new PersonaImgAdapter(mContext, mList);
        photo_gridview.setAdapter(mPersonaImgAdapter);
        photo_gridview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PersonalHomepageActivity.this, ImageActivity.class);
                intent.putExtra("image_position", position);
                intent.putExtra("image_Total", datas1.size());
                intent.putExtra("images", datas1);
                startActivity(intent);

            }
        });

        mTAPlayingAdapter = new TAPlayingAdapter(mContext, gameList);
        games_gridview.setAdapter(mTAPlayingAdapter);
        games_gridview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("gameId", mTAPlayingAdapter.getGameList().get(position).gameId);
                bundle.putParcelable("appcontent", mTAPlayingAdapter.getGameList().get(position));
                startActivity(GameDetailActivity.class, bundle);

            }
        });
    }

    @Override
    protected void initData() {
        userId = getIntent().getStringExtra("userId");
      /*  if (true) {//是否已关注
            personalBack.setVisibility(View.VISIBLE);
           *//* top_iv_right.setImageDrawable(getResources().getDrawable(R.drawable._shiqu_11));
            top_liner_right.setVisibility(View.VISIBLE);*//*
        }*/
		/*Glide.with(BaseApplication.mInstance.context.getApplicationContext())
		.load(""+mGetGuildUserData.fileAskPath+mGetGuildUserData.icon)
		.centerCrop()// 长的一边撑满
		//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片  
		.error(R.drawable.preview_card_pic_loading)//加载失败时显示的图片
		//.crossFade()
		.into(iv_icon);*/
        LoginData user = DBManager.getInstance(mContext).getUserMessage();
        new JudgeFocusTask(mContext, false, user.userId, userId, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof JudgeFocus) {
                    JudgeFocus mJudgeFocus = (JudgeFocus) object;
                    if (mJudgeFocus.data != null) {
                        if (!TextUtils.isEmpty(mJudgeFocus.data.focusId)) {
                            focusId = mJudgeFocus.data.focusId;
                            tv_follows.setText("已关注");
                            tv_follows.setTextColor(getResources().getColor(R.color.light_blue));
                            iv_follows.setImageDrawable(getResources().getDrawable(R.drawable.other_attentioned));
                        } else {
                            focusId = null;
                            tv_follows.setText("关注");
                            tv_follows.setTextColor(getResources().getColor(R.color.gh_qianhui_color));
                            iv_follows.setImageDrawable(getResources().getDrawable(R.drawable.other_attention));
                        }
                    } else {
                        focusId = null;
                    }
                }

            }

            @Override
            public void fail(String status, String msg, Object object) {
                // TODO Auto-generated method stub

            }
        }).start();
        //个人信息
        new GetMainPageInfoTask(mContext, userId, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof GetMainPageInfo) {
                    GetMainPageInfo mGetMainPageInfo = (GetMainPageInfo) object;
                    if (mGetMainPageInfo.data != null) {
                        nickName = mGetMainPageInfo.data.nickName;

                       /* if (!TextUtils.isEmpty(nickName)) {
                            topTitle.setText(nickName + "的主页");
                        } else {
                            topTitle.setText("个人主页");
                        }*/
                        //
                        if (!TextUtils.isEmpty(mGetMainPageInfo.data.icon)) {
                            Glide.with(BaseApplication.mInstance.context.getApplicationContext())
                                    .load("" + mGetMainPageInfo.data.fileAskPath + mGetMainPageInfo.data.icon)
                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                    //.centerCrop()// 长的一边撑满
                                    //.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片
                                    .error(R.drawable.pic_moren)//加载失败时显示的图片
                                    //.crossFade()
                                    .into(iv_icon);
                        } else {
                            iv_icon.setImageDrawable(getResources().getDrawable(R.drawable.pic_moren));
                        }

                        tv_item.setText(mGetMainPageInfo.data.field2);

                        weddingsNum.setText(mGetMainPageInfo.data.foucsTotal);
                        collect_Num.setText(mGetMainPageInfo.data.fansTotal);
                        gift_Num.setText(mGetMainPageInfo.data.field3);
                    }
                }

            }

            @Override
            public void fail(String status, String msg, Object object) {
               /* if (!TextUtils.isEmpty(nickName)) {
                    topTitle.setText(nickName + "的主页");
                } else {
                    topTitle.setText("个人主页");
                }*/

            }
        }).start();
        //TA的游戏
        new MygameTask(mContext, false, userId, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof Mygame) {
                    Mygame mMygame = (Mygame) object;
                    if (mMygame.data != null && mMygame.data.size() >= 0) {
                        gameList.clear();
                        if (mMygame.data.size() > 8) {
                            for (int i = 0; i < 8; i++) {
                                gameList.add(mMygame.data.get(i));
                            }
                        } else {
                            gameList.addAll(mMygame.data);
                        }

                        mTAPlayingAdapter.setGameList(gameList);
                    }
                }

            }

            @Override
            public void fail(String status, String msg, Object object) {
                // TODO Auto-generated method stub

            }
        }).start();
        //照片墙
        new QueryUserIconTask(mContext, false, userId, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof QueryUserIcon) {
                    QueryUserIcon mQueryUserIcon = (QueryUserIcon) object;
                    if (mQueryUserIcon.data != null) {
                        try {
                            mList.clear();
                            mList.addAll(mQueryUserIcon.data);

                            datas1.clear();
                            for (PersonaImg mPersonaImg : mList) {
                                datas1.add("" + mPersonaImg.fileAskPath + mPersonaImg.icon);
                            }
                            mPersonaImgAdapter.setSize(mList);
                            Collections.sort(mList);

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
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

    @Override
    @OnClick({R.id.personal_back, R.id.personal_setname/*, R.id.relat_TA_Dynamic*/, R.id.relat_TA_Playing
            , R.id.mLayout1, R.id.mLayout2, R.id.mLayout3
            , R.id.tv_follow, R.id.tv_givegift})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_back:
                finish1();
                break;
            case R.id.relat_TA_Playing:
                Bundle bundle001 = new Bundle();
                bundle001.putString("userId", userId);
                startActivity(TAPlayingActivity.class, bundle001);
                break;
            case R.id.personal_setname:
                final MyAlertDailogEd dialog1 = new MyAlertDailogEd(mContext);
                Resources res1 = mContext.getResources();
                dialog1.setTitle("修改备注", Gravity.CENTER_VERTICAL | Gravity.LEFT);
                dialog1.setContentT(nickName);
                dialog1.setLeftButtonText("取消");
                dialog1.setRightButtonText("确定");
                if (dialog1 != null && !dialog1.isShowing()) {
                    dialog1.show();
                }
                dialog1.setOnClickLeftListener(new onClickLeftListener() {
                    @Override
                    public void onClickLeft() {
                        dialog1.dismiss();

                    }
                });
                dialog1.setOnClickRightListener(new onClickRightListener() {

                    @Override
                    public void onClickRight() {
                        String text = dialog1.getEdText();
                        if (!TextUtils.isEmpty(text)) {
                            dialog1.dismiss();
                            LoginData user = DBManager.getInstance(mContext).getUserMessage();
                            new UpdateRemarkNameTask(mContext, focusId, text, new Back() {

                                @Override
                                public void success(Object object, String msg) {

                                    //刷新topTitle内容
                                    ToastUtil.showToast(mContext, "添加成功");
                                }

                                @Override
                                public void fail(String status, String msg, Object object) {
                                    ToastUtil.showToast(mContext, msg);

                                }
                            }).start();
                        } else {
                            ToastUtil.showToast(mContext, "请输入备注名");
                        }

                    }
                });
                break;
            case R.id.mLayout1:
                Bundle bundle1 = new Bundle();
                bundle1.putString("userId", userId);
                startActivity(FollowPeopleActivity.class, bundle1);
                break;
            case R.id.mLayout2:
                Bundle bundle2 = new Bundle();
                bundle2.putString("userId", userId);
                startActivity(FollowerActivity.class, bundle2);
                break;
            case R.id.mLayout3:
                Bundle bundle3 = new Bundle();
                bundle3.putString("userId", userId);
                startActivity(GiftsReceivedActivity.class, bundle3);
                break;
         /*   case R.id.relat_TA_Dynamic:
                Bundle bundle4 = new Bundle();
                bundle4.putString("userId", userId);
                startActivity(PersonalDynamicActivity.class, bundle4);
                break;
*/
            case R.id.tv_follow:
                //关注

                if (tv_follow.isEnabled()) {
                    tv_follow.setEnabled(false);
                    if (TextUtils.isEmpty(focusId)) {//未关注
                        LoginData user = DBManager.getInstance(mContext).getUserMessage();
                        new SaveFocusTask(mContext, userId, user.userId, new Back() {

                            @Override
                            public void success(Object object, String msg) {
                                tv_follows.setText("已关注");
                                iv_follows.setImageDrawable(getResources().getDrawable(R.drawable.other_attentioned));
                                tv_follows.setTextColor(getResources().getColor(R.color.light_blue));
                                LoginData user = DBManager.getInstance(mContext).getUserMessage();
                                new JudgeFocusTask(mContext, false, user.userId, userId, new Back() {

                                    @Override
                                    public void success(Object object, String msg) {
                                        if (object != null && object instanceof JudgeFocus) {
                                            JudgeFocus mJudgeFocus = (JudgeFocus) object;
                                            if (mJudgeFocus.data != null) {
                                                if (!TextUtils.isEmpty(mJudgeFocus.data.focusId)) {
                                                    focusId = mJudgeFocus.data.focusId;
                                                    tv_follows.setText("已关注");
                                                    tv_follows.setTextColor(getResources().getColor(R.color.light_blue));
                                                    iv_follows.setImageDrawable(getResources().getDrawable(R.drawable.other_attentioned));
                                                } else {
                                                    focusId = null;
                                                    tv_follows.setText("关注");
                                                    tv_follows.setTextColor(getResources().getColor(R.color.gh_qianhui_color));
                                                    iv_follows.setImageDrawable(getResources().getDrawable(R.drawable.other_attention));
                                                }
                                            } else {
                                                focusId = null;
                                            }
                                        }

                                    }

                                    @Override
                                    public void fail(String status, String msg, Object object) {
                                        // TODO Auto-generated method stub

                                    }
                                }).start();


                                final MyAlertDailog dialog = new MyAlertDailog(mContext);
                                dialog.setContent("恭喜，您已关注！");
                                dialog.setSingle("确定");
                                if (dialog != null && !dialog.isShowing()) {
                                    dialog.show();
                                }
                                dialog.setOnClickSingleListener(new onClickSingleListener() {

                                    @Override
                                    public void onClickSingle() {
                                        // TODO Auto-generated method stub
                                        dialog.dismiss();
                                        tv_follow.setEnabled(true);

                                    }
                                });

                            }

                            @Override
                            public void fail(String status, String msg, Object object) {
                                ToastUtil.showToast(mContext, msg);
                                tv_follow.setEnabled(true);
                            }
                        }).start();
                    } else {
                        new DeleteFocusTask(mContext, focusId, new Back() {

                            @Override
                            public void success(Object object, String msg) {
                                tv_follows.setText("关注");
                                tv_follows.setTextColor(getResources().getColor(R.color.gh_qianhui_color));
                                iv_follows.setImageDrawable(getResources().getDrawable(R.drawable._shiqu_03));

                                LoginData user = DBManager.getInstance(mContext).getUserMessage();
                                new JudgeFocusTask(mContext, false, user.userId, userId, new Back() {

                                    @Override
                                    public void success(Object object, String msg) {
                                        if (object != null && object instanceof JudgeFocus) {
                                            JudgeFocus mJudgeFocus = (JudgeFocus) object;
                                            if (mJudgeFocus.data != null) {
                                                if (!TextUtils.isEmpty(mJudgeFocus.data.focusId)) {
                                                    focusId = mJudgeFocus.data.focusId;
                                                    tv_follows.setText("已关注");
                                                    tv_follows.setTextColor(getResources().getColor(R.color.light_blue));
                                                    iv_follows.setImageDrawable(getResources().getDrawable(R.drawable.other_attentioned));
                                                } else {
                                                    focusId = null;
                                                    tv_follows.setText("关注");
                                                    tv_follows.setTextColor(getResources().getColor(R.color.gh_qianhui_color));
                                                    iv_follows.setImageDrawable(getResources().getDrawable(R.drawable.other_attention));
                                                }
                                            } else {
                                                focusId = null;
                                            }
                                        }

                                    }

                                    @Override
                                    public void fail(String status, String msg, Object object) {
                                        // TODO Auto-generated method stub

                                    }
                                }).start();


                                final MyAlertDailog dialog = new MyAlertDailog(mContext);
                                dialog.setContent("已取消关注！");
                                dialog.setSingle("确定");
                                if (dialog != null && !dialog.isShowing()) {
                                    dialog.show();
                                }
                                dialog.setOnClickSingleListener(new onClickSingleListener() {

                                    @Override
                                    public void onClickSingle() {
                                        // TODO Auto-generated method stub
                                        dialog.dismiss();
                                        tv_follow.setEnabled(true);

                                    }
                                });

                            }

                            @Override
                            public void fail(String status, String msg, Object object) {
                                ToastUtil.showToast(mContext, msg);
                                tv_follow.setEnabled(true);
                            }
                        }).start();
                    }
                }


                break;
            case R.id.tv_givegift:
                Bundle bundle = new Bundle();
                bundle.putString("operId", userId);
                bundle.putString("nickName", nickName);
                startActivity(GivingGiftsActivity.class, bundle);
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
    protected void onPause() {
        MobclickAgent.onPageEnd("PersonalHomepageActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("PersonalHomepageActivity");
        super.onResume();
    }
/*
    @OnClick({R.id.personal_back, R.id.personal_setname})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.personal_back:
                break;
            case R.id.personal_setname:
                break;
        }
    }*/
}
