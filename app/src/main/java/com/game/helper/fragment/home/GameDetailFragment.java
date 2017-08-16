package com.game.helper.fragment.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.BaseFragment;
import com.game.helper.R;
import com.game.helper.activity.WebActivity;
import com.game.helper.activity.home.ImageActivity;
import com.game.helper.db.manager.DBManager;
import com.game.helper.download.bean.AppContent;
import com.game.helper.installPackage.MonitorSysReceiver;
import com.game.helper.leopardkit.DownLoadModel;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.DownloadFinishTask;
import com.game.helper.net.task.QueryGameImgTask;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.model.returns.QueryGameImg;
import com.game.helper.sdk.model.returns.QueryGameImg.GameImg;
import com.game.helper.view.widget.ListViewForScrollView;
import com.umeng.analytics.MobclickAgent;
import com.yuan.leopardkit.LeopardHttp;
import com.yuan.leopardkit.db.HttpDbUtil;
import com.yuan.leopardkit.download.DownLoadManager;
import com.yuan.leopardkit.download.model.DownloadInfo;
import com.yuan.leopardkit.interfaces.IProgress;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;

@SuppressLint("ValidFragment")

/**
 * @Description 游戏详情-详情
 * @Path com.game.helper.fragment.home.GameDetailFragment.java
 * @Author lbb
 * @Date 2016年8月23日 上午10:52:12
 * @Company
 */
public class GameDetailFragment extends BaseFragment {
    TextView tv_content;

    ListViewForScrollView lv;
    private View headerView;
    private LinearLayout header_ll;
    ArrayList<String> arrayList = new ArrayList<String>();
    List<GameImg> datas = new ArrayList<GameImg>();
    ArrayList<String> datas1 = new ArrayList<String>();
    ArrayAdapter adapter;

    LinearLayout btnLinear;
    TextView btn_login;
    TextView btn_msg;

    //ImageView iv_gamed1;
    //ImageView iv_gamed2;
    protected LayoutInflater mInflater;

    public GameDetailFragment() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Register
        EventBus.getDefault().register(this);
    }

    public GameDetailFragment(BaseApplication application, Activity activity, Context context) {
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
            mView = inflater.inflate(R.layout.fragment_home_game_detail, container, false);
            ButterKnife.bind(this, mView);
            initViews();
            initEvents();
            init();
        }
        FragmentCache(mView);
        return mView;
    }


    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    protected void initViews() {
        mInflater = LayoutInflater.from(getActivity());
        //iv_gamed1 = (ImageView) findViewById(R.id.iv_gamed1);
        //iv_gamed2 = (ImageView) findViewById(R.id.iv_gamed2);

        btnLinear = (LinearLayout) findViewById(R.id.btnLinear);
        btn_msg = (TextView) findViewById(R.id.btn_msg);

        tv_content = (TextView) findViewById(R.id.tv_content);
        btn_login = (TextView) findViewById(R.id.btn_login);
        lv = (ListViewForScrollView) findViewById(R.id.lv);


        headerView = mInflater.inflate(
                R.layout.item_listview_home_game_detail_header, null);
        header_ll = (LinearLayout) headerView.findViewById(R.id.header_ll);

        adapter = new ArrayAdapter(getActivity(), R.layout.item_listview_home_game,
                R.id.textView1, arrayList);

        lv.setAdapter(adapter);
    }

    @Override
    protected void initEvents() {
        //btn_login.setOnClickListener(this);
        //iv_gamed1.setOnClickListener(this);
        // iv_gamed2.setOnClickListener(this);
    }

    public void setHeadSign() {
        try {
            lv.setAdapter(null);
            lv.removeHeaderView(headerView);
            header_ll.removeAllViews();

            for (int i = 0; i < datas.size(); i++) {
                final int a = i;
                View coupon_home_ad_item = mInflater.inflate(
                        R.layout.item_listview_home_game_detail, null);
                final ImageView icon = (ImageView) coupon_home_ad_item
                        .findViewById(R.id.coupon_ad_iv);//
                final String href = "" + datas.get(i).fileAskPath + datas.get(i).img;
                Glide.with(BaseApplication.mInstance.context.getApplicationContext())
                        .load(href)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        //.centerCrop()// 长的一边撑满
                        //.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片
                        .error(R.drawable.picture_defeated)//加载失败时显示的图片
                        //.crossFade()
                        .into(icon);

                if (!TextUtils.isEmpty(href)) {
                    coupon_home_ad_item.setOnClickListener(new OnClickListener() {//

                        @Override
                        public void onClick(View v) {
                            /*Intent intent = new Intent(getActivity(), SpaceImageDetailActivity.class);
                        intent.putExtra("images", datas1);
						intent.putExtra("position", a);
						int[] location = new int[2];
						icon.getLocationOnScreen(location);
						intent.putExtra("locationX", location[0]);
						intent.putExtra("locationY", location[1]);

						intent.putExtra("width", icon.getWidth());
						intent.putExtra("height", icon.getHeight());
						getActivity().startActivity(intent);
						//getActivity().overridePendingTransition(0, 0);
							 * */
                            Intent intent = new Intent(getActivity(), ImageActivity.class);
                            intent.putExtra("image_position", a);
                            intent.putExtra("image_Total", datas1.size());
                            intent.putExtra("images", datas1);
                            getActivity().startActivity(intent);
                        }
                    });

                }
                header_ll.addView(coupon_home_ad_item);

            }
            lv.addHeaderView(headerView);//
            lv.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    AppContent appContent;
    DownLoadModel model = null;
    String gameId, detail, fileSize;

    @Override
    protected void init() {
        gameId = getArguments().getString("gameId", "");
        detail = getArguments().getString("detail", "");
        fileSize = getArguments().getString("fileSize", "0");
        appContent = getArguments().getParcelable("appContent");

        tv_content.setText("" + Html.fromHtml(detail));
        if (appContent != null) {
            DownloadInfo info = new DownloadInfo();
            info.setUrl(appContent.downloadPath);
            info.setProgress(0L);
            info.setFileName("IRecordApp_" + appContent.gameId + ".apk");
            model = new DownLoadModel();
            model.setInfo(info);
            model.setmAppContent(appContent);

            List<DownloadInfo> downloadInfoList = HttpDbUtil.initHttpDB(mContext).queryFileInfo(0);
            for (DownloadInfo infos : downloadInfoList) {
                if (infos != null) {
                    if (model.getInfo().getFileName().equals(infos.getFileName())) {
                        model.setInfo(infos);
                        break;
                    }
                }
            }

            setData(model, model.getInfo());

        }

        new QueryGameImgTask(mContext, gameId, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof QueryGameImg) {
                    QueryGameImg mQueryGameImg = (QueryGameImg) object;
                    if (mQueryGameImg.data != null) {
                        datas1.clear();
                        datas.clear();
                        datas.addAll(mQueryGameImg.data);
                        for (int i = 0; i < datas.size(); i++) {
                            datas1.add(datas.get(i).fileAskPath + datas.get(i).img);
                        }
                        setHeadSign();
                    }
                }

            }

            @Override
            public void fail(String status, String msg, Object object) {
                // TODO Auto-generated method stub

            }
        }).start();
    }

    public void setData(DownLoadModel mDownLoadModel, final DownloadInfo info) {
        if (mDownLoadModel != null) {

            Log.e("lbb", "-----setData--88-----");
            final AppContent mAppContent = mDownLoadModel.getmAppContent();
            if ((!TextUtils.isEmpty(mAppContent.typeId) && mAppContent.typeId.equals("3"))
                    || (!TextUtils.isEmpty(mAppContent.typeName) && mAppContent.typeName.equals("H5游戏"))) {
                btn_login.setText("打开");
                btn_msg.setText("");
                btnLinear.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("Url", mAppContent.downloadPath);
                        bundle.putString("TITLE", mAppContent.gameName);
                        ((BaseActivity) mContext).startActivity(WebActivity.class, bundle);
                    }
                });
                return;
            }
            if (info.getFileLength() != 0) {

                if (info.getState() == DownLoadManager.STATE_FINISH) {
                    if (((int) ((float) info.getProgress() / info.getFileLength() * 100)) == 100) {

                        btn_login.setText("安装");
                        btn_msg.setText("");
                    } else {
                        btn_login.setText("下载");
                        info.setState(DownLoadManager.STATE_WAITING);
                        if (info.getDownLoadTask() == null) {
                            DownloadInfo mDownloadInfo = DownLoadManager.getManager().getDownloadInfo(info);
                            if (mDownloadInfo != null) {
                                info.setDownLoadTask(mDownloadInfo.getDownLoadTask());
                                info.getDownLoadTask().setState(DownLoadManager.STATE_WAITING);
                            }
                        } else {
                            info.getDownLoadTask().setState(DownLoadManager.STATE_WAITING);
                        }
                    }
                }
            }
            if (info.getState() == DownLoadManager.STATE_WAITING) {
                if (info.getFileLength() != 0) {
                    btn_login.setText("立即下载");
                    btn_msg.setText("（" + Double.toString(getRealNum(info.getFileLength() / 1024 / 1024)) + "MB）");
                } else {
                    btn_login.setText("立即下载");
                    btn_msg.setText("（" + fileSize + "MB）");
                }
            }
            if (info.getState() == DownLoadManager.STATE_PAUSE) {
                btn_login.setText("点击继续");
                double curP = 0;
                double curTotal = 0;
                long progress = info.getProgress();
                long total = info.getFileLength();
                if (progress / 1024L < 1024L) {
                    curP = (double) progress / 1024;
                    curTotal = (double) total / 1024L / 1024L;
                    btn_msg.setText("（" + Double.toString(getRealNum(curP)) + "KB/" + Double.toString(getRealNum(curTotal)) + "MB"
                            + "  " + (int) ((float) info.getProgress() / info.getFileLength() * 100) + "%）");
                } else {
                    curP = (double) progress / 1024L / 1024L;
                    curTotal = (double) total / 1024L / 1024L;
                    btn_msg.setText("（" + Double.toString(getRealNum(curP)) + "MB/" + Double.toString(getRealNum(curTotal)) + "MB"
                            + "  " + (int) ((float) info.getProgress() / info.getFileLength() * 100) + "%）");
                }
            }
            if (info.getState() == DownLoadManager.STATE_DOWNLOADING) {
                btn_login.setText("点击暂停");
                double curP = 0;
                double curTotal = 0;
                long progress = info.getProgress();
                long total = info.getFileLength();
                if (progress / 1024L < 1024L) {
                    curP = (double) progress / 1024;
                    curTotal = (double) total / 1024L / 1024L;
                    btn_msg.setText("（" + Double.toString(getRealNum(curP)) + "KB/" + Double.toString(getRealNum(curTotal)) + "MB"
                            + "  " + (int) ((float) info.getProgress() / info.getFileLength() * 100) + "%）");
                } else {
                    curP = (double) progress / 1024L / 1024L;
                    curTotal = (double) total / 1024L / 1024L;
                    btn_msg.setText("（" + Double.toString(getRealNum(curP)) + "MB/" + Double.toString(getRealNum(curTotal)) + "MB"
                            + "  " + (int) ((float) info.getProgress() / info.getFileLength() * 100) + "%）");
                }
            }
            if (info.getState() == DownLoadManager.STATE_ERROR) {
                btn_login.setText("等待");
            }
            if (info.getState() == DownLoadManager.AlreadyInstalled) {
                btn_login.setText("打开");
            }


            btnLinear.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                    BaseApplication.mInstance.isRecommendBoutiqueAdapter = 6;
                    DBManager.getInstance(mContext).insertDownloadFile(mAppContent);
                    if (info.getDownLoadTask() == null && (DownLoadManager.getManager().isHaved(info) == false || DownLoadManager.getManager().getDownloadInfo(info) == null)) {
                        //add task
                        LeopardHttp.DWONLOAD(info, new IProgress() {
                            @SuppressLint("NewApi")
                            @Override
                            public void onProgress(long progress, long total, boolean done) {
                                DownloadInfo infos = info.getDownLoadTask().getDownloadInfo();
                                DownLoadModel mDown = new DownLoadModel();
                                mDown.setmAppContent(mAppContent);
                                mDown.setInfo(infos);
                                Log.e("lbb", "------getState--9----" + infos.getState());

                                EventBus.getDefault().post(mDown);
                                if (done && ((int) ((float) progress / total * 100)) == 100) {
                                    LoginData user = DBManager.getInstance(mContext).getUserMessage();
                                    new DownloadFinishTask(mContext, user.userId, mAppContent.gameId, new Back() {

                                        @Override
                                        public void success(Object object, String msg) {
                                            // TODO Auto-generated method stub

                                        }

                                        @Override
                                        public void fail(String status, String msg, Object object) {
                                            // TODO Auto-generated method stub

                                        }
                                    }).start();
                                    if (MonitorSysReceiver.checkApkExist(mContext, mAppContent.packageName, mAppContent.gameId)) {
                                    } else {

                                        // 下载完成后弹出安装窗
                                        File file = new File(DownLoadManager.getManager().deFaultDir + infos.getFileName());
                                        Intent intentInstall = new Intent();
                                        intentInstall.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intentInstall.setAction(Intent.ACTION_VIEW);
                                        intentInstall.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                                        mContext.startActivity(intentInstall);
                                    }
                                }
                            }
                        },mContext);
                    }
                    if (info.getDownLoadTask() == null) {
                        DownloadInfo mDownloadInfo = DownLoadManager.getManager().getDownloadInfo(info);
                        if (mDownloadInfo != null) {
                            info.setDownLoadTask(mDownloadInfo.getDownLoadTask());
                        }
                    }
                    DownloadInfo infos = info.getDownLoadTask().getDownloadInfo();
                    if (infos.getState() == DownLoadManager.STATE_FINISH) {
                        if (((int) ((float) infos.getProgress() / infos.getFileLength() * 100)) == 100) {
                            btn_login.setText("打开");
                            info.setState(DownLoadManager.AlreadyInstalled);
                            infos.setState(DownLoadManager.AlreadyInstalled);
                            if (infos.getDownLoadTask() == null) {
                                DownloadInfo mDownloadInfo = DownLoadManager.getManager().getDownloadInfo(infos);
                                if (mDownloadInfo != null) {
                                    infos.setDownLoadTask(mDownloadInfo.getDownLoadTask());
                                    infos.getDownLoadTask().setState(DownLoadManager.AlreadyInstalled);
                                } else {
                                    HttpDbUtil.initHttpDB(mContext).update(infos);
                                }

                            } else {
                                infos.getDownLoadTask().setState(DownLoadManager.AlreadyInstalled);
                            }
                            if (MonitorSysReceiver.checkApkExist(mContext, mAppContent.packageName, mAppContent.gameId)) {
                                PackageManager packageManager = mContext.getApplicationContext().getPackageManager();
                                if (packageManager != null) {
                                    Intent intent = new Intent();
                                    intent = packageManager.getLaunchIntentForPackage(mAppContent.packageName);
                                    if (intent != null) {
                                        mContext.startActivity(intent);
                                    }
                                }
                            } else {

                                // 下载完成后弹出安装窗
                                File file = new File(DownLoadManager.getManager().deFaultDir + infos.getFileName());
                                Intent intentInstall = new Intent();
                                intentInstall.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intentInstall.setAction(Intent.ACTION_VIEW);
                                intentInstall.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                                mContext.startActivity(intentInstall);
                            }

                            DownloadInfo infoss = infos.getDownLoadTask().getDownloadInfo();
                            DownLoadModel mDown = new DownLoadModel();
                            mDown.setmAppContent(mAppContent);
                            mDown.setInfo(infoss);
                            EventBus.getDefault().post(mDown);
                            return;
                        } else {
                            //去重新下载
                            btn_login.setText("点击暂停");
                            if (infos.getFileLength() != 0) {
                                btn_login.setText("立即下载");
                                btn_msg.setText("（" + Double.toString(getRealNum(infos.getFileLength() / 1024 / 1024)) + "MB）");
                            } else {
                                btn_login.setText("立即下载");
                                btn_msg.setText("（" + fileSize + "MB）");
                            }
                            infos.getDownLoadTask().reStart();

                            DownloadInfo infoss = infos.getDownLoadTask().getDownloadInfo();
                            DownLoadModel mDown = new DownLoadModel();
                            mDown.setmAppContent(mAppContent);
                            mDown.setInfo(infoss);
                            EventBus.getDefault().post(mDown);
                            return;
                        }

                    }

                    if (infos.getState() == DownLoadManager.STATE_WAITING) {
                        btn_login.setText("点击暂停");

                        //infos.getDownLoadTask().setState(DownLoadManager.STATE_PAUSE);
                        infos.getDownLoadTask().reStart();

                        DownloadInfo infoss = infos.getDownLoadTask().getDownloadInfo();
                        DownLoadModel mDown = new DownLoadModel();
                        mDown.setmAppContent(mAppContent);
                        mDown.setInfo(infoss);
                        EventBus.getDefault().post(mDown);
                        return;
                    }

                    if (infos.getState() == DownLoadManager.STATE_PAUSE) {
                        btn_login.setText("点击暂停");

                        //infos.getDownLoadTask().reStart();
                        infos.getDownLoadTask().resume();

                        DownloadInfo infoss = infos.getDownLoadTask().getDownloadInfo();
                        DownLoadModel mDown = new DownLoadModel();
                        mDown.setmAppContent(mAppContent);
                        mDown.setInfo(infoss);
                        EventBus.getDefault().post(mDown);
                        return;
                    }
                    if (infos.getState() == DownLoadManager.STATE_DOWNLOADING) {
                        btn_login.setText("点击继续");
                        infos.getDownLoadTask().pause();

                        DownloadInfo infoss = infos.getDownLoadTask().getDownloadInfo();
                        DownLoadModel mDown = new DownLoadModel();
                        mDown.setmAppContent(mAppContent);
                        mDown.setInfo(infoss);
                        EventBus.getDefault().post(mDown);
                        return;
                    }
                    if (infos.getState() == DownLoadManager.STATE_ERROR) {
                        btn_login.setText("点击暂停");
                        if (infos.getFileLength() != 0) {
                            btn_login.setText("立即下载");
                            btn_msg.setText("（" + Double.toString(getRealNum(infos.getFileLength() / 1024 / 1024)) + "MB）");
                        } else {
                            btn_login.setText("立即下载");
                            btn_msg.setText("（" + fileSize + "MB）");
                        }
                        infos.getDownLoadTask().reStart();

                        DownloadInfo infoss = infos.getDownLoadTask().getDownloadInfo();
                        DownLoadModel mDown = new DownLoadModel();
                        mDown.setmAppContent(mAppContent);
                        mDown.setInfo(infoss);
                        EventBus.getDefault().post(mDown);
                        return;
                    }
                    if (infos.getState() == DownLoadManager.AlreadyInstalled) {
                        btn_login.setText("打开");
                        if (MonitorSysReceiver.checkApkExist(mContext, mAppContent.packageName, mAppContent.gameId)) {
                            PackageManager packageManager = mContext.getApplicationContext().getPackageManager();
                            if (packageManager != null) {
                                Intent intent = new Intent();
                                intent = packageManager.getLaunchIntentForPackage(mAppContent.packageName);
                                if (intent != null) {
                                    mContext.startActivity(intent);
                                }
                            }
                        } else {

                            // 下载完成后弹出安装窗
                            File file = new File(DownLoadManager.getManager().deFaultDir + infos.getFileName());
                            Intent intentInstall = new Intent();
                            intentInstall.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intentInstall.setAction(Intent.ACTION_VIEW);
                            intentInstall.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                            mContext.startActivity(intentInstall);
                        }

                        DownloadInfo infoss = infos.getDownLoadTask().getDownloadInfo();
                        DownLoadModel mDown = new DownLoadModel();
                        mDown.setmAppContent(mAppContent);
                        mDown.setInfo(infoss);
                        EventBus.getDefault().post(mDown);
                        return;
                    }
                }
            });
        }
    }

    private double getRealNum(double num) {
        BigDecimal bg = new BigDecimal(num);
        return bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.iv_gamed1:
                ((BaseActivity) getActivity()).startActivity(MineFeedBackActivity.class);
                break;*/
            /*case R.id.iv_gamed2:

                ((BaseActivity) getActivity()).startActivity(InviteFriendsActivity.class);
                break;*/
            default:
                super.onClick(v);
                break;
        }
    }
	/*private BroadcastReceiver downloadStatusReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			AppContent appContents = intent.getParcelableExtra("appContent");
			if(appContents == null) return;
			if(appContent==null) return;
			if(!TextUtils.isEmpty(appContents.downloadPath)&&!TextUtils.isEmpty(appContent.downloadPath)
					&&appContents.downloadPath.trim().equals(appContent.downloadPath.trim())){
				appContent=appContents;
				appContent.downloadPercent=(appContents.downloadPercent);
				//appContent.status=(appContents.status);
				setStatus();
			}
		}
	};*/


    /*public void setStatus(){
        if(appContent!=null){
            switch (appContent.status) {
            case PENDING://0

                btn_login.setText("立即下载（"+fileSize +"M）");
                break;
            case DOWNLOADING:
                btn_login.setText("暂停（"+appContent.downloadPercent +"%）");

                break;
            case PAUSED:
                btn_login.setText("继续（"+appContent.downloadPercent +"%）");

                break;
            case FINISHED://100

                btn_login.setText("安装");
                break;
            case AlreadyInstalled:
                btn_login.setText("打开");
                break;
            default:
                break;
            }

        }
    }*/
    @Override
    public void onStop() {

        //	DownLoadManager.getManager().stopAllTask();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        //getActivity().unregisterReceiver(downloadStatusReceiver);
        super.onDestroy();
        // Unregister
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(DownLoadModel event) {
        if (event != null) {
            if (model != null && model.getmAppContent() != null && model.getmAppContent().gameId != null
                    && model.getmAppContent().gameId.equals(event.getmAppContent().gameId)) {
                model = event;
                setData(model, model.getInfo());
            }
        }
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        MobclickAgent.onPageEnd("GameDetailFragment");
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        MobclickAgent.onPageStart("GameDetailFragment");
    }

}
