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
import com.game.helper.util.FileUtil;
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
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.BindView;
import de.greenrobot.event.EventBus;

/**
 * @Description
 * @Path com.game.helper.adapter.home.RecommendBoutiqueAdapter.java
 * @Author lbb
 * @Date 2016年8月22日 上午11:48:30
 * @Company 
 */
public class RecommendBoutiqueAdapter extends BaseAdapter {

	private List<View> viewList= new ArrayList<View>();//View对象集合
	List<DownLoadModel> mDates = new ArrayList<DownLoadModel>();
	//private List<AppContent> mDates=new ArrayList<AppContent>();
	private Context mContext;
	protected LayoutInflater mInflater;
	//private List<ViewHolder> mDisplayedHolders= new ArrayList<ViewHolder>();
	int listviewId;
	public RecommendBoutiqueAdapter(Context mContext,List<AppContent> mList,int listviewId) {
		super();
		mInflater = LayoutInflater.from(mContext);
		this.mContext=mContext;
		this.listviewId=listviewId;
		init(mList);
	}
	public void init(List<AppContent> mList){
		viewList.clear();
		mDates.clear();
		DownLoadModel model=null;
		for(AppContent mAppContent:mList){
			DownloadInfo info = new DownloadInfo();
			info.setUrl(mAppContent.downloadPath);
			info.setProgress(0L);
			info.setFileName("IRecordApp_" + mAppContent.gameId+ ".apk");

			model = new DownLoadModel();
			model.setInfo(info);
			model.setmAppContent(mAppContent);
			mDates.add(model);
		}
		List<DownloadInfo> downloadInfoList =   HttpDbUtil.initHttpDB(mContext).queryFileInfo(0);

		for(DownLoadModel mD:mDates){
			if(mD!=null&&mD.getInfo()!=null
					&&!TextUtils.isEmpty(mD.getInfo().getFileName())){
				for (DownloadInfo info:downloadInfoList){
					if(info!=null){
						if(mD.getInfo().getFileName().equals(info.getFileName())){
							mD.setInfo(info);
							break;
						}
					}
				}
			}
		}
	}
	public void setDates(List<AppContent> mDates) {
		init(mDates);
		notifyDataSetChanged();
	}
	/*public void startObserver() {
		DownloadManager.getInstance().registerObserver(this);
	}

	public void stopObserver() {
		DownloadManager.getInstance().unRegisterObserver(this);
	}*/
	/*public List<ViewHolder> getDisplayedHolders() {
		synchronized (mDisplayedHolders) {
			return new ArrayList<ViewHolder>(mDisplayedHolders);
		}
	}
	public void clearAllItem() {
		if (mDates != null){
			mDates.clear();
		}
		if (mDisplayedHolders != null) {
			mDisplayedHolders.clear();
		}
	}*/

	public List<DownLoadModel> getmDates() {
		return mDates;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mDates.size();
	}

	@Override
	public DownLoadModel getItem(int position) {
		// TODO Auto-generated method stub
		return mDates.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_gridview_home_recommend_boutique, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tv_download.setTag(position);
		DownLoadModel mDownLoadModel=(DownLoadModel) getItem(position);
		DownloadInfo info =mDownLoadModel.getInfo();
		holder.setData(mDownLoadModel,info,position);
		holder.iv_item.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Bundle bundle=new Bundle();
				bundle.putInt("currIndex", 0);
				bundle.putString("gameId", getItem(position).getmAppContent().gameId);
				bundle.putParcelable("appcontent", getItem(position).getmAppContent());
				((BaseActivity)mContext).startActivity(GameDetailActivity.class,bundle);
			}
		});
		/* 标识View对象 */
		//将list_view的ID作为Tag的Key值
		convertView.setTag(listviewId, position);//此处将位置信息作为标识传递
		viewList.add(convertView);
		return convertView;
	}

	class ViewHolder {
		public @BindView(R.id.iv_item) XCRoundImageViewByXfermode iv_item;
		public @BindView(R.id.tv_item) TextView tv_item;
		public @BindView(R.id.item_mLinear) LinearLayout item_mLinear;
		public @BindView(R.id.item_mLinearMsg) LinearLayout item_mLinearMsg;
		public @BindView(R.id.tv_type) TextView tv_type;
		public @BindView(R.id.tv_datasize) TextView tv_datasize;
		public @BindView(R.id.pb_update_progress) ProgressBar pb_update_progress;
		public @BindView(R.id.tv_download) TextView tv_download;

		public @BindView(R.id.mLinear_dz) LinearLayout mLinear_dz;
		public @BindView(R.id.tv_dz) TextView tv_dz;

		public   @BindView(R.id.item_mLinearMsg1)  LinearLayout  item_mLinearMsg1;
		public  @BindView(R.id.down_txt_pb) TextView prgressTv;
		public  @BindView(R.id.down_progress) TextView progressShow;
		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}

		@SuppressWarnings("unchecked")
		public void setData(DownLoadModel mDownLoadModel, final DownloadInfo info,final Integer  postion){
			if(mDownLoadModel!=null){
				Log.e("lbb", "-----setData---RecommendBoutiqueAdapter----");
				final AppContent mAppContent=mDownLoadModel.getmAppContent();
				if(mAppContent!=null){
					iv_item.setType(XCRoundImageViewByXfermode.TYPE_ROUND);
					iv_item.setRoundBorderRadius(23);

					mLinear_dz.setVisibility(View.GONE);
					if(!TextUtils.isEmpty(mAppContent.sczk)&&Double.parseDouble(mAppContent.sczk)>0){
						mLinear_dz.setVisibility(View.VISIBLE);
						tv_dz.setText(""+mAppContent.sczk+"折");
					}else if(!TextUtils.isEmpty(mAppContent.xczk)&&Double.parseDouble(mAppContent.xczk)>0){
						mLinear_dz.setVisibility(View.VISIBLE);
						tv_dz.setText(""+mAppContent.xczk+"折");
					}
					ViewTarget viewTarget = new ViewTarget<XCRoundImageViewByXfermode, GlideDrawable>( iv_item ) {  
						@Override  
						public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {  
							this.view.setImageDrawable( resource.getCurrent() );  
						}  
					};  
					Glide  
					.with( BaseApplication.mInstance.context.getApplicationContext() ) // safer!  
					.load( ""+mAppContent.fileAskPath+mAppContent.logo )  
					.diskCacheStrategy(DiskCacheStrategy.SOURCE)
					.into( viewTarget ); 
					/*Glide.with(BaseApplication.mInstance.context.getApplicationContext())
					.load(""+mAppContent.fileAskPath+mAppContent.logo)
					//.centerCrop()// 长的一边撑满
					//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片  
					.error(R.drawable.preview_card_pic_loading)//加载失败时显示的图片
					//.crossFade()
					.into(iv_item);*/
					tv_item.setText(mAppContent.gameName);
					//pb_update_progress.setProgress(mAppContent.downloadPercent);
					tv_type.setText(mAppContent.kindName);
					if(!TextUtils.isEmpty(mAppContent.fileSize)){
						tv_datasize.setText(""+mAppContent.fileSize+"M");
					}else{
						tv_datasize.setText("0.0"+"M");
					}
					if((!TextUtils.isEmpty(mAppContent.typeId)&&mAppContent.typeId.equals("3"))
							||(!TextUtils.isEmpty(mAppContent.typeName)&&mAppContent.typeName.equals("H5游戏"))){
						tv_download.setText("打开");
						tv_download.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								Bundle bundle=new Bundle();
								bundle.putString("Url",mAppContent.downloadPath);
								bundle.putString("TITLE", mAppContent.gameName);
								((BaseActivity)mContext).startActivity(WebActivity.class,bundle);
							}
						});
						return;
					}
				}
				setByStatus(info.getState());
				
				if(info.getFileLength()!=0){
					pb_update_progress.setMax((int) info.getFileLength());
					pb_update_progress.setProgress((int) info.getProgress());
					progressShow.setText((int) ((float) info.getProgress() / info.getFileLength() * 100) + "%");
					if (info.getState() == DownLoadManager.STATE_FINISH) {
						if(((int) ((float) info.getProgress() / info.getFileLength() * 100))==100){

							tv_download.setText("安装");
							
						}else{
							tv_download.setText("下载");
							info.setState(DownLoadManager.STATE_WAITING);
							setByStatus(DownLoadManager.STATE_WAITING);
							if(info.getDownLoadTask()==null){
	                        	DownloadInfo mDownloadInfo=DownLoadManager.getManager().getDownloadInfo(info);
	                        	if(mDownloadInfo!=null){
	                        		info.setDownLoadTask(mDownloadInfo.getDownLoadTask());
	                        		info.getDownLoadTask().setState(DownLoadManager.STATE_WAITING);
	                        	}
	                        }else{
	                        	info.getDownLoadTask().setState(DownLoadManager.STATE_WAITING);
	                        }
							//info.getDownLoadTask().pause();
						}
					}
					/*prgressTv.setText(Double.toString(getRealNum(info.getProgress()/1024/1024)) + "MB/"
							+ Double.toString(getRealNum( info.getFileLength()/1024/1024)) + "MB"+"");*/
					double curP = 0;
					double curTotal = 0;
					long progress=info.getProgress();
					long total=info.getFileLength();
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
				}else{
					pb_update_progress.setMax((int) 0);
					pb_update_progress.setProgress((int) 100);
					progressShow.setText( 0 + "%");
				}
				

				tv_download.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						BaseApplication.mInstance.isRecommendBoutiqueAdapter=1;
						DBManager.getInstance(mContext).insertDownloadFile(mAppContent);
						//add task
						if(info.getDownLoadTask()==null&&(DownLoadManager.getManager().isHaved(info)==false||DownLoadManager.getManager().getDownloadInfo(info)==null) ){
							LeopardHttp.DWONLOAD(info, new IProgress() {
								@SuppressLint("NewApi")
								@Override
								public void onProgress(final long progress, final long total, final boolean done) {
									//if(progress>0){
										DownloadInfo infos = info.getDownLoadTask().getDownloadInfo();
										//EventModel mEventModel=new EventModel();
										DownLoadModel mDown=new DownLoadModel();
										mDown.setmAppContent(mAppContent);
										mDown.setInfo(infos);
										/*mEventModel.done=done;
										mEventModel.total=total;
										mEventModel.progress=progress;
										mEventModel.infos=infos;
										mEventModel.mAppContent=mAppContent;*/
							            Log.e("lbb", "------getState------"+infos.getState());
							            
										EventBus.getDefault().post(mDown);
										if (done&&((int) ((float) progress / total * 100))==100) {
											LoginData user=DBManager.getInstance(mContext).getUserMessage();
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
											if(MonitorSysReceiver.checkApkExist(mContext,mAppContent.packageName , mAppContent.gameId)){
												/*PackageManager packageManager = mContext.getApplicationContext().getPackageManager();  
												if(packageManager!=null){
													Intent intent=new Intent();   
													intent =packageManager.getLaunchIntentForPackage(mAppContent.packageName);   
													if(intent!=null){  
														mContext.startActivity(intent); 
													}  
												}*/
											}else{

												// 下载完成后弹出安装窗
												File file = new File(DownLoadManager.getManager().deFaultDir+ infos.getFileName());
//												Intent intentInstall = new Intent();
//												intentInstall.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//												intentInstall.setAction(Intent.ACTION_VIEW);
//												intentInstall.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//												mContext.startActivity(intentInstall);
                                                FileUtil.apkInstall(file,mContext);
											}
											/*viewHolder.tv_download.setText("安装");
											infos.getDownLoadTask().setState(DownLoadManager.STATE_FINISH);
											viewHolder.prgressTv.setText(viewHolder.prgressTv.getText() + "");
											EventBus.getDefault().post(infos.getDownLoadTask().getDownloadInfo());*/
										}
										View view = null;
										//匹配视图对象 
										/*for (int i = 0; i < viewList.size(); i++) {
											if (viewList.get(i).getTag(listviewId) == postion) {
												//检查所有视图ID，如果ID匹配则取出该对象
												view = viewList.get(i);
												break;
											}
										}*/
										if (view != null) {/*
											//将视图对象中缓存的ViewHolder对象取出，并使用该对象对控件进行更新
											RecommendBoutiqueAdapter.ViewHolder viewHolder = (RecommendBoutiqueAdapter.ViewHolder) view.getTag();
											if (infos.getState() == DownLoadManager.STATE_WAITING) {
												viewHolder.tv_download.setText("下载");
											}

											if (infos.getState() == DownLoadManager.STATE_PAUSE) {
												viewHolder.tv_download.setText("继续");
											}

											if (infos.getState() == DownLoadManager.STATE_DOWNLOADING) {
												viewHolder.tv_download.setText("暂停");
											}
											if (infos.getState() == DownLoadManager.STATE_ERROR) {
												viewHolder.tv_download.setText("等待");
											}
											if (infos.getState() == DownLoadManager.AlreadyInstalled) {
												viewHolder.tv_download.setText("打开");
											}
											//setByStatus(infos.getState());
											switch (infos.getState()) {
											case DownLoadManager.STATE_DOWNLOADING:

												viewHolder.tv_item.setVisibility(View.GONE);
												viewHolder.item_mLinearMsg.setVisibility(View.GONE);
												viewHolder.pb_update_progress.setVisibility(View.VISIBLE);
												viewHolder.item_mLinearMsg1.setVisibility(View.VISIBLE);
												break;
											case DownLoadManager.STATE_ERROR:
												viewHolder.tv_item.setVisibility(View.VISIBLE);
												viewHolder.item_mLinearMsg.setVisibility(View.VISIBLE);
												viewHolder.pb_update_progress.setVisibility(View.GONE);
												viewHolder.item_mLinearMsg1.setVisibility(View.GONE);
												break;
											case DownLoadManager.STATE_WAITING:
												viewHolder.tv_item.setVisibility(View.VISIBLE);
												viewHolder.item_mLinearMsg.setVisibility(View.VISIBLE);
												viewHolder.pb_update_progress.setVisibility(View.GONE);
												viewHolder.item_mLinearMsg1.setVisibility(View.GONE);
												break;
											case DownLoadManager.STATE_PAUSE:
												viewHolder.tv_item.setVisibility(View.VISIBLE);
												viewHolder.item_mLinearMsg.setVisibility(View.VISIBLE);
												viewHolder.pb_update_progress.setVisibility(View.GONE);
												viewHolder.item_mLinearMsg1.setVisibility(View.GONE);
												break;
											case DownLoadManager.STATE_FINISH:
												viewHolder.tv_item.setVisibility(View.GONE);
												viewHolder.item_mLinearMsg.setVisibility(View.GONE);
												viewHolder.pb_update_progress.setVisibility(View.VISIBLE);
												viewHolder.item_mLinearMsg1.setVisibility(View.VISIBLE);
												break;
											case DownLoadManager.AlreadyInstalled:
												viewHolder.tv_item.setVisibility(View.VISIBLE);
												viewHolder.item_mLinearMsg.setVisibility(View.VISIBLE);
												viewHolder.pb_update_progress.setVisibility(View.GONE);
												viewHolder.item_mLinearMsg1.setVisibility(View.GONE);
												break;
											default:
												break;
											}

											viewHolder.pb_update_progress.setMax((int) total);
											viewHolder.pb_update_progress.setProgress((int) progress);
											viewHolder.progressShow.setText((int) ((float) progress / total * 100) + "%");
											double curP = 0;
											double curTotal = 0;
											if (progress / 1024L < 1024L) {
												curP = (double) progress / 1024;
												curTotal = (double) total / 1024L / 1024L;
												viewHolder.prgressTv.setText(Double.toString(getRealNum(curP)) + "KB/" + Double.toString(getRealNum(curTotal)) + "MB"
														);
											} else {
												curP = (double) progress / 1024L / 1024L;
												curTotal = (double) total / 1024L / 1024L;
												viewHolder.prgressTv.setText(Double.toString(getRealNum(curP)) + "MB/" + Double.toString(getRealNum(curTotal)) + "MB"
														);
											}
											if (done&&((int) ((float) progress / total * 100))==100) {
												LoginData user=DBManager.getInstance(mContext).getUserMessage();
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
												viewHolder.tv_download.setText("安装");
												infos.getDownLoadTask().setState(DownLoadManager.STATE_FINISH);
												viewHolder.prgressTv.setText(viewHolder.prgressTv.getText() + "");
												EventBus.getDefault().post(infos.getDownLoadTask().getDownloadInfo());
											}
										*/}
									//}



								}
							},mContext);
						}
                        if(info.getDownLoadTask()==null){
                        	DownloadInfo mDownloadInfo=DownLoadManager.getManager().getDownloadInfo(info);
                        	if(mDownloadInfo!=null){
                        		info.setDownLoadTask(mDownloadInfo.getDownLoadTask());
                        	}
                        }
						DownloadInfo infos = info.getDownLoadTask().getDownloadInfo();
						//DownloadInfo info = data.get(Integer.valueOf(v.getTag().toString())).getInfo();
						if (infos.getState() == DownLoadManager.STATE_FINISH) {
							if(((int) ((float) infos.getProgress() / infos.getFileLength() * 100))==100){
								tv_download.setText("打开");
								info.setState(DownLoadManager.AlreadyInstalled);
								infos.setState(DownLoadManager.AlreadyInstalled);
								setByStatus(DownLoadManager.AlreadyInstalled);
								if(infos.getDownLoadTask()==null){
		                        	DownloadInfo mDownloadInfo=DownLoadManager.getManager().getDownloadInfo(infos);
		                        	if(mDownloadInfo!=null){
		                        		infos.setDownLoadTask(mDownloadInfo.getDownLoadTask());
		                        		infos.getDownLoadTask().setState(DownLoadManager.AlreadyInstalled);
		                        	}else{
		                        		 HttpDbUtil.initHttpDB(mContext).update(infos);
		                        	}
		                        	
		                        }else{
		                        	infos.getDownLoadTask().setState(DownLoadManager.AlreadyInstalled);
		                        }
								//infos.getDownLoadTask().reStart();

								
								if(MonitorSysReceiver.checkApkExist(mContext,mAppContent.packageName , mAppContent.gameId)){
									PackageManager packageManager = mContext.getApplicationContext().getPackageManager();  
									if(packageManager!=null){
										Intent intent=new Intent();   
										intent =packageManager.getLaunchIntentForPackage(mAppContent.packageName);   
										if(intent!=null){  
											mContext.startActivity(intent); 
										}  
									}
								}else{

									// 下载完成后弹出安装窗
									File file = new File(DownLoadManager.getManager().deFaultDir+ infos.getFileName());
//									Intent intentInstall = new Intent();
//									intentInstall.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//									intentInstall.setAction(Intent.ACTION_VIEW);
//									intentInstall.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//									mContext.startActivity(intentInstall);
                                    FileUtil.apkInstall(file,mContext);
								}

								DownloadInfo infoss = infos.getDownLoadTask().getDownloadInfo();
								DownLoadModel mDown=new DownLoadModel();
								mDown.setmAppContent(mAppContent);
								mDown.setInfo(infoss);
								EventBus.getDefault().post(mDown);
								return;
							}else{
								//去重新下载
								tv_download.setText("暂停");
								setByStatus(DownLoadManager.STATE_DOWNLOADING);
								infos.getDownLoadTask().reStart();

								DownloadInfo infoss = infos.getDownLoadTask().getDownloadInfo();
								DownLoadModel mDown=new DownLoadModel();
								mDown.setmAppContent(mAppContent);
								mDown.setInfo(infoss);
								EventBus.getDefault().post(mDown);
								return;
							}
						}

						if (infos.getState() == DownLoadManager.STATE_WAITING) {
							tv_download.setText("暂停");
							setByStatus(DownLoadManager.STATE_DOWNLOADING);
							infos.getDownLoadTask().reStart();

							DownloadInfo infoss = infos.getDownLoadTask().getDownloadInfo();
							DownLoadModel mDown=new DownLoadModel();
							mDown.setmAppContent(mAppContent);
							mDown.setInfo(infoss);
							EventBus.getDefault().post(mDown);
							return;
						}

						if (infos.getState() == DownLoadManager.STATE_PAUSE) {
							tv_download.setText("暂停");
							setByStatus(DownLoadManager.STATE_DOWNLOADING);
							infos.getDownLoadTask().resume();

							DownloadInfo infoss = infos.getDownLoadTask().getDownloadInfo();
							DownLoadModel mDown=new DownLoadModel();
							mDown.setmAppContent(mAppContent);
							mDown.setInfo(infoss);
							EventBus.getDefault().post(mDown);
							return;
						}
						if (infos.getState() == DownLoadManager.STATE_DOWNLOADING) {
							tv_download.setText("继续");
							setByStatus(DownLoadManager.STATE_PAUSE);
							infos.getDownLoadTask().pause();

							DownloadInfo infoss = infos.getDownLoadTask().getDownloadInfo();
							DownLoadModel mDown=new DownLoadModel();
							mDown.setmAppContent(mAppContent);
							mDown.setInfo(infoss);
							EventBus.getDefault().post(mDown);
							return;
						}
						if (infos.getState() == DownLoadManager.STATE_ERROR) {
							tv_download.setText("暂停");
							setByStatus(DownLoadManager.STATE_DOWNLOADING);
							infos.getDownLoadTask().reStart();

							DownloadInfo infoss = infos.getDownLoadTask().getDownloadInfo();
							DownLoadModel mDown=new DownLoadModel();
							mDown.setmAppContent(mAppContent);
							mDown.setInfo(infoss);
							EventBus.getDefault().post(mDown);
							return;
						}
						if (infos.getState() == DownLoadManager.AlreadyInstalled) {
							tv_download.setText("打开");
							setByStatus(DownLoadManager.AlreadyInstalled);

							if(MonitorSysReceiver.checkApkExist(mContext,mAppContent.packageName , mAppContent.gameId)){
								PackageManager packageManager = mContext.getApplicationContext().getPackageManager();  
								if(packageManager!=null){
									Intent intent=new Intent();   
									intent =packageManager.getLaunchIntentForPackage(mAppContent.packageName);   
									if(intent!=null){  
										mContext.startActivity(intent); 
									}  
								}
							}else{

								// 下载完成后弹出安装窗
								File file = new File(DownLoadManager.getManager().deFaultDir+ infos.getFileName());
//								Intent intentInstall = new Intent();
//								intentInstall.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//								intentInstall.setAction(Intent.ACTION_VIEW);
//								intentInstall.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//								mContext.startActivity(intentInstall);
                                FileUtil.apkInstall(file,mContext);
								
								DownloadInfo infoss = infos.getDownLoadTask().getDownloadInfo();
								DownLoadModel mDown=new DownLoadModel();
								mDown.setmAppContent(mAppContent);
								mDown.setInfo(infoss);
								EventBus.getDefault().post(mDown);
								return;
							}
							DownloadInfo infoss = infos.getDownLoadTask().getDownloadInfo();
							DownLoadModel mDown=new DownLoadModel();
							mDown.setmAppContent(mAppContent);
							mDown.setInfo(infoss);
							EventBus.getDefault().post(mDown);
							return;
						}
					}
				});
				
				if (info.getState() == DownLoadManager.STATE_WAITING) {
					tv_download.setText("下载");
					if(MonitorSysReceiver.checkApkExist(mContext,mAppContent.packageName , mAppContent.gameId)){
						tv_download.setText("打开");
						info.setState(DownLoadManager.AlreadyInstalled);
						setByStatus(DownLoadManager.AlreadyInstalled);
					}
				}
				if (info.getState() == DownLoadManager.STATE_PAUSE) {
					tv_download.setText("继续");
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
		 * @param 
		 * @param status
		 */
		private void setByStatus( int status) {

			switch (status) {
			case DownLoadManager.STATE_DOWNLOADING:

				tv_item.setVisibility(View.GONE);
				item_mLinearMsg.setVisibility(View.GONE);
				pb_update_progress.setVisibility(View.VISIBLE);
				item_mLinearMsg1.setVisibility(View.VISIBLE);
				break;
			case DownLoadManager.STATE_ERROR:
				tv_item.setVisibility(View.VISIBLE);
				item_mLinearMsg.setVisibility(View.VISIBLE);
				pb_update_progress.setVisibility(View.GONE);
				item_mLinearMsg1.setVisibility(View.GONE);
				break;
			case DownLoadManager.STATE_WAITING:
				tv_item.setVisibility(View.VISIBLE);
				item_mLinearMsg.setVisibility(View.VISIBLE);
				pb_update_progress.setVisibility(View.GONE);
				item_mLinearMsg1.setVisibility(View.GONE);
				break;
			case DownLoadManager.STATE_PAUSE:
				tv_item.setVisibility(View.VISIBLE);
				item_mLinearMsg.setVisibility(View.VISIBLE);
				pb_update_progress.setVisibility(View.GONE);
				item_mLinearMsg1.setVisibility(View.GONE);
				break;
			case DownLoadManager.STATE_FINISH:
				tv_item.setVisibility(View.GONE);
				item_mLinearMsg.setVisibility(View.GONE);
				pb_update_progress.setVisibility(View.VISIBLE);
				item_mLinearMsg1.setVisibility(View.VISIBLE);
				break;
			case DownLoadManager.AlreadyInstalled:
				tv_item.setVisibility(View.VISIBLE);
				item_mLinearMsg.setVisibility(View.VISIBLE);
				pb_update_progress.setVisibility(View.GONE);
				item_mLinearMsg1.setVisibility(View.GONE);
				break;
			default:
				break;
			}
		}


		/*	*//**
		 * 根据状态设置图标
		 * @param downloadPercentView
		 * @param status
		 *//*
		private void setIconByStatus(TextView tv_download, AppContent.Status status) {

			switch (status) {
			case PENDING://0
				item_mLinearMsg.setVisibility(View.VISIBLE);
				pb_update_progress.setVisibility(View.GONE);
				tv_download.setText("下载");
				break;
			case DOWNLOADING:
				item_mLinearMsg.setVisibility(View.GONE);
				pb_update_progress.setVisibility(View.VISIBLE);
				tv_download.setText("暂停");
				break;
			case PAUSED:
				item_mLinearMsg.setVisibility(View.GONE);
				pb_update_progress.setVisibility(View.VISIBLE);
				tv_download.setText("继续 ");
				break;
			case FINISHED://100
				item_mLinearMsg.setVisibility(View.VISIBLE);
				pb_update_progress.setVisibility(View.GONE);
				tv_download.setText("安装");
				break;
			case AlreadyInstalled:
				item_mLinearMsg.setVisibility(View.VISIBLE);
				pb_update_progress.setVisibility(View.GONE);
				tv_download.setText("打开");
				break;
			default:
				break;
			}
		}*/
	}

	/**
	 * 局部刷新
	 * @param view
	 * @param itemIndex
	 *//*
	public void updateView(View view, AppContent itemIndex) {
		if(view == null) {
			return;
		}
		//从view中取得holder
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.iv_item = (XCRoundImageViewByXfermode) view.findViewById(R.id.iv_item);
		holder.tv_item = (TextView) view.findViewById(R.id.tv_item);
		holder.item_mLinear = (LinearLayout) view.findViewById(R.id.item_mLinear);
		holder.item_mLinearMsg = (LinearLayout) view.findViewById(R.id.item_mLinearMsg);
		holder.tv_type = (TextView) view.findViewById(R.id.tv_type);
		holder.tv_datasize = (TextView) view.findViewById(R.id.tv_datasize);
		holder.pb_update_progress = (ProgressBar) view.findViewById(R.id.pb_update_progress);
		holder.tv_download = (TextView) view.findViewById(R.id.tv_download);
		//holder.setData(mDates.get(itemIndex));
		holder.pb_update_progress.setProgress(itemIndex.downloadPercent);

		holder.setIconByStatus(holder.tv_download, itemIndex.status);
	}*/

	/*@Override
	public void onDownloadStateChanged(DownloadInfo info) {
		refreshHolder(info,2);

		Message msg=new Message();
		msg.what=1;
		msg.obj=info;
		handler.sendMessage(msg);
	}
	 */
	/*@Override
	public void onDownloadProgressed(DownloadInfo info) {
		refreshHolder(info,2);
		Message msg=new Message();
		msg.what=2;
		msg.obj=info;
		handler.sendMessage(msg);
	}
	private Handler handler  = new Handler(){
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what == 1){
				refreshHolder((DownloadInfo)msg.obj,1);
			}else if(msg.what == 2){
				refreshHolder((DownloadInfo)msg.obj,2);
			}
		}
	};
	private void refreshHolder(final DownloadInfo info,int tag) {
		List<ViewHolder> displayedHolders = getDisplayedHolders();
		for (int i = 0; i < displayedHolders.size(); i++) {
			final ViewHolder holder = displayedHolders.get(i);
			AppContent appInfo = holder.mData;
			if ((TextUtils.isEmpty(appInfo.gameId) ? 0: Long.parseLong(appInfo.gameId) )== info.getId()) {
				AppUtil.post(new Runnable() {
					@Override
					public void run() {
						holder.refreshState(info.getDownloadState(),info.getProgress());
						// Log.e("lbb", "------refreshState---"+info.getId());
					}
				});
				break;

			}
		}

	}*/
}
