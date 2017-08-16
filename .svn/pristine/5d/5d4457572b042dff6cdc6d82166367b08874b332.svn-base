package com.game.helper.activity.mine;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 性别
 * @Path com.game.helper.activity.mine.MineSetingSexActivity.java
 * @Author lbb
 * @Date 2016年8月18日 下午6:09:29
 * @Company
 */
public class MineSetingSexActivity extends BaseActivity {

    @BindView(R.id.iv_select0)
    ImageView iv_select0;
    @BindView(R.id.iv_select1)
    ImageView iv_select1;

    @BindView(R.id.relat_set_man)
    RelativeLayout relat_set_man;
    @BindView(R.id.relat_set_wman)
    RelativeLayout relat_set_wman;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_right)
    TextView topRight;
    @BindView(R.id.top_title)
    TextView topTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_setingsex);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("性别");
        topLeftBack.setVisibility(View.VISIBLE);
        topRight.setVisibility(View.VISIBLE);
        topRight.setText("确认");
    }

    @Override
    protected void initData() {

    }


    @Override
    @OnClick({R.id.top_left_layout, R.id.top_right
            , R.id.relat_set_man, R.id.relat_set_wman})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;

            case R.id.top_right:

                finish1();
                break;
            case R.id.relat_set_man:
                iv_select0.setImageResource(R.drawable.btn_choice_down);
                iv_select1.setImageResource(R.drawable.icon_qx_g);
                break;
            case R.id.relat_set_wman:
                iv_select0.setImageResource(R.drawable.icon_qx_g);
                iv_select1.setImageResource(R.drawable.btn_choice_down);
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
        MobclickAgent.onPageEnd("MineSetingSexActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("MineSetingSexActivity");
        super.onResume();
    }

}
