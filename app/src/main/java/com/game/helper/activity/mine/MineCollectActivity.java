package com.game.helper.activity.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.activity.community.ConsultingDetailsActivity;
import com.game.helper.activity.home.StrategyDetailsActivity;
import com.game.helper.adapter.mine.MineCollectAdapter;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetMyCollectionTask;
import com.game.helper.sdk.model.returns.GetMyCollection;
import com.game.helper.sdk.model.returns.GetMyCollection.MyCollection;
import com.game.helper.sdk.model.returns.LoginData;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 我的收藏
 * @Path com.game.helper.activity.mine.MineCollectActivity.java
 * @Author lbb
 * @Date 2016年8月19日 下午12:51:56
 * @Company
 */
public class MineCollectActivity extends BaseActivity {

    @BindView(R.id.collect_listView)
    ListView collect_listView;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;

    private MineCollectAdapter mMineCollectAdapter;
    private List<MyCollection> mList = new ArrayList<MyCollection>();
    LoginData user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_collect);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("我的收藏");
        topLeftBack.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        user = DBManager.getInstance(mContext).getUserMessage();

        mMineCollectAdapter = new MineCollectAdapter(mContext, mList);
        collect_listView.setAdapter(mMineCollectAdapter);
        collect_listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                MyCollection mMineCollect = (MyCollection) mMineCollectAdapter.getItem(arg2);
                if (!TextUtils.isEmpty(mMineCollect.collectionType) &&
                        mMineCollect.collectionType.equals("zx")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("contentId", mMineCollect.typeId);
                    startActivity(ConsultingDetailsActivity.class, bundle);
                } else if (!TextUtils.isEmpty(mMineCollect.collectionType) &&
                        mMineCollect.collectionType.equals("gl")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("cutId", mMineCollect.typeId);
                    startActivity(StrategyDetailsActivity.class, bundle);
                }
            }
        });
        initssss();
    }

    public void initssss() {

        new GetMyCollectionTask(mContext, user.userId, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof GetMyCollection) {
                    GetMyCollection mGetMyCollection = (GetMyCollection) object;
                    if (mGetMyCollection.data != null) {
                        mList.clear();
                        mList.addAll(mGetMyCollection.data);
                        mMineCollectAdapter.setmList(mList);
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
        MobclickAgent.onPageEnd("MineCollectActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("MineCollectActivity");
        super.onResume();
    }
}
