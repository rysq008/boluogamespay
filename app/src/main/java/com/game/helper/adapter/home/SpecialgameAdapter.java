package com.game.helper.adapter.home;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
import com.game.helper.download.model.DownloadController;
import com.game.helper.installPackage.MonitorSysReceiver;
import com.game.helper.leopardkit.DownLoadModel;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.DownloadFinishTask;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.util.ToastUtil;
import com.game.helper.view.widget.XCRoundImageViewByXfermode;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yuan.leopardkit.LeopardHttp;
import com.yuan.leopardkit.db.HttpDbUtil;
import com.yuan.leopardkit.download.DownLoadManager;
import com.yuan.leopardkit.download.model.DownloadInfo;
import com.yuan.leopardkit.interfaces.IProgress;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import de.greenrobot.event.EventBus;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadBean;
import zlc.season.rxdownload2.entity.DownloadEvent;
import zlc.season.rxdownload2.entity.DownloadFlag;
import zlc.season.rxdownload2.entity.DownloadStatus;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static zlc.season.rxdownload2.function.Utils.log;

/**
 * @Description
 * @Path com.game.helper.adapter.home.SpecialgameAdapter.java
 * @Author lbb
 * @Date 2016年8月22日 下午7:45:20
 * @Company
 */
public class SpecialgameAdapter extends BaseAdapter {

    public interface MOnItemClickListener11 {
        void onItemClick();
    }

    MOnItemClickListener11 l;

    public void setOnItemClickListener(MOnItemClickListener11 l) {
        this.l = l;
    }

    List<DownLoadModel> data = new ArrayList<DownLoadModel>();
    //private List<AppContent> mList=new ArrayList<AppContent>();
    private List<View> viewList = new ArrayList<View>();//View对象集合
    private Context mContext;
    protected LayoutInflater mInflater;
    int tag;
    int listviewId;
    DownLoadModel model = null;

    public SpecialgameAdapter(Context mContext, List<AppContent> mList, int tag, int listviewId) {
        super();
        mInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.listviewId = listviewId;
        this.tag = tag;//tag=3 表示从游戏详情里进入的。
        if (null == mList || mList.size() == 0) {
            //ToastUtil.showToast(mContext,"数据错误");
            return;
        } else {
            init(mList);
        }

    }

    public void init(List<AppContent> mList) {
        viewList.clear();
        data.clear();
        for (AppContent mAppContent : mList) {
            DownloadInfo info = new DownloadInfo();
            info.setUrl(mAppContent.downloadPath);
            info.setProgress(0L);
            info.setFileName("IRecordApp_" + mAppContent.gameId + ".apk");

            model = new DownLoadModel();
            model.setInfo(info);
            model.setmAppContent(mAppContent);
            data.add(model);
        }
        List<DownloadInfo> downloadInfoList = HttpDbUtil.initHttpDB(mContext).queryFileInfo(0);

        for (DownLoadModel mD : data) {
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
        }
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
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public DownLoadModel getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
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
            if (tag != 3) {
                convertView = mInflater.inflate(R.layout.item_listview_home_special_game, null);
            } else if (tag == 3) {
                convertView = mInflater.inflate(R.layout.item_listview_home_special_game_detile, null);
            }
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_download.setTag(position);
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
                    bundle.putParcelable("appcontent", getData().get(position).getmAppContent());
                    //Log.e("aaaaaaa", "状态==========: " + getData().get(position).getInfo().getState());
                    ((BaseActivity) mContext).startActivity(GameDetailActivity.class, bundle);
                }
            });
        } else if (tag == 3) {
            holder.game_detail_frome_tv.setText(data.get(position).getmAppContent().platName);
        }
        /* 标识View对象 */
        //将list_view的ID作为Tag的Key值
        convertView.setTag(listviewId, position);//此处将位置信息作为标识传递
        viewList.add(convertView);

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
        public TextView tv_type;
        public TextView tv_datasize;

        public TextView tv_msg;

        public LinearLayout mLinear_progress;
        public ProgressBar pb_update_progress;
        public TextView prgressTv;
        public TextView progressShow;

        public Button tv_download;

        public TextView game_detail_frome_tv;
        DownloadInfo info;
        private boolean ifhavetast;

        private DownloadController mDownloadController;
        private RxDownload mRxDownload;
        private int flag;

        private AppContent mAppContent;
        private DownloadBean downloadBean;

        public ViewHolder(View view) {
            super();
            mLinearClick = (LinearLayout) view.findViewById(R.id.mLinearClick);
            iv_item = (XCRoundImageViewByXfermode) view.findViewById(R.id.iv_item);
            mLinear_dz = (LinearLayout) view.findViewById(R.id.mLinear_dz);
            tv_dz = (TextView) view.findViewById(R.id.tv_dz);

            tv_item = (TextView) view.findViewById(R.id.tv_item);
            tv_gift = (TextView) view.findViewById(R.id.tv_gift);

            item_mLinearMsg = (LinearLayout) view.findViewById(R.id.item_mLinearMsg);
            tv_type = (TextView) view.findViewById(R.id.tv_type);
            tv_datasize = (TextView) view.findViewById(R.id.tv_datasize);
            tv_msg = (TextView) view.findViewById(R.id.tv_msg);

            mLinear_progress = (LinearLayout) view.findViewById(R.id.mLinear_progress);
            pb_update_progress = (ProgressBar) view.findViewById(R.id.pb_update_progress);
            prgressTv = (TextView) view.findViewById(R.id.down_txt_pb);
            progressShow = (TextView) view.findViewById(R.id.down_progress);

            tv_download = (Button) view.findViewById(R.id.tv_download);
            tv_download.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDownloadController.handleClick(new DownloadController.Callback() {
                        @Override
                        public void startDownload() {
                            start();
                        }

                        @Override
                        public void pauseDownload() {
                            pause();
                        }

                        @Override
                        public void install() {
                            installApk();
                        }
                    });
                }
            });
            game_detail_frome_tv = (TextView) view.findViewById(R.id.game_detail_frome_tv);

            mRxDownload = RxDownload.getInstance(mContext);

            mDownloadController = new DownloadController(new TextView(mContext), tv_download);
        }

        @SuppressWarnings("unchecked")
        public void setData(DownLoadModel mDownLoadModel, final DownloadInfo info, final Integer postion) {
            model = mDownLoadModel;
            if (mDownLoadModel != null) {
                this.info = info;
                Log.e("lbb", "-----setData-------");
                mAppContent = mDownLoadModel.getmAppContent();
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
                    Glide.with(BaseApplication.mInstance.context.getApplicationContext()) // safer!
                            .load("" + mAppContent.fileAskPath + mAppContent.logo).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(viewTarget);

                    tv_item.setText(mAppContent.gameName);
                    tv_type.setText(mAppContent.kindName);
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

                downloadBean = new DownloadBean
                        .Builder(model.getmAppContent().downloadPath)
                        .setSaveName(null)      //not need.
                        .setSavePath(null)      //not need
                        .setExtra1(model.getmAppContent().packageName)   //save extra info into database.
                        .setExtra2(model.getmAppContent().gameId)  //save extra info into database.
                        .build();

//                Observable<DownloadEvent> replayDownloadStatus = mRxDownload.receiveDownloadStatus(model.getmAppContent().downloadPath).replay().autoConnect();
//                Observable<DownloadEvent> sampled = replayDownloadStatus.filter(new Predicate<DownloadEvent>() {
//                    @Override
//                    public boolean test(@NonNull DownloadEvent downloadEvent) throws Exception {
//                        return downloadEvent.getFlag() == DownloadFlag.STARTED;
//                    }
//                }).throttleFirst(200, TimeUnit.MILLISECONDS);
//                Observable<DownloadEvent> noProgress = replayDownloadStatus.filter(new Predicate<DownloadEvent>() {
//                    @Override
//                    public boolean test(@NonNull DownloadEvent downloadEvent) throws Exception {
//                        return downloadEvent.getFlag() != DownloadFlag.STARTED;
//                    }
//                });
//                model.disposable = Observable.merge(sampled, noProgress).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<DownloadEvent>() {
//                    @Override
//                    public void accept(@NonNull DownloadEvent downloadEvent) throws Exception {
//                        if (flag != downloadEvent.getFlag()) {
//                            flag = downloadEvent.getFlag();
//                            log("all events:" + downloadEvent.getFlag());
//                        }
//                        if (downloadEvent.getFlag() == DownloadFlag.FAILED) {
//                            Throwable throwable = downloadEvent.getError();
//                            Log.w("TAG", throwable);
//                        }
//                        mDownloadController.setEvent(downloadEvent);
//                        updateProgressStatus(downloadEvent.getDownloadStatus());
//                    }
//                });
                model.disposable = mRxDownload.receiveDownloadStatus(model.getmAppContent().downloadPath)
                        .subscribe(new Consumer<DownloadEvent>() {
                            @Override
                            public void accept(DownloadEvent downloadEvent) throws Exception {
                                if (flag != downloadEvent.getFlag()) {
                                    flag = downloadEvent.getFlag();
                                    log(flag + "");
                                }

                                if (downloadEvent.getFlag() == DownloadFlag.FAILED) {
                                    Throwable throwable = downloadEvent.getError();
                                    Log.w("TAG", throwable);
                                }
                                mDownloadController.setEvent(downloadEvent);
                            }
                        });


            }
        }

        private void updateProgressStatus(DownloadStatus status) {
            pb_update_progress.setIndeterminate(status.isChunked);
            pb_update_progress.setMax((int) status.getTotalSize());
            pb_update_progress.setProgress((int) status.getDownloadSize());
            progressShow.setText(status.getPercent());
            prgressTv.setText(status.getFormatStatusString());
        }

        private void start() {
            RxPermissions.getInstance(mContext)
                    .request(WRITE_EXTERNAL_STORAGE)
                    .doOnNext(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean granted) throws Exception {
                            if (!granted) {
                                throw new RuntimeException("no permission");
                            }
                        }
                    })
                    .compose(mRxDownload.<Boolean>transformService(downloadBean))
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {
                            Toast.makeText(mContext, "下载开始", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        private void pause() {
            mRxDownload.pauseServiceDownload(model.getmAppContent().downloadPath).subscribe();
        }


        private void installApk() {
            File[] files = mRxDownload.getRealFiles(model.getmAppContent().downloadPath);
            if (files != null) {
                Uri uri = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    uri = FileProvider.getUriForFile(mContext, mContext.getApplicationInfo().packageName + ".provider", files[0]);
                } else {
                    uri = Uri.fromFile(files[0]);
                }
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(uri, "application/vnd.android.package-archive");
                mContext.startActivity(intent);
            } else {
                Toast.makeText(mContext, "File not exists", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
