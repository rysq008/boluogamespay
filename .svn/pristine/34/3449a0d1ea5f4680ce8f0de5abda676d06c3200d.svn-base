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
import com.game.helper.activity.community.DynamicDetailsActivity;
import com.game.helper.activity.community.GiftsReceivedActivity;
import com.game.helper.activity.community.PersonalHomepageActivity;
import com.game.helper.activity.home.StrategyDetailsActivity;
import com.game.helper.adapter.mine.SystemMsgAdapter;
import com.game.helper.db.manager.DBManager;
import com.game.helper.interfaces.broadcast.BaseBroadcast;
import com.game.helper.model.PushModel;
import com.game.helper.sdk.model.returns.LoginData;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@SuppressLint("ValidFragment")

/**
 * @Description 动态消息
 * @Path com.game.helper.fragment.DynamicMsgFragment.java
 * @Author lbb
 * @Date 2016年8月19日 下午2:04:06
 * @Company
 */
public class DynamicMsgFragment extends BaseFragment {
    @BindView(R.id.nodate_img_rl)
    RelativeLayout nodateImgRl;
    Unbinder unbinder;
    private List<PushModel> mList = new ArrayList<PushModel>();
    SystemMsgAdapter mSystemMsgAdapter;
    ListView listView;

    public DynamicMsgFragment() {
        super();
        // TODO Auto-generated constructor stub
    }

    public DynamicMsgFragment(BaseApplication application, Activity activity, Context context) {
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
    }

    @Override
    protected void initEvents() {
        // TODO Auto-generated method stub
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                /*推送类型：0-评论动态
                1-评论攻略
                2-点赞动态
                3-收藏攻略
                4-关注
                5-赠送礼物
                6-充值到账*/
                PushModel mMineCollect = (PushModel) mSystemMsgAdapter.getItem(arg2);
                if (mMineCollect != null) {
                    if (!TextUtils.isEmpty(mMineCollect.tradetype) && mMineCollect.tradetype.equals("0")) {
                        //评论动态
                        Bundle bundle = new Bundle();
                        bundle.putString("dynamicId", mMineCollect.id);
                        ((BaseActivity) mContext).startActivity(DynamicDetailsActivity.class, bundle);
                    } else if (!TextUtils.isEmpty(mMineCollect.tradetype) && mMineCollect.tradetype.equals("1")) {
                        //评论-攻略详情
                        Bundle bundle = new Bundle();
                        bundle.putString("cutId", mMineCollect.id);
                        ((BaseActivity) mContext).startActivity(StrategyDetailsActivity.class, bundle);
                    } else if (!TextUtils.isEmpty(mMineCollect.tradetype) && mMineCollect.tradetype.equals("2")) {
                        //赞-动态详情
                        Bundle bundle = new Bundle();
                        bundle.putString("dynamicId", mMineCollect.id);
                        ((BaseActivity) mContext).startActivity(DynamicDetailsActivity.class, bundle);
                    } else if (!TextUtils.isEmpty(mMineCollect.tradetype) && mMineCollect.tradetype.equals("3")) {
                        //收藏-攻略
                        Bundle bundle = new Bundle();
                        bundle.putString("cutId", mMineCollect.id);
                        ((BaseActivity) mContext).startActivity(StrategyDetailsActivity.class, bundle);
                    } else if (!TextUtils.isEmpty(mMineCollect.tradetype) && mMineCollect.tradetype.equals("5")) {
                        //关注了你-跳转至关注我的人的主页
                        LoginData user = DBManager.getInstance(mContext).getUserMessage();
                        Bundle bundle = new Bundle();
                        bundle.putString("userId", mMineCollect.id);
                        ((BaseActivity) mContext).startActivity(PersonalHomepageActivity.class, bundle);
                    } else if (!TextUtils.isEmpty(mMineCollect.tradetype) && mMineCollect.tradetype.equals("4")) {
                        //送了你一个礼物
                        Bundle bundle03 = new Bundle();
                        bundle03.putString("userId", mMineCollect.id);
                        ((BaseActivity) mContext).startActivity(GiftsReceivedActivity.class, bundle03);
                    }
                    mMineCollect.isRead = 1;
                    DBManager.getInstance(getActivity()).insert(mMineCollect);
                    mList.set(arg2, mMineCollect);
                    mSystemMsgAdapter.setmList(mList);
                    //mSystemMsgAdapter.getmList().get(arg2).isRead=1;
                    //mSystemMsgAdapter.notifyDataSetChanged();
                    getActivity().sendBroadcast(new Intent(ACTION_GET_INFO));
					/*if(mMineCollect.type==1){
						if(mMineCollect.actionType==11){
							//评论-动态详情
							
						}else if(mMineCollect.actionType==12){
							//评论-攻略详情
							((BaseActivity)mContext).startActivity(StrategyDetailsActivity.class);
						}else if(mMineCollect.actionType==21){
							//赞-动态详情
							((BaseActivity)mContext).startActivity(DynamicDetailsActivity.class);
						}else if(mMineCollect.actionType==22){
							//评论-攻略详情
							((BaseActivity)mContext).startActivity(StrategyDetailsActivity.class);
						}else if(mMineCollect.actionType==31){
							
						}else if(mMineCollect.actionType==32){
							//收藏-攻略
							((BaseActivity)mContext).startActivity(StrategyDetailsActivity.class);
							
						}else if(mMineCollect.actionType==41){
							//关注了你-跳转至关注我的人的主页
							Bundle bundle=new Bundle();
							bundle.putString("userId", "userId");
							((BaseActivity)mContext).startActivity(PersonalHomepageActivity.class,bundle);
							
						}else if(mMineCollect.actionType==51){
							//送了你一个礼物
							Bundle bundle03=new Bundle();
							bundle03.putString("userId", "userId");
							((BaseActivity)mContext).startActivity(GiftsReceivedActivity.class,bundle03);
							
						}
					}*/
                }

            }
        });
    }

    @Override
    protected void init() {
        mList.clear();
        List<PushModel> list = DBManager.getInstance(getActivity()).getPushAllMessage();
        for (PushModel push : list) {
            if (!TextUtils.isEmpty(push.tradetype) && push.tradetype.equals("6") == false && push.tradetype.equals("7") == false
                    && push.tradetype.equals("8") == false) {
                mList.add(push);
            }
        }
        mSystemMsgAdapter.setmList(mList);

        if (mList.size()<=0){
            listView.setVisibility(View.GONE);
            nodateImgRl.setVisibility(View.VISIBLE);
        }else{
            listView.setVisibility(View.VISIBLE);
            nodateImgRl.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        MobclickAgent.onPageEnd("DynamicMsgFragment");
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        MobclickAgent.onPageStart("DynamicMsgFragment");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}