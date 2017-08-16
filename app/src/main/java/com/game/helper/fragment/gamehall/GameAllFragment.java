package com.game.helper.fragment.gamehall;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.game.helper.BaseApplication;
import com.game.helper.BaseFragment;
import com.game.helper.R;
import com.game.helper.adapter.home.MineGameAdapter;
import com.game.helper.download.bean.AppContent;
import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.net.task.QueryGameBykindAndTypeTask;
import com.game.helper.sdk.model.returns.QueryGameBykindAndType;
import com.game.helper.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class GameAllFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.tv_merchantsall_name)
    TextView tvMerchantsallName;
    @BindView(R.id.iv_merchantsall_image)
    ImageView ivMerchantsallImage;
    @BindView(R.id.ll_all)
    LinearLayout llAll;
    @BindView(R.id.tv_sorting_name)
    TextView tvSortingName;
    @BindView(R.id.iv_sorting_image)
    ImageView ivSortingImage;
    @BindView(R.id.ll_sorting)
    LinearLayout llSorting;
    @BindView(R.id.listView_Classify)
    GridView listViewClassify;
    @BindView(R.id.ll3)
    LinearLayout ll3;
    @BindView(R.id.ll_sortingdirectory)
    LinearLayout llSortingdirectory;
    @BindView(R.id.v_lina)
    View vLina;
    @BindView(R.id.listView_gameClassify)
    ListView listViewGameClassify;

    private List<AppContent> mList = new ArrayList<AppContent>();
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    SetAllGame();
                    break;
            }
            return false;
        }
    });

    public GameAllFragment() {
        // Required empty public constructor
    }
/*
    public GameAllFragment(BaseApplication application, Activity activity, Context context) {
        super(application, activity, context);

    }*/

    public static GameAllFragment newInstance() {
        Bundle args = new Bundle();
        GameAllFragment fragment = new GameAllFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        initViews();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.activity_home_game_all_fragment, container, false);
        }
        ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    protected void initViews() {
        Message msg = new Message();
        msg.what = 1;
        handler.sendMessage(msg);
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void init() {

    }


    private void SetAllGame() {

        String typeId = "3";
        String kindId = "";//全部分类
        String orderby = "field1";//默认排序
        String offset = "0";//分页
        new QueryGameBykindAndTypeTask(mContext, typeId, kindId, orderby,offset, new BaseBBXTask.Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof QueryGameBykindAndType) {
                    QueryGameBykindAndType mData = (QueryGameBykindAndType) object;
                    if (mData.data != null && mData.data.size() >= 0) {
                        mList.clear();
                        mList.addAll(mData.data);

                        MineGameAdapter mMineGameAdapter = new MineGameAdapter(mContext, mList, 1, R.id.listView_gameClassify);
                        //mMineGameAdapter.setOnItemClickListener(this);
                        listViewGameClassify.setAdapter(mMineGameAdapter);
                        mMineGameAdapter.setmList(mList);
                    }
                }
            }

            @Override
            public void fail(String status, String msg, Object object) {
                ToastUtil.showToast(mContext, msg);
            }
        }).start();
    }
}
