package com.game.helper.activity.mine;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.adapter.mine.MineFeedAdapter;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetfeedBackTask;
import com.game.helper.sdk.model.returns.GetfeedBack;
import com.game.helper.sdk.model.returns.GetfeedBack.FeedBack;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.util.TimeUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description
 * @Path com.game.helper.activity.mine.MineFeedActivity.java
 * @Author lbb
 * @Date 2016年11月24日 下午6:28:10
 * @Company
 */
public class MineFeedActivity extends BaseActivity {
    @BindView(R.id.problem_listView)
    ListView problem_listView;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;
    private List<FeedBack> mList = new ArrayList<FeedBack>();
    private MineFeedAdapter mMineFeedAdapter;

    private Comparator comp1 = new Comparator() {
        public int compare(Object o1, Object o2) {
            FeedBack p1 = (FeedBack) o1;
            FeedBack p2 = (FeedBack) o2;
            int a = TimeUtil.parserTime(TimeUtil.TIME_FORMAT_FULL, p1.createTimeString, p2.createTimeString);
            if (a != 0) {
                if (a == -1) {
                    return 1;
                } else if (a == 1) {
                    return -1;
                }
            }
            return 0;
        }
    };

    LoginData user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_problems);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("我的问题反馈");
        topLeftBack.setVisibility(View.VISIBLE);
        mMineFeedAdapter = new MineFeedAdapter(MineFeedActivity.this, mList);
        problem_listView.setAdapter(mMineFeedAdapter);
    }

    @Override
    protected void initData() {
        user = DBManager.getInstance(mContext).getUserMessage();
        new GetfeedBackTask(mContext, user.userId, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof GetfeedBack) {
                    GetfeedBack mGetfeedBack = (GetfeedBack) object;
                    if (mGetfeedBack.data != null) {
                        mList.clear();
                        mList.addAll(mGetfeedBack.data);
                        Collections.sort(mList, comp1);
                        mMineFeedAdapter.setmLists(mList);
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
        MobclickAgent.onPageEnd("MineProblemsActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("MineProblemsActivity");
        super.onResume();
    }
}
