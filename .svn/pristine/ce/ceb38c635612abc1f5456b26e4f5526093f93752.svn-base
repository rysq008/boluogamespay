package com.game.helper.activity.community;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetGuildByIdTask;
import com.game.helper.sdk.model.returns.GetGuildById;
import com.game.helper.sdk.model.returns.GetGuildById.GetGuildByIdData;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.SharedPreUtil;
import com.game.helper.util.ToastUtil;
import com.game.helper.view.widget.CircleImageView;
import com.game.helper.view.widget.XCRoundImageViewByXfermode;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 公会信息
 * @Path com.game.helper.activity.community.SociatyInformationActivity.java
 * @Author lbb
 * @Date 2016年8月25日 下午2:53:34
 * @Company
 */
public class SociatyInformationActivity extends BaseActivity {
    @BindView(R.id.relat_chairman)
    RelativeLayout relat_chairman;

    @BindView(R.id.iv_icon)
    XCRoundImageViewByXfermode iv_icon;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_id)
    TextView tv_id;
    @BindView(R.id.tv_createTime)
    TextView tv_createTime;

    @BindView(R.id.iv_headportrait)
    CircleImageView iv_headportrait;
    @BindView(R.id.tv_Rname)
    TextView tv_Rname;
    @BindView(R.id.tv_msg)
    TextView tv_msg;
    @BindView(R.id.tv_content)
    TextView tv_content;

    String guildId;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.top_iv_right)
    ImageView top_iv_right;
    @BindView(R.id.top_liner_right)
    RelativeLayout top_liner_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_community_sociaty_information);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("公会信息");
        topLeftBack.setVisibility(View.VISIBLE);


        guildId = getIntent().getStringExtra("guildId");
    }

    @Override
    protected void initData() {
        String json = SharedPreUtil.getStringValue(mContext, ACTION_GetGuildById + guildId, "");
        if (!TextUtils.isEmpty(json)) {
            Object mObject = new JsonBuild().getData(GetGuildById.class, json);
            if (mObject != null && mObject instanceof GetGuildById) {
                GetGuildById mGetGuildById = (GetGuildById) mObject;
                if (mGetGuildById.data != null) {
                    setDatasForGuild(mGetGuildById.data);
                }
            }
        }
        //获取工会详情
        new GetGuildByIdTask(mContext, guildId, new Back() {
            @Override
            public void success(Object object, String msg) {
                // TODO Auto-generated method stub
                if (object != null && object instanceof GetGuildById) {
                    GetGuildById mGetGuildById = (GetGuildById) object;
                    if (mGetGuildById.data != null) {
                        setDatasForGuild(mGetGuildById.data);
                        SharedPreUtil.putStringValue(mContext, ACTION_GetGuildById + guildId, new JsonBuild().setModel(object).getJson1());
                    }
                }
            }

            @Override
            public void fail(String status, String msg, Object object) {

                String json = SharedPreUtil.getStringValue(mContext, ACTION_GetGuildById + guildId, "");
                if (!TextUtils.isEmpty(json)) {
                    Object mObject = new JsonBuild().getData(GetGuildById.class, json);
                    if (mObject != null && mObject instanceof GetGuildById) {
                        GetGuildById mGetGuildById = (GetGuildById) mObject;
                        if (mGetGuildById.data != null) {
                            setDatasForGuild(mGetGuildById.data);
                        }
                    }
                } else {
                    ToastUtil.showToast(mContext, msg);
                }
            }
        }).start();
    }

    private LoginData user;
    String guild_userId;
    String fileAskPath;
    String iconThumb;
    String icon, name, declareContent, abstractContent;

    public void setDatasForGuild(GetGuildByIdData data) {
        guild_userId = data.userId;
        user = DBManager.getInstance(mContext).getUserMessage();
        if (!TextUtils.isEmpty(guild_userId) && user != null && !TextUtils.isEmpty(user.userId) && guild_userId.equals(user.userId)) {//会长
            top_iv_right.setImageDrawable(getResources().getDrawable(R.drawable._shiqu_11));
            top_liner_right.setVisibility(View.VISIBLE);
        }
        fileAskPath = data.fileAskPath;
        icon = data.icon;
        iconThumb = data.iconThumb;
        Glide.with(BaseApplication.mInstance.context.getApplicationContext())
                .load("" + data.fileAskPath + data.icon)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                //.centerCrop()// 长的一边撑满
                //.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片
                .error(R.drawable.preview_card_pic_loading)//加载失败时显示的图片
                //.crossFade()
                .into(iv_icon);

        if (!TextUtils.isEmpty(data.userIcon)) {
            Glide.with(BaseApplication.mInstance.context.getApplicationContext())
                    .load("" + data.fileAskPath + data.userIcon)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    //.centerCrop()// 长的一边撑满
                    //.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片
                    .error(R.drawable.pic_moren)//加载失败时显示的图片
                    //.crossFade()
                    .into(iv_headportrait);
        } else {
            iv_headportrait.setImageDrawable(getResources().getDrawable(R.drawable.pic_moren));
        }
        name = data.name;
        declareContent = data.declareContent;
        abstractContent = data.abstractContent;
        tv_name.setText(data.name);
        tv_id.setText(data.guildId);
        tv_createTime.setText(data.createTimeString);


        tv_Rname.setText(data.field1);
        tv_msg.setText(data.declareContent);//宣言
        tv_content.setText(data.abstractContent);
    }

    @Override
    @OnClick({R.id.top_left_layout, R.id.top_liner_right, R.id.relat_chairman})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            case R.id.relat_chairman:
                Bundle bundle = new Bundle();
                bundle.putString("userId", guild_userId);
                startActivity(PersonalHomepageActivity.class, bundle);
                break;
            case R.id.top_liner_right:
                Bundle bundle1 = new Bundle();
                bundle1.putString("guildId", guildId);
                bundle1.putString("fileAskPath", fileAskPath);
                bundle1.putString("icon", icon);
                bundle1.putString("iconThumb", iconThumb);
                bundle1.putString("name", name);
                bundle1.putString("declareContent", declareContent);
                bundle1.putString("abstractContent", abstractContent);
                startActivity(RedactSociatyActivity.class, bundle1);
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
        MobclickAgent.onPageEnd("SociatyInformationActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("SociatyInformationActivity");
        super.onResume();
    }

}
