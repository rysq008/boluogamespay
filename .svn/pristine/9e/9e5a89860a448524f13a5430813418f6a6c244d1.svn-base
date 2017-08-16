package com.game.helper.activity.home;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.adapter.home.DownloadManagementAdapter;
import com.game.helper.db.manager.DBManager;
import com.game.helper.download.bean.AppContent;
import com.game.helper.leopardkit.DownLoadManagerModel;
import com.game.helper.leopardkit.DownLoadModel;
import com.umeng.analytics.MobclickAgent;
import com.yuan.leopardkit.db.HttpDbUtil;
import com.yuan.leopardkit.download.model.DownloadInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * @Description 下载管理
 * @Path com.game.helper.activity.home.DownloadManagementActivity.java
 * @Author lbb
 * @Date 2016年8月23日 下午6:32:37
 * @Company
 */
public class DownloadManagementActivity extends BaseActivity {
    @BindView(R.id.downloadManagement_listView)
    ListView downloadManagement_listView;
    DownloadManagementAdapter mDownloadManagementAdapter;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;
    private List<DownLoadModel> mList = new ArrayList<DownLoadModel>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_download_management);
        ButterKnife.bind(this);
        // Register
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("下载管理");
        topLeftBack.setVisibility(View.VISIBLE);
        mDownloadManagementAdapter = new DownloadManagementAdapter(mContext, mList, R.id.downloadManagement_listView);
        //mDownloadManagementAdapter.setOnItemClickListener(this);
        downloadManagement_listView.setAdapter(mDownloadManagementAdapter);

    }

    @Override
    protected void initData() {
        mList.clear();
        List<DownloadInfo> downloadInfoList = HttpDbUtil.initHttpDB(mContext).queryFileInfo(0);
        List<AppContent> mAppContentList = DBManager.getInstance(mContext).getAll();
        if (downloadInfoList != null) {
            DownLoadModel model = null;
            for (DownloadInfo info : downloadInfoList) {
                String utr = "";
                for (AppContent mAppContent : mAppContentList) {
                    utr = "IRecordApp_" + mAppContent.gameId + ".apk";
                    if (utr.equals(info.getFileName())) {
                        model = new DownLoadModel();
                        model.setInfo(info);
                        model.setmAppContent(mAppContent);
                        mList.add(model);
                        break;
                    }
                }

            }
        }
        mDownloadManagementAdapter.setmList(mList);

    }

    int i = 0;

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        MobclickAgent.onPageStart("CollarDiscountNumberActivity");
        super.onResume();

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
    public void onDestroy() {
        //unregisterReceiver(downloadStatusReceiver);
        // Unregister
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        //DownLoadManager.getManager().stopAllTask();
        MobclickAgent.onPageEnd("CollarDiscountNumberActivity");
        super.onPause();
    }

    public void onEventMainThread(DownLoadModel event) {
        if (event != null) {
            if (BaseApplication.mInstance.isRecommendBoutiqueAdapter != 0) {

                for (DownLoadModel md : mList) {
                    Log.e("lbb", "--------onEventMainThread3-------");
                    if (md.getmAppContent() != null && md.getmAppContent().gameId != null
                            && md.getmAppContent().gameId.equals(event.getmAppContent().gameId)) {
                        mList.set(mList.indexOf(md), event);
                        mDownloadManagementAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        }
    }
}
