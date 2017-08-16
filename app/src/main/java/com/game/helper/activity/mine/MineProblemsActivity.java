package com.game.helper.activity.mine;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.adapter.mine.MineProblemsAdapter;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetQuestionlistTask;
import com.game.helper.sdk.model.returns.GetQuestionlist;
import com.game.helper.sdk.model.returns.GetQuestionlist.Question;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 常见问题
 * @Path com.game.helper.activity.mine.MineProblemsActivity.java
 * @Author lbb
 * @Date 2016年8月19日 下午1:22:43
 * @Company
 */
public class MineProblemsActivity extends BaseActivity {
    @BindView(R.id.problem_listView)
    ListView problem_listView;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;
    private List<Question> mList = new ArrayList<Question>();
    private MineProblemsAdapter mMineProblemsAdapter;

    private Comparator comp1 = new Comparator() {
        public int compare(Object o1, Object o2) {
            Question p1 = (Question) o1;
            Question p2 = (Question) o2;
            int a = 0;
            if (p1.orderBy > p2.orderBy) {
                return 1;
            } else if (p1.orderBy > p2.orderBy) {
                return -1;
            }
            return 0;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_problems);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("常见问题");
        topLeftBack.setVisibility(View.VISIBLE);
        mMineProblemsAdapter = new MineProblemsAdapter(MineProblemsActivity.this, mList);
        problem_listView.setAdapter(mMineProblemsAdapter);
    }

    @Override
    protected void initData() {

        new GetQuestionlistTask(mContext, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof GetQuestionlist) {
                    GetQuestionlist mGetQuestionlist = (GetQuestionlist) object;
                    if (mGetQuestionlist.data != null) {
                        mList.clear();
                        mList.addAll(mGetQuestionlist.data);
                        Collections.sort(mList, comp1);
                        mMineProblemsAdapter.setmLists(mList);
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
