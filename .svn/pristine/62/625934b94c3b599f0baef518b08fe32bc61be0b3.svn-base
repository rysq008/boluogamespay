package com.game.helper.activity.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.adapter.home.SpinerAdapter;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.AddMyZkhTask;
import com.game.helper.net.task.CgameplatlistTask;
import com.game.helper.sdk.model.returns.Cgameplat;
import com.game.helper.sdk.model.returns.Cgameplatlist;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.util.ToastUtil;
import com.game.helper.view.pop.SpinerPopWindow;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description
 * @Path com.game.helper.activity.home.AddCollarDiscountNumberActivity.java
 * @Author lbb
 * @Date 2016年11月21日 上午10:23:06
 * @Company
 */
public class AddCollarDiscountNumberActivity extends BaseActivity implements SpinerAdapter.IOnItemSelectListener {
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
    private List<Cgameplat> mListType = new ArrayList<Cgameplat>();  //类型列表
    @BindView(R.id.tv_value)
    TextView mTView;
    @BindView(R.id.bt_dropdown)
    ImageView bt_dropdown;

    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.et_userpwd)
    EditText et_userpwd;

    private SpinerAdapter mAdapter;
    //设置PopWindow
    private SpinerPopWindow mSpinerPopWindow;
    @BindView(R.id.btn_adds)
    TextView btn_adds;//添加

    LoginData user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_collardiscountnumber);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        user = DBManager.getInstance(mContext).getUserMessage();
        topTitle.setText("添加折扣号");
        topLeftBack.setVisibility(View.VISIBLE);

        mTView.setHint("选择客户端");
        mTView.setTag("");

        mAdapter = new SpinerAdapter(AddCollarDiscountNumberActivity.this, mListType);
        //初始化PopWindow
        mSpinerPopWindow = new SpinerPopWindow(this);
        mSpinerPopWindow.setAdatper(mAdapter);
        mSpinerPopWindow.setItemListener(this);

    }

    @Override
    protected void initData() {
        new CgameplatlistTask(mContext, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof Cgameplatlist) {
                    Cgameplatlist mCgameplatlist = (Cgameplatlist) object;
                    if (mCgameplatlist.data != null) {
                        mListType.clear();
                        mListType.addAll(mCgameplatlist.data);
                        mSpinerPopWindow.refreshData(mListType, 0);
                        mTView.setHint("选择客户端");
                        mTView.setTag("");
                    }
                }

            }

            @Override
            public void fail(String status, String msg, Object object) {


            }
        }).start();
    }

    @Override
    @OnClick({R.id.top_left_layout, R.id.tv_value, R.id.bt_dropdown, R.id.btn_adds})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            case R.id.tv_value:
            case R.id.bt_dropdown:
                showSpinWindow();
                break;
            case R.id.btn_adds:
                if (btn_adds.isEnabled()) {
                    btn_adds.setEnabled(false);
                    if (!TextUtils.isEmpty(platId)) {
                        if (!TextUtils.isEmpty(et_code.getText().toString())) {
                        /*if(!TextUtils.isEmpty(et_userpwd.getText().toString())){
							
						
						}else{
							btn_adds.setEnabled(true);
							ToastUtil.showToast(mContext, "请输入游戏密码");
						}*/
                            new AddMyZkhTask(mContext, user.userId, platId,
                                    et_code.getText().toString(), new Back() {

                                @Override
                                public void success(Object object, String msg) {
                                    ToastUtil.showToast(mContext, "添加成功");
                                    btn_adds.setEnabled(true);
                                }

                                @Override
                                public void fail(String status, String msg, Object object) {
                                    ToastUtil.showToast(mContext, msg);
                                    btn_adds.setEnabled(true);
                                }
                            }).start();
                        } else {
                            btn_adds.setEnabled(true);
                            ToastUtil.showToast(mContext, "请输入游戏账号");
                        }
                    } else {
                        btn_adds.setEnabled(true);
                        ToastUtil.showToast(mContext, "请选择客户端");
                    }
                }
                break;

            default:
                super.onClick(v);
                break;
        }
    }

    private void showSpinWindow() {
        mSpinerPopWindow.setWidth(mTView.getWidth());
        mSpinerPopWindow.showAsDropDown(mTView);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (mSpinerPopWindow != null && mSpinerPopWindow.isShowing()) {
                mSpinerPopWindow.dismiss();
            }
            finish1();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    String platId;

    @Override
    public void onItemClick(int pos) {

        if (pos >= 0 && pos <= mListType.size()) {
            String value = mListType.get(pos).platName;
            platId = mListType.get(pos).platId;
            mTView.setText(value.toString());
            mTView.setTag(mListType.get(pos).platId);
        }
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageEnd("AddCollarDiscountNumberActivity");
        super.onResume();
    }

    @Override
    protected void onPause() {
        MobclickAgent.onPageStart("AddCollarDiscountNumberActivity");
        super.onPause();
    }

}
