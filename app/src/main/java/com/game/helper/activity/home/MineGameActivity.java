package com.game.helper.activity.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.adapter.home.MineGameAdapter;
import com.game.helper.db.manager.DBManager;
import com.game.helper.download.bean.AppContent;
import com.game.helper.installPackage.MonitorSysReceiver;
import com.game.helper.leopardkit.DownLoadModel;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.MygameTask;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.model.returns.Mygame;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.SharedPreUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * @Description 我的游戏
 * @Path com.game.helper.activity.home.MineGameActivity.java
 * @Author lbb
 * @Date 2016年8月23日 上午8:58:14
 * @Company
 */
public class MineGameActivity extends BaseActivity {
    @BindView(R.id.mLinear_NULL)
    LinearLayout mLinear_NULL;
    @BindView(R.id.minegame_listView)
    ListView minegame_listView;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_right)
    TextView topRight;
    @BindView(R.id.top_liner_right)
    RelativeLayout top_liner_right;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.top_iv_right)
    ImageView top_iv_right;
    private List<AppContent> mList = new ArrayList<AppContent>();
    MineGameAdapter mMineGameAdapter;
    //private Map<String, Downloador> downloadorMap;

    LoginData user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_mine_game);
        ButterKnife.bind(this);
        // Register
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        user = DBManager.getInstance(mContext).getUserMessage();

        topTitle.setText("我的游戏");
        topLeftBack.setVisibility(View.VISIBLE);
        top_iv_right.setImageDrawable(getResources().getDrawable(R.drawable.shouye_17));
        top_liner_right.setVisibility(View.VISIBLE);

        mMineGameAdapter = new MineGameAdapter(mContext, mList, 1, R.id.minegame_listView);
        //mMineGameAdapter.setOnItemClickListener(this);
        minegame_listView.setAdapter(mMineGameAdapter);
        mLinear_NULL.setVisibility(View.GONE);
        minegame_listView.setVisibility(View.VISIBLE);


		/*minegame_listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Bundle bundle=new Bundle();
				bundle.putInt("currIndex", 0);
				bundle.putString("gameId", mMineGameAdapter.getItem(arg2).getmAppContent().gameId);
				startActivity(GameDetailActivity.class,bundle);
			}
		});*/

		/*IntentFilter intent = new IntentFilter(Constants.DOWNLOAD_MSG);
        registerReceiver(downloadStatusReceiver, intent);*/

    }

    @Override
    protected void initData() {

        String json = SharedPreUtil.getStringValue(mContext, ACTION_Mygame + "_" + user.userId, "");
        if (!TextUtils.isEmpty(json)) {
            Object mObject = new JsonBuild().getData(Mygame.class, json);
            if (mObject != null && mObject instanceof Mygame) {
                Mygame mData = (Mygame) mObject;
                if (mData.data != null && mData.data.size() >= 0) {
                    if (mData.data.size() == 0) {
                        mLinear_NULL.setVisibility(View.VISIBLE);
                        minegame_listView.setVisibility(View.GONE);
                    } else {
                        mLinear_NULL.setVisibility(View.GONE);
                        minegame_listView.setVisibility(View.VISIBLE);
                    }

                    mList.clear();
                    //mList.addAll(mData.data);
                    for (AppContent mAppContent : mData.data) {
                        if (MonitorSysReceiver.checkApkExist(mContext, mAppContent.packageName, mAppContent.gameId)) {
                            mList.add(mAppContent);
                        }
                    }
                    //initStatus1();
                    mMineGameAdapter.setmList(mList);

                } else {
                    mLinear_NULL.setVisibility(View.VISIBLE);
                    minegame_listView.setVisibility(View.GONE);
                }
            }
        }
        new MygameTask(mContext, true, user.userId, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof Mygame) {
                    Mygame mData = (Mygame) object;
                    if (mData.data != null && mData.data.size() >= 0) {
                        if (mData.data.size() == 0) {
                            mLinear_NULL.setVisibility(View.VISIBLE);
                            minegame_listView.setVisibility(View.GONE);
                        } else {
                            mLinear_NULL.setVisibility(View.GONE);
                            minegame_listView.setVisibility(View.VISIBLE);
                        }

                        mList.clear();
                        //mList.addAll(mData.data);
                        for (AppContent mAppContent : mData.data) {
                            if (MonitorSysReceiver.checkApkExist(mContext, mAppContent.packageName, mAppContent.gameId)) {
                                mList.add(mAppContent);
                            }
                        }
                        //	initStatus1();
                        mMineGameAdapter.setmList(mList);
                        //mLinear_NULL.setVisibility(View.GONE);
                        //minegame_listView.setVisibility(View.VISIBLE);

                        SharedPreUtil.putStringValue(mContext, ACTION_Mygame + "_" + user.userId, new JsonBuild().setModel(object).getJson1());
                    } else {
                        mLinear_NULL.setVisibility(View.VISIBLE);
                        minegame_listView.setVisibility(View.GONE);
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
    @OnClick({R.id.top_left_layout, R.id.top_liner_right})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            case R.id.top_liner_right:
                startActivity(DownloadManagementActivity.class);
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

	/*@Override
    public void onItemClick(int position) {
		AppContent appContent = mList.get(position);
		if(appContent.status==AppContent.Status.PENDING){

			appContent.status=(AppContent.Status.DOWNLOADING);
			mMineGameAdapter.notifyDataSetChanged();
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
			mMineGameAdapter.notifyDataSetChanged();
		}else if(appContent.status==AppContent.Status.PAUSED){

			appContent.status=(AppContent.Status.DOWNLOADING);
			mMineGameAdapter.notifyDataSetChanged();
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
	 */

    /**
     * 初始化下载状态
     *//*
	private void initStatus1() {
		List<AppContent> list = DBManager.getInstance(mContext.getApplicationContext()).getAll();
		for (AppContent appContent : list) {
			for (AppContent app : mList) {
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
		for(AppContent app : mList){
			if(app.status==AppContent.Status.PENDING){
				if(MonitorSysReceiver.isInstall(mContext.getApplicationContext(), app.packageName, app.gameName)){//已安装
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
	private BroadcastReceiver downloadStatusReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			AppContent appContent = intent.getParcelableExtra("appContent");
			if(appContent == null) return;
			int itemIndex1 = 0;
			for(int i=0;i<mList.size();i++){
				AppContent appContent1=mList.get(i);
				if(appContent.downloadPath.equals(appContent1.downloadPath)) {
					itemIndex1 = mList.indexOf(appContent1);
					appContent1.downloadPercent=(appContent.downloadPercent);
					appContent1.status=(appContent.status);
					itemIndex1=i;
					break;
				}
			}
			updateView1(itemIndex1);
		}
	};
	private void updateView1(int itemIndex) {
		//得到第一个可显示控件的位置，
		int visiblePosition = minegame_listView.getFirstVisiblePosition();
		//只有当要更新的view在可见的位置时才更新，不可见时，跳过不更新
		if (itemIndex - visiblePosition >= 0) {
			//得到要更新的item的view
			View view = minegame_listView.getChildAt(itemIndex - visiblePosition);
			//调用adapter更新界面
			mMineGameAdapter.updateView(view, itemIndex);
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

    @Override
    protected void onPause() {
        //DownLoadManager.getManager().stopAllTask();
        MobclickAgent.onPageEnd("MineGameActivity");
        super.onPause();
    }

    int i = 0;

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
		/*if(i==0){
			i=1;
		}else{
			initData();
		}*/
        MobclickAgent.onPageStart("MineGameActivity");
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public void onEventMainThread(DownLoadModel event) {
        if (event != null) {
            if (BaseApplication.mInstance.isRecommendBoutiqueAdapter != 0) {

                for (DownLoadModel md : mMineGameAdapter.getmList()) {
                    Log.e("lbb", "--------onEventMainThread41-------");
                    if (md.getmAppContent() != null && md.getmAppContent().gameId != null
                            && md.getmAppContent().gameId.equals(event.getmAppContent().gameId)) {

                        mMineGameAdapter.getmList().set(mMineGameAdapter.getmList().indexOf(md), event);
                        mMineGameAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        }
    }

}
