package com.game.helper.adapter.home;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.activity.WebActivity;
import com.game.helper.activity.home.GameDetailActivity;
import com.game.helper.db.DbBtHelper;
import com.game.helper.db.manager.DBManager;
import com.game.helper.download.DownLoaderManger;
import com.game.helper.download.OnProgressListener;
import com.game.helper.download.bean.AppContent;
import com.game.helper.download.bean.FileInfo;
import com.game.helper.installPackage.MonitorSysReceiver;
import com.game.helper.leopardkit.DownLoadModel;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.DownloadFinishTask;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.services.ui.NumberProgressBar;
import com.game.helper.util.FileUtil;
import com.game.helper.util.ToastUtil;
import com.game.helper.view.widget.XCRoundImageViewByXfermode;
import com.yuan.leopardkit.LeopardHttp;
import com.yuan.leopardkit.db.HttpDbUtil;
import com.yuan.leopardkit.download.DownLoadManager;
import com.yuan.leopardkit.download.model.DownloadInfo;
import com.yuan.leopardkit.interfaces.IProgress;
import com.yuan.leopardkit.utils.NetWorkUtil;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * @Description
 * @Path com.game.helper.adapter.home.SpecialgameAdapter.java
 * @Author lbb
 * @Date 2016年8月22日 下午7:45:20
 * @Company
 */
public class BtGameAdapter extends BaseAdapter implements OnProgressListener {

    private Long mTaskId;
    private DownloadManager downloadManager;
    private ViewHolder holder;
    private TextView tv_start;
    private DownLoaderManger downLoader;
    private FileInfo info;
    private String apkname="";

    public interface MOnItemClickListener11 {
        void onItemClick();
    }

    MOnItemClickListener11 l;

    public void setOnItemClickListener(MOnItemClickListener11 l) {
        this.l = l;
    }

    List<DownLoadModel> data = new ArrayList<DownLoadModel>();
    private List<View> viewList = new ArrayList<View>();//View对象集合
    private Context mContext;
    protected LayoutInflater mInflater;
    int tag;
    int listviewId;

    public BtGameAdapter(Context mContext, List<AppContent> mList, int tag, int listviewId) {
        super();
        mInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.listviewId = listviewId;
        this.tag = tag;//tag=3 表示从游戏详情里进入的。
        if (null == mList || mList.size() == 0) {
            //ToastUtil.showToast(mContext, "数据错误");
            return;
        } else {
            init(mList);
        }

    }

    public void init(List<AppContent> mList) {
        viewList.clear();
        data.clear();
        DownLoadModel model = null;
        for (AppContent mAppContent : mList) {
           /* DownloadInfo info = new DownloadInfo();
            info.setUrl(mAppContent.downloadPath);
            info.setProgress(0L);
            info.setFileName("IRecordApp_" + mAppContent.gameId + ".apk");
*/
            model = new DownLoadModel();
            //model.setInfo(info);
            model.setmAppContent(mAppContent);
            data.add(model);
        }
        //List<DownloadInfo> downloadInfoList = HttpDbUtil.initHttpDB(mContext).queryFileInfo(0);

        /*for (DownLoadModel mD : data) {
            if (mD != null && mD.getInfo() != null
                    && !TextUtils.isEmpty(mD.getInfo().getFileName())) {
                for (DownloadInfo info : downloadInfoList) {
                    if (info != null) {
                        if (mD.getInfo().getFileName().equals(info.getFileName())) {
                            mD.setInfo(info);
                            break;
                        }
                    }
                }
            }
        }*/

        final DbBtHelper helper = new DbBtHelper(mContext);
        downLoader = DownLoaderManger.getInstance(helper, this);
    }


    /**
     * 进度条监听的回调
     *
     * @param max
     * @param progress
     */
    @Override
    public void updateProgress(int max, int progress) {
        holder.pb_update_progress.setMax(max);
        holder.pb_update_progress.setProgress(progress);
    }

    public List<DownLoadModel> getData() {
        return data;
    }

    public void setmList(List<AppContent> mList) {
        init(mList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public DownLoadModel getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        holder = null;
        if (convertView == null) {
            if (tag != 3) {
                convertView = mInflater.inflate(R.layout.item_btgame_list, null);
            }
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_item.setText(data.get(position).getmAppContent().gameName);

        tv_start.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!apkname.equals("IRecordApp_" + data.get(position).getmAppContent().gameId + ".apk")){
                    info = new FileInfo("IRecordApp_" + data.get(position).getmAppContent().gameId + ".apk", data.get(position).getmAppContent().downloadPath);
                    downLoader.addTask(info);
                    apkname = "IRecordApp_" + data.get(position).getmAppContent().gameId + ".apk";
                }

                if (downLoader.getCurrentState(info.getUrl())) {
                    downLoader.stop(info.getUrl());
                    tv_start.setText("开始下载");
                } else {
                    downLoader.start(info.getUrl());
                    tv_start.setText("暂停下载");
                }
            }
        });


        holder.tv_download.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                /*Intent i = new Intent(mContext, DownloadService.class);
                i.putExtra("versionUrl", data.get(position).getmAppContent().downloadPath);
                i.putExtra("versionName", data.get(position).getmAppContent().);
                startService(i);*/

            }
        });


       /* holder.tv_download.setTag(position);
        final DownloadInfo info = data.get(position).getInfo();
        holder.setData(data.get(position), info, position);
        if (tag != 3) {
            holder.mLinearClick.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (l != null) {
                        l.onItemClick();
                    }
                    Bundle bundle = new Bundle();
                    bundle.putInt("currIndex", 0);
                    bundle.putString("gameId", getItem(position).getmAppContent().gameId);
                    bundle.putParcelable("appcontent", getItem(position).getmAppContent());
                    ((BaseActivity) mContext).startActivity(GameDetailActivity.class, bundle);
                }
            });
        } else if (tag == 3) {
            holder.game_detail_frome_tv.setText(data.get(position).getmAppContent().platName);
        }
        *//* 标识View对象 *//*
        //将list_view的ID作为Tag的Key值
        convertView.setTag(listviewId, position);//此处将位置信息作为标识传递
        viewList.add(convertView);*/

        return convertView;
    }


    class ViewHolder {
        public LinearLayout mLinearClick;

        public XCRoundImageViewByXfermode iv_item;
        public LinearLayout mLinear_dz;
        public TextView tv_dz;

        public TextView tv_item;
        public TextView tv_gift;

        public LinearLayout item_mLinearMsg;
        //public TextView tv_type;
        public TextView tv_datasize;

        public TextView tv_msg;

        public LinearLayout mLinear_progress;
        public NumberProgressBar pb_update_progress;
        public TextView prgressTv;
        public TextView progressShow;

        public TextView tv_download;

        public TextView game_detail_frome_tv;


        public ViewHolder(View view) {
            super();
            mLinearClick = (LinearLayout) view.findViewById(R.id.mLinearClick);
            iv_item = (XCRoundImageViewByXfermode) view.findViewById(R.id.iv_item);
            mLinear_dz = (LinearLayout) view.findViewById(R.id.mLinear_dz);
            tv_dz = (TextView) view.findViewById(R.id.tv_dz);

            tv_item = (TextView) view.findViewById(R.id.tv_item);
            tv_gift = (TextView) view.findViewById(R.id.tv_gift);

            item_mLinearMsg = (LinearLayout) view.findViewById(R.id.item_mLinearMsg);
            //tv_type = (TextView) view.findViewById(R.id.tv_type);
            tv_datasize = (TextView) view.findViewById(R.id.tv_datasize);
            tv_msg = (TextView) view.findViewById(R.id.tv_msg);

            mLinear_progress = (LinearLayout) view.findViewById(R.id.mLinear_progress);
            pb_update_progress = (NumberProgressBar) view.findViewById(R.id.pb_update_progress);
            prgressTv = (TextView) view.findViewById(R.id.down_txt_pb);
            progressShow = (TextView) view.findViewById(R.id.down_progress);

            tv_download = (TextView) view.findViewById(R.id.tv_download);
            game_detail_frome_tv = (TextView) view.findViewById(R.id.game_detail_frome_tv);
            tv_start = (TextView) view.findViewById(R.id.tv_start);

			/*ButterKnife.bind(this, view);
            mView=view;
			mView.setTag(this);*/
        }

        @SuppressWarnings("unchecked")
        public void setData(DownLoadModel mDownLoadModel, final DownloadInfo info, final Integer postion) {
            if (mDownLoadModel != null) {

                Log.e("lbb", "-----setData-------");
                final AppContent mAppContent = mDownLoadModel.getmAppContent();
                if (mAppContent != null) {
                    iv_item.setType(XCRoundImageViewByXfermode.TYPE_ROUND);
                    iv_item.setRoundBorderRadius(23);
                    mLinear_dz.setVisibility(View.GONE);
                    if (!TextUtils.isEmpty(mAppContent.sczk) && Double.parseDouble(mAppContent.sczk) > 0) {
                        mLinear_dz.setVisibility(View.VISIBLE);
                        tv_dz.setText("" + mAppContent.sczk + "折");
                    } else if (!TextUtils.isEmpty(mAppContent.xczk) && Double.parseDouble(mAppContent.xczk) > 0) {
                        mLinear_dz.setVisibility(View.VISIBLE);
                        tv_dz.setText("" + mAppContent.xczk + "折");
                    }
                    ViewTarget viewTarget = new ViewTarget<XCRoundImageViewByXfermode, GlideDrawable>(iv_item) {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            this.view.setImageDrawable(resource.getCurrent());
                        }
                    };
                    Glide
                            .with(BaseApplication.mInstance.context.getApplicationContext()) // safer!
                            .load("" + mAppContent.fileAskPath + mAppContent.logo)
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(viewTarget);
                    tv_item.setText(mAppContent.gameName);
                    //tv_type.setText(mAppContent.kindName);
                    if (!TextUtils.isEmpty(mAppContent.fileSize)) {
                        tv_datasize.setText("" + mAppContent.fileSize + "M");
                    } else {
                        tv_datasize.setText("0.0" + "M");
                    }

                    if (tag != 3) {
                        tv_msg.setText(mAppContent.intro);
                    }
                    if ((!TextUtils.isEmpty(mAppContent.typeId) && mAppContent.typeId.equals("3"))
                            || (!TextUtils.isEmpty(mAppContent.typeName) && mAppContent.typeName.equals("H5游戏"))) {
                        //tv_download.setText("打开");
                        setDowState(DownLoadManager.AlreadyInstalled);
                        tv_download.setOnClickListener(new OnClickListener() {

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
                }
                setByStatus(info.getState());

                if (info.getFileLength() != 0) {
                    pb_update_progress.setMax((int) info.getFileLength());
                    pb_update_progress.setProgress((int) info.getProgress());
                    progressShow.setText((int) ((float) info.getProgress() / info.getFileLength() * 100) + "%");
                    if (info.getState() == DownLoadManager.STATE_FINISH) {
                        if (((int) ((float) info.getProgress() / info.getFileLength() * 100)) == 100) {

                            //tv_download.setText("安装");
                            setDowState(DownLoadManager.AlreadyInstalled);
                        } else {
                            //tv_download.setText("下载");
                            info.setState(DownLoadManager.STATE_WAITING);
                            setByStatus(DownLoadManager.STATE_WAITING);
                            setDowState(DownLoadManager.STATE_WAITING);
                            if (info.getDownLoadTask() == null) {
                                DownloadInfo mDownloadInfo = DownLoadManager.getManager().getDownloadInfo(info);
                                if (mDownloadInfo != null) {
                                    info.setDownLoadTask(mDownloadInfo.getDownLoadTask());
                                    info.getDownLoadTask().setState(DownLoadManager.STATE_WAITING);
                                }
                            } else {
                                info.getDownLoadTask().setState(DownLoadManager.STATE_WAITING);
                            }
                            //	info.getDownLoadTask().pause();
                        }
                    }
                    double curP = 0;
                    double curTotal = 0;
                    long progress = info.getProgress();
                    long total = info.getFileLength();
                    if (progress / 1024L < 1024L) {
                        curP = (double) progress / 1024;
                        curTotal = (double) total / 1024L / 1024L;
                        prgressTv.setText(Double.toString(getRealNum(curP)) + "KB/" + Double.toString(getRealNum(curTotal)) + "MB"
                        );
                    } else {
                        curP = (double) progress / 1024L / 1024L;
                        curTotal = (double) total / 1024L / 1024L;
                        prgressTv.setText(Double.toString(getRealNum(curP)) + "MB/" + Double.toString(getRealNum(curTotal)) + "MB"
                        );
                    }
                    /*prgressTv.setText(Double.toString(getRealNum(info.getProgress()/1024/1024)) + "MB/"
								+ Double.toString(getRealNum( info.getFileLength()/1024/1024)) + "MB"+"");*/
                } else {
                    pb_update_progress.setMax((int) 0);
                    pb_update_progress.setProgress((int) 100);
                    progressShow.setText(0 + "%");
                }
                if (info.getState() == DownLoadManager.STATE_WAITING) {
                    //tv_download.setText("下载");
                    setDowState(DownLoadManager.STATE_WAITING);
                    if (MonitorSysReceiver.checkApkExist(mContext, mAppContent.packageName, mAppContent.gameId)) {
                        //tv_download.setText("打开");
                        info.setState(DownLoadManager.AlreadyInstalled);
                        setByStatus(DownLoadManager.AlreadyInstalled);
                        setDowState(DownLoadManager.AlreadyInstalled);
                    }
                }
                if (info.getState() == DownLoadManager.STATE_PAUSE) {
                    //tv_download.setText("继续");
                    setDowState(DownLoadManager.STATE_PAUSE);
                }
                if (info.getState() == DownLoadManager.STATE_DOWNLOADING) {
                    //tv_download.setText("暂停");
                    setDowState(DownLoadManager.STATE_DOWNLOADING);
                }
                if (info.getState() == DownLoadManager.STATE_ERROR) {
                    //tv_download.setText("等待");
                    setDowState(DownLoadManager.STATE_ERROR);
                }
                if (info.getState() == DownLoadManager.AlreadyInstalled) {
                    //tv_download.setText("打开");
                    setDowState(DownLoadManager.AlreadyInstalled);
                }


                tv_download.setOnClickListener(new OnClickListener() {

                    public void onClick(View v) {
                        BaseApplication.mInstance.isRecommendBoutiqueAdapter = 3;
                        DBManager.getInstance(mContext).insertDownloadFile(mAppContent);
                        if (info.getDownLoadTask() == null && (DownLoadManager.getManager().isHaved(info) == false || DownLoadManager.getManager().getDownloadInfo(info) == null)) {
                            //add task
                            LeopardHttp.DWONLOAD(info, new IProgress() {
                                @SuppressLint("NewApi")
                                @Override
                                public void onProgress(long progress, long total, boolean done) {
                                    //if(progress>0){
                                    DownloadInfo infos = info.getDownLoadTask().getDownloadInfo();
                                    DownLoadModel mDown = new DownLoadModel();
                                    mDown.setmAppContent(mAppContent);
                                    mDown.setInfo(infos);

                                    Log.e("lbb", "------getState-3-----" + infos.getState());

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
												/*PackageManager packageManager = mContext.getApplicationContext().getPackageManager();  
												if(packageManager!=null){
													Intent intent=new Intent();   
													intent =packageManager.getLaunchIntentForPackage(mAppContent.packageName);   
													if(intent!=null){  
														mContext.startActivity(intent); 
													}  
												}*/
                                        } else {

                                            // 下载完成后弹出安装窗
                                            File file = new File(DownLoadManager.getManager().deFaultDir + infos.getFileName());
//                                            Intent intentInstall = new Intent();
//                                            intentInstall.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                            intentInstall.setAction(Intent.ACTION_VIEW);
//                                            intentInstall.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//                                            mContext.startActivity(intentInstall);
                                            FileUtil.apkInstall(file,mContext);
                                        }
						        			/*viewHolder.tv_download.setText("安装");
						        			infos.getDownLoadTask().setState(DownLoadManager.STATE_FINISH);
						        			viewHolder.prgressTv.setText(viewHolder.prgressTv.getText() + "");*/
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
                        //DownloadInfo info = data.get(Integer.valueOf(v.getTag().toString())).getInfo();
                        if (infos.getState() == DownLoadManager.STATE_FINISH) {
                            if (((int) ((float) infos.getProgress() / infos.getFileLength() * 100)) == 100) {
                                //tv_download.setText("打开");
                                info.setState(DownLoadManager.AlreadyInstalled);
                                infos.setState(DownLoadManager.AlreadyInstalled);
                                setByStatus(DownLoadManager.AlreadyInstalled);
                                setDowState(DownLoadManager.AlreadyInstalled);
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
                                //infos.getDownLoadTask().reStart();
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
//                                    Intent intentInstall = new Intent();
//                                    intentInstall.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    intentInstall.setAction(Intent.ACTION_VIEW);
//                                    intentInstall.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//                                    mContext.startActivity(intentInstall);
                                    FileUtil.apkInstall(file,mContext);
                                }

                                DownloadInfo infoss = infos.getDownLoadTask().getDownloadInfo();
                                DownLoadModel mDown = new DownLoadModel();
                                mDown.setmAppContent(mAppContent);
                                mDown.setInfo(infoss);
                                EventBus.getDefault().post(mDown);
                                return;
                            } else {
                                //去重新下载
                                //tv_download.setText("暂停");
                                setByStatus(DownLoadManager.STATE_DOWNLOADING);
                                infos.getDownLoadTask().reStart();
                                setDowState(DownLoadManager.STATE_DOWNLOADING);
                                DownloadInfo infoss = infos.getDownLoadTask().getDownloadInfo();
                                DownLoadModel mDown = new DownLoadModel();
                                mDown.setmAppContent(mAppContent);
                                mDown.setInfo(infoss);
                                EventBus.getDefault().post(mDown);
                                return;
                            }

                        }

                        if (infos.getState() == DownLoadManager.STATE_WAITING) {
                            //tv_download.setText("暂停");
                            setByStatus(DownLoadManager.STATE_DOWNLOADING);
                            infos.getDownLoadTask().reStart();
                            setDowState(DownLoadManager.STATE_DOWNLOADING);
                            DownloadInfo infoss = infos.getDownLoadTask().getDownloadInfo();
                            DownLoadModel mDown = new DownLoadModel();
                            mDown.setmAppContent(mAppContent);
                            mDown.setInfo(infoss);
                            EventBus.getDefault().post(mDown);
                            return;
                        }

                        if (infos.getState() == DownLoadManager.STATE_PAUSE) {
                            //tv_download.setText("暂停");
                            setByStatus(DownLoadManager.STATE_DOWNLOADING);
                            infos.getDownLoadTask().resume();
                            setDowState(DownLoadManager.STATE_DOWNLOADING);
                            DownloadInfo infoss = infos.getDownLoadTask().getDownloadInfo();
                            DownLoadModel mDown = new DownLoadModel();
                            mDown.setmAppContent(mAppContent);
                            mDown.setInfo(infoss);
                            EventBus.getDefault().post(mDown);
                            return;
                        }
                        if (infos.getState() == DownLoadManager.STATE_DOWNLOADING) {
                            //tv_download.setText("继续");
                            setByStatus(DownLoadManager.STATE_PAUSE);
                            infos.getDownLoadTask().pause();
                            setDowState(DownLoadManager.STATE_PAUSE);
                            DownloadInfo infoss = infos.getDownLoadTask().getDownloadInfo();
                            DownLoadModel mDown = new DownLoadModel();
                            mDown.setmAppContent(mAppContent);
                            mDown.setInfo(infoss);
                            EventBus.getDefault().post(mDown);
                            return;
                        }
                        if (infos.getState() == DownLoadManager.STATE_ERROR) {
                            //tv_download.setText("暂停");
                            setByStatus(DownLoadManager.STATE_DOWNLOADING);
                            infos.getDownLoadTask().reStart();
                            setDowState(DownLoadManager.STATE_DOWNLOADING);
                            DownloadInfo infoss = infos.getDownLoadTask().getDownloadInfo();
                            DownLoadModel mDown = new DownLoadModel();
                            mDown.setmAppContent(mAppContent);
                            mDown.setInfo(infoss);
                            EventBus.getDefault().post(mDown);
                            return;
                        }
                        if (infos.getState() == DownLoadManager.AlreadyInstalled) {
                            //tv_download.setText("打开");
                            setByStatus(DownLoadManager.AlreadyInstalled);
                            setDowState(DownLoadManager.AlreadyInstalled);
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
//                                Intent intentInstall = new Intent();
//                                intentInstall.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                intentInstall.setAction(Intent.ACTION_VIEW);
//                                intentInstall.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//                                mContext.startActivity(intentInstall);
                                FileUtil.apkInstall(file,mContext);

                                DownloadInfo infoss = infos.getDownLoadTask().getDownloadInfo();
                                DownLoadModel mDown = new DownLoadModel();
                                mDown.setmAppContent(mAppContent);
                                mDown.setInfo(infoss);
                                EventBus.getDefault().post(mDown);
                                return;
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

                //	pb_update_progress.setProgress(mAppContent.downloadPercent);
                //	setIconByStatus(tv_download, mAppContent.status);
            }
        }

        private double getRealNum(double num) {
            BigDecimal bg = new BigDecimal(num);
            return bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        }

        /**
         * 根据状态设置图标
         *
         * @param
         * @param status
         */
        private void setByStatus(int status) {

            switch (status) {
                case DownLoadManager.STATE_DOWNLOADING:
                    item_mLinearMsg.setVisibility(View.GONE);
                    mLinear_progress.setVisibility(View.VISIBLE);
                    break;
                case DownLoadManager.STATE_ERROR:
                    item_mLinearMsg.setVisibility(View.VISIBLE);
                    mLinear_progress.setVisibility(View.GONE);
                    break;
                case DownLoadManager.STATE_WAITING:
                    item_mLinearMsg.setVisibility(View.VISIBLE);
                    mLinear_progress.setVisibility(View.GONE);
                    break;
                case DownLoadManager.STATE_PAUSE:
                    item_mLinearMsg.setVisibility(View.VISIBLE);
                    mLinear_progress.setVisibility(View.GONE);
                    break;
                case DownLoadManager.STATE_FINISH:
                    item_mLinearMsg.setVisibility(View.GONE);
                    mLinear_progress.setVisibility(View.VISIBLE);
                    break;
                case DownLoadManager.AlreadyInstalled:
                    item_mLinearMsg.setVisibility(View.VISIBLE);
                    mLinear_progress.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }

        /*
        public static final int STATE_WAITING = 1;      //等待    --> 下载，暂停
        public static final int STATE_DOWNLOADING = 2;  //下载中  --> 暂停，完成，错误
        public static final int STATE_PAUSE = 3;        //暂停    --> 等待，下载
        public static final int STATE_FINISH = 4;       //完成    --> 重新下载
        public static final int STATE_ERROR = 5;        //错误    --> 等待
        public static final int AlreadyInstalled = 6;        //安装    --> 打开应用*/
        public void setDowState(int state) {
            switch (state) {
                case DownLoadManager.STATE_WAITING:
                    tv_download.setBackgroundResource(R.drawable.home_install);
                    tv_download.setText("");
                    break;
                case DownLoadManager.STATE_DOWNLOADING:
                    tv_download.setBackgroundResource(R.drawable.apk_state);
                    tv_download.setText("暂停");
                    break;

                case DownLoadManager.STATE_PAUSE:
                    tv_download.setBackgroundResource(R.drawable.apk_state);
                    tv_download.setText("继续");
                    break;

                case DownLoadManager.STATE_FINISH:

                    break;

                case DownLoadManager.STATE_ERROR:
                    tv_download.setBackgroundResource(R.drawable.apk_state);
                    tv_download.setText("等待");
                    break;

                case DownLoadManager.AlreadyInstalled:
                    tv_download.setBackgroundResource(R.drawable.home_open);
                    //tv_download.setText("");
                    break;
            }
        }
    }
}
