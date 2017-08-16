package com.game.helper.fragment.mine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.BaseFragment;
import com.game.helper.R;
import com.game.helper.activity.mine.MineRechargeDetailActivity;
import com.game.helper.adapter.mine.SystemMsgAdapter;
import com.game.helper.db.manager.DBManager;
import com.game.helper.interfaces.broadcast.BaseBroadcast;
import com.game.helper.model.PushModel;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@SuppressLint("ValidFragment")

/**
 * @Description 充值消息（充值到账）
 * @Path com.game.helper.fragment.RechargeMsgFragment.java
 * @Author lbb
 * @Date 2016年8月19日 下午2:04:50
 * @Company
 */
public class RechargeMsgFragment extends BaseFragment {
    @BindView(R.id.nodate_img_rl)
    RelativeLayout nodateImgRl;
    Unbinder unbinder;
    private List<PushModel> mList = new ArrayList<PushModel>();
    SystemMsgAdapter mSystemMsgAdapter;
    ListView listView;

    public RechargeMsgFragment() {
        super();
        // TODO Auto-generated constructor stub
    }

    public RechargeMsgFragment(BaseApplication application, Activity activity, Context context) {
        super(application, activity, context);
        // TODO Auto-generated constructor stub
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        mApplication = (BaseApplication) getActivity().getApplication();
        mApplication.context = getActivity();
        super.onCreateView(inflater, container, savedInstanceState);
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_mine_msg, container, false);
            unbinder = ButterKnife.bind(this, mView);
            initViews();
            initEvents();
            init();
        }
        FragmentCache(mView);
        return mView;
    }

    @Override
    protected void initViews() {
        receiver = new BaseBroadcast(getActivity()) {

            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO Auto-generated method stub
                super.onReceive(context, intent);
                String action = intent.getAction();
                if (ACTION_GET_INFO.equals(action)) {
                    init();
                }
            }

        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_GET_INFO);
        getActivity().registerReceiver(receiver, filter);

        listView = (ListView) findViewById(R.id.listView);
        mSystemMsgAdapter = new SystemMsgAdapter(mContext, mList);
        listView.setAdapter(mSystemMsgAdapter);
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                PushModel mMineCollect = (PushModel) mSystemMsgAdapter.getItem(arg2);
                if (mMineCollect != null) {

                    //6-充值到账
                    Bundle bundle = new Bundle();
                    bundle.putString("orderId", mMineCollect.id);
                    bundle.putString("tradedate", mMineCollect.tradedate);
                    bundle.putString("tradetype", mMineCollect.tradetype);
                    ((BaseActivity) mContext).startActivity(MineRechargeDetailActivity.class, bundle);
                    mMineCollect.isRead = 1;
                    DBManager.getInstance(getActivity()).insert(mMineCollect);
                    mList.set(arg2, mMineCollect);
                    mSystemMsgAdapter.setmList(mList);
                    //mSystemMsgAdapter.getmList().get(arg2).isRead=1;
                    //mSystemMsgAdapter.notifyDataSetChanged();
                    getActivity().sendBroadcast(new Intent(ACTION_GET_INFO));
                }

            }
        });
    }

    @Override
    protected void initEvents() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void init() {
        mList.clear();
        List<PushModel> list = DBManager.getInstance(getActivity()).getPushAllMessage();
        for (PushModel push : list) {
            if (!TextUtils.isEmpty(push.tradetype) && (push.tradetype.equals("6") || push.tradetype.equals("7") || push.tradetype.equals("8"))) {
                mList.add(push);
            }
        }
        mSystemMsgAdapter.setmList(mList);

        if (mList.size() <= 0) {
            listView.setVisibility(View.GONE);
            nodateImgRl.setVisibility(View.VISIBLE);
        } else {
            listView.setVisibility(View.VISIBLE);
            nodateImgRl.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        MobclickAgent.onPageEnd("RechargeMsgFragment");
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        MobclickAgent.onPageStart("RechargeMsgFragment");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}