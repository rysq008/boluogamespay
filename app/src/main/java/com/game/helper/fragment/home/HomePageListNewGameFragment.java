package com.game.helper.fragment.home;

import android.os.Bundle;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * 新游上架
 */
public class HomePageListNewGameFragment extends BaseFragment {

    @BindView(R.id.home_fragment_pagelist_newgame_lv)
    ListView homeFragmentPagelistNewgameLv;
    private ArrayList<AppContent> mmSpecialgameAdapter2List = new ArrayList<AppContent>();
    private SpecialgameAdapter mSpecialgameAdapter;

    public HomePageListNewGameFragment() {
        // Required empty public constructor
    }

   /* public HomePageListNewGameFragment(BaseApplication application, Activity activity, Context context, List<AppContent> mmSpecialgameAdapter2List) {
        super(application, activity, context);
        this.mmSpecialgameAdapter2List = mmSpecialgameAdapter2List;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mmSpecialgameAdapter2List = getArguments().getParcelableArrayList("mmSpecialgameAdapter2List");
    }

    public static HomePageListNewGameFragment newInstance(ArrayList<AppContent> mmSpecialgameAdapter2List) {

        Bundle args = new Bundle();
        args.putParcelableArrayList("mmSpecialgameAdapter2List", mmSpecialgameAdapter2List);
        HomePageListNewGameFragment fragment = new HomePageListNewGameFragment();
        fragment.setArguments(args);
        return fragment;
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

    public void onEventMainThread(final DownLoadModel event) {
        if (event != null && getUserVisibleHint() && event.needRefreshAdapter) {
            if (BaseApplication.mInstance.isRecommendBoutiqueAdapter != 0) {

                for (final DownLoadModel md : mSpecialgameAdapter.getData()) {

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
