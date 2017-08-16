package com.game.helper.fragment.gamehall;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.game.helper.BaseFragment;
import com.game.helper.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class GetBackFragment extends BaseFragment {

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
    Unbinder unbinder;


    public GetBackFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.activity_home_game_getback_fragment, container, false);
        }
        unbinder = ButterKnife.bind(this, mView);
        return mView;
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
