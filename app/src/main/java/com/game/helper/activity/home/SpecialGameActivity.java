package com.game.helper.activity.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.adapter.home.SpecialgameAdapter;
import com.game.helper.download.bean.AppContent;
import com.game.helper.leopardkit.DownLoadModel;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.QueryGameByModularTask;
import com.game.helper.sdk.model.returns.QueryGameByModular;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.SharedPreUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class SpecialGameActivity extends BaseActivity {

    @BindView(R.id.mSpecialGame_listView)
    ListView mSpecialGame_listView;
    SpecialgameAdapter mSpecialgameAdapter1;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;
    private List<AppContent> mmSpecialgameAdapter1List = new ArrayList<AppContent>();
    //private Map<String, Downloador> downloadorMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_special_game);
        ButterKnife.bind(this);
        // Register
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("特价游戏");
        topLeftBack.setVisibility(View.VISIBLE);
        mSpecialgameAdapter1 = new SpecialgameAdapter(mContext, mmSpecialgameAdapter1List, 1, R.id.mSpecialGame_listView);
        //mSpecialgameAdapter1.setOnItemClickListener(this);
        mSpecialGame_listView.setAdapter(mSpecialgameAdapter1);
        /*IntentFilter intent = new IntentFilter(Constants.DOWNLOAD_MSG);
		registerReceiver(downloadStatusReceiver, intent);*/
		/*mSpecialGame_listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Bundle bundle=new Bundle();
				bundle.putInt("currIndex", 0);
				bundle.putString("gameId", mSpecialgameAdapter1.getItem(position).getmAppContent().gameId);
				startActivity(GameDetailActivity.class,bundle);
				
			}
		});*/
    }

    @Override
    protected void initData() {
        String json1 = SharedPreUtil.getStringValue(mContext, ACTION_QueryGameByModular_TJ_N, "");
        if (!TextUtils.isEmpty(json1)) {
            Object mObject = new JsonBuild().getData(QueryGameByModular.class, json1);
            if (mObject != null && mObject instanceof QueryGameByModular) {
                QueryGameByModular mData = (QueryGameByModular) mObject;
                if (mData.data != null && mData.data.size() >= 0) {
                    mmSpecialgameAdapter1List.clear();
                    mmSpecialgameAdapter1List.addAll(mData.data);
                    //initStatus1();
                    mSpecialgameAdapter1.setmList(mmSpecialgameAdapter1List);

                }
            }
        }

        new QueryGameByModularTask(mContext, MODE_TJ, "","0", new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof QueryGameByModular) {
                    QueryGameByModular mQueryGameByModular = (QueryGameByModular) object;
                    if (mQueryGameByModular.data != null && mQueryGameByModular.data.size() >= 0) {
                        mmSpecialgameAdapter1List.clear();
                        mmSpecialgameAdapter1List.addAll(mQueryGameByModular.data);
                        //initStatus1();
                        mSpecialgameAdapter1.setmList(mmSpecialgameAdapter1List);
                        SharedPreUtil.putStringValue(mContext, ACTION_QueryGameByModular_TJ_N, new JsonBuild().setModel(object).getJson1());

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

    /**
     * 初始化下载状态
     *//*
	private void initStatus1() {
		List<AppContent> list = DBManager.getInstance(mContext).getAll();
		for (AppContent appContent : list) {
			for (AppContent app : mmSpecialgameAdapter1List) {
				if(app.downloadPath.trim().equals(appContent.downloadPath.trim())) {
					if(appContent.status==AppContent.Status.DOWNLOADING){
						
						if(BaseApplication.mInstance.downloadorMap != null && BaseApplication.mInstance.downloadorMap.containsKey(appContent.downloadPath.trim())) {
							BaseApplication.mInstance.downloadorMap.get(appContent.downloadPath.trim()).download();
						}else{
							if(BaseApplication.mInstance.downloadorMap == null) {
								BaseApplication.mInstance.downloadorMap = new HashMap<String, Downloador>();
							}
							Downloador downloador = new Downloador(getActivity(), appContent);
							downloador.download();
							BaseApplication.mInstance.downloadorMap.put(appContent.downloadPath.trim(), downloador);
						}
						
						appContent.status=AppContent.Status.PAUSED;
						Downloador downloador = new Downloador(getActivity(), appContent);
						downloador.download();
						if(downloadorMap == null) {
							downloadorMap = new HashMap<String, Downloador>();
						}
						downloadorMap.put(app.downloadPath.trim(), downloador);
					}
					app.status=(appContent.status);
					app.downloadPercent=(appContent.downloadPercent);
					break;
				}
			}
		}
		//初始化本地已下载完成存在的
		for(AppContent app : mmSpecialgameAdapter1List){
			if(app.status==AppContent.Status.PENDING){
				if(MonitorSysReceiver.isInstall(mContext.getApplicationContext(), app.packageName,app.gameName)){//已安装
					app.status=AppContent.Status.AlreadyInstalled;
					app.downloadPercent=100;
					DBManager.getInstance(mContext).insertDownloadFile(app);
					DBManager.getInstance(mContext).delDownloadInfoByUrl(app.downloadPath);
				}else{
					//未安装
					if(!TextUtils.isEmpty(app.downloadPath)){
						File file = new File(FileUtil.getDownloadDir(AppUtil.getContext()) + File.separator + 
								app.gameName+ ".apk");
						if (file.exists()){
							if (!TextUtils.isEmpty(app.fileSize)&&(Double.parseDouble(app.fileSize)*1024*1024)== file.length()) {
								app.status=AppContent.Status.FINISHED;
								app.downloadPercent=100;
								DBManager.getInstance(mContext).insertDownloadFile(app);
								DBManager.getInstance(mContext).delDownloadInfoByUrl(app.downloadPath);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void onItemClick(int position, int tag) {
		AppContent appContent = mmSpecialgameAdapter1List.get(position);
		if(appContent.status==AppContent.Status.PENDING){

			appContent.status=(AppContent.Status.DOWNLOADING);
			mSpecialgameAdapter1.notifyDataSetChanged();
			if(BaseApplication.mInstance.downloadorMap != null && BaseApplication.mInstance.downloadorMap.containsKey(appContent.downloadPath.trim())) {
				BaseApplication.mInstance.downloadorMap.get(appContent.downloadPath.trim()).download();
			}else{
				if(BaseApplication.mInstance.downloadorMap == null) {
					BaseApplication.mInstance.downloadorMap = new HashMap<String, Downloador>();
				}
				Downloador downloador = new Downloador(mContext, appContent);
				downloador.download();
				BaseApplication.mInstance.downloadorMap.put(appContent.downloadPath.trim(), downloador);
			}
		}else if(appContent.status==AppContent.Status.DOWNLOADING){
			if(BaseApplication.mInstance.downloadorMap != null && BaseApplication.mInstance.downloadorMap.containsKey(appContent.downloadPath.trim())) {

				BaseApplication.mInstance.downloadorMap.get(appContent.downloadPath.trim()).pause();
				BaseApplication.mInstance.downloadorMap.remove(appContent.downloadPath.trim());
			}
			if(appContent.downloadPercent>=0&&appContent.downloadPercent<100){
				appContent.status=(AppContent.Status.PAUSED);
			}else{
				appContent.status=(AppContent.Status.FINISHED);
			}
			mSpecialgameAdapter1.notifyDataSetChanged();
		}else if(appContent.status==AppContent.Status.PAUSED){

			appContent.status=(AppContent.Status.DOWNLOADING);
			mSpecialgameAdapter1.notifyDataSetChanged();
			if(BaseApplication.mInstance.downloadorMap != null && BaseApplication.mInstance.downloadorMap.containsKey(appContent.downloadPath.trim())) {
				BaseApplication.mInstance.downloadorMap.get(appContent.downloadPath.trim()).download();
			}else{
				if(BaseApplication.mInstance.downloadorMap == null) {
					BaseApplication.mInstance.downloadorMap = new HashMap<String, Downloador>();
				}
				Downloador downloador = new Downloador(mContext, appContent);
				downloador.download();
				BaseApplication.mInstance.downloadorMap.put(appContent.downloadPath.trim(), downloador);
			}
		}else if(appContent.status==AppContent.Status.FINISHED){

			appContent.status=(AppContent.Status.AlreadyInstalled);
			mRecommendBoutiqueAdapter.notifyDataSetChanged();

			if(BaseApplication.mInstance.downloadorMap != null && BaseApplication.mInstance.downloadorMap.containsKey(appContent.downloadPath.trim())) {

				BaseApplication.mInstance.downloadorMap.get(appContent.downloadPath.trim()).Installed();
				BaseApplication.mInstance.downloadorMap.remove(appContent.downloadPath.trim());
			}else{
				if(MonitorSysReceiver.isInstall(mContext.getApplicationContext(), appContent.packageName,appContent.gameName)){
					PackageManager packageManager = mContext.getApplicationContext().getPackageManager();  
					if(packageManager!=null){
						if(!TextUtils.isEmpty(appContent.packageName)){
							Intent intent=new Intent();   
							intent =packageManager.getLaunchIntentForPackage(appContent.packageName);   
							if(intent!=null){  
								startActivity(intent); 
							}  
						}else{
							PackageManager pm = mContext.getPackageManager();
							PackageInfo info = pm.getPackageArchiveInfo(FileUtil.getDownloadDir(AppUtil.getContext()) + File.separator + appContent.gameName + ".apk", PackageManager.GET_ACTIVITIES);
							ApplicationInfo appInfo = null;
							if (info != null) {
								appInfo = info.applicationInfo;
								String packageName = appInfo.packageName;
								Intent intent = mContext.getPackageManager()
										.getLaunchIntentForPackage(packageName);
								mContext.startActivity(intent);
							}
						}
					}
				}else{
					//未安装
					MonitorSysReceiver.intentInstall(mContext.getApplicationContext(), appContent);
				}
			}
		}else if(appContent.status==AppContent.Status.AlreadyInstalled){
			if(MonitorSysReceiver.isInstall(mContext.getApplicationContext(), appContent.packageName, appContent.gameName)){
				PackageManager packageManager = mContext.getApplicationContext().getPackageManager();  
				if(packageManager!=null){
					if(!TextUtils.isEmpty(appContent.packageName)){
						Intent intent=new Intent();   
						intent =packageManager.getLaunchIntentForPackage(appContent.packageName);   
						if(intent!=null){  
							startActivity(intent); 
						}  
					}else{
						PackageManager pm = mContext.getPackageManager();
						PackageInfo info = pm.getPackageArchiveInfo(FileUtil.getDownloadDir(AppUtil.getContext()) + File.separator + appContent.gameName + ".apk", PackageManager.GET_ACTIVITIES);
						ApplicationInfo appInfo = null;
						if (info != null) {
							appInfo = info.applicationInfo;
							String packageName = appInfo.packageName;
							Intent intent = mContext.getPackageManager()
									.getLaunchIntentForPackage(packageName);
							mContext.startActivity(intent);
						}
					}
				}
			}else{
				//未安装
				MonitorSysReceiver.intentInstall(mContext.getApplicationContext(), appContent);
			}
		}

	}
	private BroadcastReceiver downloadStatusReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			AppContent appContent = intent.getParcelableExtra("appContent");
			if(appContent == null) return;
			int itemIndex1 = 0;
			for(int i=0;i<mmSpecialgameAdapter1List.size();i++){
				//AppContent appContent1=mmSpecialgameAdapter1List.get(i);
				if(appContent.downloadPath.equals(mmSpecialgameAdapter1List.get(i).downloadPath)) {
					//itemIndex1 = mmSpecialgameAdapter1List.indexOf(appContent1);
					mmSpecialgameAdapter1List.get(i).downloadPercent=(appContent.downloadPercent);
					mmSpecialgameAdapter1List.get(i).status=(appContent.status);
					itemIndex1=i;
					break;
				}
			}
			updateView1(itemIndex1);
		}
	};
	private void updateView1(int itemIndex) {
		//得到第一个可显示控件的位置，
		int visiblePosition = mSpecialGame_listView.getFirstVisiblePosition();
		//只有当要更新的view在可见的位置时才更新，不可见时，跳过不更新
		if (itemIndex - visiblePosition >= 0) {
			//得到要更新的item的view
			View view = mSpecialGame_listView.getChildAt(itemIndex - visiblePosition);
			//调用adapter更新界面
			mSpecialgameAdapter1.updateView(view, mmSpecialgameAdapter1List.get(itemIndex));
		}
	}
*/
    @Override
    public void onDestroy() {
        //unregisterReceiver(downloadStatusReceiver);
        // Unregister
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    int i = 0;

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        MobclickAgent.onPageStart("SpecialGameActivity");
        super.onResume();
		/*if(i==0){
			i=1;
		}else{
			initData();
		}*/
    }

    @Override
    protected void onPause() {
        //DownLoadManager.getManager().stopAllTask();
        MobclickAgent.onPageEnd("SpecialGameActivity");
        super.onPause();
    }

    @Override
    public void onStop() {

        super.onStop();
    }

    public void onEventMainThread(DownLoadModel event) {
        if (event != null) {
            if (BaseApplication.mInstance.isRecommendBoutiqueAdapter != 0) {

                for (DownLoadModel md : mSpecialgameAdapter1.getData()) {
                    Log.e("lbb", "--------onEventMainThread5-------");
                    if (md.getmAppContent() != null && md.getmAppContent().gameId != null
                            && md.getmAppContent().gameId.equals(event.getmAppContent().gameId)) {

                        mSpecialgameAdapter1.getData().set(mSpecialgameAdapter1.getData().indexOf(md), event);
                        mSpecialgameAdapter1.notifyDataSetChanged();
                        break;
                    }
                }
            }
        }
    }
}
