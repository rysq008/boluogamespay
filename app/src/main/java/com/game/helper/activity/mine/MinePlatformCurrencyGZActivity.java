package com.game.helper.activity.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.activity.community.SociatyBaseActivity;
import com.game.helper.activity.community.SociatyDetailsActivity;
import com.game.helper.activity.home.InviteFriendsActivity;
import com.game.helper.adapter.home.TaskCenterAwardAdapter;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetPtbRuleTask;
import com.game.helper.sdk.model.returns.GetPtbRule;
import com.game.helper.sdk.model.returns.GetPtbRule.PtbRule;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.view.widget.ListViewForScrollView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 我的金币规则
 * @Path com.game.helper.activity.mine.MinePlatformCurrencyGZActivity.java
 * @Author lbb
 * @Date 2016年8月19日 下午12:24:15
 * @Company
 */
public class MinePlatformCurrencyGZActivity extends BaseActivity {
    TaskCenterAwardAdapter mTaskCenterAwardAdapter;
    @BindView(R.id.award_listView)
    ListViewForScrollView award_listView;
    List<PtbRule> mList = new ArrayList<PtbRule>();
    LoginData user;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_platformcurrency_guize);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        user = DBManager.getInstance(mContext).getUserMessage();
        topTitle.setText("获取金币规则");
        topLeftBack.setVisibility(View.VISIBLE);
        mTaskCenterAwardAdapter = new TaskCenterAwardAdapter(mContext, mList);
        award_listView.setAdapter(mTaskCenterAwardAdapter);
        award_listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PtbRule mPtbRule = mTaskCenterAwardAdapter.getmList().get(position);
                if (mPtbRule != null && !TextUtils.isEmpty(mPtbRule.ruleCode)) {
                    if (mPtbRule.ruleCode.equals("invite")
                            || mPtbRule.ruleCode.equals("friendCircle")
                            || mPtbRule.ruleCode.equals("qq")
                            || mPtbRule.ruleCode.equals("weibo")
                            || mPtbRule.ruleCode.equals("qqFriend")
                            || mPtbRule.ruleCode.equals("weixinFriend")) {//邀请好友
                        startActivity(InviteFriendsActivity.class);
                    } else if (mPtbRule.ruleCode.equals("pSign")) {//平台签到

                    } else if (mPtbRule.ruleCode.equals("gSign")) {//工会签到
                        if (!TextUtils.isEmpty(user.guildId) && !user.guildId.equals("0")) {
                            Bundle bundle = new Bundle();
                            bundle.putString("guildId", user.guildId);
                            startActivity(SociatyDetailsActivity.class, bundle);
                        } else {
                            startActivity(SociatyBaseActivity.class);
                        }
                    } else if (mPtbRule.ruleCode.equals("publish")) {//发布攻略
                        /*Bundle bundle=new Bundle();
						bundle.putString("gameId", gameId);
						startActivity(ReleaseStrategyActivity.class,bundle);*/
                    } else if (mPtbRule.ruleCode.equals("pl")) {//评论攻略/资讯

                    }
                }
            }
        });
    }

    @Override
    protected void initData() {
        new GetPtbRuleTask(mContext, true, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof GetPtbRule) {
                    GetPtbRule mGetPtbRule = (GetPtbRule) object;
                    if (mGetPtbRule.data != null) {
                        mList.clear();
                        mList.addAll(mGetPtbRule.data);
                        mTaskCenterAwardAdapter.setmList(mList);
                    }
                }

            }

            @Override
            public void fail(String status, String msg, Object object) {
                // TODO Auto-generated method stub

            }
        }).start();
		/*TaskCenterAward m=new TaskCenterAward();
		m.img=R.drawable.shouye_127;
		m.msg="每日签到一次";
		m.title="每日签到";
		m.num="1";
		mList.add(m);
		m=new TaskCenterAward();
		m.img=R.drawable.shouye_134;
		m.msg="每日阅读最多奖励三次";
		m.title="发布攻略";
		m.num="2";
		mList.add(m);
		m=new TaskCenterAward();
		m.img=R.drawable.shouye_132;
		m.msg="每日最多可以获得30金币";
		m.title="攻略被赞";
		m.num="10";
		mList.add(m);
		m=new TaskCenterAward();
		m.img=R.drawable.shouye_129;
		m.msg="分享资讯";
		m.title="每日最多可获得30金币";
		m.num="1";
		mList.add(m);
		mTaskCenterAwardAdapter.setmList(mList);*/
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
        MobclickAgent.onPageEnd("MinePlatformCurrencyGZActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("MinePlatformCurrencyGZActivity");
        super.onResume();
    }
}
