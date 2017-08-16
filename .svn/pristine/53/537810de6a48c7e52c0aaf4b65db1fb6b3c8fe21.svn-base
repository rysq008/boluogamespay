package com.game.helper.fragment.community;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;

import com.example.mylistview.pullListView.PullToRefreshLayout;
import com.example.mylistview.pullListView.PullToRefreshLayout.OnRefreshListener;
import com.example.mylistview.pullListView.PullableListView;
import com.example.mylistview.pullListView.PullableListView.OnLoadListener;
import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.BaseFragment;
import com.game.helper.R;
import com.game.helper.activity.community.ConsultingDetailsActivity;
import com.game.helper.adapter.community.ConsultingAdapter;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetInfoListTask;
import com.game.helper.sdk.model.returns.GetInfoList;
import com.game.helper.sdk.model.returns.GetInfoList.GetInfoListData;
import com.game.helper.util.TimeUtil;
import com.game.helper.util.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@SuppressLint("ValidFragment")

/**
 * @Description 活动
 * @Path com.game.helper.fragment.community.ConsultingActivityFragment.java
 * @Author lbb
 * @Date 2016年8月25日 下午4:07:56
 * @Company
 */
public class ConsultingActivityFragment extends BaseFragment implements OnLoadListener, OnRefreshListener {

    PullableListView mMyListView;
    PullToRefreshLayout pullToRefreshLayout;

    ConsultingAdapter mConsultingAdapter;
    @BindView(R.id.nodate_img_rl)
    RelativeLayout nodateImgRl;
    private List<GetInfoListData> data = new ArrayList<GetInfoListData>();
    private int page = 0;//页码 没传默认为第1页
    private int type = 0;//0：刷新，1：加载更多
    private int currIndex = 0;//当前页卡编号
    private boolean isStart = false;

    public ConsultingActivityFragment() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ConsultingActivityFragment(BaseApplication application, Activity activity, Context context) {
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
            mView = inflater.inflate(R.layout.fragment_community_consulting_activity, container, false);
            ButterKnife.bind(this, mView);
            initViews();
            initEvents();
            init();
        }
        FragmentCache(mView);
        return mView;
    }

    @Override
    protected void initViews() {
        mMyListView = (PullableListView) findViewById(R.id.MyListView);
        pullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.pullToRefreshLayout);

        pullToRefreshLayout.setOnRefreshListener(this);
        mMyListView.setDividerHeight(0);
        mMyListView.setOnLoadListener(this);
        mMyListView.hideStateTextView(true);
        mMyListView.setBackgroundLoadingRelative(android.R.color.transparent);

        mConsultingAdapter = new ConsultingAdapter(getActivity(), data);
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headerview = inflater.inflate(R.layout.headerview, null);
        //【主要，添加headerview，则在OnItenClick中的Position就会错乱】
        mMyListView.addHeaderView(headerview, null, false);
        mMyListView.setAdapter(mConsultingAdapter);


    }

    @Override
    protected void initEvents() {
        mMyListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO 【修正有Header的ListView的position的BUG】
                int headerViewsCount = mMyListView.getHeaderViewsCount();//得到header的总数量
                //【得到新的修正后的position】
                int newPosition = position - headerViewsCount;

                Bundle bundle = new Bundle();
                bundle.putString("typeName", ((GetInfoListData) mConsultingAdapter.getItem(newPosition)).typeName);
                bundle.putString("createTimeString", ((GetInfoListData) mConsultingAdapter.getItem(newPosition)).createTimeString);
                bundle.putString("contentId", ((GetInfoListData) mConsultingAdapter.getItem(newPosition)).contentId);
                bundle.putString("fileAskPath", ((GetInfoListData) mConsultingAdapter.getItem(newPosition)).fileAskPath);
                bundle.putString("dzNum", ((GetInfoListData) mConsultingAdapter.getItem(newPosition)).dzNum);

                bundle.putString("plNum", ((GetInfoListData) mConsultingAdapter.getItem(newPosition)).plNum);
                bundle.putString("typeId", ((GetInfoListData) mConsultingAdapter.getItem(newPosition)).typeId);
                bundle.putString("contentTitle", ((GetInfoListData) mConsultingAdapter.getItem(newPosition)).contentTitle);
                bundle.putString("contentAbstract", ((GetInfoListData) mConsultingAdapter.getItem(newPosition)).contentAbstract);
                bundle.putString("contentImage", ((GetInfoListData) mConsultingAdapter.getItem(newPosition)).contentImage);
                bundle.putString("contentText", ((GetInfoListData) mConsultingAdapter.getItem(newPosition)).contentText);
                bundle.putString("scNum", ((GetInfoListData) mConsultingAdapter.getItem(newPosition)).scNum);
                ((BaseActivity) getActivity()).startActivity(ConsultingDetailsActivity.class, bundle);
            }
        });

    }

    String typeId;

    @Override
    protected void init() {
        typeId = getArguments().getString("typeId", "1");
        currIndex = getArguments().getInt("currIndex", 0);
        isStart = false;

        type = 0;
        page = 0;
        getRefresh();

    }

    public void getRefresh() {

        if (currIndex == 0) {
            isStart = true;
        }
        new GetInfoListTask(getActivity(), isStart, typeId, "" + page, TimeUtil.getCurTime(TimeUtil.TIME_FORMAT_FULLTT), new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof GetInfoList) {
                    GetInfoList mGetInfoList = (GetInfoList) object;
                    if (mGetInfoList.data != null) {
                        if (type == 0) {// 刷新
                            if (mGetInfoList.data.size() >= 0) {
                                data.clear();
                                data.addAll(mGetInfoList.data);
                                page++;
                            }
                            if (mGetInfoList.data.size() < 10) {
                                mMyListView.setHasMoreData(false);
                                mMyListView.hideStateTextView(true);
                            } else {
                                mMyListView.setHasMoreData(true);
                            }
                            mConsultingAdapter.setData(data);
                            //pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                        } else {
                            if (mGetInfoList.data.size() >= 0) {
                                data.addAll(mGetInfoList.data);
                                page++;
                            }
                            if (mGetInfoList.data.size() < 10) {
                                mMyListView.setHasMoreData(false);
                                ToastUtil.showToast(mContext, "已经到底了");
                                mMyListView.hideStateTextView(true);
                            } else {
                                mMyListView.setHasMoreData(true);
                            }
                            mConsultingAdapter.setData(data);
                        }
                        if (data.size() == 0) {
                            pullToRefreshLayout.setVisibility(View.GONE);
                            nodateImgRl.setVisibility(View.VISIBLE);
                        }
                    } else {
                        pullToRefreshLayout.setVisibility(View.GONE);
                        nodateImgRl.setVisibility(View.VISIBLE);
                    }
                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
                isStart = true;
                if (data.size() == 0) {
                    if (currIndex == 0) {
                        ToastUtil.showToast(mContext, "暂无数据");
                    }
                    currIndex = 0;
                    mMyListView.hideStateTextView(true);
                    mMyListView.setBackgroundLoadingRelative(android.R.color.transparent);//gray
                } else {
                    /*mMyListView.hideStateTextView(false);
                    mMyListView.setBackgroundLoadingRelative(R.color.white);*/
                }
            }

            @Override
            public void fail(String status, String msg, Object object) {
                if (type == 0) {// 刷新
                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
                    ToastUtil.showToast(mContext, msg);
                } else if (type == 1) {// 加载
                    ToastUtil.showToast(mContext, "获取列表失败");
                }
                pullToRefreshLayout.setVisibility(View.GONE);
                isStart = true;
            }
        }).start();
    }

    int i = 0;

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("ConsultingActivityFragment");
        if (i == 0) {
            i = 1;
        } else {
            currIndex = getArguments().getInt("currIndex", 0);
            isStart = false;
            type = 0;
            page = 0;
            getRefresh();
        }
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        type = 0;
        page = 0;
        getRefresh();

    }

    @Override
    public void onLoad(PullableListView pullableListView) {
        type = 1;
        getRefresh();
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        MobclickAgent.onPageEnd("ConsultingActivityFragment");
    }
}
