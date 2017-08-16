package com.game.helper.activity.mine;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 系统消息详情
 * @Path com.game.helper.activity.mine.MineSystemMsgDetailActivity.java
 * @Author lbb
 * @Date 2016年8月19日 下午2:23:02
 * @Company
 */
public class MineSystemMsgDetailActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView tvtitle;
    @BindView(R.id.time)
    TextView tvtime;
    @BindView(R.id.msg)
    TextView tvmsg;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_systemmsg_detail);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("系统消息");
        topLeftBack.setVisibility(View.VISIBLE);
    }

    String pushId, title, tradedate, content;

    @Override
    protected void initData() {
        pushId = getIntent().getStringExtra("pushId");
        title = getIntent().getStringExtra("title");
        tradedate = getIntent().getStringExtra("tradedate");
        content = getIntent().getStringExtra("content");
        tvtitle.setText(title + "系统消息");
        tvtime.setText(tradedate);
        tvmsg.setText(content);
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
        MobclickAgent.onPageEnd("MineSystemMsgDetailActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("MineSystemMsgDetailActivity");
        super.onResume();
    }
}
