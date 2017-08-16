package com.game.helper.fragment.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.game.helper.BaseApplication;
import com.game.helper.BaseFragment;
import com.game.helper.R;
import com.game.helper.adapter.home.SpecialgameAdapter;
import com.game.helper.download.bean.AppContent;
import com.game.helper.leopardkit.DownLoadModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;

public class HomePageListHotGameFragment extends BaseFragment {

    @BindView(R.id.home_fragment_pagelist_newgame_lv)
    ListView homeFragmentPagelistNewgameLv;
    private static List<AppContent> mmSpecialgameAdapter2List = new ArrayList<AppContent>();
    private SpecialgameAdapter mSpecialgameAdapter;

    public HomePageListHotGameFragment() {
        // Required empty public constructor
    }
/*

    public HomePageListHotGameFragment(BaseApplication application, Activity activity, Context context, List<AppContent> mmSpecialgameAdapter2List) {
        super(application, activity, context);
        this.mmSpecialgameAdapter2List = mmSpecialgameAdapter2List;
    }
*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mmSpecialgameAdapter2List=getArguments().getParcelableArrayList("mList");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_page_list, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initView();
        return view;
    }

    public static final HomePageListHotGameFragment newInstance(ArrayList<AppContent> mmSpecialgameAdapter2List ) {
        HomePageListHotGameFragment listfragment=new HomePageListHotGameFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("mList",mmSpecialgameAdapter2List);
        listfragment.setArguments(args);
        return listfragment;
    }
    
    @Override
    protected void initViews() {

    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void init() {

    }

    private void initView() {
        //// TODO: 2017/5/24 当list没有数据或者数据很短的时候加判断
        if (mmSpecialgameAdapter2List.size() >= 2) {
            mSpecialgameAdapter = new SpecialgameAdapter(getActivity(),
                    mmSpecialgameAdapter2List.subList(0, 2), 2, R.id.home_fragment_pagelist_newgame_lv);
            //mSpecialgameAdapter2.setOnItemClickListener(this);
            homeFragmentPagelistNewgameLv.setAdapter(mSpecialgameAdapter);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void onEventMainThread(DownLoadModel event) {
        if (event != null) {
            if (BaseApplication.mInstance.isRecommendBoutiqueAdapter != 0) {

                for (DownLoadModel md : mSpecialgameAdapter.getData()) {
                    Log.e("lbb", "--------onEventMainThread71-------");
                    if (md.getmAppContent() != null && md.getmAppContent().gameId != null
                            && md.getmAppContent().gameId.equals(event.getmAppContent().gameId)) {

                        mSpecialgameAdapter.getData().set(mSpecialgameAdapter.getData().indexOf(md), event);
                        mSpecialgameAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
