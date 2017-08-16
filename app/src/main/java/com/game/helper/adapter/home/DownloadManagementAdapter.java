package com.game.helper.adapter.home;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
import com.game.helper.db.manager.DBManager;
import com.game.helper.download.bean.AppContent;
import com.game.helper.installPackage.MonitorSysReceiver;
import com.game.helper.leopardkit.DownLoadModel;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.DownloadFinishTask;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.view.dialog.MyAlertDailog;
import com.game.helper.view.dialog.MyAlertDailog.onClickLeftListener;
import com.game.helper.view.dialog.MyAlertDailog.onClickRightListener;
import com.game.helper.view.widget.XCRoundImageViewByXfermode;
import com.yuan.leopardkit.LeopardHttp;
import com.yuan.leopardkit.db.HttpDbUtil;
import com.yuan.leopardkit.download.DownLoadManager;
import com.yuan.leopardkit.download.model.DownloadInfo;
import com.yuan.leopardkit.interfaces.IProgress;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * @Description
 * @Path com.game.helper.adapter.home.DownloadManagementAdapter.java
 * @Author lbb
 * @Date 2016年8月23日 下午6:38:42
 * @Company
 */
public class DownloadManagementAdapter extends BaseAdapter {

    /*	public  interface MOnItemClickListener001{
        void onItemClick(int position);
        void onItemDelete(int position);
    }
    MOnItemClickListener001 l;
    public void setOnItemClickListener(MOnItemClickListener001 l){
        this.l=l;
    }*/
    private List<View> viewList = new ArrayList<View>();//View对象集合
    List<DownLoadModel> mList = new ArrayList<DownLoadModel>();
    //private List<AppContent> mList=new ArrayList<AppContent>();
    private Context mContext;
    protected LayoutInflater mInflater;
    int listviewId;

    public DownloadManagementAdapter(Context mContext, List<DownLoadModel> mList, int listviewId) {
        super();
        mInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.listviewId = listviewId;
        this.mList = mList;
    }

    public List<DownLoadModel> getmList() {
        return mList;
    }

    public void setmList(List<DownLoadModel> mList) {
        viewList.clear();
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mList.size();
    }

    @Override
    public DownLoadModel getItem(int position) {
        // TODO Auto-generated method stub
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_listview_home_download_management, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_download.setTag(position);
        final DownLoadModel mDownLoadModel = (DownLoadModel) getItem(position);
        final DownloadInfo info = mDownLoadModel.getInfo();
        holder.setData(mDownLoadModel.getmAppContent(), info, position);
        holder.iv_delete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                final MyAlertDailog dialog = new MyAlertDailog(
                        mContext);
                Resources res = mContext.getResources();
                dialog.setTitle("删除任务记录", Gravity.CENTER_VERTICAL | Gravity.LEFT);
                dialog.setContent1("删除任务将清空下载任务，您确认不体验这款游戏了吗？"
                        , Gravity.CENTER_VERTICAL | Gravity.LEFT);
                dialog.setLeftButtonText("取消");
                dialog.setRightButtonText("确定");
                if (dialog != null && !dialog.isShowing()) {
                    dialog.show();
                }
                dialog.setOnClickLeftListener(new onClickLeftListener() {
                    @Override
                    public void onClickLeft() {
                        dialog.dismiss();

                    }
                });
                dialog.setOnClickRightListener(new onClickRightListener() {

                    @Override
                    public void onClickRight() {
                        dialog.dismiss();
                        try {
                            if (info.getDownLoadTask() != null) {
                                DownloadInfo infos = info.getDownLoadTask().getDownloadInfo();
                                infos.getDownLoadTask().remove();

                                DownLoadModel mDown = new DownLoadModel();
                                mDown.setmAppContent(mDownLoadModel.getmAppContent());
                                mDown.setInfo(info.getDownLoadTask().getDownloadInfo());
                                EventBus.getDefault().post(mDown);

                            } else {
                                HttpDbUtil.initHttpDB(mContext).delete(info);
                            }
                            if (mList.size() > 0) {
                                mList.remove(position);
                            }
                            notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                });
            }
        });
        holder.mLinearClick.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("currIndex", 0);
                bundle.putString("gameId", getItem(position).getmAppContent().gameId);
                bundle.putParcelable("appcontent", getItem(position).getmAppContent());
                ((BaseActivity) mContext).startActivity(GameDetailActivity.class, bundle);
            }
        });
        /* 标识View对象 */
        //将list_view的ID作为Tag的Key值
        convertView.setTag(listviewId, position);//此处将位置信息作为标识传递
        viewList.add(convertView);

        return convertView;
    }

    class ViewHolder {

        public
        @BindView(R.id.mLinearClick)
        LinearLayout mLinearClick;
        public
        @BindView(R.id.iv_item)
        XCRoundImageViewByXfermode iv_item;
        public
        @BindView(R.id.mLinear_dz)
        LinearLayout mLinear_dz;
        public
        @BindView(R.id.tv_dz)
        TextView tv_dz;
        public
        @BindView(R.id.iv_delete)
        ImageView iv_delete;
        public
        @BindView(R.id.tv_item)
        TextView tv_item;
        public
        @BindView(R.id.tv_first)
        TextView tv_first;
        public
        @BindView(R.id.tv_gift)
        TextView tv_gift;
        public
        @BindView(R.id.item_mLinearMsg)
        LinearLayout item_mLinearMsg;
        public
        @BindView(R.id.tv_type)
        TextView tv_type;
        public
        @BindView(R.id.tv_datasize)
        TextView tv_datasize;
        public
        @BindView(R.id.tv_msg)
        TextView tv_msg;
        public
        @BindView(R.id.tv_download)
        Button tv_download;
        public
        @BindView(R.id.mLinear_progress)
        LinearLayout mLinear_progress;
        public
        @BindView(R.id.pb_update_progress)
        ProgressBar pb_update_progress;
        public
        @BindView(R.id.down_txt_pb)
        TextView prgressTv;
        public
        @BindView(R.id.down_progress)
        TextView progressShow;
        public
        @BindView(R.id.tv_first3)
        TextView tv_first3;
        private AppContent mAppContent;
        private DownloadInfo info;

        Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 0x334:
                        doDowlod();
                        break;
                }
                return false;
            }
        });

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @SuppressWarnings("unchecked")
        public void setData(final AppContent mAppContent, final DownloadInfo info, final Integer postion) {
            this.mAppContent = mAppContent;
            this.info = info;

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
				/*Glide.with(BaseApplication.mInstance.context.getApplicationContext())
				.load(""+mAppContent.fileAskPath+mAppContent.logo)
				//.centerCrop()// 长的一边撑满
				//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片
				.error(R.drawable.preview_card_pic_loading)//加载失败时显示的图片
				//.crossFade()
				.into(iv_item);*/
                tv_item.setText(mAppContent.gameName);
                tv_type.setText(mAppContent.kindName);
                if (!TextUtils.isEmpty(mAppContent.fileSize)) {
                    tv_datasize.setText("" + mAppContent.fileSize + "M");
                } else {
                    tv_datasize.setText("0.0" + "M");
                }
                tv_msg.setText(mAppContent.intro);

                if ((!TextUtils.isEmpty(mAppContent.typeId) && mAppContent.typeId.equals("3"))
                        || (!TextUtils.isEmpty(mAppContent.typeName) && mAppContent.typeName.equals("H5游戏"))) {
                    tv_download.setText("打开");
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

                        tv_download.setText("安装");
                    } else {
                        tv_download.setText("下载");
                        info.setState(DownLoadManager.STATE_WAITING);
                        setByStatus(DownLoadManager.STATE_WAITING);
                        if (info.getDownLoadTask() == null) {
                            DownloadInfo mDownloadInfo = DownLoadManager.getManager().getDownloadInfo(info);
                            if (mDownloadInfo != null) {
                                info.setDownLoadTask(mDownloadInfo.getDownLoadTask());
                                info.getDownLoadTask().setState(DownLoadManager.STATE_WAITING);
                            }
                        } else {
                            info.getDownLoadTask().setState(DownLoadManager.STATE_WAITING);
                        }
                        //info.getDownLoadTask().pause();
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

            tv_download.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            //Log.e(TGA, "Item_ACTION_DOWN");
                            new Thread() {
                                @Override
                                public void run() {
                                    Message msg = new Message();
                                    msg.what = 0x333;
                                    handler.sendMessage(msg);
                                }
                            }.start();
                            break;
                    }
                    return false;
                }
            });

            if (info.getState() == DownLoadManager.STATE_WAITING) {
                tv_download.setText("下载");
                if (MonitorSysReceiver.checkApkExist(mContext, mAppContent.packageName, mAppContent.gameId)) {
                    tv_download.setText("打开");
                    info.setState(DownLoadManager.AlreadyInstalled);
                    setByStatus(DownLoadManager.AlreadyInstalled);
                }
            }
            if (info.getState() == DownLoadManager.STATE_DOWNLOADING) {
                tv_download.setText("暂停");
            }
            if (info.getState() == DownLoadManager.STATE_ERROR) {
                tv_download.setText("等待");
            }
            if (info.getState() == DownLoadManager.AlreadyInstalled) {
                tv_download.setText("打开");
            }
            if (info.getState() == DownLoadManager.STATE_PAUSE) {
                tv_download.setText("继续");

            }
        }

        private void doDowlod() {

            BaseApplication.mInstance.isRecommendBoutiqueAdapter = 2;
            DBManager.getInstance(mContext).insertDownloadFile(mAppContent);

            if (info.getDownLoadTask() == null && (DownLoadManager.getManager().isHaved(info) == false || DownLoadManager.getManager().getDownloadInfo(info) == null)) {
                //add task
                LeopardHttp.DWONLOAD(info, new IProgress() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onProgress(final long progress, final long total, final boolean done) {
                        //if(progress>0){
                        DownloadInfo infos = info.getDownLoadTask().getDownloadInfo();
                        //EventModel mEventModel=new EventModel();
                        DownLoadModel mDown = new DownLoadModel();
                        mDown.setmAppContent(mAppContent);
                        mDown.setInfo(infos);

                        Log.e("lbb", "------getState--DWONLOAD----" + infos.getState());
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
                                intentInstall.setAction(android.content.Intent.ACTION_VIEW);
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
            //DownloadInfo info = data.get(Integer.valueOf(v.getTag().toString())).getInfo();
            if (infos.getState() == DownLoadManager.STATE_FINISH) {
                if (((int) ((float) infos.getProgress() / infos.getFileLength() * 100)) == 100) {
                    tv_download.setText("打开");
                    info.setState(DownLoadManager.AlreadyInstalled);
                    infos.setState(DownLoadManager.AlreadyInstalled);
                    setByStatus(DownLoadManager.AlreadyInstalled);
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
                        Intent intentInstall = new Intent();
                        intentInstall.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intentInstall.setAction(android.content.Intent.ACTION_VIEW);
                        intentInstall.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                        mContext.startActivity(intentInstall);
                    }
                    EventBus.getDefault().post(infos.getDownLoadTask().getDownloadInfo());
                    return;
                } else {
                    //去重新下载
                    tv_download.setText("暂停");
                    setByStatus(DownLoadManager.STATE_DOWNLOADING);
                    infos.getDownLoadTask().reStart();
                    EventBus.getDefault().post(infos.getDownLoadTask().getDownloadInfo());
                    return;
                }
            }

            if (infos.getState() == DownLoadManager.STATE_WAITING) {
                tv_download.setText("暂停");
                setByStatus(DownLoadManager.STATE_DOWNLOADING);
                info.getDownLoadTask().reStart();

                DownloadInfo infoss = infos.getDownLoadTask().getDownloadInfo();
                DownLoadModel mDown = new DownLoadModel();
                mDown.setmAppContent(mAppContent);
                mDown.setInfo(infoss);
                EventBus.getDefault().post(mDown);
                return;
            }

            if (infos.getState() == DownLoadManager.STATE_PAUSE) {
                tv_download.setText("暂停");
                setByStatus(DownLoadManager.STATE_DOWNLOADING);
                info.getDownLoadTask().resume();

                DownloadInfo infoss = infos.getDownLoadTask().getDownloadInfo();
                DownLoadModel mDown = new DownLoadModel();
                mDown.setmAppContent(mAppContent);
                mDown.setInfo(infoss);
                EventBus.getDefault().post(mDown);
                return;
            }
            if (infos.getState() == DownLoadManager.STATE_DOWNLOADING) {
                tv_download.setText("继续");
                setByStatus(DownLoadManager.STATE_PAUSE);
                info.getDownLoadTask().pause();

                DownloadInfo infoss = infos.getDownLoadTask().getDownloadInfo();
                DownLoadModel mDown = new DownLoadModel();
                mDown.setmAppContent(mAppContent);
                mDown.setInfo(infoss);
                EventBus.getDefault().post(mDown);
                return;
            }
            if (infos.getState() == DownLoadManager.STATE_ERROR) {
                tv_download.setText("暂停");
                setByStatus(DownLoadManager.STATE_DOWNLOADING);
                info.getDownLoadTask().reStart();

                DownloadInfo infoss = infos.getDownLoadTask().getDownloadInfo();
                DownLoadModel mDown = new DownLoadModel();
                mDown.setmAppContent(mAppContent);
                mDown.setInfo(infoss);
                EventBus.getDefault().post(mDown);
                return;
            }
            if (infos.getState() == DownLoadManager.AlreadyInstalled) {
                tv_download.setText("打开");
                setByStatus(DownLoadManager.AlreadyInstalled);

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
                    intentInstall.setAction(android.content.Intent.ACTION_VIEW);
                    intentInstall.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                    mContext.startActivity(intentInstall);

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
    }
}